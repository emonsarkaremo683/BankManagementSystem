package com.elitetech_inc.ensarkbank.account_management.nominee.entity;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.enums.NomineeRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "nominees")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Nominee extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NomineeRelation relation;


    @Column(nullable = false)
    private String photo;
    @Column(nullable = false)
    private String nid_front;
    @Column(nullable = false)
    private String nid_back;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;


}
