package com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BalanceSheetReportSection {
    private String title;
    private List<BalanceSheetReportSectionLine> lines;
    private BigDecimal total;
}
