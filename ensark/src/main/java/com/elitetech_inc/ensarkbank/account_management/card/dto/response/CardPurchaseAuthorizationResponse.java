package com.elitetech_inc.ensarkbank.account_management.card.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response for CardService#authorizePurchase. Deliberately a flat DTO rather
 * than the HoldTransaction entity: HoldTransaction carries a lazy Account
 * relation with no @JsonIgnore, so serializing it directly would either
 * trigger lazy-loading of the full account graph or fail once the request's
 * persistence context closes.
 */
@Data
@Builder
public class CardPurchaseAuthorizationResponse {
    private Long holdId;
    private String authorizationReference;
    private BigDecimal amount;
    private LocalDateTime expiresAt;
}
