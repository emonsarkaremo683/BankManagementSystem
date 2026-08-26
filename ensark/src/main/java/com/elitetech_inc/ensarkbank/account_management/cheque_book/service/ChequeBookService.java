package com.elitetech_inc.ensarkbank.account_management.cheque_book.service;

import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.request.ChequeBookRequest;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeBookResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeLeafResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeafStatusHistory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ChequeBookService {

    ChequeBookResponse apply(ChequeBookRequest request);

    ChequeBookResponse update(Long id, ChequeBookRequest request);

    List<ChequeBookResponse> findByCustomerEmail(String email);

    List<ChequeBookResponse> findByAccountNumber(String accountNumber);

    List<ChequeBookResponse> search(String query);

    List<ChequeBookResponse> getAll();

    ChequeBookResponse approve(Long id);

    ChequeBookResponse reject(Long id, String reason);

    ChequeBookResponse getById(Long id);

    ChequeLeafResponse issueLeaf(Long chequeBookId, BigDecimal amount, String payeeName, String remarks);

    ChequeLeafResponse getLeafById(Long leafId);

    ChequeLeafResponse getLeafByChequeNumber(String chequeNumber);

    List<ChequeLeafResponse> getAllPresented();

    List<ChequeLeafResponse> getAllPresentedByBranchId(Long branchId);

    ChequeLeafResponse presentLeaf(Long leafId, String remarks);

    ChequeLeafResponse clearLeaf(Long leafId, String transactionReference);

    ChequeLeafResponse bounceLeaf(Long leafId, String reason);

    ChequeLeafResponse stopPayment(Long leafId, String remarks);

    ChequeLeafResponse cancelLeaf(Long leafId, String remarks);

    List<ChequeLeafResponse> getLeavesByChequeBookId(Long chequeBookId);

    List<ChequeLeafResponse> getLeavesByCustomerId(Long customerId, String status);

    List<ChequeLeafStatusHistory> getLeafStatusHistory(Long leafId);

    // New state machine transitions
    ChequeBookResponse markPrinted(Long id);
    ChequeBookResponse markReadyForDelivery(Long id);
    ChequeBookResponse markDelivered(Long id);
    ChequeBookResponse activate(Long id);
    ChequeBookResponse block(Long id, String reason);
    ChequeBookResponse reissueChequeBook(Long oldBookId);

    // New queries & leaf operations
    long getUnusedLeafCount(Long chequeBookId);
    Map<String, Long> getChequeBookSummary(Long accountId);
    ChequeLeafResponse findByChequeBookIdAndLeafNumber(Long chequeBookId, int leafNumber);
    ChequeLeafResponse revertToIssued(Long leafId, String reason);
    ChequeLeafResponse stopPaymentOnPresented(Long leafId, String remarks);
    void expireOverdueLeaves();
}
