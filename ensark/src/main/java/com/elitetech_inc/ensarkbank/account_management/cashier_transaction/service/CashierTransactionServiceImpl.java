package com.elitetech_inc.ensarkbank.account_management.cashier_transaction.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account.service.AccountService;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.CashierTransaction;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionMapper;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.repository.CashierTransactionRepository;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeaf;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeafStatusHistory;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.repository.ChequeLeafRepository;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.repository.ChequeLeafStatusHistoryRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.JoinHelper;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JoinHelperRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionService;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.email.TransactionEmailService;
import com.elitetech_inc.ensarkbank.common.enums.ChequeLeafStatus;
import com.elitetech_inc.ensarkbank.common.enums.HolderType;
import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.common.notification.websocket.WebSocketNotificationService;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.repository.EmployeeRepository;
import com.elitetech_inc.ensarkbank.util.BranchValidator;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashierTransactionServiceImpl implements CashierTransactionService {

    private final CashierTransactionRepository cashierTransactionRepository;
    private final CashierTransactionMapper cashierTransactionMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final BranchRepository branchRepository;
    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;
    private final JoinHelperRepository joinHelperRepository;
    private final ChequeLeafRepository chequeLeafRepository;
    private final ChequeLeafStatusHistoryRepository chequeLeafStatusHistoryRepository;
    private final WebSocketNotificationService webSocketNotificationService;
    private final TransactionEmailService transactionEmailService;
    private final RequestValidator requestValidator;
    private final BranchValidator branchValidator;
    private final Validator validator;

    @Override
    @Transactional
    public CashierTransactionResponse create(CashierTransactionRequest request) {
        validateRequest(request);
        validateBranch(request.getBranchId());
        validateAccount(request.getAccountNumber());
        validateEmployee(request.getEmployeeId());

        ChequeLeaf chequeLeaf = validateAndPrepareCheque(request);

        Account vaultAccount = accountService.getOrCreateVaultAccount(findBranch(request.getBranchId()));
        Transaction transaction = buildAndExecuteTransaction(request, vaultAccount);
        CashierTransaction cashierTransaction = buildAndPersistCashierTransaction(request, transaction);

        completeChequeIfPresent(chequeLeaf, transaction, cashierTransaction);
        persistJoinHelper(cashierTransaction, transaction);
        sendNotifications(cashierTransaction, request);

        return cashierTransactionMapper.toResponse(cashierTransaction);
    }

    @Override
    @Transactional
    public CashierTransactionResponse reverse(Long transactionId) {
        transactionService.reverseTransaction(transactionId);
        CashierTransaction ct = cashierTransactionRepository.findCashierTransactionByTransaction_id(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("CashierTransaction", transactionId));
        return cashierTransactionMapper.toResponse(ct);
    }

    private void validateRequest(CashierTransactionRequest request) {
        requestValidator.validateCashierTransaction(request);
        if (request.getTransactionRequest() == null) {
            throw new BadRequestException("Transaction request is required");
        }
    }

    private void validateBranch(Long branchId) {
        Branch branch = findBranch(branchId);
        branchValidator.assertNotAgentBank(branch.getId());
    }

    private void validateAccount(String accountNumber) {
        if (validator.checkAccountExists(accountNumber)) {
            validator.checkAccountStatus(accountNumber);
        }
    }

    private void validateEmployee(Long employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", employeeId));
    }

    private Branch findBranch(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", branchId));
    }

    private ChequeLeaf validateAndPrepareCheque(CashierTransactionRequest request) {
        if (request.getCheckNo() == null || request.getCheckNo().isEmpty()) {
            return null;
        }

        ChequeLeaf chequeLeaf = chequeLeafRepository.findByChequeNumber(request.getCheckNo())
                .orElseThrow(() -> new ResourceNotFoundException("ChequeLeaf", "checkNo", request.getCheckNo()));

        if (!chequeLeaf.getChequeBook().getAccount().getAccountNumber().equals(request.getAccountNumber())) {
            throw new BadRequestException("Cheque leaf #" + chequeLeaf.getChequeNumber() + " does not belong to account " + request.getAccountNumber());
        }

        if (chequeLeaf.getStatus() != ChequeLeafStatus.ISSUED && chequeLeaf.getStatus() != ChequeLeafStatus.PRESENTED) {
            throw new BadRequestException("Cheque is in " + chequeLeaf.getStatus() + " status. Only ISSUED or PRESENTED cheques are accepted.");
        }

        if (chequeLeaf.getExpiryDate() != null && chequeLeaf.getExpiryDate().isBefore(LocalDate.now())) {
            markChequeExpired(chequeLeaf);
            throw new BadRequestException("Cheque has expired on " + chequeLeaf.getExpiryDate());
        }

        if (chequeLeaf.getAmount() == null || chequeLeaf.getAmount().compareTo(request.getTransactionRequest().getAmount()) != 0) {
            throw new BadRequestException("Cheque amount (" + chequeLeaf.getAmount()
                    + ") does not match transaction amount (" + request.getTransactionRequest().getAmount() + ")");
        }

        ChequeLeafStatus oldStatus = chequeLeaf.getStatus();
        transitionChequeStatus(chequeLeaf, oldStatus, ChequeLeafStatus.PRESENTED, "Presented for cashier transaction");
        return chequeLeaf;
    }

    private void markChequeExpired(ChequeLeaf chequeLeaf) {
        ChequeLeafStatus previousStatus = chequeLeaf.getStatus();
        chequeLeaf.setStatus(ChequeLeafStatus.EXPIRED);
        chequeLeafRepository.save(chequeLeaf);
        recordLeafStatusChange(chequeLeaf, previousStatus, ChequeLeafStatus.EXPIRED, "Cheque expired on " + chequeLeaf.getExpiryDate());
    }

    private void completeChequeIfPresent(ChequeLeaf chequeLeaf, Transaction transaction, CashierTransaction cashierTransaction) {
        if (chequeLeaf == null) {
            return;
        }
        chequeLeaf.setStatus(ChequeLeafStatus.CLEARED);
        chequeLeaf.setClearanceDate(LocalDate.now());
        chequeLeaf.setTransactionReference(transaction.getId().toString());
        chequeLeafRepository.save(chequeLeaf);
        recordLeafStatusChange(chequeLeaf, ChequeLeafStatus.PRESENTED, ChequeLeafStatus.CLEARED,
                "Cleared via cashier transaction #" + cashierTransaction.getId());
    }

    private Transaction buildAndExecuteTransaction(CashierTransactionRequest request, Account vaultAccount) {
        Transaction transaction = transactionMapper.toTransaction(request.getTransactionRequest());
        transaction.setTransactionType(request.getType());
        transaction.setChannel(TransactionChannel.BRANCH);
        transaction.setStatus(TransactionStatus.SUCCESS);

        String sourceAccount = request.getType() == TransactionType.DEPOSIT
                ? vaultAccount.getAccountNumber()
                : request.getAccountNumber();
        String targetAccount = request.getType() == TransactionType.DEPOSIT
                ? request.getAccountNumber()
                : vaultAccount.getAccountNumber();

        transactionService.createTransaction(request.getTransactionRequest(), transaction, sourceAccount, targetAccount);
        return transaction;
    }

    private CashierTransaction buildAndPersistCashierTransaction(CashierTransactionRequest request, Transaction transaction) {
        CashierTransaction cashierTransaction = new CashierTransaction();
        cashierTransaction.setCheckNo(request.getCheckNo());
        cashierTransaction.setBranch(findBranch(request.getBranchId()));
        cashierTransaction.setAccountNumber(request.getAccountNumber());
        cashierTransaction.setAccountName(request.getAccountName());
        cashierTransaction.setBankName(request.getBankName());
        cashierTransaction.setRoutingNumber(request.getRoutingNumber());
        cashierTransaction.setEmployee(employeeRepository.getReferenceById(request.getEmployeeId()));
        cashierTransaction.setTransaction(transaction);
        return cashierTransactionRepository.save(cashierTransaction);
    }

    private void transitionChequeStatus(ChequeLeaf leaf, ChequeLeafStatus from, ChequeLeafStatus to, String reason) {
        leaf.setStatus(to);
        chequeLeafRepository.save(leaf);
        recordLeafStatusChange(leaf, from, to, reason);
    }

    private void recordLeafStatusChange(ChequeLeaf leaf, ChequeLeafStatus from, ChequeLeafStatus to, String reason) {
        ChequeLeafStatusHistory history = new ChequeLeafStatusHistory();
        history.setChequeLeaf(leaf);
        history.setFromStatus(from);
        history.setToStatus(to);
        history.setReason(reason);
        chequeLeafStatusHistoryRepository.save(history);
    }

    private void persistJoinHelper(CashierTransaction cashierTransaction, Transaction transaction) {
        JoinHelper joinHelper = new JoinHelper();
        joinHelper.setCashierTransaction(cashierTransaction);
        joinHelper.setTransaction(transaction);
        joinHelperRepository.save(joinHelper);
    }

    private void sendNotifications(CashierTransaction saved, CashierTransactionRequest request) {
        try {
            Account customerAccount = accountRepository.findAccountByAccountNumber(request.getAccountNumber()).orElse(null);
            if (customerAccount == null || customerAccount.getHolders() == null || customerAccount.getHolders().isEmpty()) {
                return;
            }

            Customer customer = resolvePrimaryHolder(customerAccount);
            if (customer == null || customer.getUser() == null) {
                return;
            }

            sendWebSocketNotification(saved, request, customer);
            sendEmailNotification(customer, request);
        } catch (Exception e) {
            log.error("Failed to send cashier transaction notifications: {}", e.getMessage());
        }
    }

    private Customer resolvePrimaryHolder(Account account) {
        AccountHolder primaryHolder = account.getHolders().stream()
                .filter(h -> h.getHolderType() == HolderType.PRIMARY)
                .findFirst()
                .orElse(account.getHolders().getFirst());
        return primaryHolder.getCustomer();
    }

    private void sendWebSocketNotification(CashierTransaction saved, CashierTransactionRequest request, Customer customer) {
        String amount = request.getTransactionRequest().getAmount().toPlainString();
        String type = request.getType().name();

        String title = type + " of $" + amount + " Successful";
        String message = "Your " + type.toLowerCase() + " transaction of $" + amount
                + " to account " + request.getAccountNumber()
                + " has been processed successfully. Branch: " + saved.getBranch().getName();

        NotificationType notificationType = type.equals("DEPOSIT") ? NotificationType.DEPOSIT : NotificationType.WITHDRAW;
        webSocketNotificationService.sendNotificationToUser(
                customer.getUser().getId(), notificationType, title, message,
                saved.getId().toString(), "CASHIER_TRANSACTION");
    }

    private void sendEmailNotification(Customer customer, CashierTransactionRequest request) {
        String amount = request.getTransactionRequest().getAmount().toPlainString();
        String type = request.getType().name();
        transactionEmailService.sendCashierTransactionEmail(
                customer.getUser().getEmail(), customer.getName(), type, amount, request.getAccountNumber());
    }
}
