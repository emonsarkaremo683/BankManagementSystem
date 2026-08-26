package com.elitetech_inc.ensarkbank.customer_management.customer.dto.request;

import com.elitetech_inc.ensarkbank.common.address.address.dto.request.AddressRequest;
import com.elitetech_inc.ensarkbank.common.address.address.entity.Address;
import com.elitetech_inc.ensarkbank.common.address.policestation.entity.PoliceStation;
import com.elitetech_inc.ensarkbank.common.enums.AddressType;
import com.elitetech_inc.ensarkbank.common.enums.CustomerOccupation;
import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.enums.Gender;
import com.elitetech_inc.ensarkbank.customer_management.kyc.dto.request.KycRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CustomerRequest {

    private String email;
    private String password;

    private String name;
    private Gender gender;
    private String phone;
    private CustomerOccupation occupation;
    private Date dob;
    private String profile;


    private List<AddressRequest> addresses = new ArrayList<>();
    private List<KycRequest> kycRequests;

}
