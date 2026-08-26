package com.elitetech_inc.ensarkbank.customer_management.kyc;

import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.enums.KYCStatus;
import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.Kyc;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.KycDocuments;
import com.elitetech_inc.ensarkbank.customer_management.kyc.repository.KycDocumentsRepository;
import com.elitetech_inc.ensarkbank.customer_management.kyc.repository.KycRepository;
import com.elitetech_inc.ensarkbank.util.EmailUtil;
import com.elitetech_inc.ensarkbank.util.NotificationUtil;
import com.elitetech_inc.ensarkbank.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;
    private final KycDocumentsRepository documentsRepository;
    private final CustomerRepository customerRepository;
    private final Utils utils;
    private final NotificationUtil notificationUtil;
    private final EmailUtil emailUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public KycDocuments getDocumentById(Long documentId) {
        return documentsRepository.findByIdWithCustomer(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("KYC Document", documentId));
    }

    @Override
    @Transactional
    public void updateStatus(Long customerId, KYCStatus status) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        if (customer.getKyc() == null) {
            throw new ResourceNotFoundException("KYC", "customerId", String.valueOf(customerId));
        }

        Kyc kyc = kycRepository.findById(customer.getKyc().getId())
                .orElseThrow(() -> new ResourceNotFoundException("KYC", customer.getKyc().getId()));

        kyc.setStatus(status);
        Kyc savedKyc = kycRepository.save(kyc);
        customer.setKyc(savedKyc);

        if (customer.getUser() != null) {
            NotificationType notifType = status == KYCStatus.VERIFIED
                    ? NotificationType.KYC_VERIFIED : NotificationType.KYC_REJECTED;
            String title = "KYC " + status.name();
            String message = "Your KYC verification has been " + status.name().toLowerCase() + ".";

            notificationUtil.notifyUser(customer.getUser().getId(), notifType, title, message,
                    String.valueOf(customerId), "KYC");
            emailUtil.sendKycStatusEmail(customer.getUser().getEmail(), customer.getName(), status.name());
        }
    }

    @Override
    @Transactional
    public void updateDocuments(Long customerId, Map<DocumentType, MultipartFile> documents) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        Kyc kyc;
        if (customer.getKyc() == null) {
            kyc = new Kyc();
            kyc.setCustomer(customer);
            kyc.setStatus(KYCStatus.PENDING);
        } else {
            kyc = kycRepository.findById(customer.getKyc().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("KYC", customer.getKyc().getId()));
            if (kyc.getStatus() == KYCStatus.VERIFIED) {
                throw new IllegalArgumentException("KYC is already verified. Documents cannot be updated.");
            }
            if (kyc.getStatus() == KYCStatus.UNDER_REVIEW || kyc.getStatus() == KYCStatus.PENDING) {
                kyc.setStatus(KYCStatus.PENDING);
            }
        }

        for (Map.Entry<DocumentType, MultipartFile> entry : documents.entrySet()) {
            DocumentType docType = entry.getKey();
            MultipartFile docFile = entry.getValue();

            if (docFile == null || docFile.isEmpty()) continue;

            Iterator<KycDocuments> iterator = kyc.getDocuments().iterator();
            while (iterator.hasNext()) {
                KycDocuments doc = iterator.next();
                if (doc.getDoc_type() == docType) {
                    String storedPath = doc.getPath();
                    String deleteName = storedPath.startsWith("kyc/") ? storedPath.substring(4) : storedPath;
                    utils.deleteFile("kyc", deleteName);
                    iterator.remove();
                    entityManager.flush();
                    break;
                }
            }

            String filePath = utils.uploadFile(docFile, "kyc", docType.name());
            KycDocuments newDoc = new KycDocuments();
            newDoc.setDoc_type(docType);
            newDoc.setPath("kyc/" + filePath);
            newDoc.setKyc(kyc);
            kyc.getDocuments().add(newDoc);
        }

        Kyc savedKyc = kycRepository.save(kyc);
        customer.setKyc(savedKyc);
        customerRepository.save(customer);

        notificationUtil.notifyAuthorities(
                NotificationType.KYC_SUBMITTED,
                "KYC Documents Submitted",
                "Customer " + customer.getName() + " (ID: " + customerId + ") has submitted KYC documents for review.",
                String.valueOf(customerId),
                "KYC"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Kyc findByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        if (customer.getKyc() == null) {
            throw new ResourceNotFoundException("KYC", "customerId", String.valueOf(customerId));
        }

        return kycRepository.findById(customer.getKyc().getId())
                .orElseThrow(() -> new ResourceNotFoundException("KYC", customer.getKyc().getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public Kyc findByAccountId(Long accountId) {
        String sql = "SELECT c FROM Customer c JOIN c.kyc k WHERE c.id IN " +
                "(SELECT ah.customer.id FROM AccountHolder ah WHERE ah.account.id = :accountId)";
        Customer customer = entityManager.createQuery(sql, Customer.class)
                .setParameter("accountId", accountId)
                .getSingleResult();

        if (customer == null || customer.getKyc() == null) {
            throw new ResourceNotFoundException("KYC", "accountId", String.valueOf(accountId));
        }

        return kycRepository.findById(customer.getKyc().getId())
                .orElseThrow(() -> new ResourceNotFoundException("KYC", customer.getKyc().getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean passportExistsByAccountNumber(String accountNumber) {
        String sql = "SELECT COUNT(kd) FROM KycDocuments kd " +
                "JOIN kd.kyc k JOIN k.customer c " +
                "JOIN c.holders ah JOIN ah.account a " +
                "WHERE a.accountNumber = :accountNumber AND kd.doc_type = 'PASSPORT'";
        Long count = entityManager.createQuery(sql, Long.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional
    public void savePassport(Long customerId, MultipartFile passport) {
        if (passport == null || passport.isEmpty()) return;

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        Kyc kyc;
        if (customer.getKyc() == null) {
            kyc = new Kyc();
            kyc.setCustomer(customer);
            kyc.setStatus(KYCStatus.PENDING);
        } else {
            kyc = kycRepository.findById(customer.getKyc().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("KYC", customer.getKyc().getId()));
        }

        Iterator<KycDocuments> iterator = kyc.getDocuments().iterator();
        while (iterator.hasNext()) {
            KycDocuments doc = iterator.next();
            if (doc.getDoc_type() == DocumentType.PASSPORT) {
                String storedPath = doc.getPath();
                String deleteName = storedPath.startsWith("kyc/") ? storedPath.substring(4) : storedPath;
                utils.deleteFile("kyc", deleteName);
                iterator.remove();
                entityManager.flush();
                break;
            }
        }

        String filePath = utils.uploadFile(passport, "kyc", DocumentType.PASSPORT.name());
        KycDocuments newDoc = new KycDocuments();
        newDoc.setDoc_type(DocumentType.PASSPORT);
        newDoc.setPath("kyc/" + filePath);
        newDoc.setKyc(kyc);
        kyc.getDocuments().add(newDoc);

        Kyc savedKyc = kycRepository.save(kyc);
        customer.setKyc(savedKyc);
        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Kyc> getAllPendingVerification() {
        return kycRepository.findAll().stream()
                .filter(k -> k.getStatus() == KYCStatus.PENDING)
                .toList();
    }

    @Override
    @Transactional
    public void rejectWithReason(Long customerId, String reason) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        Kyc kyc = customer.getKyc();
        if (kyc == null) {
            kyc = new Kyc();
            kyc.setCustomer(customer);
            customer.setKyc(kyc);
        }
        kyc.setStatus(KYCStatus.REJECTED);
        kyc.setRejectionReason(reason);
        kycRepository.save(kyc);
        customerRepository.save(customer);

        notificationUtil.notifyUser(customer.getUser().getId(), NotificationType.KYC_REJECTED, "KYC Rejected", "Your KYC request has been rejected. Reason: " + reason, String.valueOf(kyc.getId()), "KYC");
        emailUtil.sendKycStatusEmail(customer.getUser().getEmail(), customer.getName(), KYCStatus.REJECTED.name());
    }

    @Override
    @Transactional(readOnly = true)
    public KycDocuments findById(Long kycDocumentId) {
        return documentsRepository.findById(kycDocumentId)
                .orElseThrow(() -> new ResourceNotFoundException("KYC Document", kycDocumentId));
    }
}
