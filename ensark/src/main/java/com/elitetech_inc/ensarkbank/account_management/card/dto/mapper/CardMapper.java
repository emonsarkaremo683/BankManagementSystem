package com.elitetech_inc.ensarkbank.account_management.card.dto.mapper;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.card.dto.request.CardRequest;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardResponse;
import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.enums.HolderType;
import com.elitetech_inc.ensarkbank.util.CardGenerator;
import com.elitetech_inc.ensarkbank.util.Validator;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Builder
public class CardMapper {

    private final AccountRepository accountRepository;
    private final CardGenerator cardGenerator;
    private final Validator validator;
    private final PasswordEncoder encoder;


    public Card toCard(CardRequest cr){

        Account account = accountRepository.findById(cr.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account"+ cr.getAccountId()));

        validator.checkAccountStatus(account.getAccountNumber());
        validator.checkAccountType(account.getAccountNumber());
        Card card = new Card();
        card.setAccount(account);
        card.setCardNetwork(cr.getCardNetwork());
        card.setCardType(cr.getCardType());
        card.setStatus(CardStatus.PENDING);
        card.setCvv(cardGenerator.getCvv());
        card.setPinHash(encoder.encode(cr.getPin()));
        card.setCardNumber(cardGenerator.cardGenerator(cr.getCardNetwork(), cr.getCardType(), account.getAccountNumber()));
        card.setExpiryDate(Date.from(LocalDate.now().plusYears(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        card.setInternationalEnabled(cr.isInternationalEnabled());
        card.setOnlineTransactionEnabled(cr.isOnlineTransactionEnabled());

        return card;
    }

    public CardResponse toCardResponse(Card card){

        return CardResponse.builder()
                .cardId(card.getId())
                .cardNumber(card.getCardNumber())
                .cardHolderName(card.getAccount().getHolders()
                        .stream()
                        .filter(h -> h.getHolderType() == HolderType.PRIMARY)
                        .findFirst()
                        .map(h -> h.getCustomer().getName()).orElse("Unknown"))
                .isInternationalEnabled(card.isInternationalEnabled())
                .isOnlineTransactionEnabled(card.isOnlineTransactionEnabled())
                .cardNetwork(card.getCardNetwork())
                .cardType(card.getCardType())
                .status(card.getStatus())
                .expiryDate(card.getExpiryDate())
                .dailyLimit(card.getDailyLimit())
                .monthlyLimit(card.getMonthlyLimit())
                .accountNumber(card.getAccount() != null ? card.getAccount().getAccountNumber() : null)
                .createdAt(card.getCreatedAt() != null ? Date.from(card.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant()) : null)
                .build();
    }
}

