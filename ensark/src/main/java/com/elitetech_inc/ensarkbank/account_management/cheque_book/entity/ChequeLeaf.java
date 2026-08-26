package com.elitetech_inc.ensarkbank.account_management.cheque_book.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.ChequeLeafStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cheque_leaves")
@Data
public class ChequeLeaf extends BaseEntity {

    @Column(nullable = false)
    private int leafNumber;

    @Column(nullable = false)
    private String chequeNumber;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    private String payeeName;

    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChequeLeafStatus status = ChequeLeafStatus.UNUSED;

    private LocalDate issueDate;
    private LocalDate clearanceDate;
    private LocalDate expiryDate;

    private String bounceReason;

    private String transactionReference;

    @Enumerated(EnumType.STRING)
    private com.elitetech_inc.ensarkbank.common.enums.PresentmentChannel presentmentChannel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cheque_book_id", nullable = false)
    @JsonIgnore
    private ChequeBook chequeBook;
}
