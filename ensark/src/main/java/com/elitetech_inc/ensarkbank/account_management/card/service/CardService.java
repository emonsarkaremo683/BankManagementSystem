package com.elitetech_inc.ensarkbank.account_management.card.service;

import com.elitetech_inc.ensarkbank.account_management.card.dto.request.CardRequest;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardResponse;
import com.elitetech_inc.ensarkbank.common.enums.CardNetwork;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardUsageResponse;
import com.elitetech_inc.ensarkbank.account_management.hold_transaction.entity.HoldTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {

    CardResponse apply(CardRequest request, Long accountId);

    CardResponse findByCardNumber(String cardNumber);

    boolean cardNumberExists(String cardNumber);

    List<CardResponse> search(String query);

    List<CardResponse> findByCustomerEmail(String email);

    List<CardResponse> findByAccountNumber(String accountNumber);

    CardResponse applyForMultiCurrency(Long cardId);

    CardResponse activeMultiCurrency(Long cardId, boolean active);

    CardResponse changeCardNetwork(Long cardId, CardNetwork network);

    CardResponse changeCardType(Long cardId, CardType type);

    CardResponse updatePin(Long cardId, String oldPin, String newPin);

    CardResponse updateStatus(Long cardId, CardStatus status);

    CardResponse updateByEmployee(Long cardId, CardRequest request);

    CardResponse findById(Long id);

    List<CardResponse> getAll();

    CardResponse renewCard(Long cardId);

    CardResponse setTransactionLimit(Long cardId, BigDecimal dailyLimit, BigDecimal monthlyLimit);

    CardResponse reportLostOrStolen(Long cardId, String reason);

    /**
     * Authorizes a card purchase: checks the amount against the card's
     * remaining daily/monthly limits, places a CARD_AUTH hold on the linked
     * account for the amount, and increments the usage counters. Throws if
     * the card is not active or the purchase would exceed either limit.
     */
    HoldTransaction authorizePurchase(Long cardId, BigDecimal amount, String merchantInfo);

    CardUsageResponse getUsage(Long cardId);
}
