package com.elitetech_inc.ensarkbank.account_management.account_interest.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account_interest.AccountInterest;
import com.elitetech_inc.ensarkbank.account_management.account_interest.AccountInterestRepository;
import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionPostingService;
import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.common.enums.*;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.util.NotificationUtil;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestAccrualService {

    private final AccountInterestRepository interestRepository;
    private final AccountRepository accountRepository;
    private final TransactionPostingService transactionPostingService;
    private final TransactionRepository transactionRepository;
    private final NotificationUtil notificationUtil;
    private final Utils utils;

    private static final BigDecimal DAYS_IN_YEAR = BigDecimal.valueOf(365);

    @Transactional
    public int accrueAll() {
        List<AccountInterest> policies = interestRepository.findAll();
        int totalProcessed = 0;

        for (AccountInterest policy : policies) {
            totalProcessed += accrueForPolicy(policy);
        }

        log.info("Interest accrual complete: processed {} accounts across {} policies",
                totalProcessed, policies.size());
        return totalProcessed;
    }

    @Transactional
    public int accrueForType(AccountType accountType) {
        AccountInterest policy = interestRepository.findByAccountType(accountType)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No interest policy defined for account type: " + accountType));
        return accrueForPolicy(policy);
    }

    private int accrueForPolicy(AccountInterest policy) {
        List<AccountType> types = List.of(policy.getAccountType());
        List<Account> accounts = accountRepository.findByAccountTypeInAndAccountStatus(
                types, AccountStatus.ACTIVE);

        int processed = 0;
        for (Account account : accounts) {
            BigDecimal balance = account.getAvailableBalance();
            if (balance == null || balance.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            BigDecimal interest = calculateInterest(
                    balance, policy.getInterestRate(), policy.getTimeSpan());
            if (interest.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            postInterest(account, interest);
            notifyPrimaryHolder(account, interest);
            processed++;
        }

        log.info("Interest accrual for {}: {} accounts credited (rate={}, timeSpan={} days)",
                policy.getAccountType(), processed, policy.getInterestRate(), policy.getTimeSpan());
        return processed;
    }

    private BigDecimal calculateInterest(BigDecimal balance, BigDecimal annualRate,
                                          Long timeSpanDays) {
        return balance
                .multiply(annualRate)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(timeSpanDays))
                .divide(DAYS_IN_YEAR, 2, RoundingMode.HALF_UP);
    }

    private void postInterest(Account account, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.INTEREST_POSTING);
        transaction.setChannel(TransactionChannel.SYSTEM);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionId(utils.generateReference());
        transaction.setReferenceNo(utils.generateReference());
        transaction.setAmount(amount);
        transaction.setRemarks("Interest posted for account " + account.getAccountNumber());

        transactionPostingService.interestPosting(
                transaction, "Owners Equity", account.getAccountNumber(), amount);
        transactionRepository.save(transaction);
    }

    private void notifyPrimaryHolder(Account account, BigDecimal amount) {
        account.getHolders().stream()
                .filter(h -> h.getHolderType() == HolderType.PRIMARY)
                .map(AccountHolder::getCustomer)
                .filter(customer -> customer != null && customer.getUser() != null)
                .findFirst()
                .ifPresent(customer -> {
                    User user = customer.getUser();
                    notificationUtil.notifyUser(
                            user.getId(),
                            NotificationType.INTEREST_CREDITED,
                            "Interest Credited",
                            "Your " + account.getAccountType() + " account has been credited with $" +
                                    amount.setScale(2, RoundingMode.HALF_UP) +
                                    " interest. New balance: $" +
                                    account.getAvailableBalance().setScale(2, RoundingMode.HALF_UP),
                            account.getId().toString(),
                            "ACCOUNT"
                    );
                });
    }
}
