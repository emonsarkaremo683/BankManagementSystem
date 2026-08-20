package com.elitetech_inc.ensarkbank.common.address.address.dto.response;

import com.elitetech_inc.ensarkbank.common.enums.AddressType;
import lombok.Data;

@Data
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
