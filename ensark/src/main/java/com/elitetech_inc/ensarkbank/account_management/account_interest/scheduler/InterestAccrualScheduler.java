package com.elitetech_inc.ensarkbank.account_management.account_interest.scheduler;

import com.elitetech_inc.ensarkbank.account_management.account_interest.service.InterestAccrualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InterestAccrualScheduler {

    private final InterestAccrualService interestAccrualService;

    @Scheduled(cron = "0 0 1 1 * ?")
    public void monthlyInterestAccrual() {
        log.info("Starting monthly interest accrual job");
        try {
            int processed = interestAccrualService.accrueAll();
            log.info("Monthly interest accrual completed: {} accounts credited", processed);
        } catch (Exception e) {
            log.error("Monthly interest accrual failed: {}", e.getMessage(), e);
        }
    }
}
