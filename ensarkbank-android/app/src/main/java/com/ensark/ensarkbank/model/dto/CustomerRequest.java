package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.CustomerOccupation;
import com.ensark.ensarkbank.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String phone;
    private CustomerOccupation occupation;
    private Date dob;
    private String profile;
    @Builder.Default
    private List<AddressRequest> addresses = new ArrayList<>();
    private List<KycRequest> kycRequests;
}
