package com.elitetech_inc.ensarkbank.account_management.loan.dto;

import com.elitetech_inc.ensarkbank.account_management.loan.entity.Loan;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanDocument;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanGuarantor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanMapper {

    public LoanApplicationResponse toResponse(Loan loan) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setLoanId(loan.getId());
        response.setAccountId(loan.getAccount().getId());
        response.setAccountNumber(loan.getAccount().getAccountNumber());

        response.setPrincipalAmount(loan.getPrincipalAmount());
        response.setAnnualInterestRate(loan.getAnnualInterestRate());
        response.setTenureMonths(loan.getTenureMonths());

        response.setEmiAmount(loan.getEmiAmount());
        response.setTotalPayable(loan.getTotalPayable());
        response.setOutstandingBalance(loan.getOutstandingBalance());
        response.setDisbursementCharge(loan.getDisbursementCharge());

        response.setStatus(loan.getStatus());

        response.setApplicationDate(loan.getApplicationDate());
        response.setApprovalDate(loan.getApprovalDate());
        response.setDisbursementDate(loan.getDisbursementDate());
        response.setNextDueDate(loan.getNextDueDate());

        response.setRejectionReason(loan.getRejectionReason());
        response.setDisbursementTransactionRef(loan.getDisbursementTransactionRef());

        if (loan.getGuarantors() != null) {
            response.setGuarantors(loan.getGuarantors().stream()
                    .map(this::toGuarantorResponse)
                    .collect(Collectors.toList()));
        } else {
            response.setGuarantors(new ArrayList<>());
        }

        if (loan.getDocuments() != null) {
            response.setDocuments(loan.getDocuments().stream()
                    .map(this::toDocumentResponse)
                    .collect(Collectors.toList()));
        } else {
            response.setDocuments(new ArrayList<>());
        }

        return response;
    }

    public LoanApplicationResponse.GuarantorResponse toGuarantorResponse(LoanGuarantor guarantor) {
        LoanApplicationResponse.GuarantorResponse dto = new LoanApplicationResponse.GuarantorResponse();
        dto.setId(guarantor.getId());
        dto.setName(guarantor.getName());
        dto.setPhone(guarantor.getPhone());
        dto.setAddress(guarantor.getAddress());
        dto.setNidNumber(guarantor.getNidNumber());
        dto.setRelation(guarantor.getRelation());
        dto.setPhotoPath(guarantor.getPhotoPath());
        return dto;
    }

    public LoanApplicationResponse.DocumentResponse toDocumentResponse(LoanDocument document) {
        LoanApplicationResponse.DocumentResponse dto = new LoanApplicationResponse.DocumentResponse();
        dto.setId(document.getId());
        dto.setFileName(document.getFileName());
        dto.setOriginalFileName(document.getOriginalFileName());
        dto.setContentType(document.getContentType());
        dto.setFileSize(document.getFileSize());
        return dto;
    }
}
