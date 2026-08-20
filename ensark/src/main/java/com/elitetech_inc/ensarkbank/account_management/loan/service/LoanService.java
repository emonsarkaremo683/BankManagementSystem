package com.elitetech_inc.ensarkbank.account_management.loan.service;

import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanApplicationRequest;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanApplicationResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanRepaymentResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanScheduleResponse;
import com.elitetech_inc.ensarkbank.common.enums.LoanStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoanService {

    LoanApplicationResponse apply(LoanApplicationRequest request,
                                  List<MultipartFile> documents,
                                  MultipartFile guarantorPhoto);

    LoanApplicationResponse updateStatus(Long loanId, LoanStatus status, String reason);

    List<LoanApplicationResponse> findByCustomerEmail(String email);

    List<LoanApplicationResponse> findByAccountNumber(String accountNumber);

    List<LoanApplicationResponse> search(String query);

    LoanApplicationResponse findById(Long id);

    List<LoanApplicationResponse> getAll();

    List<LoanRepaymentResponse> getRepaymentsByLoan(Long loanId);

    List<LoanScheduleResponse> getSchedule(Long loanId);

    LoanRepaymentResponse payInstallment(Long repaymentId);

    List<LoanApplicationResponse> findByStatus(LoanStatus status);

    LoanApplicationResponse closeLoanForeclosure(Long loanId, Long sweepFromAccountId);

    void recalculateEmiSchedule(Long loanId);

    java.util.Map<String, Object> getLoanSummary(Long loanId);

    LoanRepaymentResponse payInstallmentByAccount(Long repaymentId, Long accountId);

    LoanRepaymentResponse payInstallmentByCashier(Long repaymentId, Long cashierId, Long branchId);
}
