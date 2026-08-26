package com.elitetech_inc.ensarkbank.account_management.account_transaction.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.OtpStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transaction_otp")
@Data
public class TransactionOtp extends BaseEntity {

    @ToString.Exclude
    private String otpCode;

    private String customerEmail;

    private String accountNumber;

    @ToString.Exclude
    @Column(columnDefinition = "TEXT")
    private String pendingTransactionPayload;

    @Enumerated(EnumType.STRING)
    private OtpStatus status;

    private int attemptCount;

    private LocalDateTime expiresAt;
}
