package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.CardType;
import com.ensark.ensarkbank.model.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardSettingsRequest {
    private Long id;
    private Long cardId;
    private RequestType requestType;
    private boolean requestedValue;
    private CardType requestedCardType;
    private RequestStatus status;
    private String rejectionReason;
    private Long requestedById;

    public enum RequestType {
        INTERNATIONAL_ENABLED,
        ONLINE_TRANSACTION_ENABLED,
        CARD_TYPE_CHANGE
    }
}
