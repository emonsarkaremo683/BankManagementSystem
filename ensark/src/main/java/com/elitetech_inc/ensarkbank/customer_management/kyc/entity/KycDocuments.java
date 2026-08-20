package com.elitetech_inc.ensarkbank.customer_management.kyc.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "kyc_documents", uniqueConstraints = @UniqueConstraint(columnNames = {"kyc_id", "doc_type"}))
@Data
public class KycDocuments extends BaseEntity {

    @Column(nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType doc_type;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kyc_id")
    @JsonIgnore
    private Kyc kyc;
}
