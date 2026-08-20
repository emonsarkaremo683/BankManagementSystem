package com.elitetech_inc.ensarkbank.common.address.address.entity;


import com.elitetech_inc.ensarkbank.common.address.policestation.entity.PoliceStation;
import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity{

    private String holdingNo;
    private String area;
    private String postalCode;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "policestation_id")
    @JsonIgnore
    private PoliceStation policeStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
