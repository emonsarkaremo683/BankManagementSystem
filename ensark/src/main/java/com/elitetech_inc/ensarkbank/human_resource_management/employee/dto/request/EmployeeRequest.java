package com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.request;

import com.elitetech_inc.ensarkbank.common.address.address.dto.request.AddressRequest;
import com.elitetech_inc.ensarkbank.common.enums.CustomerOccupation;
import com.elitetech_inc.ensarkbank.common.enums.Designation;
import com.elitetech_inc.ensarkbank.common.enums.Gender;
import com.elitetech_inc.ensarkbank.common.enums.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EmployeeRequest {

    private String email;
    private String password;
    private Role role;

    private Long branchId;

    private String name;
    private Gender gender;
    private String phone;
    private Designation designation;
    private Date dob;
    private String profile;
    private List<AddressRequest> addresses = new ArrayList<>();
}
