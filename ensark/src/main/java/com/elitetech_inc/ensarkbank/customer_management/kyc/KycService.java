package com.elitetech_inc.ensarkbank.customer_management.kyc;

import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.enums.KYCStatus;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.Kyc;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.KycDocuments;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface KycService {

    KycDocuments getDocumentById(Long documentId);

    void updateStatus(Long customerId, KYCStatus status);

    void updateDocuments(Long customerId, Map<DocumentType, MultipartFile> documents);

    Kyc findByCustomerId(Long customerId);

    Kyc findByAccountId(Long accountId);

    boolean passportExistsByAccountNumber(String accountNumber);

    void savePassport(Long customerId, MultipartFile passport);

    java.util.List<Kyc> getAllPendingVerification();

    void rejectWithReason(Long customerId, String reason);

    KycDocuments findById(Long kycDocumentId);
}
