package com.elitetech_inc.ensarkbank.common.address.policestation.entity;


import com.elitetech_inc.ensarkbank.common.address.district.entity.District;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "policestations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoliceStation extends BaseEntity {


    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnoreProperties({"division", "policeStations"})
    private District district;

//    @OneToMany(mappedBy = "policeStation",  fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Branch> branches;

}
