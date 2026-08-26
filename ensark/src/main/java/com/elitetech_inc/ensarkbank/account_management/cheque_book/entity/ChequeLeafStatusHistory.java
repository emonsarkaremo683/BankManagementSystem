package com.elitetech_inc.ensarkbank.account_management.cheque_book.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.ChequeLeafStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cheque_leaf_status_history")
@Data
public class ChequeLeafStatusHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cheque_leaf_id", nullable = false)
    private ChequeLeaf chequeLeaf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChequeLeafStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChequeLeafStatus toStatus;

    private String reason;

    private String performedBy;
}
