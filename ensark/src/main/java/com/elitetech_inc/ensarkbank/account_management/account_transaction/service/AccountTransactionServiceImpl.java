package com.elitetech_inc.ensarkbank.account_management.account_transaction.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;
import com.elitetech_inc.ensarkbank.common.email.TransactionEmailService;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.JoinHelper;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JoinHelperRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JournalRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.HolderType;
import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.enums.OtpStatus;
import com.elitetech_inc.ensarkbank.common.notification.websocket.WebSocketNotificationService;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.Beneficiary;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.repository.BeneficiaryRepository;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.util.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.mapper.AccountTransactionMapper;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.AccountTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.BatchTransactionItem;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.BatchTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.OtpVerifyRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.AccountTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.BatchTransactionItemResult;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.BatchTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.OtpInitiateResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.TransactionOtp;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.repository.AccountTransactionRepository;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.repository.TransactionOtpRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionService;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import com.elitetech_inc.ensarkbank.util.RequestValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private static final int OTP_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 3;
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountTransactionMapper accountTransactionMapper;
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    private final BranchRepository branchRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final JoinHelperRepository joinHelperRepository;
    private final JournalRepository journalRepository;
    private final RequestValidator requestValidator;
    private final Validator validator;
    private final TransactionOtpRepository transactionOtpRepository;
    private final TransactionEmailService transactionEmailService;
    private final WebSocketNotificationService webSocketNotificationService;



    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private jakarta.persistence.EntityManager entityManager;

    private AccountTransactionService getSelf() {
        return applicationContext.getBean(AccountTransactionService.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountTransactionResponse save(AccountTransactionRequest atr) {
        requestValidator.validateAccountTransaction(atr);
        if (atr == null || atr.getRequest() == null) {
            throw new IllegalArgumentException("Account transaction request is required");
        }

        Account sender = accountRepository.findById(atr.getSenderAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        validator.checkAccountStatus(sender.getAccountNumber());

        BigDecimal transferAmount = atr.getRequest().getAmount();
        if (sender.getAvailableBalance().compareTo(transferAmount) < 0) {
            throw new IllegalArgumentException("Insufficient balance. Available: " + sender.getAvailableBalance());
        }

        Account receiver = null;
        if(atr.getBeneficiaryId() != null){
            Beneficiary bt = beneficiaryRepository.findById(atr.getBeneficiaryId()).orElseThrow(() -> new IllegalArgumentException("Beneficiary not found"));
            if(checkAccountNumber(bt.getAccNumber())){
                receiver = accountRepository.findAccountByAccountNumber(bt.getAccNumber()).orElseThrow(() -> new IllegalArgumentException("Account number not found"));
            }
        }

        if(atr.getReceiverAccountId() != null){
            receiver = accountRepository.findById(atr.getReceiverAccountId()).orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));
        }

        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setChannel(TransactionChannel.INTERNET_BANKING);
        transaction.setAmount(atr.getRequest().getAmount());
        transaction.setRemarks(atr.getRequest().getRemarks());
        transaction.setChargeAmount(BigDecimal.ZERO);
        transaction.setVatAmount(BigDecimal.ZERO);

        transactionService.createTransaction(atr.getRequest(), transaction, sender.getAccountNumber(),
                receiver != null ? receiver.getAccountNumber() : atr.getReceiverAccountNumber()
                );

        entityManager.flush();

        AccountTransaction accountTransaction = new AccountTransaction();

        accountTransaction.setAccount(sender);
        accountTransaction.setTransaction(transaction);
        accountTransaction.setReceiverAccountNumber(receiver != null ? receiver.getAccountNumber() : atr.getReceiverAccountNumber());
        String receiverName = "Customer";
        if (receiver != null && receiver.getHolders() != null && !receiver.getHolders().isEmpty()) {
            receiverName = receiver.getHolders().getFirst().getCustomer().getName();
        } else if (atr.getReceiverName() != null) {
            receiverName = atr.getReceiverName();
        }
        accountTransaction.setReceiverName(receiverName);
        accountTransaction.setBankName(receiver != null ? "Ensark Bank" : atr.getBankName());
        accountTransaction.setRoutingNumber(receiver != null ? receiver.getBranch().getRoutingNumber() : atr.getRoutingNumber());

        JoinHelper jh = new JoinHelper();
        jh.setAccountTransaction(accountTransaction);
        jh.setTransaction(transaction);
        joinHelperRepository.save(jh);

        AccountTransactionResponse savedResponse = accountTransactionMapper.toResponse(accountTransactionRepository.save(accountTransaction), sender.getAccountNumber());

        sendTransferNotification(savedResponse, sender.getAccountNumber());
        if (receiver != null) {
            sendDepositNotification(savedResponse, receiver.getAccountNumber());
        }

        return savedResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountTransactionResponse> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return accountTransactionRepository.findById(id)
                .map(at -> accountTransactionMapper.toResponse(at, at.getAccount().getAccountNumber()));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountTransactionResponse> findByReferenceNo(String referenceNo) {
        if (referenceNo == null || referenceNo.isBlank()) {
            return Optional.empty();
        }
        Optional<Transaction> txnOpt = transactionService.findByTransactionId(referenceNo);
        if (txnOpt.isEmpty()) {
            return Optional.empty();
        }
        Transaction t = txnOpt.get();
        JoinHelper jh = joinHelperRepository.findJoinHelperByTransaction_Id(t.getId());
        if (jh != null && jh.getAccountTransaction() != null) {
            AccountTransaction at = jh.getAccountTransaction();
            String accNum = at.getAccount() != null ? at.getAccount().getAccountNumber() : null;
            return Optional.of(accountTransactionMapper.toResponse(at, accNum));
        }
        return Optional.of(mapTransactionOnly(t));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountTransactionResponse> findByAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return Optional.empty();
        }
        return accountTransactionRepository.findByAccountTransactionByAccountNumber(accountNumber)
                .stream()
                .findFirst()
                .map(at -> accountTransactionMapper.toResponse(at, accountNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTransactionResponse> findAllByAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return List.of();
        }
        List<AccountTransaction> sent = accountTransactionRepository.findByAccountTransactionByAccountNumber(accountNumber);
        List<AccountTransaction> received = accountTransactionRepository.findByReceiverAccountNumber(accountNumber);

        List<AccountTransaction> all = new ArrayList<>();
        all.addAll(sent);
        all.addAll(received);

        return all.stream()
                .map(at -> accountTransactionMapper.toResponse(at, accountNumber))
                .sorted(Comparator.comparing((AccountTransactionResponse r) -> r.getResponse().getCreatedAt()).reversed())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTransactionResponse> findAll() {
        List<JoinHelper> helpers = joinHelperRepository.findAllWithTransactions();
        return helpers.stream()
                .filter(jh -> jh.getTransaction() != null)
                .map(jh -> {
                    AccountTransaction at = jh.getAccountTransaction();
                    if (at != null) {
                        String accNum = at.getAccount() != null ? at.getAccount().getAccountNumber() : null;
                        return accountTransactionMapper.toResponse(at, accNum);
                    }
                    return mapTransactionOnly(jh.getTransaction());
                })
                .toList();
    }

    private AccountTransactionResponse mapTransactionOnly(Transaction t) {
        List<Journal> journals = journalRepository.findJournalByTransaction_Id(t.getId());

        String senderAccountNumber = "";
        String senderName = "";
        String receiverAccountNumber = "";
        String receiverName = "";

        for (Journal j : journals) {
            if (j.getEntryType() == com.elitetech_inc.ensarkbank.common.enums.EntryType.DEBIT) {
                senderAccountNumber = j.getAccountNumber() != null ? j.getAccountNumber() : "";
                if (j.getAccount() != null && j.getAccount().getHolders() != null && !j.getAccount().getHolders().isEmpty()) {
                    var holder = j.getAccount().getHolders().stream()
                            .filter(h -> h.getHolderType() == com.elitetech_inc.ensarkbank.common.enums.HolderType.PRIMARY)
                            .findFirst()
                            .orElse(j.getAccount().getHolders().getFirst());
                    senderName = holder.getCustomer() != null ? holder.getCustomer().getName() : "";
                }
            } else if (j.getEntryType() == com.elitetech_inc.ensarkbank.common.enums.EntryType.CREDIT) {
                receiverAccountNumber = j.getAccountNumber() != null ? j.getAccountNumber() : "";
                if (j.getAccount() != null && j.getAccount().getHolders() != null && !j.getAccount().getHolders().isEmpty()) {
                    var holder = j.getAccount().getHolders().stream()
                            .filter(h -> h.getHolderType() == com.elitetech_inc.ensarkbank.common.enums.HolderType.PRIMARY)
                            .findFirst()
                            .orElse(j.getAccount().getHolders().getFirst());
                    receiverName = holder.getCustomer() != null ? holder.getCustomer().getName() : "";
                }
            }
        }

        return AccountTransactionResponse.builder()
                .id(null)
                .transactionId(t.getTransactionId())
                .senderAccountNumber(senderAccountNumber)
                .senderName(senderName)
                .receiverAccountNumber(receiverAccountNumber)
                .receiverName(receiverName)
                .bankName("Ensark Bank")
                .direction("")
                .response(transactionMapper.toResponse(t))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTransactionResponse> findByBranchIds(List<Long> branchIds) {
        return accountTransactionRepository.findByBranchIds(branchIds)
                .stream()
                .map(at -> accountTransactionMapper.toResponse(at, null))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTransactionResponse> findByAccountId(Long accountId) {
        if (accountId == null) {
            return List.of();
        }
        Account account = accountRepository.findById(accountId).orElse(null);
        String accountNumber = account != null ? account.getAccountNumber() : null;
        return accountTransactionRepository.findByAccountId(accountId)
                .stream()
                .map(at -> accountTransactionMapper.toResponse(at, accountNumber))
                .toList();
    }

    @Override
    @Transactional
    public OtpInitiateResponse initiateOnlineTransaction(AccountTransactionRequest atr) {
        requestValidator.validateAccountTransaction(atr);
        if (atr == null || atr.getRequest() == null) {
            throw new IllegalArgumentException("Account transaction request is required");
        }

        Account sender = accountRepository.findById(atr.getSenderAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        validator.checkAccountStatus(sender.getAccountNumber());

        BigDecimal amount = atr.getRequest().getAmount();
        if (sender.getAvailableBalance() == null || sender.getAvailableBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        Optional<TransactionOtp> activeOtp = transactionOtpRepository.findActivePendingOtp(sender.getAccountNumber());
        if (activeOtp.isPresent()) {
            throw new IllegalStateException("A verification code is already active for this account. Please wait for it to expire or use the existing code.");
        }

        String customerEmail = resolveCustomerEmail(sender);
        if (customerEmail == null || customerEmail.isBlank()) {
            throw new IllegalArgumentException("No registered email found for this account");
        }

        String otpCode = generateOtpCode();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(OTP_EXPIRY_MINUTES);

        String payload;
        try {
            payload = OBJECT_MAPPER.writeValueAsString(atr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize transaction request", e);
        }

        TransactionOtp transactionOtp = new TransactionOtp();
        transactionOtp.setOtpCode(otpCode);
        transactionOtp.setCustomerEmail(customerEmail);
        transactionOtp.setAccountNumber(sender.getAccountNumber());
        transactionOtp.setPendingTransactionPayload(payload);
        transactionOtp.setStatus(OtpStatus.PENDING);
        transactionOtp.setAttemptCount(0);
        transactionOtp.setExpiresAt(expiresAt);

        transactionOtpRepository.save(transactionOtp);



        transactionEmailService.sendOtpEmail(customerEmail, otpCode, amount, TransactionType.TRANSFER.toString());

        return OtpInitiateResponse.builder()
                .otpReferenceId(transactionOtp.getId())
                .maskedEmail(maskEmail(customerEmail))
                .expiresAt(expiresAt)
                .build();
    }

    @Override
    @Transactional
    public AccountTransactionResponse verifyOnlineTransaction(OtpVerifyRequest req) {
        if (req.getOtpReferenceId() == null || req.getOtpCode() == null) {
            throw new IllegalArgumentException("OTP reference ID and code are required");
        }

        TransactionOtp transactionOtp = transactionOtpRepository.findById(req.getOtpReferenceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired verification code"));

        if (transactionOtp.getStatus() != OtpStatus.PENDING) {
            throw new IllegalArgumentException("Invalid or expired verification code");
        }

        if (transactionOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            transactionOtp.setStatus(OtpStatus.EXPIRED);
            transactionOtpRepository.save(transactionOtp);
            throw new IllegalArgumentException("Invalid or expired verification code");
        }

        if (transactionOtp.getAttemptCount() >= MAX_ATTEMPTS) {
            transactionOtp.setStatus(OtpStatus.FAILED);
            transactionOtpRepository.save(transactionOtp);
            throw new IllegalArgumentException("Invalid or expired verification code");
        }

        if (!transactionOtp.getOtpCode().equals(req.getOtpCode())) {
            int newCount = transactionOtp.getAttemptCount() + 1;
            transactionOtp.setAttemptCount(newCount);
            if (newCount >= MAX_ATTEMPTS) {
                transactionOtp.setStatus(OtpStatus.FAILED);
            }
            transactionOtpRepository.save(transactionOtp);
            int remaining = MAX_ATTEMPTS - newCount;
            throw new IllegalArgumentException("Incorrect verification code. " + remaining + " attempt(s) remaining.");
        }

        getSelf().markOtpVerified(transactionOtp.getId());

        AccountTransactionRequest atr;
        try {
            atr = OBJECT_MAPPER.readValue(transactionOtp.getPendingTransactionPayload(), AccountTransactionRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize pending transaction payload", e);
        }

        AccountTransactionResponse response;
        try {
            response = getSelf().save(atr);
        } catch (Exception e) {
            log.error("Transaction execution failed after OTP verification for account: {}", transactionOtp.getAccountNumber(), e);
            throw e;
        }

        String email = transactionOtp.getCustomerEmail();
        transactionEmailService.sendTransactionSuccessEmail(email, response);

        sendTransferNotification(response, transactionOtp.getAccountNumber());

        return response;
    }

    @Override
    @Transactional
    public void markOtpVerified(Long otpId) {
        TransactionOtp otp = transactionOtpRepository.findById(otpId)
                .orElseThrow(() -> new IllegalArgumentException("OTP record not found"));
        otp.setStatus(OtpStatus.VERIFIED);
        transactionOtpRepository.save(otp);
    }

    @Override
    public AccountTransactionResponse reverseTransaction(Long transactionId) {
        transactionService.reverseTransaction(transactionId);
        AccountTransaction at = accountTransactionRepository.findAccountTransactionByTransactionId(transactionId).orElseThrow();

        return accountTransactionMapper.toResponse(at, at.getAccount().getAccountNumber());
    }

    @Override
    @Transactional
    public AccountTransactionResponse reverseTransactionByReferenceNo(String referenceNo) {
        Optional<Transaction> txnOpt = transactionService.findByTransactionId(referenceNo);
        if (txnOpt.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found: " + referenceNo);
        }
        Transaction txn = txnOpt.get();
        return reverseTransaction(txn.getId());
    }


    private String resolveCustomerEmail(Account account) {
        if (account.getHolders() == null || account.getHolders().isEmpty()) {
            return null;
        }
        AccountHolder primaryHolder = account.getHolders().stream()
                .filter(h -> h.getHolderType() == HolderType.PRIMARY)
                .findFirst()
                .orElse(account.getHolders().getFirst());
        Customer customer = primaryHolder.getCustomer();
        if (customer == null || customer.getUser() == null) {
            return null;
        }
        return customer.getUser().getEmail();
    }

    private String generateOtpCode() {
        int upperBound = (int) Math.pow(10, OTP_LENGTH);
        int otp = SECURE_RANDOM.nextInt(upperBound);
        return String.format("%0" + OTP_LENGTH + "d", otp);
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "***";
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "*" + email.substring(atIndex);
        }
        return email.charAt(0) + "*".repeat(atIndex - 1) + email.substring(atIndex);
    }

    private boolean checkAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return false;
        }
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    private void sendTransferNotification(AccountTransactionResponse response, String senderAccountNumber) {
        try {
            Account senderAccount = accountRepository.findAccountByAccountNumber(senderAccountNumber).orElse(null);
            if (senderAccount == null || senderAccount.getHolders() == null || senderAccount.getHolders().isEmpty()) {
                return;
            }

            AccountHolder holder = senderAccount.getHolders().stream()
                    .filter(h -> h.getHolderType() == HolderType.PRIMARY)
                    .findFirst()
                    .orElse(senderAccount.getHolders().getFirst());

            Customer customer = holder.getCustomer();
            if (customer == null || customer.getUser() == null) {
                return;
            }

            Long userId = customer.getUser().getId();
            String amount = response.getResponse().getAmount().toPlainString();
            String receiverAcc = response.getReceiverAccountNumber() != null ? response.getReceiverAccountNumber() : "N/A";

            String title = "Transfer of $" + amount + " Successful";
            String message = "Your transfer of $" + amount + " to account " + receiverAcc + " has been completed successfully.";

            webSocketNotificationService.sendNotificationToUser(
                    userId,
                    NotificationType.TRANSFER,
                    title,
                    message,
                    response.getResponse().getReferenceNo(),
                    "TRANSFER"
            );
        } catch (Exception e) {
            log.error("Failed to send transfer notification: {}", e.getMessage());
        }
    }

    private void sendDepositNotification(AccountTransactionResponse response, String receiverAccountNumber) {
        try {
            Account receiverAccount = accountRepository.findAccountByAccountNumber(receiverAccountNumber).orElse(null);
            if (receiverAccount == null || receiverAccount.getHolders() == null || receiverAccount.getHolders().isEmpty()) {
                return;
            }

            AccountHolder holder = receiverAccount.getHolders().stream()
                    .filter(h -> h.getHolderType() == HolderType.PRIMARY)
                    .findFirst()
                    .orElse(receiverAccount.getHolders().getFirst());

            Customer customer = holder.getCustomer();
            if (customer == null || customer.getUser() == null) {
                return;
            }

            Long userId = customer.getUser().getId();
            String amount = response.getResponse().getAmount().toPlainString();
            String senderAcc = response.getSenderAccountNumber() != null ? response.getSenderAccountNumber() : "N/A";

            String title = "Deposit of $" + amount + " Received";
            String message = "You have received a deposit of $" + amount + " from account " + senderAcc + ".";

            webSocketNotificationService.sendNotificationToUser(
                    userId,
                    NotificationType.DEPOSIT,
                    title,
                    message,
                    response.getResponse().getReferenceNo(),
                    "TRANSFER"
            );
        } catch (Exception e) {
            log.error("Failed to send deposit notification: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public BatchTransactionResponse processBatch(BatchTransactionRequest request) {
        List<BatchTransactionItemResult> results = new ArrayList<>();
        int successCount = 0;
        int failedCount = 0;

        if (request.getTransactions() == null || request.getTransactions().isEmpty()) {
            return BatchTransactionResponse.builder()
                    .totalRequested(0)
                    .totalSuccess(0)
                    .totalFailed(0)
                    .results(results)
                    .build();
        }

        for (int i = 0; i < request.getTransactions().size(); i++) {
            BatchTransactionItem item = request.getTransactions().get(i);
            try {
                Account sender = accountRepository.findAccountByAccountNumber(item.getSenderAccountNumber())
                        .orElseThrow(() -> new IllegalArgumentException("Sender account not found: " + item.getSenderAccountNumber()));

                Transaction transaction = new Transaction();
                transaction.setStatus(TransactionStatus.PENDING);
                transaction.setTransactionType(TransactionType.TRANSFER);
                transaction.setChannel(TransactionChannel.INTERNET_BANKING);
                transaction.setAmount(item.getAmount());
                transaction.setRemarks(item.getRemarks());
                transaction.setChargeAmount(BigDecimal.ZERO);
                transaction.setVatAmount(BigDecimal.ZERO);

                TransactionRequest tr = new TransactionRequest();
                tr.setAmount(item.getAmount());
                tr.setRemarks(item.getRemarks());

                transactionService.createTransaction(tr, transaction,
                        item.getSenderAccountNumber(), item.getReceiverAccountNumber());

                AccountTransaction accountTransaction = new AccountTransaction();
                accountTransaction.setAccount(sender);
                accountTransaction.setTransaction(transaction);
                accountTransaction.setReceiverAccountNumber(item.getReceiverAccountNumber());
                accountTransactionRepository.save(accountTransaction);

                successCount++;
                results.add(BatchTransactionItemResult.builder()
                        .index(i)
                        .success(true)
                        .referenceNo(transaction.getReferenceNo())
                        .senderAccountNumber(item.getSenderAccountNumber())
                        .receiverAccountNumber(item.getReceiverAccountNumber())
                        .build());

            } catch (Exception e) {
                failedCount++;
                log.error("Batch item {} failed: {}", i, e.getMessage());
                results.add(BatchTransactionItemResult.builder()
                        .index(i)
                        .success(false)
                        .errorMessage(e.getMessage())
                        .senderAccountNumber(item.getSenderAccountNumber())
                        .receiverAccountNumber(item.getReceiverAccountNumber())
                        .build());
            }
        }

        return BatchTransactionResponse.builder()
                .totalRequested(request.getTransactions().size())
                .totalSuccess(successCount)
                .totalFailed(failedCount)
                .results(results)
                .build();
    }


}
