package com.elitetech_inc.ensarkbank.account_management.loan.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account.service.AccountService;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanApplicationRequest;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanApplicationResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanMapper;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanRepaymentResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanScheduleResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.Loan;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanDocument;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanGuarantor;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanRepayment;
import com.elitetech_inc.ensarkbank.account_management.loan.repository.LoanRepository;
import com.elitetech_inc.ensarkbank.account_management.loan.repository.LoanRepaymentRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionService;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.LoanStatus;
import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.enums.RepaymentStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.util.EmailUtil;
import com.elitetech_inc.ensarkbank.util.NotificationUtil;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Utils;
import com.elitetech_inc.ensarkbank.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanRepaymentRepository repaymentRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final LoanMapper loanMapper;
    private final BranchRepository branchRepository;
    private final TransactionMapper transactionMapper;
    private final RequestValidator requestValidator;
    private final Validator validator;
    private final NotificationUtil notificationUtil;
    private final EmailUtil emailUtil;
    private final Utils utils;

    private static final int SCALE = 2;
    private static final RoundingMode RM = RoundingMode.HALF_UP;
    private static final BigDecimal DISBURSEMENT_CHARGE_RATE = new BigDecimal("0.05");

    @Override
    @Transactional
    public LoanApplicationResponse apply(LoanApplicationRequest request,
                                         List<MultipartFile> documents,
                                         MultipartFile guarantorPhoto) {
        requestValidator.validateLoanApplication(request);

        if (request.getPrincipalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Principal must be positive");
        }
        if (request.getTenureMonths() <= 0) {
            throw new BadRequestException("Tenure must be positive");
        }

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", request.getAccountId()));
        validator.checkAccountStatus(account.getAccountNumber());

        BigDecimal emi = calculateEMI(request.getPrincipalAmount(), request.getAnnualInterestRate(), request.getTenureMonths());
        BigDecimal totalPayable = emi.multiply(BigDecimal.valueOf(request.getTenureMonths())).setScale(SCALE, RM);
        BigDecimal disbursementCharge = request.getPrincipalAmount().multiply(DISBURSEMENT_CHARGE_RATE).setScale(SCALE, RM);

        Loan loan = new Loan();
        loan.setAccount(account);
        loan.setPrincipalAmount(request.getPrincipalAmount().setScale(SCALE, RM));
        loan.setAnnualInterestRate(request.getAnnualInterestRate().setScale(2, RM));
        loan.setTenureMonths(request.getTenureMonths());
        loan.setEmiAmount(emi);
        loan.setOutstandingBalance(request.getPrincipalAmount().setScale(SCALE, RM));
        loan.setTotalPayable(totalPayable);
        loan.setDisbursementCharge(disbursementCharge);
        loan.setStatus(LoanStatus.PENDING);

        Loan savedLoan = loanRepository.save(loan);

        saveGuarantor(savedLoan, request.getGuarantor(), guarantorPhoto);
        saveDocuments(savedLoan, documents);

        notificationUtil.notifyAuthorities(
                NotificationType.LOAN_APPLICATION,
                "New Loan Application",
                "Loan application for account " + account.getAccountNumber() +
                        " - Principal: " + request.getPrincipalAmount() + " BDT, Tenure: " + request.getTenureMonths() + " months. Status: PENDING.",
                String.valueOf(savedLoan.getId()),
                "LOAN"
        );

        return loanMapper.toResponse(savedLoan);
    }

    @Override
    @Transactional
    public LoanApplicationResponse updateStatus(Long loanId, LoanStatus status, String reason) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", loanId));

        if (status == LoanStatus.APPROVED) {
            return approve(loan, reason);
        } else if (status == LoanStatus.REJECTED) {
            return reject(loan, reason);
        } else if (status == LoanStatus.DISBURSED) {
            return disburse(loan);
        }

        throw new BadRequestException("Invalid status transition to " + status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByCustomerEmail(String email) {
        return loanRepository.findByAccountHoldersCustomerUserEmail(email)
                .stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByAccountNumber(String accountNumber) {
        Loan loan = loanRepository.findByAccountAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "accountNumber", accountNumber));
        return List.of(loanMapper.toResponse(loan));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> search(String query) {
        if (query == null || query.isBlank()) {
            return getAll();
        }
        return loanRepository.search(query)
                .stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LoanApplicationResponse findById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", id));
        return loanMapper.toResponse(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanRepaymentResponse> getRepaymentsByLoan(Long loanId) {
        loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", loanId));
        return repaymentRepository.findByLoanIdOrderByInstallmentNumberAsc(loanId)
                .stream()
                .map(this::toRepaymentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanScheduleResponse> getSchedule(Long loanId) {
        loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", loanId));
        return repaymentRepository.findByLoanIdOrderByInstallmentNumberAsc(loanId)
                .stream()
                .map(this::toScheduleResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanRepaymentResponse payInstallment(Long repaymentId) {
        LoanRepayment repayment = repaymentRepository.findById(repaymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment", repaymentId));
        return payInstallmentByAccount(repaymentId, repayment.getLoan().getAccount().getId());
    }

    @Override
    @Transactional
    public LoanRepaymentResponse payInstallmentByAccount(Long repaymentId, Long accountId) {
        LoanRepayment repayment = repaymentRepository.findById(repaymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment", repaymentId));

        if (repayment.getStatus() == RepaymentStatus.PAID || repayment.getStatus() == RepaymentStatus.LATE) {
            throw new BadRequestException("Installment already paid");
        }

        Loan loan = repayment.getLoan();
        Account payAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", accountId));
        Account loanControlAccount = getLoanControlAccount(loan);

        BigDecimal amountToCharge = repayment.getEmiAmount();
        boolean isLate = repayment.getDueDate().isBefore(LocalDate.now());
        if (isLate) {
            BigDecimal lateFee = repayment.getEmiAmount().multiply(new BigDecimal("0.02")).setScale(SCALE, RM);
            amountToCharge = amountToCharge.add(lateFee);
        }

        TransactionRequest request = new TransactionRequest();
        request.setAmount(amountToCharge);
        request.setRemarks("EMI payment by account - Loan #" + loan.getId() + " Installment #" + repayment.getInstallmentNumber() + (isLate ? " (includes 2% late fee)" : ""));

        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setChannel(TransactionChannel.INTERNET_BANKING);
        transaction.setTransactionType(TransactionType.LOAN_REPAYMENT);
        transaction.setLoanRepaymentId(repayment.getId());

        TransactionResponse response = transactionService.createTransaction(
                request,
                transaction,
                payAccount.getAccountNumber(),
                loanControlAccount.getAccountNumber()
        );

        repayment.setStatus(isLate ? RepaymentStatus.LATE : RepaymentStatus.PAID);
        repayment.setPaidDate(LocalDate.now());
        repayment.setTransactionRef(response.getTransactionId());
        repaymentRepository.save(repayment);

        loan.setOutstandingBalance(repayment.getRemainingBalanceAfter());

        updateLoanStatusAfterRepayment(loan);

        return toRepaymentResponse(repayment);
    }

    @Override
    @Transactional
    public LoanRepaymentResponse payInstallmentByCashier(Long repaymentId, Long cashierId, Long branchId) {
        LoanRepayment repayment = repaymentRepository.findById(repaymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment", repaymentId));

        if (repayment.getStatus() == RepaymentStatus.PAID || repayment.getStatus() == RepaymentStatus.LATE) {
            throw new BadRequestException("Installment already paid");
        }

        Loan loan = repayment.getLoan();
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", branchId));
        Account vaultAccount = accountService.getOrCreateVaultAccount(branch);
        Account loanControlAccount = getLoanControlAccount(loan);

        BigDecimal amountToCharge = repayment.getEmiAmount();
        boolean isLate = repayment.getDueDate().isBefore(LocalDate.now());
        if (isLate) {
            BigDecimal lateFee = repayment.getEmiAmount().multiply(new BigDecimal("0.01")).setScale(SCALE, RM);
            amountToCharge = amountToCharge.add(lateFee);
        }

        TransactionRequest request = new TransactionRequest();
        request.setAmount(amountToCharge);
        request.setRemarks("EMI payment by cash - Loan #" + loan.getId() + " Installment #" + repayment.getInstallmentNumber() + (isLate ? " (includes 1% late fee)" : ""));

        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setChannel(TransactionChannel.BRANCH);
        transaction.setTransactionType(TransactionType.LOAN_REPAYMENT);
        transaction.setLoanRepaymentId(repayment.getId());

        TransactionResponse response = transactionService.createTransaction(
                request,
                transaction,
                vaultAccount.getAccountNumber(),
                loanControlAccount.getAccountNumber()
        );

        repayment.setStatus(isLate ? RepaymentStatus.LATE : RepaymentStatus.PAID);
        repayment.setPaidDate(LocalDate.now());
        repayment.setTransactionRef(response.getTransactionId());
        repaymentRepository.save(repayment);

        loan.setOutstandingBalance(repayment.getRemainingBalanceAfter());

        updateLoanStatusAfterRepayment(loan);

        return toRepaymentResponse(repayment);
    }

    private void updateLoanStatusAfterRepayment(Loan loan) {
        List<LoanRepayment> schedule = repaymentRepository.findByLoanIdOrderByInstallmentNumberAsc(loan.getId());
        boolean allSettled = schedule.stream()
                .allMatch(r -> r.getStatus() == RepaymentStatus.PAID || r.getStatus() == RepaymentStatus.LATE);

        if (allSettled) {
            loan.setStatus(LoanStatus.CLOSED);
        } else {
            schedule.stream()
                    .filter(r -> r.getStatus() == RepaymentStatus.PENDING)
                    .findFirst()
                    .ifPresent(next -> loan.setNextDueDate(next.getDueDate()));
        }
        loanRepository.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByStatus(LoanStatus status) {
        return loanRepository.findAll().stream()
                .filter(l -> l.getStatus() == status)
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanApplicationResponse closeLoanForeclosure(Long loanId, Long sweepFromAccountId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", loanId));

        if (loan.getStatus() == LoanStatus.CLOSED) {
            throw new BadRequestException("Loan is already closed");
        }

        List<LoanRepayment> pending = repaymentRepository.findByLoanIdOrderByInstallmentNumberAsc(loanId)
                .stream()
                .filter(r -> r.getStatus() == RepaymentStatus.PENDING)
                .toList();

        if (pending.isEmpty()) {
            loan.setOutstandingBalance(BigDecimal.ZERO);
            loan.setStatus(LoanStatus.CLOSED);
            return loanMapper.toResponse(loanRepository.save(loan));
        }

        // Foreclosure settles every remaining installment at once, so the
        // payoff amount is the sum of principal + interest across ALL
        // pending installments — not loan.getOutstandingBalance() (which
        // only tracks principal) and not a single matched repayment.
        BigDecimal payoffAmount = pending.stream()
                .map(r -> r.getPrincipalComponent().add(
                        r.getInterestComponent() != null ? r.getInterestComponent() : BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(SCALE, RM);

        Account sweepFromAccount = accountRepository.findById(sweepFromAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", sweepFromAccountId));
        Account loanControlAccount = getLoanControlAccount(loan);

        TransactionRequest request = new TransactionRequest();
        request.setAmount(payoffAmount);
        request.setRemarks("Loan Foreclosure - Loan #" + loan.getId());

        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setChannel(TransactionChannel.INTERNET_BANKING);
        transaction.setTransactionType(TransactionType.LOAN_FORECLOSURE);
        // Foreclosure settles every pending installment together, not the
        // single installment loanRepayment()'s lookup-by-id expects, so this
        // is intentionally left unset (see TransactionPostingService.loanForeclosure).
        transaction.setLoanRepaymentId(null);

        transactionService.createTransaction(
                request,
                transaction,
                sweepFromAccount.getAccountNumber(),
                loanControlAccount.getAccountNumber()
        );

        for (LoanRepayment rep : pending) {
            rep.setStatus(RepaymentStatus.PAID);
            rep.setPaidDate(LocalDate.now());
            rep.setTransactionRef(transaction.getTransactionId());
            repaymentRepository.save(rep);
        }

        loan.setOutstandingBalance(BigDecimal.ZERO);
        loan.setStatus(LoanStatus.CLOSED);

        Loan saved = loanRepository.save(loan);
        notifyLoanCustomer(saved, "CLOSED");
        return loanMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void recalculateEmiSchedule(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", loanId));

        List<LoanRepayment> repayments = repaymentRepository.findByLoanIdOrderByInstallmentNumberAsc(loanId);
        List<LoanRepayment> pending = repayments.stream()
                .filter(r -> r.getStatus() == RepaymentStatus.PENDING)
                .toList();

        if (pending.isEmpty()) return;

        BigDecimal remainingBalance = loan.getOutstandingBalance();
        BigDecimal monthlyRate = loan.getAnnualInterestRate()
                .divide(BigDecimal.valueOf(1200), MathContext.DECIMAL64);

        int remainingMonths = pending.size();
        BigDecimal newEmi = calculateEMI(remainingBalance, loan.getAnnualInterestRate(), remainingMonths);
        loan.setEmiAmount(newEmi);

        BigDecimal balance = remainingBalance;
        for (int i = 0; i < remainingMonths; i++) {
            LoanRepayment rep = pending.get(i);
            BigDecimal interest = balance.multiply(monthlyRate).setScale(SCALE, RM);
            BigDecimal principal = newEmi.subtract(interest).setScale(SCALE, RM);

            if (i == remainingMonths - 1) {
                principal = balance;
            }

            balance = balance.subtract(principal).setScale(SCALE, RM);
            if (balance.compareTo(BigDecimal.ZERO) < 0) balance = BigDecimal.ZERO;

            rep.setInterestComponent(interest);
            rep.setPrincipalComponent(principal);
            rep.setEmiAmount(i == remainingMonths - 1 ? principal.add(interest).setScale(SCALE, RM) : newEmi);
            rep.setRemainingBalanceAfter(balance);
            repaymentRepository.save(rep);
        }
        loanRepository.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Map<String, Object> getLoanSummary(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", loanId));

        BigDecimal principal = loan.getPrincipalAmount();
        BigDecimal outstanding = loan.getOutstandingBalance();
        BigDecimal paid = principal.subtract(outstanding);

        double percentPaid = principal.compareTo(BigDecimal.ZERO) > 0
                ? paid.multiply(BigDecimal.valueOf(100)).divide(principal, 2, RoundingMode.HALF_UP).doubleValue()
                : 0.0;

        java.util.Map<String, Object> summary = new java.util.HashMap<>();
        summary.put("loanId", loanId);
        summary.put("principalAmount", principal);
        summary.put("outstandingBalance", outstanding);
        summary.put("paidAmount", paid);
        summary.put("percentPaid", percentPaid);
        summary.put("nextDueDate", loan.getNextDueDate());
        summary.put("status", loan.getStatus());

        return summary;
    }

    private LoanApplicationResponse approve(Loan loan, String reason) {
        if (loan.getStatus() == LoanStatus.APPROVED) {
            return disburse(loan);
        }

        requireStatus(loan, LoanStatus.PENDING);
        loan.setStatus(LoanStatus.APPROVED);
        loan.setApprovalDate(LocalDate.now());
        loanRepository.save(loan);

        LoanApplicationResponse response = disburse(loan);
        notifyLoanCustomer(loan, "APPROVED");
        return response;
    }

    private LoanApplicationResponse reject(Loan loan, String reason) {
        requireStatus(loan, LoanStatus.PENDING);
        loan.setStatus(LoanStatus.REJECTED);
        loan.setRejectionReason(reason);
        LoanApplicationResponse response = loanMapper.toResponse(loanRepository.save(loan));
        notifyLoanCustomer(loan, "REJECTED");
        return response;
    }

    private LoanApplicationResponse disburse(Loan loan) {
        requireStatus(loan, LoanStatus.APPROVED);

        Account loanControlAccount = getLoanControlAccount(loan);

        TransactionRequest request = new TransactionRequest();
        request.setAmount(loan.getPrincipalAmount().setScale(SCALE, RM));
        request.setRemarks("Loan disbursement - Loan #" + loan.getId());

        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setTransactionType(TransactionType.LOAN_DISBURSEMENT);
        transaction.setChannel(TransactionChannel.INTERNET_BANKING);

        TransactionResponse response = transactionService.createTransaction(
                request,
                transaction,
                loanControlAccount.getAccountNumber(),
                loan.getAccount().getAccountNumber()
        );

        loan.setStatus(LoanStatus.DISBURSED);
        loan.setDisbursementDate(LocalDate.now());
        loan.setDisbursementTransactionRef(response.getTransactionId());
        loanRepository.save(loan);

        generateRepaymentSchedule(loan);

        loan.setStatus(LoanStatus.ACTIVE);
        loan.setNextDueDate(loan.getRepayments().getFirst().getDueDate());
        return loanMapper.toResponse(loanRepository.save(loan));
    }

    private void saveGuarantor(Loan loan, LoanApplicationRequest.GuarantorRequest request, MultipartFile photo) {
        LoanGuarantor guarantor = new LoanGuarantor();
        guarantor.setLoan(loan);
        guarantor.setName(request.getName());
        guarantor.setPhone(request.getPhone());
        guarantor.setAddress(request.getAddress());
        guarantor.setNidNumber(request.getNidNumber());
        guarantor.setRelation(request.getRelation());

        if (photo != null && !photo.isEmpty()) {
            String photoPath = utils.uploadFile(photo, "loan/guarantor", request.getName());
            guarantor.setPhotoPath(photoPath);
        }

        loan.getGuarantors().add(guarantor);
    }

    private void saveDocuments(Loan loan, List<MultipartFile> documents) {
        if (documents == null || documents.isEmpty()) return;

        for (MultipartFile file : documents) {
            if (file.isEmpty()) continue;

            LoanDocument document = new LoanDocument();
            document.setLoan(loan);
            document.setOriginalFileName(file.getOriginalFilename());
            document.setContentType(file.getContentType());
            document.setFileSize(file.getSize());

            String fileName = utils.uploadFile(file, "loan/documents", "doc");
            document.setFileName(fileName);
            document.setFilePath("loan/documents/" + fileName);

            loan.getDocuments().add(document);
        }
    }

    private void generateRepaymentSchedule(Loan loan) {
        BigDecimal balance = loan.getPrincipalAmount();
        BigDecimal monthlyRate = loan.getAnnualInterestRate()
                .divide(BigDecimal.valueOf(1200), MathContext.DECIMAL64);

        LocalDate dueDate = loan.getDisbursementDate().plusMonths(1);

        for (int i = 1; i <= loan.getTenureMonths(); i++) {
            BigDecimal interest = balance.multiply(monthlyRate).setScale(SCALE, RM);
            BigDecimal principalComponent = loan.getEmiAmount().subtract(interest).setScale(SCALE, RM);

            if (i == loan.getTenureMonths()) {
                principalComponent = balance;
            }

            balance = balance.subtract(principalComponent).setScale(SCALE, RM);
            if (balance.compareTo(BigDecimal.ZERO) < 0) balance = BigDecimal.ZERO;

            LoanRepayment repayment = new LoanRepayment();
            repayment.setLoan(loan);
            repayment.setInstallmentNumber(i);
            repayment.setDueDate(dueDate);
            repayment.setPrincipalComponent(principalComponent);
            repayment.setInterestComponent(interest);
            repayment.setEmiAmount(i == loan.getTenureMonths()
                    ? principalComponent.add(interest).setScale(SCALE, RM)
                    : loan.getEmiAmount());
            repayment.setRemainingBalanceAfter(balance);
            repayment.setStatus(RepaymentStatus.PENDING);

            repaymentRepository.save(repayment);
            loan.getRepayments().add(repayment);
            dueDate = dueDate.plusMonths(1);
        }
    }

    private void notifyLoanCustomer(Loan loan, String status) {
        if (loan.getAccount() == null || loan.getAccount().getHolders() == null) return;

        loan.getAccount().getHolders().stream()
                .filter(h -> h.getCustomer() != null && h.getCustomer().getUser() != null)
                .findFirst()
                .ifPresent(holder -> {
                    var user = holder.getCustomer().getUser();
                    var customer = holder.getCustomer();

                    NotificationType notifType;
                    String title;
                    String message;

                    switch (status) {
                        case "APPROVED":
                            notifType = NotificationType.LOAN_APPROVED;
                            title = "Loan Approved";
                            message = "Your loan #" + loan.getId() + " for " +
                                    loan.getPrincipalAmount() + " BDT has been approved.";
                            break;
                        case "REJECTED":
                            notifType = NotificationType.LOAN_REJECTED;
                            title = "Loan Rejected";
                            message = "Your loan #" + loan.getId() + " for " +
                                    loan.getPrincipalAmount() + " BDT has been rejected.";
                            break;
                        case "CLOSED":
                            notifType = NotificationType.GENERAL;
                            title = "Loan Closed";
                            message = "Your loan #" + loan.getId() + " has been fully repaid and closed.";
                            break;
                        default:
                            return;
                    }

                    notificationUtil.notifyUser(user.getId(), notifType, title, message,
                            String.valueOf(loan.getId()), "LOAN");
                    emailUtil.sendLoanStatusEmail(user.getEmail(), customer.getName(),
                            String.valueOf(loan.getId()), status);
                });
    }

    private LoanRepaymentResponse toRepaymentResponse(LoanRepayment repayment) {
        LoanRepaymentResponse dto = new LoanRepaymentResponse();
        dto.setId(repayment.getId());
        dto.setLoanId(repayment.getLoan().getId());
        dto.setInstallmentNumber(repayment.getInstallmentNumber());
        dto.setDueDate(repayment.getDueDate());
        dto.setPrincipalComponent(repayment.getPrincipalComponent());
        dto.setInterestComponent(repayment.getInterestComponent());
        dto.setEmiAmount(repayment.getEmiAmount());
        dto.setRemainingBalanceAfter(repayment.getRemainingBalanceAfter());
        dto.setStatus(repayment.getStatus());
        dto.setPaidDate(repayment.getPaidDate());
        dto.setTransactionRef(repayment.getTransactionRef());
        return dto;
    }

    private LoanScheduleResponse toScheduleResponse(LoanRepayment repayment) {
        LoanScheduleResponse dto = new LoanScheduleResponse();
        dto.setRepaymentId(repayment.getId());
        dto.setInstallmentNumber(repayment.getInstallmentNumber());
        dto.setDueDate(repayment.getDueDate());
        dto.setPrincipalComponent(repayment.getPrincipalComponent());
        dto.setInterestComponent(repayment.getInterestComponent());
        dto.setEmiAmount(repayment.getEmiAmount());
        dto.setRemainingBalanceAfter(repayment.getRemainingBalanceAfter());
        dto.setStatus(repayment.getStatus());
        dto.setPaidDate(repayment.getPaidDate());
        dto.setTransactionRef(repayment.getTransactionRef());
        return dto;
    }

    private BigDecimal calculateEMI(BigDecimal principal, BigDecimal annualRate, int tenureMonths) {
        if (annualRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(tenureMonths), SCALE, RM);
        }
        BigDecimal r = annualRate.divide(BigDecimal.valueOf(1200), MathContext.DECIMAL64);
        BigDecimal onePlusR = BigDecimal.ONE.add(r);
        BigDecimal onePlusRPowN = onePlusR.pow(tenureMonths, MathContext.DECIMAL64);

        BigDecimal numerator = principal.multiply(r).multiply(onePlusRPowN);
        BigDecimal denominator = onePlusRPowN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, SCALE, RM);
    }

    private void requireStatus(Loan loan, LoanStatus expected) {
        if (loan.getStatus() != expected) {
            throw new BadRequestException("Loan must be in " + expected + " state, was " + loan.getStatus());
        }
    }

    private Account getLoanControlAccount(Loan loan) {
        Branch branch = branchRepository.findById(loan.getAccount().getBranch().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", loan.getAccount().getBranch().getId()));
        return accountService.getOrCreateLoanControlAccount(branch);
    }
}
