package com.elitetech_inc.ensarkbank.report_management.profit_and_loss.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProfitAndLossSection {
    private String title;
    private List<ProfitAndLossLine> lines;
    private BigDecimal total;
}
