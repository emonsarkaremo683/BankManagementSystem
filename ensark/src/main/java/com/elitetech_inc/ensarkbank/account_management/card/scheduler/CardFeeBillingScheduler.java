package com.elitetech_inc.ensarkbank.account_management.card.scheduler;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.service.AccountService;
import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionPostingService;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardFeeBillingScheduler {

    private final CardRepository cardRepository;
    private final TransactionPostingService transactionPostingService;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final Utils utils;

    private static final BigDecimal ANNUAL_FEE = new BigDecimal("600.00");

    @Scheduled(cron = "0 0 0 1 1 ?")
    @Transactional
    public void billAnnualCardFees() {
        log.info("Annual card fee billing job started.");
        List<Card> activeCards = cardRepository.findAll().stream()
                .filter(card -> card.getStatus() == CardStatus.ACTIVE)
                .toList();

        for (Card card : activeCards) {
            try {
                Account customerAccount = card.getAccount();
                if (customerAccount == null) continue;

                Account feeIncomeAccount = accountService.getOrCreateFeeIncomeAccount(customerAccount.getBranch());

                Transaction transaction = new Transaction();
                transaction.setTransactionType(TransactionType.CARD_ISSUE_CHARGE);
                transaction.setChannel(TransactionChannel.CARD);
                transaction.setAmount(ANNUAL_FEE);
                transaction.setRemarks("Annual card issue fee for card: " + card.getCardNumber());
                transaction.setStatus(TransactionStatus.SUCCESS);
                transaction.setReferenceNo(utils.generateReference());
                transaction.setTransactionId(utils.generateReference());

                transactionRepository.save(transaction);
                transactionPostingService.feeCharge(transaction, customerAccount.getAccountNumber(), feeIncomeAccount.getAccountNumber(), ANNUAL_FEE);
                log.info("Charged annual card fee of 600 BDT to account {} for card {}", customerAccount.getAccountNumber(), card.getCardNumber());
            } catch (Exception e) {
                log.error("Failed to charge annual card fee for card {}, error: {}", card.getCardNumber(), e.getMessage());
            }
        }
        log.info("Annual card fee billing job completed.");
    }
}
