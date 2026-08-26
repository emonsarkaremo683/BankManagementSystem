package com.elitetech_inc.ensarkbank.account_management.hold_transaction.entity;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.credit_account.entity.CreditAccount;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.HoldReason;
import com.elitetech_inc.ensarkbank.common.enums.HoldStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hold_transactions")
@Data
public class HoldTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_account_id")
    private CreditAccount creditAccount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HoldReason reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HoldStatus status;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private Long relatedTransactionId;

    private String authorizationReference;

    private String merchantInfo;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    @PrePersist
    @PreUpdate
    private void validateHoldTarget() {
        if ((account == null && creditAccount == null) || (account != null && creditAccount != null)) {
            throw new IllegalStateException("Hold must be against either a deposit Account or a CreditAccount, but not both or neither");
        }
    }
}
