package com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.mapper;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.request.ChequeBookRequest;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeBookResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeLeafResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeBook;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeaf;
import com.elitetech_inc.ensarkbank.common.enums.ChequeBookStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChequeBookMapper {

    private final AccountRepository accountRepository;

    public ChequeBook toChequeBook(ChequeBookRequest request, String bookSerialNumber) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found: " + request.getAccountId()));

        int startLeaf = 1;
        int endLeaf = request.getNumberOfLeaves();

        ChequeBook chequeBook = new ChequeBook();
        chequeBook.setAccount(account);
        chequeBook.setNumberOfLeaves(request.getNumberOfLeaves());
        chequeBook.setStartLeafNumber(startLeaf);
        chequeBook.setEndLeafNumber(endLeaf);
        chequeBook.setBookSerialNumber(bookSerialNumber);
        chequeBook.setStatus(ChequeBookStatus.REQUESTED);
        chequeBook.setApplicationDate(LocalDate.now());
        chequeBook.setLeaves(new ArrayList<>());

        return chequeBook;
    }

    public ChequeBookResponse toChequeBookResponse(ChequeBook chequeBook) {
        return ChequeBookResponse.builder()
                .chequeBookId(chequeBook.getId())
                .bookSerialNumber(chequeBook.getBookSerialNumber())
                .numberOfLeaves(chequeBook.getNumberOfLeaves())
                .startLeafNumber(chequeBook.getStartLeafNumber())
                .endLeafNumber(chequeBook.getEndLeafNumber())
                .status(chequeBook.getStatus())
                .accountId(chequeBook.getAccount() != null ? chequeBook.getAccount().getId() : null)
                .accountNumber(chequeBook.getAccount() != null ? chequeBook.getAccount().getAccountNumber() : null)
                .applicationDate(chequeBook.getApplicationDate())
                .approvalDate(chequeBook.getApprovalDate())
                .deliveryDate(chequeBook.getDeliveryDate())
                .activationDate(chequeBook.getActivationDate())
                .expiryDate(chequeBook.getExpiryDate())
                .rejectionReason(chequeBook.getRejectionReason())
                .leaves(chequeBook.getLeaves() != null
                        ? chequeBook.getLeaves().stream().map(this::toChequeLeafResponse).collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public ChequeLeafResponse toChequeLeafResponse(ChequeLeaf leaf) {
        return ChequeLeafResponse.builder()
                .leafId(leaf.getId())
                .leafNumber(leaf.getLeafNumber())
                .chequeNumber(leaf.getChequeNumber())
                .amount(leaf.getAmount())
                .payeeName(leaf.getPayeeName())
                .remarks(leaf.getRemarks())
                .status(leaf.getStatus())
                .issueDate(leaf.getIssueDate())
                .clearanceDate(leaf.getClearanceDate())
                .expiryDate(leaf.getExpiryDate())
                .bounceReason(leaf.getBounceReason())
                .transactionReference(leaf.getTransactionReference())
                .chequeBookId(leaf.getChequeBook() != null ? leaf.getChequeBook().getId() : null)
                .bookSerialNumber(leaf.getChequeBook() != null ? leaf.getChequeBook().getBookSerialNumber() : null)
                .build();
    }
}
