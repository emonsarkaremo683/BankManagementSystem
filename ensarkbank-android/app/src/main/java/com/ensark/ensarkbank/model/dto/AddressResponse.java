package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long id;
    private String holdingNo;
    private String area;
    private String postalCode;
    private AddressType addressType;
    private Long policeStationId;
    private String policeStationName;
    private Long districtId;
    private String districtName;
    private Long divisionId;
    private String divisionName;
}
