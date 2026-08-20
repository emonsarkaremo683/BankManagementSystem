package com.elitetech_inc.ensarkbank.standing_order.scheduler;

import com.elitetech_inc.ensarkbank.standing_order.service.StandingOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StandingOrderScheduler {

    private final StandingOrderService standingOrderService;

    @Scheduled(cron = "0 0 * * * *")
    public void processDueOrders() {
        log.info("Running standing order scheduler...");
        try {
            standingOrderService.processDueOrders();
        } catch (Exception e) {
            log.error("Standing order scheduler failed: {}", e.getMessage(), e);
        }
    }
}
