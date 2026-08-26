package com.elitetech_inc.ensarkbank.report_management.profit_and_loss.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.report_management.profit_and_loss.dto.response.ProfitAndLossLine;
import com.elitetech_inc.ensarkbank.report_management.profit_and_loss.dto.response.ProfitAndLossReportResponse;
import com.elitetech_inc.ensarkbank.report_management.profit_and_loss.dto.response.ProfitAndLossSection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfitAndLossReportServiceImpl implements ProfitAndLossReportService {

    private final AccountRepository accountRepository;

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    @Override
    @Transactional(readOnly = true)
    public ProfitAndLossReportResponse getAll() {
        ProfitAndLossReportResponse response = new ProfitAndLossReportResponse();

        List<ProfitAndLossLine> incomeLines = new ArrayList<>();
        List<ProfitAndLossLine> expenseLines = new ArrayList<>();

        List<Account> allAccounts = accountRepository.findAll();
        for (Account account : allAccounts) {
            AccountCategory category = account.getCategory();
            if (category == null) continue;

            BigDecimal balance = account.getCurrentBalance() != null
                    ? account.getCurrentBalance()
                    : (account.getAvailableBalance() != null ? account.getAvailableBalance() : ZERO);

            if (balance.compareTo(ZERO) == 0) continue;

            String custName = !account.getHolders().isEmpty() && account.getHolders().getFirst().getCustomer() != null
                    ? account.getHolders().getFirst().getCustomer().getName()
                    : "Account";

            ProfitAndLossLine line = new ProfitAndLossLine();
            line.setAccountNumber(account.getAccountNumber());
            line.setAccountName(custName + " (" + account.getAccountNumber() + ")");

            switch (category) {
                case INCOME -> {
                    line.setAmount(balance.abs());
                    incomeLines.add(line);
                }
                case EXPENSE -> {
                    line.setAmount(balance.abs());
                    expenseLines.add(line);
                }
                default -> { }
            }
        }

        ProfitAndLossSection incomeSection = new ProfitAndLossSection();
        incomeSection.setTitle("Income");
        incomeSection.setLines(incomeLines);
        incomeSection.setTotal(incomeLines.stream()
                .map(ProfitAndLossLine::getAmount)
                .reduce(ZERO, BigDecimal::add));

        ProfitAndLossSection expenseSection = new ProfitAndLossSection();
        expenseSection.setTitle("Expenses");
        expenseSection.setLines(expenseLines);
        expenseSection.setTotal(expenseLines.stream()
                .map(ProfitAndLossLine::getAmount)
                .reduce(ZERO, BigDecimal::add));

        response.setIncome(incomeSection);
        response.setExpenses(expenseSection);
        response.setNetProfit(incomeSection.getTotal().subtract(expenseSection.getTotal()));

        return response;
    }
}
