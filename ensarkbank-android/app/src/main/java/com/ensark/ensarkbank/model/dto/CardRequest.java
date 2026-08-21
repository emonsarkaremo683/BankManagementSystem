package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.CardNetwork;
import com.ensark.ensarkbank.model.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    private Long accountId;
    private CardNetwork cardNetwork;
    private CardType cardType;
    private String pin;
    private boolean isInternationalEnabled;
    private boolean isOnlineTransactionEnabled;
}
