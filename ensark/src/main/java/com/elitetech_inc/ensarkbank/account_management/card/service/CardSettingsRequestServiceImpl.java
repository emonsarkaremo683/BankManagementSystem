package com.elitetech_inc.ensarkbank.account_management.card.service;

import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.account_management.card.entity.CardSettingsRequest;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardSettingsRequestRepository;
import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.enums.RequestStatus;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.util.EmailUtil;
import com.elitetech_inc.ensarkbank.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardSettingsRequestServiceImpl implements CardSettingsRequestService {

    private final CardSettingsRequestRepository requestRepository;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final NotificationUtil notificationUtil;
    private final EmailUtil emailUtil;

    @Override
    public CardSettingsRequest createRequest(Long cardId, CardSettingsRequest.RequestType requestType, boolean requestedValue, Long customerId, CardType requestedCardType) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        List<CardSettingsRequest> pending = requestRepository.findByCardIdAndStatus(cardId, RequestStatus.PENDING);
        for (CardSettingsRequest pr : pending) {
            if (pr.getRequestType() == requestType) {
                throw new IllegalStateException("A pending request for this setting already exists");
            }
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CardSettingsRequest request = new CardSettingsRequest();
        request.setCard(card);
        request.setRequestType(requestType);
        request.setRequestedValue(requestedValue);
        request.setRequestedCardType(requestedCardType);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestedBy(customer.getUser());

        CardSettingsRequest saved = requestRepository.save(request);

        // Notify authorities
        String maskedCard = card.getCardNumber().length() > 4
                ? "****" + card.getCardNumber().substring(card.getCardNumber().length() - 4)
                : card.getCardNumber();
        notificationUtil.notifyAuthorities(
                NotificationType.GENERAL,
                "Card Settings Request",
                "Customer " + customer.getName() + " has requested " + requestType + " change for card " + maskedCard + ".",
                String.valueOf(saved.getId()),
                "CARD_SETTINGS_REQUEST"
        );

        return saved;
    }

    @Override
    public List<CardSettingsRequest> getRequestsByStatus(RequestStatus status) {
        return requestRepository.findByStatus(status);
    }

    @Override
    public List<CardSettingsRequest> getRequestsByCustomerId(Long customerId) {
        return requestRepository.findByRequestedById(customerId);
    }

    @Override
    public List<CardSettingsRequest> getRequestsByCardId(Long cardId) {
        return cardRepository.findById(cardId)
                .map(card -> requestRepository.findByCardIdAndStatus(cardId, RequestStatus.PENDING))
                .orElse(List.of());
    }

    @Override
    @Transactional
    public CardSettingsRequest approveRequest(Long requestId) {
        CardSettingsRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Request is not pending");
        }

        request.setStatus(RequestStatus.APPROVED);

        Card card = request.getCard();
        switch (request.getRequestType()) {
            case INTERNATIONAL_ENABLED -> card.setInternationalEnabled(request.isRequestedValue());
            case ONLINE_TRANSACTION_ENABLED -> card.setOnlineTransactionEnabled(request.isRequestedValue());
            case CARD_TYPE_CHANGE -> {
                if (request.getRequestedCardType() != null) {
                    card.setCardType(request.getRequestedCardType());
                    card.setStatus(com.elitetech_inc.ensarkbank.common.enums.CardStatus.PENDING);
                }
            }
        }
        cardRepository.save(card);

        CardSettingsRequest saved = requestRepository.save(request);

        // Notify customer
        notifyCardRequestCustomer(request, "APPROVED");

        return saved;
    }

    @Override
    public CardSettingsRequest rejectRequest(Long requestId, String reason) {
        CardSettingsRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Request is not pending");
        }

        request.setStatus(RequestStatus.REJECTED);
        request.setRejectionReason(reason);

        CardSettingsRequest saved = requestRepository.save(request);

        // Notify customer
        notifyCardRequestCustomer(request, "REJECTED");

        return saved;
    }

    private void notifyCardRequestCustomer(CardSettingsRequest request, String status) {
        if (request.getRequestedBy() == null) return;

        var user = request.getRequestedBy();
        String maskedCard = request.getCard().getCardNumber().length() > 4
                ? "****" + request.getCard().getCardNumber().substring(request.getCard().getCardNumber().length() - 4)
                : request.getCard().getCardNumber();

        String title = "Card Request " + status;
        String message = "Your card settings request (" + request.getRequestType() + ") for card " +
                maskedCard + " has been " + status.toLowerCase() + ".";

        notificationUtil.notifyUser(user.getId(), NotificationType.CARD_STATUS_CHANGED, title, message,
                String.valueOf(request.getId()), "CARD_SETTINGS_REQUEST");

        emailUtil.sendCardStatusEmail(user.getEmail(), user.getUsername(), maskedCard, status);
    }
}
