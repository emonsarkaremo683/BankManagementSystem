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
public class AddressRequest {
    private String holdingNo;
    private String area;
    private String postalCode;
    private AddressType addressType;
    private Long policeStationId;
}
