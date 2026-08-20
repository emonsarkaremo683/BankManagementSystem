package com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.response;

import com.elitetech_inc.ensarkbank.common.address.address.dto.response.AddressResponse;
import com.elitetech_inc.ensarkbank.common.enums.Designation;
import com.elitetech_inc.ensarkbank.common.enums.EmployeeStatus;
import com.elitetech_inc.ensarkbank.common.enums.Gender;
import com.elitetech_inc.ensarkbank.common.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class EmployeeResponse {
    private Long user_id;
    private String email;
    private String password;
    private Role role;
    @JsonProperty("isEmailVerified")
    private boolean isEmailVerified;
    private boolean active;

    private Long id;
    private String name;
    private Gender gender;
    private String phone;
    private Designation designation;
    private EmployeeStatus status;
    private Date dob;
    private String profile;

    private String branchName;
    private Long branchId;

    private List<AddressResponse> addresses;
    private String imageUrl;
    private String createdAt;
}
