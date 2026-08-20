package com.elitetech_inc.ensarkbank.fraud_detection.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.FraudFlagStatus;
import com.elitetech_inc.ensarkbank.common.enums.FraudRiskLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fraud_flags")
@Data
public class FraudFlag extends BaseEntity {

    private Long userId;

    private Long transactionId;

    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FraudRiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FraudFlagStatus status = FraudFlagStatus.PENDING;

    @Column(nullable = false)
    private String reason;

    private String details;

    private String ipAddress;

    private String deviceInfo;

    private String location;

    @Column(precision = 19, scale = 4)
    private BigDecimal flaggedAmount;

    private String reviewedBy;

    private String reviewNotes;
}
