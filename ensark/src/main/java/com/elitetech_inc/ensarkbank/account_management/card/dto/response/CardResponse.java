package com.elitetech_inc.ensarkbank.account_management.card.dto.response;

import com.elitetech_inc.ensarkbank.common.enums.CardNetwork;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class CardResponse {
    private Long cardId;
    private String cardNumber;
    private String cardHolderName;
    private CardNetwork cardNetwork;
    private CardType cardType;
    private CardStatus status;
    private Date expiryDate;
    private BigDecimal dailyLimit;
    private BigDecimal monthlyLimit;
    private String accountNumber;
    private boolean isInternationalEnabled;
    private boolean isOnlineTransactionEnabled;
    private Date createdAt;
}
