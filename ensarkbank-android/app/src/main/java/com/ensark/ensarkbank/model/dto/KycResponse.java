package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.KYCStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycResponse {
    private Long id;
    private KYCStatus status;
    private String rejectionReason;
    private List<KycDocumentResponse> documents;
}
