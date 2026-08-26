package com.elitetech_inc.ensarkbank.report_management.ledger.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LedgerReportResponse {
    private Long branchId;
    private String branchName;
    private String accountNumber;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private List<LedgerReportLineResponse> entries;
}
