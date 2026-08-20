package com.elitetech_inc.ensarkbank.common.address.address.dto.request;

import com.elitetech_inc.ensarkbank.common.address.policestation.entity.PoliceStation;
import com.elitetech_inc.ensarkbank.common.enums.AddressType;
import lombok.Data;

@Data
public class AddressRequest {
    private String holdingNo;
    private String area;
    private String postalCode;

    private AddressType addressType;

    private PoliceStation policeStation;
}
