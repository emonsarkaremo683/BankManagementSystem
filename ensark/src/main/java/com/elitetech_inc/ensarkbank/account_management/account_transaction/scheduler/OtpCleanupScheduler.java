package com.elitetech_inc.ensarkbank.account_management.account_transaction.scheduler;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.TransactionOtp;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.repository.TransactionOtpRepository;
import com.elitetech_inc.ensarkbank.common.enums.OtpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpCleanupScheduler {

    private final TransactionOtpRepository transactionOtpRepository;

    @Scheduled(fixedRate = 900000)
    @Transactional
    public void cleanupExpiredOtps() {
        List<TransactionOtp> expiredOtps = transactionOtpRepository.findExpiredPendingOtps();
        if (!expiredOtps.isEmpty()) {
            expiredOtps.forEach(otp -> otp.setStatus(OtpStatus.EXPIRED));
            transactionOtpRepository.saveAll(expiredOtps);
            log.info("Cleaned up {} expired OTP records", expiredOtps.size());
        }
    }
}
