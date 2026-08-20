package com.elitetech_inc.ensarkbank.account_management.card.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.card.dto.mapper.CardMapper;
import com.elitetech_inc.ensarkbank.account_management.card.dto.request.CardRequest;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardResponse;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardUsageResponse;
import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.account_management.hold_transaction.entity.HoldTransaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionPostingService;
import com.elitetech_inc.ensarkbank.common.enums.CardNetwork;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.common.enums.HoldReason;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.util.CardGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final CardGenerator cardGenerator;
    private final PasswordEncoder encoder;
    private final TransactionPostingService transactionPostingService;

    private static final int CARD_AUTH_HOLD_MINUTES = 30;

    @Override
    @Transactional
    public CardResponse apply(CardRequest request, Long accountId) {
        Card card = cardMapper.toCard(request);
        card.setCardNumber(cardGenerator.cardGenerator(
                request.getCardNetwork(), request.getCardType(), card.getAccount().getAccountNumber()));
        card.setCvv(cardGenerator.getCvv());
        card.setStatus(CardStatus.PENDING);

        Card saved = cardRepository.save(card);
        log.info("Card applied: id={}, number={}", saved.getId(), saved.getCardNumber());
        return cardMapper.toCardResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse findByCardNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardNumber));
        return cardMapper.toCardResponse(card);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean cardNumberExists(String cardNumber) {
        return cardRepository.existsByCardNumber(cardNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> search(String query) {
        return cardRepository.search(query)
                .stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> findByCustomerEmail(String email) {
        return cardRepository.findByAccountHoldersCustomerUserEmail(email)
                .stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> findByAccountNumber(String accountNumber) {
        return cardRepository.findByAccountAccountNumber(accountNumber)
                .stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CardResponse applyForMultiCurrency(Long cardId) {
        Card card = findCardOrThrow(cardId);
        if (card.isInternationalEnabled()) {
            throw new BadRequestException("Card already has multi-currency enabled");
        }
        card.setInternationalEnabled(true);
        card.setStatus(CardStatus.PENDING);
        log.info("Multi-currency applied: cardId={}", cardId);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse activeMultiCurrency(Long cardId, boolean active) {
        Card card = findCardOrThrow(cardId);
        card.setInternationalEnabled(active);
        log.info("Multi-currency {}: cardId={}", active ? "activated" : "deactivated", cardId);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse changeCardNetwork(Long cardId, CardNetwork network) {
        Card card = findCardOrThrow(cardId);
        card.setCardNetwork(network);
        card.setStatus(CardStatus.PENDING);
        log.info("Card network changed to {}: cardId={}", network, cardId);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse changeCardType(Long cardId, CardType type) {
        Card card = findCardOrThrow(cardId);
        card.setCardType(type);
        card.setStatus(CardStatus.PENDING);
        log.info("Card type changed to {}: cardId={}", type, cardId);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse updatePin(Long cardId, String oldPin, String newPin) {
        Card card = findCardOrThrow(cardId);
        if (!encoder.matches(oldPin, card.getPinHash())) {
            throw new BadRequestException("Old PIN is incorrect");
        }
        card.setPinHash(encoder.encode(newPin));
        log.info("PIN updated: cardId={}", cardId);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse updateStatus(Long cardId, CardStatus status) {
        Card card = findCardOrThrow(cardId);
        card.setStatus(status);
        log.info("Card status changed to {}: cardId={}", status, cardId);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse updateByEmployee(Long cardId, CardRequest request) {
        Card card = findCardOrThrow(cardId);
        card.setCardNetwork(request.getCardNetwork());
        card.setCardType(request.getCardType());
        card.setDailyLimit(request.isInternationalEnabled() ? new BigDecimal("10000.00") : BigDecimal.ZERO);
        card.setOnlineTransactionEnabled(request.isOnlineTransactionEnabled());
        card.setStatus(CardStatus.PENDING);
        log.info("Card updated by employee: cardId={}", cardId);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        Card card = findCardOrThrow(id);
        return cardMapper.toCardResponse(card);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> getAll() {
        return cardRepository.findAll()
                .stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList());
    }

    private Card findCardOrThrow(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card", id));
    }

    @Override
    @Transactional
    public CardResponse renewCard(Long cardId) {
        Card card = findCardOrThrow(cardId);
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(java.util.Calendar.YEAR, 5);
        card.setExpiryDate(cal.getTime());
        card.setStatus(CardStatus.ACTIVE);
        log.info("Card renewed: cardId={}, newExpiry={}", cardId, card.getExpiryDate());
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse setTransactionLimit(Long cardId, BigDecimal dailyLimit, BigDecimal monthlyLimit) {
        Card card = findCardOrThrow(cardId);
        card.setDailyLimit(dailyLimit);
        card.setMonthlyLimit(monthlyLimit);
        log.info("Card transaction limit updated: cardId={}, dailyLimit={}, monthlyLimit={}", cardId, dailyLimit, monthlyLimit);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public HoldTransaction authorizePurchase(Long cardId, BigDecimal amount, String merchantInfo) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Purchase amount must be positive");
        }

        Card card = findCardOrThrow(cardId);

        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new BadRequestException("Card is not active: " + card.getStatus());
        }

        Account account = card.getAccount();
        if (account == null) {
            throw new BadRequestException("Card is not linked to an account");
        }

        BigDecimal dailyLimit = zeroIfNull(card.getDailyLimit());
        BigDecimal monthlyLimit = zeroIfNull(card.getMonthlyLimit());
        BigDecimal projectedDaily = zeroIfNull(card.getCurrentDailyUsage()).add(amount);
        BigDecimal projectedMonthly = zeroIfNull(card.getCurrentMonthlyUsage()).add(amount);

        if (projectedDaily.compareTo(dailyLimit) > 0) {
            throw new BadRequestException("Purchase declined: exceeds daily card limit of " + dailyLimit);
        }
        if (projectedMonthly.compareTo(monthlyLimit) > 0) {
            throw new BadRequestException("Purchase declined: exceeds monthly card limit of " + monthlyLimit);
        }

        // Hold funds first — if the account doesn't have sufficient available
        // balance this throws and neither the hold nor the usage counters below
        // are committed, since this method is @Transactional.
        HoldTransaction hold = transactionPostingService.holdAmount(
                account, amount, HoldReason.CARD_AUTH, CARD_AUTH_HOLD_MINUTES, merchantInfo);

        card.setCurrentDailyUsage(projectedDaily);
        card.setCurrentMonthlyUsage(projectedMonthly);
        cardRepository.save(card);

        log.info("Card purchase authorized: cardId={}, amount={}, dailyUsage={}/{}, monthlyUsage={}/{}",
                cardId, amount, projectedDaily, dailyLimit, projectedMonthly, monthlyLimit);
        return hold;
    }

    @Override
    @Transactional(readOnly = true)
    public CardUsageResponse getUsage(Long cardId) {
        Card card = findCardOrThrow(cardId);
        BigDecimal dailyLimit = zeroIfNull(card.getDailyLimit());
        BigDecimal monthlyLimit = zeroIfNull(card.getMonthlyLimit());
        BigDecimal dailyUsage = zeroIfNull(card.getCurrentDailyUsage());
        BigDecimal monthlyUsage = zeroIfNull(card.getCurrentMonthlyUsage());

        return CardUsageResponse.builder()
                .cardId(card.getId())
                .cardNumber(card.getCardNumber())
                .dailyLimit(dailyLimit)
                .monthlyLimit(monthlyLimit)
                .currentDailyUsage(dailyUsage)
                .currentMonthlyUsage(monthlyUsage)
                .dailyRemaining(dailyLimit.subtract(dailyUsage).max(BigDecimal.ZERO))
                .monthlyRemaining(monthlyLimit.subtract(monthlyUsage).max(BigDecimal.ZERO))
                .build();
    }

    private BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    @Override
    @Transactional
    public CardResponse reportLostOrStolen(Long cardId, String reason) {
        Card card = findCardOrThrow(cardId);
        card.setStatus(CardStatus.BLOCKED);
        card.setInternationalEnabled(false);
        card.setOnlineTransactionEnabled(false);
        log.info("Card reported lost/stolen: cardId={}, reason={}", cardId, reason);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }
}
