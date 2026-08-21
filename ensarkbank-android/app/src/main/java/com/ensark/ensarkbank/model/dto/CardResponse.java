package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.CardNetwork;
import com.ensark.ensarkbank.model.enums.CardStatus;
import com.ensark.ensarkbank.model.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
