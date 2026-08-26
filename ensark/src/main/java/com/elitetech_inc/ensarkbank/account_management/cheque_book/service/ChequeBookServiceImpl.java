package com.elitetech_inc.ensarkbank.account_management.cheque_book.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.service.AccountService;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.mapper.ChequeBookMapper;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.request.ChequeBookRequest;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeBookResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeLeafResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeBook;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeaf;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeafStatusHistory;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.repository.ChequeBookRepository;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.repository.ChequeLeafRepository;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.repository.ChequeLeafStatusHistoryRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionPostingService;
import com.elitetech_inc.ensarkbank.common.enums.*;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.util.NotificationUtil;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChequeBookServiceImpl implements ChequeBookService {

    private final ChequeBookRepository chequeBookRepository;
    private final ChequeLeafRepository chequeLeafRepository;
    private final ChequeLeafStatusHistoryRepository chequeLeafStatusHistoryRepository;
    private final ChequeBookMapper chequeBookMapper;
    private final NotificationUtil notificationUtil;
    private final TransactionPostingService transactionPostingService;
    private final TransactionRepository transactionRepository;
    private final ObjectProvider<AccountService> accountServiceProvider;
    private final Utils utils;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public ChequeBookResponse apply(ChequeBookRequest request) {
        String bookSerialNumber = generateBookSerialNumber();
        ChequeBook chequeBook = chequeBookMapper.toChequeBook(request, bookSerialNumber);
        ChequeBook saved = chequeBookRepository.save(chequeBook);

        notifyAuthorities(NotificationType.CHEQUE_BOOK_REQUEST, "New Cheque Book Request",
                "Customer has requested a new cheque book with " + request.getNumberOfLeaves() + " leaves. Book #" + bookSerialNumber,
                String.valueOf(saved.getId()));

        return chequeBookMapper.toChequeBookResponse(saved);
    }

    @Override
    @Transactional
    public ChequeBookResponse update(Long id, ChequeBookRequest request) {
        ChequeBook chequeBook = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));

        if (chequeBook.getStatus() != ChequeBookStatus.REQUESTED) {
            throw new BadRequestException("Cheque book is not in REQUESTED status, cannot update");
        }

        chequeBook.setNumberOfLeaves(request.getNumberOfLeaves());
        return chequeBookMapper.toChequeBookResponse(chequeBookRepository.save(chequeBook));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeBookResponse> findByCustomerEmail(String email) {
        return chequeBookRepository.findByCustomerEmail(email)
                .stream()
                .map(chequeBookMapper::toChequeBookResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeBookResponse> findByAccountNumber(String accountNumber) {
        return chequeBookRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(chequeBookMapper::toChequeBookResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeBookResponse> search(String query) {
        return chequeBookRepository.search(query)
                .stream()
                .map(chequeBookMapper::toChequeBookResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeBookResponse> getAll() {
        return chequeBookRepository.findAll()
                .stream()
                .map(chequeBookMapper::toChequeBookResponse)
                .toList();
    }

    @Override
    @Transactional
    public ChequeBookResponse approve(Long id) {
        ChequeBook chequeBook = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));

        if (chequeBook.getStatus() != ChequeBookStatus.REQUESTED) {
            throw new BadRequestException("Cheque book is not in REQUESTED status, cannot approve");
        }

        BigDecimal feeAmount;
        int leafCount = chequeBook.getNumberOfLeaves();
        if (leafCount <= 25) {
            feeAmount = new BigDecimal("50.00");
        } else if (leafCount <= 50) {
            feeAmount = new BigDecimal("100.00");
        } else {
            feeAmount = new BigDecimal("150.00");
        }

        Account customerAccount = chequeBook.getAccount();
        Account feeAccount = accountServiceProvider.getObject().getOrCreateFeeIncomeAccount(customerAccount.getBranch());

        Transaction chargeTxn = new Transaction();
        chargeTxn.setTransactionType(TransactionType.CHEQUE_ISSUE_CHARGE);
        chargeTxn.setChannel(TransactionChannel.BRANCH);
        chargeTxn.setAmount(feeAmount);
        chargeTxn.setRemarks("Cheque book issue charge for book #" + chequeBook.getBookSerialNumber());
        chargeTxn.setStatus(TransactionStatus.PENDING);
        chargeTxn.setReferenceNo(utils.generateReference());
        chargeTxn.setTransactionId(utils.generateReference());

        transactionPostingService.feeCharge(chargeTxn, customerAccount.getAccountNumber(), feeAccount.getAccountNumber(), feeAmount);
        chargeTxn.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(chargeTxn);

        chequeBook.setStatus(ChequeBookStatus.APPROVED);
        chequeBook.setApprovalDate(LocalDate.now());

        chequeBook.getLeaves().clear();
        for (int i = chequeBook.getStartLeafNumber(); i <= chequeBook.getEndLeafNumber(); i++) {
            ChequeLeaf leaf = new ChequeLeaf();
            leaf.setLeafNumber(i);
            leaf.setChequeNumber(chequeBook.getBookSerialNumber() + String.format("%04d", i));
            leaf.setAmount(BigDecimal.ZERO);
            leaf.setChequeBook(chequeBook);
            leaf.setStatus(ChequeLeafStatus.UNUSED);
            chequeBook.getLeaves().add(leaf);
        }

        ChequeBook saved = chequeBookRepository.save(chequeBook);

        notifyPrimaryHolder(saved, NotificationType.CHEQUE_BOOK_APPROVED, "Cheque Book Approved",
                "Your cheque book #" + saved.getBookSerialNumber() + " has been approved.");

        return chequeBookMapper.toChequeBookResponse(saved);
    }

    @Override
    @Transactional
    public ChequeBookResponse reject(Long id, String reason) {
        ChequeBook chequeBook = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));

        if (chequeBook.getStatus() != ChequeBookStatus.REQUESTED) {
            throw new BadRequestException("Cheque book is not in REQUESTED status, cannot reject");
        }

        chequeBook.setStatus(ChequeBookStatus.REJECTED);
        chequeBook.setRejectionReason(reason);
        ChequeBook saved = chequeBookRepository.save(chequeBook);

        notifyPrimaryHolder(saved, NotificationType.CHEQUE_BOOK_REJECTED, "Cheque Book Rejected",
                "Your cheque book #" + saved.getBookSerialNumber() + " has been rejected. Reason: " + reason);

        return chequeBookMapper.toChequeBookResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ChequeBookResponse getById(Long id) {
        ChequeBook chequeBook = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));
        return chequeBookMapper.toChequeBookResponse(chequeBook);
    }

    @Override
    @Transactional
    public ChequeLeafResponse issueLeaf(Long chequeBookId, BigDecimal amount, String payeeName, String remarks) {
        ChequeBook chequeBook = chequeBookRepository.findById(chequeBookId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", chequeBookId));

        if (chequeBook.getStatus() != ChequeBookStatus.ACTIVE) {
            throw new BadRequestException("Cheque book is not ACTIVE, cannot issue cheque");
        }

        ChequeLeaf leaf = chequeLeafRepository.findFirstByChequeBookIdAndStatusOrderByLeafNumberAsc(chequeBookId, ChequeLeafStatus.UNUSED)
                .orElseThrow(() -> new BadRequestException("No unused leaves remaining in this cheque book"));

        leaf.setAmount(amount);
        leaf.setPayeeName(payeeName);
        leaf.setRemarks(remarks);
        leaf.setStatus(ChequeLeafStatus.ISSUED);
        leaf.setIssueDate(LocalDate.now());
        leaf.setExpiryDate(LocalDate.now().plusMonths(6));

        ChequeLeaf saved = chequeLeafRepository.save(leaf);

        if (chequeLeafRepository.countByChequeBookIdAndStatus(chequeBook.getId(), ChequeLeafStatus.UNUSED) == 0) {
            chequeBook.setStatus(ChequeBookStatus.EXHAUSTED);
            chequeBookRepository.save(chequeBook);
        }

        recordLeafStatusChange(saved, ChequeLeafStatus.UNUSED, ChequeLeafStatus.ISSUED, "Cheque issued to " + payeeName);
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ChequeLeafResponse getLeafById(Long leafId) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));
        return chequeBookMapper.toChequeLeafResponse(leaf);
    }

    @Override
    @Transactional(readOnly = true)
    public ChequeLeafResponse getLeafByChequeNumber(String chequeNumber) {
        ChequeLeaf leaf = chequeLeafRepository.findByChequeNumber(chequeNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", "chequeNumber", chequeNumber));
        return chequeBookMapper.toChequeLeafResponse(leaf);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeLeafResponse> getAllPresented() {
        return chequeLeafRepository.findAllPresented()
                .stream()
                .map(chequeBookMapper::toChequeLeafResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeLeafResponse> getAllPresentedByBranchId(Long branchId) {
        return chequeLeafRepository.findAllPresentedByBranchId(branchId)
                .stream()
                .map(chequeBookMapper::toChequeLeafResponse)
                .toList();
    }

    @Override
    @Transactional
    public ChequeLeafResponse presentLeaf(Long leafId, String remarks) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));

        if (leaf.getStatus() != ChequeLeafStatus.ISSUED) {
            throw new BadRequestException("Cheque leaf is not ISSUED, cannot present");
        }

        if (leaf.getExpiryDate() != null && leaf.getExpiryDate().isBefore(LocalDate.now())) {
            leaf.setStatus(ChequeLeafStatus.EXPIRED);
            chequeLeafRepository.save(leaf);
            recordLeafStatusChange(leaf, ChequeLeafStatus.ISSUED, ChequeLeafStatus.EXPIRED, "Cheque expired");
            throw new BadRequestException("Cheque has expired on " + leaf.getExpiryDate());
        }

        leaf.setStatus(ChequeLeafStatus.PRESENTED);
        leaf.setRemarks(remarks);
        leaf.setPresentmentChannel(PresentmentChannel.CLEARING_HOUSE);
        ChequeLeaf saved = chequeLeafRepository.save(leaf);
        recordLeafStatusChange(saved, ChequeLeafStatus.ISSUED, ChequeLeafStatus.PRESENTED, remarks);
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Transactional
    public ChequeLeafResponse clearLeaf(Long leafId, String transactionReference) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));

        if (leaf.getStatus() != ChequeLeafStatus.PRESENTED) {
            throw new BadRequestException("Cheque leaf is not PRESENTED, cannot clear");
        }

        Account customerAccount = leaf.getChequeBook().getAccount();
        Branch branch = customerAccount.getBranch();
        Account vaultAccount = accountServiceProvider.getObject().getOrCreateVaultAccount(branch);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setChannel(TransactionChannel.BRANCH);
        transaction.setAmount(leaf.getAmount());
        transaction.setRemarks("Cheque clearance - Cheque #" + leaf.getChequeNumber());
        transaction.setReferenceNo(utils.generateReference());
        transaction.setTransactionId(utils.generateReference());

        transactionPostingService.cashWithdrawal(transaction, customerAccount.getAccountNumber(), vaultAccount.getAccountNumber(), leaf.getAmount());

        // Only mark/persist the transaction as SUCCESS once cashWithdrawal()
        // has actually gone through. Previously this was saved as SUCCESS
        // before the posting ran, so a failure there (e.g. insufficient
        // vault balance) could leave a "successful" withdrawal record for
        // money that was never moved.
        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);

        leaf.setStatus(ChequeLeafStatus.CLEARED);
        leaf.setClearanceDate(LocalDate.now());
        leaf.setTransactionReference(transaction.getId().toString());
        ChequeLeaf saved = chequeLeafRepository.save(leaf);
        recordLeafStatusChange(saved, ChequeLeafStatus.PRESENTED, ChequeLeafStatus.CLEARED, "Cleared with ref " + transaction.getId());
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Transactional
    public ChequeLeafResponse bounceLeaf(Long leafId, String reason) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));

        if (leaf.getStatus() != ChequeLeafStatus.PRESENTED) {
            throw new BadRequestException("Cheque leaf is not PRESENTED, cannot bounce");
        }

        leaf.setStatus(ChequeLeafStatus.BOUNCED);
        leaf.setBounceReason(reason);
        ChequeLeaf saved = chequeLeafRepository.save(leaf);
        recordLeafStatusChange(saved, ChequeLeafStatus.PRESENTED, ChequeLeafStatus.BOUNCED, reason);
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Transactional
    public ChequeLeafResponse stopPayment(Long leafId, String remarks) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));

        if (leaf.getStatus() != ChequeLeafStatus.ISSUED) {
            throw new BadRequestException("Cheque leaf is not ISSUED, cannot stop payment");
        }

        leaf.setStatus(ChequeLeafStatus.STOP_PAYMENT);
        leaf.setRemarks(remarks);
        ChequeLeaf saved = chequeLeafRepository.save(leaf);
        recordLeafStatusChange(saved, ChequeLeafStatus.ISSUED, ChequeLeafStatus.STOP_PAYMENT, remarks);
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Transactional
    public ChequeLeafResponse cancelLeaf(Long leafId, String remarks) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));

        if (leaf.getStatus() == ChequeLeafStatus.CLEARED ||
            leaf.getStatus() == ChequeLeafStatus.BOUNCED ||
            leaf.getStatus() == ChequeLeafStatus.EXPIRED ||
            leaf.getStatus() == ChequeLeafStatus.CANCELLED) {
            throw new BadRequestException("Cheque leaf is in " + leaf.getStatus() + " status, cannot cancel");
        }

        ChequeLeafStatus previousStatus = leaf.getStatus();
        leaf.setStatus(ChequeLeafStatus.CANCELLED);
        leaf.setRemarks(remarks);
        ChequeLeaf saved = chequeLeafRepository.save(leaf);
        recordLeafStatusChange(saved, previousStatus, ChequeLeafStatus.CANCELLED, remarks);
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeLeafResponse> getLeavesByChequeBookId(Long chequeBookId) {
        return chequeLeafRepository.findByChequeBookId(chequeBookId)
                .stream()
                .filter(leaf -> leaf.getStatus() != ChequeLeafStatus.UNUSED)
                .map(chequeBookMapper::toChequeLeafResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeLeafResponse> getLeavesByCustomerId(Long customerId, String status) {
        List<ChequeLeaf> leaves;
        if (status == null || status.isBlank()) {
            leaves = chequeLeafRepository.findByCustomerId(customerId).stream()
                    .filter(leaf -> leaf.getStatus() != ChequeLeafStatus.UNUSED)
                    .toList();
        } else if (status.equalsIgnoreCase("ALL")) {
            leaves = chequeLeafRepository.findByCustomerId(customerId);
        } else {
            ChequeLeafStatus chequeLeafStatus;
            try {
                chequeLeafStatus = ChequeLeafStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid cheque leaf status: " + status);
            }
            leaves = chequeLeafRepository.findByCustomerIdAndStatus(customerId, chequeLeafStatus);
        }
        return leaves.stream()
                .map(chequeBookMapper::toChequeLeafResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChequeLeafStatusHistory> getLeafStatusHistory(Long leafId) {
        chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));
        return chequeLeafStatusHistoryRepository.findByChequeLeafIdOrderByCreatedAtAsc(leafId);
    }

    @Override
    @Transactional
    public ChequeBookResponse markPrinted(Long id) {
        ChequeBook book = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));
        if (book.getStatus() != ChequeBookStatus.APPROVED) {
            throw new BadRequestException("Cheque book must be APPROVED to be marked PRINTED");
        }
        book.setStatus(ChequeBookStatus.PRINTED);
        return chequeBookMapper.toChequeBookResponse(chequeBookRepository.save(book));
    }

    @Override
    @Transactional
    public ChequeBookResponse markReadyForDelivery(Long id) {
        ChequeBook book = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));
        if (book.getStatus() != ChequeBookStatus.PRINTED) {
            throw new BadRequestException("Cheque book must be PRINTED to be marked READY_FOR_DELIVERY");
        }
        book.setStatus(ChequeBookStatus.READY_FOR_DELIVERY);
        return chequeBookMapper.toChequeBookResponse(chequeBookRepository.save(book));
    }

    @Override
    @Transactional
    public ChequeBookResponse markDelivered(Long id) {
        ChequeBook book = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));
        if (book.getStatus() != ChequeBookStatus.READY_FOR_DELIVERY) {
            throw new BadRequestException("Cheque book must be READY_FOR_DELIVERY to be marked DELIVERED");
        }
        book.setStatus(ChequeBookStatus.DELIVERED);
        book.setDeliveryDate(LocalDate.now());
        return chequeBookMapper.toChequeBookResponse(chequeBookRepository.save(book));
    }

    @Override
    @Transactional
    public ChequeBookResponse activate(Long id) {
        ChequeBook book = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));
        if (book.getStatus() != ChequeBookStatus.DELIVERED) {
            throw new BadRequestException("Cheque book must be DELIVERED to be activated");
        }
        book.setStatus(ChequeBookStatus.ACTIVE);
        book.setActivationDate(LocalDate.now());
        book.setExpiryDate(LocalDate.now().plusYears(5));

        ChequeBook saved = chequeBookRepository.save(book);
        notifyPrimaryHolder(saved, NotificationType.CHEQUE_BOOK_APPROVED, "Cheque Book Activated",
                "Your cheque book #" + saved.getBookSerialNumber() + " has been activated and is ready for use.");
        return chequeBookMapper.toChequeBookResponse(saved);
    }

    @Override
    @Transactional
    public ChequeBookResponse block(Long id, String reason) {
        ChequeBook book = chequeBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", id));

        if (book.getStatus() == ChequeBookStatus.REJECTED ||
            book.getStatus() == ChequeBookStatus.EXHAUSTED ||
            book.getStatus() == ChequeBookStatus.EXPIRED ||
            book.getStatus() == ChequeBookStatus.CANCELLED) {
            throw new BadRequestException("Cheque book is in terminal status " + book.getStatus() + ", cannot block");
        }

        book.setStatus(ChequeBookStatus.BLOCKED);
        for (ChequeLeaf leaf : book.getLeaves()) {
            if (leaf.getStatus() == ChequeLeafStatus.UNUSED) {
                leaf.setStatus(ChequeLeafStatus.CANCELLED);
                leaf.setRemarks("Book blocked: " + reason);
                chequeLeafRepository.save(leaf);
                recordLeafStatusChange(leaf, ChequeLeafStatus.UNUSED, ChequeLeafStatus.CANCELLED, "Book blocked: " + reason);
            }
        }

        ChequeBook saved = chequeBookRepository.save(book);
        notifyPrimaryHolder(saved, NotificationType.CHEQUE_BOOK_REJECTED, "Cheque Book Blocked",
                "Your cheque book #" + saved.getBookSerialNumber() + " has been blocked. Reason: " + reason);
        return chequeBookMapper.toChequeBookResponse(saved);
    }

    @Override
    @Transactional
    public ChequeBookResponse reissueChequeBook(Long oldBookId) {
        ChequeBook oldBook = chequeBookRepository.findById(oldBookId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Book", oldBookId));

        block(oldBookId, "Reissued to new book");

        ChequeBookRequest request = new ChequeBookRequest();
        request.setAccountId(oldBook.getAccount().getId());
        request.setNumberOfLeaves(oldBook.getNumberOfLeaves());

        return apply(request);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnusedLeafCount(Long chequeBookId) {
        return chequeLeafRepository.countByChequeBookIdAndStatus(chequeBookId, ChequeLeafStatus.UNUSED);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getChequeBookSummary(Long accountId) {
        List<ChequeBook> books = chequeBookRepository.findByAccountNumber(
                accountRepository.findById(accountId)
                        .orElseThrow(() -> new ResourceNotFoundException("Account", accountId))
                        .getAccountNumber()
        );

        Map<String, Long> summary = new HashMap<>();
        for (ChequeLeafStatus status : ChequeLeafStatus.values()) {
            summary.put(status.name(), 0L);
        }

        for (ChequeBook book : books) {
            for (ChequeLeaf leaf : book.getLeaves()) {
                String key = leaf.getStatus().name();
                summary.put(key, summary.getOrDefault(key, 0L) + 1);
            }
        }
        return summary;
    }

    @Override
    @Transactional(readOnly = true)
    public ChequeLeafResponse findByChequeBookIdAndLeafNumber(Long chequeBookId, int leafNumber) {
        ChequeLeaf leaf = chequeLeafRepository.findByChequeBookId(chequeBookId).stream()
                .filter(l -> l.getLeafNumber() == leafNumber)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf not found for book " + chequeBookId + " and leaf number " + leafNumber));
        return chequeBookMapper.toChequeLeafResponse(leaf);
    }

    @Override
    @Transactional
    public ChequeLeafResponse revertToIssued(Long leafId, String reason) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));
        if (leaf.getStatus() != ChequeLeafStatus.PRESENTED) {
            throw new BadRequestException("Only PRESENTED leaves can be reverted to ISSUED");
        }
        leaf.setStatus(ChequeLeafStatus.ISSUED);
        ChequeLeaf saved = chequeLeafRepository.save(leaf);
        recordLeafStatusChange(saved, ChequeLeafStatus.PRESENTED, ChequeLeafStatus.ISSUED, "Reverted from presented: " + reason);
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Transactional
    public ChequeLeafResponse stopPaymentOnPresented(Long leafId, String remarks) {
        ChequeLeaf leaf = chequeLeafRepository.findById(leafId)
                .orElseThrow(() -> new ResourceNotFoundException("Cheque Leaf", leafId));
        if (leaf.getStatus() != ChequeLeafStatus.PRESENTED) {
            throw new BadRequestException("Cheque leaf is not PRESENTED, cannot stop payment through this path");
        }
        leaf.setStatus(ChequeLeafStatus.STOP_PAYMENT);
        leaf.setRemarks(remarks);
        ChequeLeaf saved = chequeLeafRepository.save(leaf);
        recordLeafStatusChange(saved, ChequeLeafStatus.PRESENTED, ChequeLeafStatus.STOP_PAYMENT, remarks);
        return chequeBookMapper.toChequeLeafResponse(saved);
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void expireOverdueLeaves() {
        log.info("Cheque leaf expiry sweep started.");
        List<ChequeLeaf> overdue = chequeLeafRepository.findAll().stream()
                .filter(leaf -> leaf.getStatus() == ChequeLeafStatus.ISSUED && leaf.getExpiryDate() != null && leaf.getExpiryDate().isBefore(LocalDate.now()))
                .toList();

        for (ChequeLeaf leaf : overdue) {
            leaf.setStatus(ChequeLeafStatus.EXPIRED);
            chequeLeafRepository.save(leaf);
            recordLeafStatusChange(leaf, ChequeLeafStatus.ISSUED, ChequeLeafStatus.EXPIRED, "Cheque expired via schedule sweep");
            log.info("Cheque leaf #{} has been expired.", leaf.getChequeNumber());
        }
        log.info("Cheque leaf expiry sweep completed.");
    }

    private String generateBookSerialNumber() {
        long count = chequeBookRepository.count() + 1;
        String timestamp = String.valueOf(System.currentTimeMillis() % 100000);
        return "CBS-" + String.format("%06d", count) + timestamp;
    }

    private String generateChequeNumber() {
        return "CHQ-" + System.currentTimeMillis() % 1000000;
    }

    private void notifyAuthorities(NotificationType type, String title, String message, String refId) {
        try {
            notificationUtil.notifyAuthorities(type, title, message, refId, "CHEQUE_BOOK");
        } catch (Exception e) {
            log.warn("Failed to send authority notification: {}", e.getMessage());
        }
    }

    private void notifyPrimaryHolder(ChequeBook chequeBook, NotificationType type, String title, String message) {
        try {
            if (chequeBook.getAccount() != null && chequeBook.getAccount().getHolders() != null) {
                chequeBook.getAccount().getHolders().stream()
                        .filter(h -> h.getCustomer() != null && h.getCustomer().getUser() != null)
                        .filter(h -> h.getHolderType() == HolderType.PRIMARY)
                        .findFirst()
                        .ifPresent(holder -> notificationUtil.notifyUser(
                                holder.getCustomer().getUser().getId(), type, title, message,
                                String.valueOf(chequeBook.getId()), "CHEQUE_BOOK"));
            }
        } catch (Exception e) {
            log.warn("Failed to send customer notification: {}", e.getMessage());
        }
    }

    private void recordLeafStatusChange(ChequeLeaf leaf, ChequeLeafStatus fromStatus, ChequeLeafStatus toStatus, String reason) {
        ChequeLeafStatusHistory history = new ChequeLeafStatusHistory();
        history.setChequeLeaf(leaf);
        history.setFromStatus(fromStatus);
        history.setToStatus(toStatus);
        history.setReason(reason);
        chequeLeafStatusHistoryRepository.save(history);
    }
}
