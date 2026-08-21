package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycRequest {
    private Long id;
    private String path;
    private DocumentType doc_type;
}
