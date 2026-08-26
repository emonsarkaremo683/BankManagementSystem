package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.TransactionChannel;
import com.ensark.ensarkbank.model.enums.TransactionStatus;
import com.ensark.ensarkbank.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String referenceNo;
    private TransactionType transactionType;
    private TransactionChannel channel;
    private TransactionStatus status;
    private BigDecimal amount;
    private BigDecimal chargeAmount;
    private BigDecimal vatAmount;
    private String remarks;
    private Date createdAt;
    @Builder.Default
    private List<JournalResponse> journals = new ArrayList<>();
}
