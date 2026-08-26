package com.elitetech_inc.ensarkbank.report_management.ledger.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LedgerReportLineResponse {
    private Long journalId;
    private LocalDateTime date;
    private String transactionId;
    private String particulars;
    private String accountNumber;
    private String accountName;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;
}
