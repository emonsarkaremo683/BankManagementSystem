package com.elitetech_inc.ensarkbank.account_management.card.scheduler;

import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Resets card daily/monthly usage counters so
 * {@link com.elitetech_inc.ensarkbank.account_management.card.service.CardServiceImpl#authorizePurchase}
 * enforces limits against the current period, not an ever-growing total.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CardLimitResetScheduler {

    private final CardRepository cardRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void resetDailyUsage() {
        int updated = cardRepository.resetAllDailyUsage();
        log.info("Card daily usage reset: {} card(s) updated", updated);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional
    public void resetMonthlyUsage() {
        int updated = cardRepository.resetAllMonthlyUsage();
        log.info("Card monthly usage reset: {} card(s) updated", updated);
    }
}
