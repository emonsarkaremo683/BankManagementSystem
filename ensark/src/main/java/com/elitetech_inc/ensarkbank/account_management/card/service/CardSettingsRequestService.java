package com.elitetech_inc.ensarkbank.account_management.card.service;

import com.elitetech_inc.ensarkbank.account_management.card.entity.CardSettingsRequest;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.common.enums.RequestStatus;

import java.util.List;

public interface CardSettingsRequestService {
    CardSettingsRequest createRequest(Long cardId, CardSettingsRequest.RequestType requestType, boolean requestedValue, Long customerId, CardType requestedCardType);
    List<CardSettingsRequest> getRequestsByStatus(RequestStatus status);
    List<CardSettingsRequest> getRequestsByCustomerId(Long customerId);
    List<CardSettingsRequest> getRequestsByCardId(Long cardId);
    CardSettingsRequest approveRequest(Long requestId);
    CardSettingsRequest rejectRequest(Long requestId, String reason);
}
