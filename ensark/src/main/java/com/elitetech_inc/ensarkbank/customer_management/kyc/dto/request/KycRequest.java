package com.elitetech_inc.ensarkbank.customer_management.kyc.dto.request;

import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import lombok.Data;

@Data
public class KycRequest {
    private Long id;
    private String path;
    private DocumentType doc_type;

}
