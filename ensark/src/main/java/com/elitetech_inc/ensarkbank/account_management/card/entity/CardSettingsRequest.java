package com.elitetech_inc.ensarkbank.account_management.card.entity;

import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.common.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "card_settings_requests")
@Data
public class CardSettingsRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    @JsonIgnoreProperties({"account", "pinHash", "cvv"})
    private Card card;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType requestType;

    private boolean requestedValue;

    @Enumerated(EnumType.STRING)
    private CardType requestedCardType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    private String rejectionReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by", nullable = false)
    @JsonIgnoreProperties({"addresses", "password"})
    private User requestedBy;

    public enum RequestType {
        INTERNATIONAL_ENABLED,
        ONLINE_TRANSACTION_ENABLED,
        CARD_TYPE_CHANGE
    }
}
