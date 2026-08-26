package com.elitetech_inc.ensarkbank.customer_management.beneficiary.service;

import com.elitetech_inc.ensarkbank.common.enums.OtpStatus;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.common.email.TransactionEmailService;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.mapper.BeneficiaryMapper;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.request.BeneficiaryRequest;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.response.BeneficiaryResponse;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.Beneficiary;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.BeneficiaryVerification;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.repository.BeneficiaryRepository;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.repository.BeneficiaryVerificationRepository;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private static final int OTP_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 3;
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final BeneficiaryRepository beneficiaryRepository;
    private final BeneficiaryVerificationRepository verificationRepository;
    private final BeneficiaryMapper beneficiaryMapper;
    private final CustomerRepository customerRepository;
    private final RequestValidator requestValidator;
    private final TransactionEmailService transactionEmailService;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public BeneficiaryResponse add(BeneficiaryRequest request) {
        requestValidator.validateBeneficiary(request);

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", request.getCustomerId()));

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setName(request.getName());
        beneficiary.setProvider(request.getProvider());
        beneficiary.setBeneficiaryType(request.getBeneficiaryType());
        beneficiary.setAccNumber(request.getAccNumber());
        beneficiary.setRoutingNumber(request.getRoutingNumber());
        beneficiary.setCustomer(customer);

        return beneficiaryMapper.toResponse(beneficiaryRepository.save(beneficiary));
    }

    @Override
    @Transactional
    public BeneficiaryResponse update(Long id, BeneficiaryRequest request) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary", id));

        if (request.getName() != null) beneficiary.setName(request.getName());
        if (request.getProvider() != null) beneficiary.setProvider(request.getProvider());
        if (request.getAccNumber() != null) beneficiary.setAccNumber(request.getAccNumber());
        if (request.getRoutingNumber() != null) beneficiary.setRoutingNumber(request.getRoutingNumber());
        if (request.getBeneficiaryType() != null) beneficiary.setBeneficiaryType(request.getBeneficiaryType());

        return beneficiaryMapper.toResponse(beneficiaryRepository.save(beneficiary));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponse> getByCustomerEmailReverse(String email) {
        return beneficiaryRepository.findByCustomerEmail(email)
                .stream()
                .map(beneficiaryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponse> getByCustomerId(Long customerId) {
        return beneficiaryRepository.findBeneficiaryByCustomer_id(customerId)
                .stream()
                .map(beneficiaryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BeneficiaryResponse findById(Long id) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary", id));
        return beneficiaryMapper.toResponse(beneficiary);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!beneficiaryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Beneficiary", id);
        }
        beneficiaryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void initiateVerification(Long beneficiaryId) {
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary", beneficiaryId));

        if (beneficiary.isVerified()) {
            throw new BadRequestException("Beneficiary is already verified");
        }

        verificationRepository.findActiveVerification(beneficiaryId, OtpStatus.PENDING)
                .ifPresent(v -> {
                    v.setStatus(OtpStatus.EXPIRED);
                    verificationRepository.save(v);
                });

        String customerEmail = resolveCustomerEmail(beneficiary);
        if (customerEmail == null || customerEmail.isBlank()) {
            throw new BadRequestException("No registered email found for this customer");
        }

        String otpCode = generateOtpCode();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);

        BeneficiaryVerification verification = new BeneficiaryVerification();
        verification.setBeneficiary(beneficiary);
        verification.setOtpCode(otpCode);
        verification.setCustomerEmail(customerEmail);
        verification.setStatus(OtpStatus.PENDING);
        verification.setAttemptCount(0);
        verification.setExpiresAt(expiresAt);

        verificationRepository.save(verification);
        transactionEmailService.sendOtpEmail(customerEmail, otpCode, null, "BENEFICIARY_VERIFICATION");
    }

    @Override
    @Transactional
    public void verify(Long beneficiaryId, String otpCode) {
        BeneficiaryVerification verification = verificationRepository
                .findActiveVerification(beneficiaryId, OtpStatus.PENDING)
                .orElseThrow(() -> new BadRequestException("No active verification found"));

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            verification.setStatus(OtpStatus.EXPIRED);
            verificationRepository.save(verification);
            throw new BadRequestException("Verification code has expired");
        }

        if (verification.getAttemptCount() >= MAX_ATTEMPTS) {
            verification.setStatus(OtpStatus.FAILED);
            verificationRepository.save(verification);
            throw new BadRequestException("Maximum verification attempts exceeded");
        }

        if (!verification.getOtpCode().equals(otpCode)) {
            int newCount = verification.getAttemptCount() + 1;
            verification.setAttemptCount(newCount);
            if (newCount >= MAX_ATTEMPTS) {
                verification.setStatus(OtpStatus.FAILED);
            }
            verificationRepository.save(verification);
            int remaining = MAX_ATTEMPTS - newCount;
            throw new BadRequestException("Incorrect verification code. " + remaining + " attempt(s) remaining.");
        }

        verification.setStatus(OtpStatus.VERIFIED);
        verificationRepository.save(verification);

        Beneficiary beneficiary = verification.getBeneficiary();
        beneficiary.setVerified(true);
        beneficiaryRepository.save(beneficiary);
    }

    private String resolveCustomerEmail(Beneficiary beneficiary) {
        if (beneficiary.getCustomer() == null || beneficiary.getCustomer().getUser() == null) {
            return null;
        }
        return beneficiary.getCustomer().getUser().getEmail();
    }

    private String generateOtpCode() {
        int upperBound = (int) Math.pow(10, OTP_LENGTH);
        int otp = SECURE_RANDOM.nextInt(upperBound);
        return String.format("%0" + OTP_LENGTH + "d", otp);
    }

    @Override
    @Transactional
    public BeneficiaryResponse blockBeneficiary(Long id, String reason) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary", id));
        beneficiary.setBlocked(true);
        beneficiary.setBlockReason(reason);
        return beneficiaryMapper.toResponse(beneficiaryRepository.save(beneficiary));
    }

    @Override
    @Transactional
    public BeneficiaryResponse unblockBeneficiary(Long id) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary", id));
        beneficiary.setBlocked(false);
        beneficiary.setBlockReason(null);
        return beneficiaryMapper.toResponse(beneficiaryRepository.save(beneficiary));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponse> findByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", accountId));

        Customer customer = account.getHolders().stream()
                .filter(h -> h.getHolderType() == com.elitetech_inc.ensarkbank.common.enums.HolderType.PRIMARY)
                .map(com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder::getCustomer)
                .findFirst()
                .orElseGet(() -> {
                    if (account.getHolders().isEmpty()) {
                        throw new BadRequestException("No holders associated with account " + accountId);
                    }
                    return account.getHolders().get(0).getCustomer();
                });

        return getByCustomerId(customer.getId());
    }
}
