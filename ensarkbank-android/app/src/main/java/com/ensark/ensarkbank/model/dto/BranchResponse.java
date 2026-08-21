package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.BranchStatus;
import com.ensark.ensarkbank.model.enums.BranchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private Long id;
    private String name;
    private String address;
    private String routingNumber;
    private String branchCode;
    private String email;
    private String phoneNumber;
    private BranchType type;
    private BranchStatus status;
}
