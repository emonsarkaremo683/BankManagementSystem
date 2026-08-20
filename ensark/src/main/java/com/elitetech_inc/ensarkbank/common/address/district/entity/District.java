package com.elitetech_inc.ensarkbank.common.address.district.entity;


import com.elitetech_inc.ensarkbank.common.address.division.entity.Division;
import com.elitetech_inc.ensarkbank.common.address.policestation.entity.PoliceStation;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "districts")
@Data

public class District extends BaseEntity {

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "divisionID")
    @JsonIgnoreProperties("districts")
    private Division division;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PoliceStation> policeStations;
}
