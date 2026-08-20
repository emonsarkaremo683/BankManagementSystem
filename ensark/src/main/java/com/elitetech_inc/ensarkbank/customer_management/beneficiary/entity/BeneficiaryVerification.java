package com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.OtpStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "beneficiary_verifications")
@Data
public class BeneficiaryVerification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "beneficiary_id", nullable = false)
    private Beneficiary beneficiary;

    @Column(nullable = false)
    private String otpCode;

    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpStatus status = OtpStatus.PENDING;

    private int attemptCount;

    private LocalDateTime expiresAt;
}
