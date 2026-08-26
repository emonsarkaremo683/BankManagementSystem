package com.elitetech_inc.ensarkbank.account_management.account.service;

import com.elitetech_inc.ensarkbank.account_management.account.dto.mapper.AccountMapper;
import com.elitetech_inc.ensarkbank.account_management.account.dto.request.AccountRequest;
import com.elitetech_inc.ensarkbank.account_management.account.dto.response.AccountResponse;
import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.mapper.AccountHolderMapper;
import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.repository.AccountTransactionRepository;
import com.elitetech_inc.ensarkbank.account_management.hold_transaction.service.HoldTransactionService;
import com.elitetech_inc.ensarkbank.account_management.nominee.entity.Nominee;
import com.elitetech_inc.ensarkbank.account_management.nominee.repository.NomineeRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionService;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.accounting_system.ledger.service.AccountingRuleEngine;
import com.elitetech_inc.ensarkbank.common.enums.*;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.util.AccountNumberGenerator;
import com.elitetech_inc.ensarkbank.util.EmailUtil;
import com.elitetech_inc.ensarkbank.util.NotificationUtil;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Utils;
import com.elitetech_inc.ensarkbank.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountHolderMapper accountHolderMapper;
    private final NomineeRepository nomineeRepository;
    private final BranchRepository branchRepository;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final HoldTransactionService holdTransactionService;
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final RequestValidator requestValidator;
    private final Validator validator;
    private final Utils utils;
    private final NotificationUtil notificationUtil;
    private final EmailUtil emailUtil;
    private final AccountingRuleEngine accountingRuleEngine;

    @Override
    @Transactional
    public AccountResponse create(AccountRequest request, List<MultipartFile> signatures, Map<String, MultipartFile> nominees) {
        requestValidator.validateAccount(request);

        List<AccountHolder> holders = request.getAccountHolders()
                .stream()
                .map(accountHolderMapper::toAccountHolder)
                .toList();

        validator.checkKycStatus(holders.stream().map(h -> h.getCustomer().getId()).findFirst().orElseThrow());

        Account account = accountMapper.toAccount(request);
        uploadSignatures(signatures, holders);
        account.addHolders(holders);

        Nominee nominee = accountMapper.toNominee(request);
        if (nominees != null && !nominees.isEmpty()) {
            for (Map.Entry<String, MultipartFile> entry : nominees.entrySet()) {
                String path = utils.uploadFile(entry.getValue(), "nominee", holders.getFirst().getCustomer().getName());
                switch (entry.getKey()) {
                    case "nid_front" -> nominee.setNid_front("nominee/" + path);
                    case "nid_back" -> nominee.setNid_back("nominee/" + path);
                    case "photo" -> nominee.setPhoto("nominee/" + path);
                }
            }
        }

        account.setAccountStatus(AccountStatus.PENDING);
        Account saved = accountRepository.save(account);

        nominee.setAccount(saved);
        nomineeRepository.save(nominee);

        notifyAuthorities(saved, holders);
        return accountMapper.toAccountResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", id));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAll() {
        return accountRepository.findAll()
                .stream()
                .filter(a -> a.getAccountNumber().startsWith("acc"))
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllByBranchIds(List<Long> branchIds) {
        return branchIds.stream()
                .flatMap(branchId -> accountRepository.findAllByBranchId(branchId).stream())
                .distinct()
                .filter(a -> a.getAccountNumber().startsWith("acc"))
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findByCustomerEmail(String email) {
        return accountRepository.findByCustomerEmail(email)
                .stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllBranchVault() {
        return accountRepository.findAllBranchVaults()
                .stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findAccountByBranchId(Long branchId) {
        return accountRepository.findAllByBranchId(branchId)
                .stream()
                .filter(a -> a.getAccountNumber().startsWith("acc"))
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findBranchVaultByBranchId(Long branchId) {
        Account vault = accountRepository.findBranchVaultByBranchId(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch vault account", "branchId", String.valueOf(branchId)));
        return accountMapper.toAccountResponse(vault);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> search(String query) {
        return accountRepository.search(query)
                .stream()
                .filter(a -> a.getAccountNumber().startsWith("acc"))
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findByAccountNumber(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean accountNumberExists(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public AccountResponse updateByEmployee(Long id, AccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", id));
        requestValidator.validateAccount(request);

        accountMapper.updateAccountFromRequest(request, account);
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponse updateStatus(Long id, AccountStatus status) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", id));

        boolean wasPending = account.getAccountStatus() == AccountStatus.PENDING;
        account.setAccountStatus(status);

        if (status == AccountStatus.ACTIVE && wasPending) {
            handleInitialDeposit(account);
        }

        Account updated = accountRepository.save(account);
        notifyAccountHolder(account, status);
        return accountMapper.toAccountResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber));
        return account.getAvailableBalance();
    }

    @Override
    @Transactional
    public Account getOrCreateVaultAccount(Branch branch) {
        boolean isHeadOffice = branch.getType() == BranchType.HEAD_OFFICE;
        String prefix = isHeadOffice ? "head-" : "br-";
        String vaultAccountNumber = prefix + branch.getRoutingNumber();

        return accountRepository.findAccountByAccountNumber(vaultAccountNumber)
                .orElseGet(() -> {
                    log.info("Auto-creating vault account {} for branch {}", vaultAccountNumber, branch.getId());
                    Account vault = new Account();
                    vault.setBranch(branch);
                    vault.setAccountStatus(AccountStatus.ACTIVE);
                    vault.setCategory(AccountCategory.ASSET);
                    vault.setAccountType(isHeadOffice ? AccountType.INTER_BANK_VAULT : AccountType.BRANCH_VAULT);
                    vault.setAccountNumber(vaultAccountNumber);
                    BigDecimal initialBalance = isHeadOffice
                            ? BigDecimal.valueOf(10000000.00)
                            : BigDecimal.valueOf(5000000.00);
                    vault.setAvailableBalance(initialBalance);
                    vault.setCurrentBalance(initialBalance);
                    vault.setHoldBalance(BigDecimal.ZERO);

                    AccountHolder holder = new AccountHolder();
                    holder.setCanWithdraw(true);
                    holder.setCanDeposit(true);
                    holder.setCanApproveTransaction(true);
                    holder.setHolderType(HolderType.INTER_BRANCH_SETTLEMENT);

                    vault.setHolders(new ArrayList<>());
                    vault.getHolders().add(holder);
                    return accountRepository.save(vault);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Account getOrCreateLoanControlAccount(Branch branch) {
        String loanAccountNumber = "loan-" + branch.getRoutingNumber();

        return accountRepository.findAccountByAccountNumber(loanAccountNumber)
                .orElseGet(() -> {
                    log.info("Auto-creating loan control account {} for branch {}", loanAccountNumber, branch.getId());
                    Account loanAccount = new Account();
                    loanAccount.setBranch(branch);
                    loanAccount.setAccountStatus(AccountStatus.ACTIVE);
                    loanAccount.setAccountType(AccountType.LOAN_VAULT);
                    loanAccount.setCategory(AccountCategory.ASSET);
                    loanAccount.setAccountNumber(loanAccountNumber);
                    loanAccount.setAvailableBalance(BigDecimal.ZERO);
                    loanAccount.setCurrentBalance(BigDecimal.ZERO);
                    loanAccount.setHoldBalance(BigDecimal.ZERO);

                    AccountHolder holder = new AccountHolder();
                    holder.setCanWithdraw(true);
                    holder.setCanDeposit(true);
                    holder.setCanApproveTransaction(true);
                    holder.setHolderType(HolderType.INTER_BRANCH_SETTLEMENT);

                    loanAccount.setHolders(new ArrayList<>());
                    loanAccount.getHolders().add(holder);
                    return accountRepository.save(loanAccount);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> resolveBranchAndChildIds(Long branchId) {
        List<Long> result = new ArrayList<>();
        result.add(branchId);
        collectChildBranchIds(branchId, result);
        return result;
    }

    @Override
    @Transactional
    public AccountResponse closeAccount(Long id, Long sweepToAccountId) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", id));

        if (account.getAccountStatus() == AccountStatus.CLOSED) {
            throw new BadRequestException("Account is already closed");
        }

        BigDecimal balance = account.getAvailableBalance() != null ? account.getAvailableBalance() : BigDecimal.ZERO;
        BigDecimal holdBalance = account.getHoldBalance() != null ? account.getHoldBalance() : BigDecimal.ZERO;

        if (holdBalance.compareTo(BigDecimal.ZERO) > 0) {
            throw new BadRequestException("Cannot close account with active holds. Release all holds first.");
        }

        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            if (sweepToAccountId == null) {
                throw new BadRequestException("Account has a balance of " + balance + ". Provide a sweep-to account.");
            }

            Account sweepTarget = accountRepository.findById(sweepToAccountId)
                    .orElseThrow(() -> new ResourceNotFoundException("Sweep-to account", sweepToAccountId));

            TransactionRequest sweepRequest = new TransactionRequest();
            sweepRequest.setAmount(balance);
            sweepRequest.setRemarks("Balance sweep before account closure - Account " + account.getAccountNumber());
            Transaction sweepTransaction = transactionMapper.toTransaction(sweepRequest);
            sweepTransaction.setTransactionType(TransactionType.TRANSFER);
            sweepTransaction.setChannel(TransactionChannel.INTERNET_BANKING);

            transactionService.createTransaction(sweepRequest, sweepTransaction,
                    account.getAccountNumber(), sweepTarget.getAccountNumber());

            account.setAvailableBalance(BigDecimal.ZERO);
            account.setCurrentBalance(BigDecimal.ZERO);
        }

        account.setAccountStatus(AccountStatus.CLOSED);
        Account closed = accountRepository.save(account);
        notifyAccountHolder(account, AccountStatus.CLOSED);
        return accountMapper.toAccountResponse(closed);
    }

    private void handleInitialDeposit(Account account) {
        BigDecimal depositAmount = account.getHoldBalance();
        if (depositAmount != null && depositAmount.compareTo(BigDecimal.ZERO) > 0) {
            Branch branch = account.getBranch();
            if (branch != null) {
                Account vaultAccount = getOrCreateVaultAccount(branch);
                TransactionRequest depositRequest = new TransactionRequest();
                depositRequest.setAmount(depositAmount);
                depositRequest.setRemarks("Initial deposit for account " + account.getAccountNumber());
                Transaction depositTransaction = transactionMapper.toTransaction(depositRequest);
                depositTransaction.setTransactionType(TransactionType.DEPOSIT);
                depositTransaction.setChannel(TransactionChannel.BRANCH);
                transactionService.createTransaction(depositRequest, depositTransaction, vaultAccount.getAccountNumber(), account.getAccountNumber());

                AccountTransaction accountTransaction = new AccountTransaction();
                accountTransaction.setAccount(vaultAccount);
                accountTransaction.setTransaction(depositTransaction);
                accountTransaction.setReceiver(account);
                accountTransaction.setReceiverAccountNumber(account.getAccountNumber());
                accountTransaction.setReceiverName(account.getHolders().stream()
                        .findFirst().map(AccountHolder::getCustomer).map(c -> c.getName())
                        .orElse("Customer"));
                accountTransaction.setBankName("Ensark Bank");
                accountTransaction.setRoutingNumber(branch.getRoutingNumber());
                accountTransactionRepository.save(accountTransaction);
            }
        }

        BigDecimal activeHoldBalance = holdTransactionService.getActiveHoldBalance(account.getId());
        account.setHoldBalance(activeHoldBalance);
        Account refreshed = accountRepository.findById(account.getId()).orElse(account);
        account.setAvailableBalance(refreshed.getAvailableBalance());
        account.setCurrentBalance(refreshed.getAvailableBalance().add(activeHoldBalance));
    }

    private void collectChildBranchIds(Long parentId, List<Long> result) {
        List<Branch> children = branchRepository.findByParentBranch_Id(parentId);
        for (Branch child : children) {
            result.add(child.getId());
            collectChildBranchIds(child.getId(), result);
        }
    }

    private void notifyAuthorities(Account saved, List<AccountHolder> holders) {
        String customerName = holders.stream()
                .findFirst()
                .map(h -> h.getCustomer().getName())
                .orElse("Unknown");
        notificationUtil.notifyAuthorities(
                NotificationType.ACCOUNT_CREATED,
                "New Account Application",
                "Customer " + customerName + " has applied for a new account. Status: PENDING.",
                String.valueOf(saved.getId()),
                "ACCOUNT"
        );
    }

    private void notifyAccountHolder(Account acc, AccountStatus status) {
        if (acc.getHolders() == null || acc.getHolders().isEmpty()) return;

        acc.getHolders().stream()
                .filter(h -> h.getCustomer() != null && h.getCustomer().getUser() != null)
                .findFirst()
                .ifPresent(holder -> {
                    var user = holder.getCustomer().getUser();
                    var customer = holder.getCustomer();
                    NotificationType notifType = status == AccountStatus.ACTIVE
                            ? NotificationType.ACCOUNT_CREATED : NotificationType.ACCOUNT_SUSPENDED;
                    String title = "Account " + status.name();
                    String message = "Your account " + acc.getAccountNumber() + " has been " + status.name().toLowerCase() + ".";
                    notificationUtil.notifyUser(user.getId(), notifType, title, message, String.valueOf(acc.getId()), "ACCOUNT");
                    emailUtil.sendAccountStatusEmail(user.getEmail(), customer.getName(), acc.getAccountNumber(), status.name());
                });
    }

    private void uploadSignatures(List<MultipartFile> signatures, List<AccountHolder> holders) {
        if (signatures == null || signatures.isEmpty()) return;
        int limit = Math.min(signatures.size(), holders.size());
        for (int i = 0; i < limit; i++) {
            String path = utils.uploadFile(signatures.get(i), "signature", holders.get(i).getCustomer().getName());
            holders.get(i).setSignature(path);
        }
    }

    @Override
    @Transactional
    public Account getOrCreateFeeIncomeAccount(com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch branch) {
        String feeAccountNumber = "fee-" + branch.getRoutingNumber();

        return accountRepository.findAccountByAccountNumber(feeAccountNumber)
                .orElseGet(() -> {
                    log.info("Auto-creating fee income account {} for branch {}", feeAccountNumber, branch.getId());
                    Account feeAccount = new Account();
                    feeAccount.setBranch(branch);
                    feeAccount.setAccountStatus(AccountStatus.ACTIVE);
                    feeAccount.setAccountType(AccountType.BRANCH_VAULT);
                    feeAccount.setCategory(AccountCategory.INCOME);
                    feeAccount.setAccountNumber(feeAccountNumber);
                    feeAccount.setAvailableBalance(BigDecimal.ZERO);
                    feeAccount.setCurrentBalance(BigDecimal.ZERO);
                    feeAccount.setHoldBalance(BigDecimal.ZERO);

                    AccountHolder holder = new AccountHolder();
                    holder.setCanWithdraw(true);
                    holder.setCanDeposit(true);
                    holder.setCanApproveTransaction(true);
                    holder.setHolderType(HolderType.INTER_BRANCH_SETTLEMENT);

                    feeAccount.setHolders(new ArrayList<>());
                    feeAccount.getHolders().add(holder);
                    return accountRepository.save(feeAccount);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findByStatus(AccountStatus status) {
        return accountRepository.findAll().stream()
                .filter(a -> a.getAccountStatus() == status && a.getAccountNumber().startsWith("acc"))
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findByAccountType(com.elitetech_inc.ensarkbank.common.enums.AccountType type) {
        return accountRepository.findAll().stream()
                .filter(a -> a.getAccountType() == type && a.getAccountNumber().startsWith("acc"))
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void validateMinimumBalance(String accountNumber, BigDecimal debitAmount) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber));

        BigDecimal minBalance = getMinimumBalanceForType(account.getAccountType());

        // DEBIT on ASSET/EXPENSE = INCREASE, so balance goes UP — no sufficiency concern.
        // DEBIT on LIABILITY/EQUITY/INCOME = DECREASE, so balance goes DOWN — check it.
        BalanceEffect effect = accountingRuleEngine.resolve(EntryType.DEBIT, account.getCategory());
        BigDecimal remaining;
        if (effect == BalanceEffect.INCREASE) {
            remaining = account.getAvailableBalance().add(debitAmount);
        } else {
            remaining = account.getAvailableBalance().subtract(debitAmount);
        }

        if (remaining.compareTo(minBalance) < 0) {
            throw new BadRequestException("Transaction violates minimum balance requirement for account type " +
                    account.getAccountType() + ". Minimum required: " + minBalance + " BDT. Remaining if processed: " + remaining + " BDT");
        }
    }

    private BigDecimal getMinimumBalanceForType(AccountType type) {
        if (type == null) return BigDecimal.ZERO;
        return switch (type) {
            case SAVINGS -> new BigDecimal("500.00");
            case STUDENT -> new BigDecimal("100.00");
            case CURRENT -> new BigDecimal("1000.00");
            case BUSINESS -> new BigDecimal("5000.00");
            default -> BigDecimal.ZERO;
        };
    }
}
