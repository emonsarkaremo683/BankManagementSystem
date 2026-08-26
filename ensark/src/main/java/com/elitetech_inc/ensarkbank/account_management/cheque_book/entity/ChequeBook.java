package com.elitetech_inc.ensarkbank.account_management.cheque_book.entity;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.ChequeBookStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cheque_books")
@Data
public class ChequeBook extends BaseEntity {

    @Column(nullable = false)
    private int numberOfLeaves;

    @Column(nullable = false)
    private int startLeafNumber;

    @Column(nullable = false)
    private int endLeafNumber;

    @Column(nullable = false)
    private String bookSerialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChequeBookStatus status = ChequeBookStatus.REQUESTED;

    private LocalDate applicationDate = LocalDate.now();
    private LocalDate approvalDate;
    private LocalDate deliveryDate;
    private LocalDate activationDate;
    private LocalDate expiryDate;

    private String rejectionReason;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "chequeBook", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ChequeLeaf> leaves = new ArrayList<>();
}
