package com.elitetech_inc.ensarkbank.account_management.card.dto.request;

import com.elitetech_inc.ensarkbank.common.enums.CardNetwork;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import lombok.Data;

@Data
public class CardRequest {
    private Long accountId;
    private CardNetwork cardNetwork;
    private CardType cardType;
    private String pin;
    private boolean isInternationalEnabled;
    private boolean isOnlineTransactionEnabled;

}
