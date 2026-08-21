package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.CustomerOccupation;
import com.ensark.ensarkbank.model.enums.CustomerStatus;
import com.ensark.ensarkbank.model.enums.Gender;
import com.ensark.ensarkbank.model.enums.KYCStatus;
import com.ensark.ensarkbank.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String email;
    private Role role;
    private boolean isEmailVerified;
    private boolean active;
    private String name;
    private Gender gender;
    private String phone;
    private CustomerOccupation occupation;
    private Date dob;
    private String profile;
    private List<AddressResponse> addresses;
    private List<KycRequest> documents;
    private KYCStatus kycStatus;
    private CustomerStatus status;
}
