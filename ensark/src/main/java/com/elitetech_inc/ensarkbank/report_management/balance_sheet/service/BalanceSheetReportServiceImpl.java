package com.elitetech_inc.ensarkbank.report_management.balance_sheet.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportResponse;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportSection;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportSectionLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BalanceSheetReportServiceImpl implements BalanceSheetReportService {

    private final AccountRepository accountRepository;
    private final com.elitetech_inc.ensarkbank.report_management.ReportExportService reportExportService;

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    @Override
    @Transactional(readOnly = true)
    public BalanceSheetReportResponse getAll() {
        return buildBalanceSheet(null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public BalanceSheetReportResponse getAllByTimeSpan(LocalDate from, LocalDate to) {
        return buildBalanceSheet(from, to);
    }

    private BalanceSheetReportResponse buildBalanceSheet(LocalDate fromDate, LocalDate toDate) {
        BalanceSheetReportResponse response = new BalanceSheetReportResponse();
        response.setBranchId(null);
        response.setBranchName("ALL BRANCHES");

        BalanceSheetReportSection assets = new BalanceSheetReportSection();
        assets.setTitle("Assets");
        BalanceSheetReportSection liabilities = new BalanceSheetReportSection();
        liabilities.setTitle("Liabilities");
        BalanceSheetReportSection equity = new BalanceSheetReportSection();
        equity.setTitle("Equity");

        Map<String, BalanceSheetReportSectionLine> assetLines = new LinkedHashMap<>();
        Map<String, BalanceSheetReportSectionLine> liabilityLines = new LinkedHashMap<>();
        Map<String, BalanceSheetReportSectionLine> equityLines = new LinkedHashMap<>();

        List<Account> allAccounts = accountRepository.findAll();
        for (Account account : allAccounts) {
            BigDecimal balance = account.getCurrentBalance() != null ? account.getCurrentBalance() : (account.getAvailableBalance() != null ? account.getAvailableBalance() : ZERO);

            AccountCategory category = account.getCategory();
            if (category == null && account.getAccountType() != null) {
                String typeStr = account.getAccountType().name();
                if (typeStr.contains("LOAN") || typeStr.contains("MORTGAGE") || typeStr.contains("OVERDRAFT")) {
                    category = AccountCategory.ASSET;
                } else {
                    category = AccountCategory.LIABILITY;
                }
            }
            if (category == null) {
                category = AccountCategory.LIABILITY;
            }

            BalanceSheetReportSectionLine line = new BalanceSheetReportSectionLine();
            line.setGlCode("GL-ACC-" + account.getId());
            String custName = !account.getHolders().isEmpty() && account.getHolders().getFirst().getCustomer() != null ? account.getHolders().getFirst().getCustomer().getName() : "Account";
            line.setAccountName(custName + " (" + account.getAccountNumber() + ")");
            line.setAmount(balance.abs());

            switch (category) {
                case ASSET -> put(assetLines, "ACC-" + account.getId(), line);
                case LIABILITY -> put(liabilityLines, "ACC-" + account.getId(), line);
                case EQUITY -> put(equityLines, "ACC-" + account.getId(), line);
                case INCOME -> put(equityLines, "INC-" + account.getId(), line);
                case EXPENSE -> {
                    line.setAmount(balance.abs().negate());
                    put(equityLines, "EXP-" + account.getId(), line);
                }
            }
        }

        assets.setLines(new ArrayList<>(assetLines.values()));
        liabilities.setLines(new ArrayList<>(liabilityLines.values()));
        equity.setLines(new ArrayList<>(equityLines.values()));
        assets.setTotal(sum(assets.getLines()));
        liabilities.setTotal(sum(liabilities.getLines()));
        equity.setTotal(sum(equity.getLines()));

        BigDecimal totalAssets = assets.getTotal();
        BigDecimal totalLiabilities = liabilities.getTotal();
        BigDecimal totalEquity = equity.getTotal();

        response.setAssets(assets);
        response.setLiabilities(liabilities);
        response.setEquity(equity);
        response.setTotalAssets(totalAssets);
        response.setTotalLiabilitiesAndEquity(totalLiabilities.add(totalEquity));
        return response;
    }

    private void put(Map<String, BalanceSheetReportSectionLine> map, String key, BalanceSheetReportSectionLine line) {
        map.merge(key, line, (a, b) -> {
            a.setAmount(a.getAmount().add(b.getAmount()));
            return a;
        });
    }

    private BigDecimal sum(List<BalanceSheetReportSectionLine> lines) {
        return lines.stream()
                .map(BalanceSheetReportSectionLine::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportToPdf(LocalDate from, LocalDate to) {
        BalanceSheetReportResponse report = getAllByTimeSpan(from, to);
        return reportExportService.exportBalanceSheetToPdf(report);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportToExcel(LocalDate from, LocalDate to) {
        BalanceSheetReportResponse report = getAllByTimeSpan(from, to);
        return reportExportService.exportBalanceSheetToExcel(report);
    }
}
