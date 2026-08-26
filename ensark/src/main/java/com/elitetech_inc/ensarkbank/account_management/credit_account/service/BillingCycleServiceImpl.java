package com.elitetech_inc.ensarkbank.account_management.credit_account.service;

import com.elitetech_inc.ensarkbank.account_management.credit_account.entity.CreditAccount;
import com.elitetech_inc.ensarkbank.account_management.credit_account.repository.CreditAccountRepository;
import com.elitetech_inc.ensarkbank.common.enums.CreditAccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingCycleServiceImpl implements BillingCycleService {

    private final CreditAccountRepository creditAccountRepository;

    @Value("${credit.billing.minimum-payment-percentage:0.02}")
    private BigDecimal minimumPaymentPercentage;

    @Value("${credit.billing.minimum-payment-floor:25}")
    private BigDecimal minimumPaymentFloor;

    @Value("${credit.billing.grace-period-days:21}")
    private int gracePeriodDays;

    @Override
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void processBillingCycles() {
        int today = LocalDate.now().getDayOfMonth();
        List<CreditAccount> accounts = creditAccountRepository.findActiveByBillingCycleDay(today);
        log.info("Billing cycle job running: found {} accounts with billingCycleDay={}", accounts.size(), today);

        for (CreditAccount account : accounts) {
            try {
                processAccountBillingCycle(account);
            } catch (Exception e) {
                log.error("Error processing billing cycle for creditAccount={}, error={}", account.getId(), e.getMessage(), e);
            }
        }

        log.info("Billing cycle job completed: processed {} accounts", accounts.size());
    }

    private void processAccountBillingCycle(CreditAccount account) {
        log.info("Processing billing cycle for creditAccount={}, previousStatementDate={}, lastStatementDate={}",
                account.getId(), account.getStatementBalance(), account.getLastStatementDate());

        BigDecimal outstanding = zeroIfNull(account.getOutstandingBalance());

        if (account.getLastStatementDate() != null && account.getStatementBalance() != null) {
            if (!account.isInGracePeriod()) {
                BigDecimal interest = calculateInterest(account, outstanding);
                if (interest.compareTo(BigDecimal.ZERO) > 0) {
                    outstanding = outstanding.add(interest);
                    account.setOutstandingBalance(outstanding);
                    log.info("Interest charged: creditAccount={}, interest={}, newOutstanding={}", account.getId(), interest, outstanding);
                }
            }
        }

        account.setStatementBalance(outstanding);

        BigDecimal minPayment = outstanding.multiply(minimumPaymentPercentage).setScale(2, RoundingMode.HALF_UP);
        if (minPayment.compareTo(minimumPaymentFloor) < 0 && outstanding.compareTo(BigDecimal.ZERO) > 0) {
            minPayment = minimumPaymentFloor;
        }
        if (outstanding.compareTo(BigDecimal.ZERO) <= 0) {
            minPayment = BigDecimal.ZERO;
        }
        account.setMinimumPaymentDue(minPayment);

        LocalDate statementDate = LocalDate.now();
        account.setPaymentDueDate(statementDate.plusDays(gracePeriodDays));
        account.setLastStatementDate(statementDate);

        if (account.getStatementBalance() != null && account.getStatementBalance().compareTo(BigDecimal.ZERO) <= 0) {
            account.setInGracePeriod(true);
        } else {
            account.setInGracePeriod(false);
        }

        if (outstanding.compareTo(account.getCreditLimit()) > 0) {
            account.setStatus(CreditAccountStatus.DELINQUENT);
            log.warn("Credit account delinquent: creditAccount={}, outstanding={}, creditLimit={}", account.getId(), outstanding, account.getCreditLimit());
        }

        creditAccountRepository.save(account);
        log.info("Billing cycle completed for creditAccount={}, statementBalance={}, minimumPayment={}, paymentDueDate={}, isInGracePeriod={}",
                account.getId(), account.getStatementBalance(), account.getMinimumPaymentDue(), account.getPaymentDueDate(), account.isInGracePeriod());
    }

    private BigDecimal calculateInterest(CreditAccount account, BigDecimal averageDailyBalance) {
        BigDecimal apr = account.getApr();
        if (apr == null || apr.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal dailyRate = apr.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);
        int daysInCycle = 30;
        if (account.getLastStatementDate() != null) {
            daysInCycle = (int) java.time.temporal.ChronoUnit.DAYS.between(account.getLastStatementDate(), LocalDate.now());
            if (daysInCycle <= 0) {
                daysInCycle = 30;
            }
        }

        return averageDailyBalance.multiply(dailyRate).multiply(BigDecimal.valueOf(daysInCycle)).setScale(4, RoundingMode.HALF_UP);
    }

    private BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
