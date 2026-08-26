package com.elitetech_inc.ensarkbank.dashboard;

import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.account_management.loan.repository.LoanRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.*;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.repository.EmployeeRepository;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.repository.ChequeBookRepository;
import com.elitetech_inc.ensarkbank.fraud_detection.repository.FraudFlagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;
    private final CardRepository cardRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ChequeBookRepository chequeBookRepository;
    private final FraudFlagRepository fraudFlagRepository;

    private static final BigDecimal BD_ZERO = BigDecimal.ZERO;

    @Override
    @Transactional
    public DashboardResponse getDashboardData(List<Long> branchIds, Role role) {
        DashboardResponse response = new DashboardResponse();

        LocalDate today = LocalDate.now();
        LocalDateTime thisMonthStart = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastMonthStart = today.minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastMonthEnd = today.withDayOfMonth(1).atStartOfDay().minusNanos(1);

        if (branchIds == null) {
            populateGlobalStats(response, today, lastMonthStart, lastMonthEnd);
        } else {
            populateBranchStats(response, branchIds, today, lastMonthStart, lastMonthEnd);
        }

        populatePendingApprovals(response, branchIds, role);

        return response;
    }

    private void populateGlobalStats(DashboardResponse response, LocalDate today,
                                      LocalDateTime lastMonthStart, LocalDateTime lastMonthEnd) {
        response.setTotalAccounts(accountRepository.findAll().stream().filter(
                account -> account.getAccountNumber().startsWith("acc")
        ).count());
        response.setTotalCustomers(customerRepository.findAll().size());
        response.setTotalTransactions(transactionRepository.countAll());
        response.setTotalLoans(loanRepository.countAll());
        response.setTotalBalance(accountRepository.sumBalanceAll());
        response.setTotalActiveCards(cardRepository.countByStatus(CardStatus.ACTIVE));
        response.setTotalEmployees(employeeRepository.count());

        response.setTotalDeposit(nvl(transactionRepository.sumDepositAll()));
        response.setTotalWithdraw(nvl(transactionRepository.sumWithdrawAll()));
        response.setTotalTransfer(nvl(transactionRepository.sumTransferAll()));
        response.setCashInflow(nvl(transactionRepository.sumCashInflowAll()));
        response.setCashOutflow(nvl(transactionRepository.sumCashOutflowAll()));
        response.setTotalRevenue(nvl(transactionRepository.sumRevenueAll()));
        response.setTotalAssets(nvl(accountRepository.sumAssetBalanceAll()));
        response.setTotalLiabilities(nvl(accountRepository.sumLiabilityBalanceAll()));
        BigDecimal totalIncome = nvl(accountRepository.sumIncomeBalanceAll());
        BigDecimal totalExpense = nvl(accountRepository.sumExpenseBalanceAll());
        response.setTotalExpense(totalExpense.compareTo(BD_ZERO) > 0 ? totalExpense : response.getCashOutflow());

        response.setTransactionTrends(buildTransactionTrends(null));
        response.setAccountTypeDistribution(buildAccountTypeDistribution(null));
        response.setLoanStatusDistribution(buildLoanStatusDistribution(null));
        response.setTransactionTypeDistribution(buildTransactionTypeDistribution(null));
        response.setTransactionStatusDistribution(buildTransactionStatusDistribution(null));
        response.setBranchWiseSummary(buildBranchWiseSummary());

        long customersNow = customerRepository.findAll().size();
        long customersPrev = accountRepository.countDistinctCustomersByCreatedAtBetween(lastMonthStart, lastMonthEnd);
        response.setCustomersTrend(buildTrend(customersNow, customersPrev));

        long accountsNow = accountRepository.findAll().stream().filter(
                account -> account.getAccountNumber().startsWith("acc")
        ).count();
        long accountsPrev = accountRepository.countByCreatedAtBetween(lastMonthStart, lastMonthEnd);
        response.setAccountsTrend(buildTrend(accountsNow, accountsPrev));

        BigDecimal balanceNow = accountRepository.sumBalanceAll();
        response.setBalanceTrend(buildBalanceTrend(balanceNow, lastMonthStart, lastMonthEnd));

        long txnNow = transactionRepository.countAll();
        long txnPrev = transactionRepository.countByCreatedAtBetween(lastMonthStart, lastMonthEnd);
        response.setTransactionsTrend(buildTrend(txnNow, txnPrev));

        long loansNow = loanRepository.countAll();
        long loansPrev = loanRepository.countByCreatedAtBetween(lastMonthStart, lastMonthEnd);
        response.setLoansTrend(buildTrend(loansNow, loansPrev));

        long atmsNow = cardRepository.countByStatus(CardStatus.ACTIVE);
        response.setAtmsTrend(buildTrend(atmsNow, atmsNow));
    }

    private void populateBranchStats(DashboardResponse response, List<Long> branchIds,
                                      LocalDate today, LocalDateTime lastMonthStart, LocalDateTime lastMonthEnd) {
        response.setTotalAccounts(accountRepository.countByBranchIdIn(branchIds));
        response.setTotalCustomers(accountRepository.countDistinctCustomersByBranchIds(branchIds));
        response.setTotalTransactions(transactionRepository.countByBranchIds(branchIds));
        response.setTotalLoans(loanRepository.countByBranchIds(branchIds));
        response.setTotalBalance(accountRepository.sumBalanceByBranchIds(branchIds));
        response.setTotalActiveCards(cardRepository.countByStatusAndBranchIds(CardStatus.ACTIVE, branchIds));
        response.setTotalEmployees(employeeRepository.countByBranchIds(branchIds));

        response.setTotalDeposit(nvl(transactionRepository.sumDepositByBranchIds(branchIds)));
        response.setTotalWithdraw(nvl(transactionRepository.sumWithdrawByBranchIds(branchIds)));
        response.setTotalTransfer(nvl(transactionRepository.sumTransferByBranchIds(branchIds)));
        response.setCashInflow(nvl(transactionRepository.sumCashInflowByBranchIds(branchIds)));
        response.setCashOutflow(nvl(transactionRepository.sumCashOutflowByBranchIds(branchIds)));
        response.setTotalRevenue(nvl(transactionRepository.sumRevenueByBranchIds(branchIds)));
        response.setTotalAssets(nvl(accountRepository.sumAssetBalanceByBranchIds(branchIds)));
        response.setTotalLiabilities(nvl(accountRepository.sumLiabilityBalanceByBranchIds(branchIds)));

        response.setTransactionTrends(buildTransactionTrends(branchIds));
        response.setAccountTypeDistribution(buildAccountTypeDistribution(branchIds));
        response.setLoanStatusDistribution(buildLoanStatusDistribution(branchIds));
        response.setTransactionTypeDistribution(buildTransactionTypeDistribution(branchIds));
        response.setTransactionStatusDistribution(buildTransactionStatusDistribution(branchIds));

        long customersNow = accountRepository.countDistinctCustomersByBranchIds(branchIds);
        long customersPrev = accountRepository.countDistinctCustomersByBranchIdsAndCreatedAtBetween(branchIds, lastMonthStart, lastMonthEnd);
        response.setCustomersTrend(buildTrend(customersNow, customersPrev));

        long accountsNow = accountRepository.countByBranchIdIn(branchIds);
        long accountsPrev = accountRepository.countByBranchIdInAndCreatedAtBetween(branchIds, lastMonthStart, lastMonthEnd);
        response.setAccountsTrend(buildTrend(accountsNow, accountsPrev));

        BigDecimal balanceNow = accountRepository.sumBalanceByBranchIds(branchIds);
        response.setBalanceTrend(buildBalanceTrend(balanceNow, lastMonthStart, lastMonthEnd));

        long txnNow = transactionRepository.countByBranchIds(branchIds);
        long txnPrev = transactionRepository.countByBranchIdsAndCreatedAtBetween(branchIds, lastMonthStart, lastMonthEnd);
        response.setTransactionsTrend(buildTrend(txnNow, txnPrev));

        long loansNow = loanRepository.countByBranchIds(branchIds);
        long loansPrev = loanRepository.countByBranchIdsAndCreatedAtBetween(branchIds, lastMonthStart, lastMonthEnd);
        response.setLoansTrend(buildTrend(loansNow, loansPrev));

        long atmsNow = cardRepository.countByStatusAndBranchIds(CardStatus.ACTIVE, branchIds);
        response.setAtmsTrend(buildTrend(atmsNow, atmsNow));
    }

    private DashboardResponse.TrendData buildTrend(long current, long previous) {
        double pct = previous == 0 ? (current > 0 ? 100.0 : 0.0) : ((double)(current - previous) / previous) * 100;
        return new DashboardResponse.TrendData(Math.round(pct * 10.0) / 10.0, current, previous, pct >= 0);
    }

    private DashboardResponse.TrendData buildBalanceTrend(BigDecimal currentBalance, LocalDateTime lastMonthStart, LocalDateTime lastMonthEnd) {
        BigDecimal prevBalance = accountRepository.sumBalanceAll();
        if (prevBalance == null) prevBalance = BigDecimal.ZERO;
        double pct = prevBalance.compareTo(BigDecimal.ZERO) == 0
            ? (currentBalance.compareTo(BigDecimal.ZERO) > 0 ? 100.0 : 0.0)
            : currentBalance.subtract(prevBalance).doubleValue() / prevBalance.doubleValue() * 100;
        return new DashboardResponse.TrendData(Math.round(pct * 10.0) / 10.0, currentBalance.longValue(), prevBalance.longValue(), pct >= 0);
    }

    private List<DashboardResponse.TimeSeriesPoint> buildTransactionTrends(List<Long> branchIds) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");

        LocalDateTime start = today.minusDays(6).atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<Object[]> inflowOutflow;
        if (branchIds == null) {
            inflowOutflow = transactionRepository.sumInflowOutflowByDateAll(start, end);
        } else {
            inflowOutflow = transactionRepository.sumInflowOutflowByDate(start, end, branchIds);
        }

        Map<String, DashboardResponse.TimeSeriesPoint> dateMap = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String label = date.format(formatter);
            dateMap.put(label, new DashboardResponse.TimeSeriesPoint(label, 0, BD_ZERO, BD_ZERO, BD_ZERO));
        }

        for (Object[] row : inflowOutflow) {
            if (row[0] != null) {
                LocalDate date = LocalDate.parse(row[0].toString());
                String label = date.format(formatter);
                BigDecimal inflow = nvl((BigDecimal) row[1]);
                BigDecimal outflow = nvl((BigDecimal) row[2]);
                long count = inflow.add(outflow).compareTo(BD_ZERO) > 0 ? 1 : 0;
                dateMap.put(label, new DashboardResponse.TimeSeriesPoint(label, count, inflow.subtract(outflow), inflow, outflow));
            }
        }

        return new ArrayList<>(dateMap.values());
    }

    private List<DashboardResponse.LabelValue> buildAccountTypeDistribution(List<Long> branchIds) {
        List<Object[]> results;
        if (branchIds == null) {
            results = accountRepository.countByAccountTypeGroupedAll();
        } else {
            results = accountRepository.countByAccountTypeGrouped(branchIds);
        }

        List<DashboardResponse.LabelValue> distribution = new ArrayList<>();
        for (Object[] row : results) {
            AccountType type = (AccountType) row[0];
            long count = ((Number) row[1]).longValue();
            distribution.add(new DashboardResponse.LabelValue(type.name(), count, BD_ZERO));
        }
        return distribution;
    }

    private List<DashboardResponse.LabelValue> buildLoanStatusDistribution(List<Long> branchIds) {
        List<Object[]> results;
        if (branchIds == null) {
            results = loanRepository.countByStatusGroupedAll();
        } else {
            results = loanRepository.countByStatusGrouped(branchIds);
        }

        List<DashboardResponse.LabelValue> distribution = new ArrayList<>();
        for (Object[] row : results) {
            LoanStatus status = (LoanStatus) row[0];
            long count = ((Number) row[1]).longValue();
            distribution.add(new DashboardResponse.LabelValue(status.name(), count, BD_ZERO));
        }
        return distribution;
    }

    private List<DashboardResponse.LabelValue> buildTransactionTypeDistribution(List<Long> branchIds) {
        List<Object[]> results;
        if (branchIds == null) {
            results = transactionRepository.countByTransactionTypeGroupedAll();
        } else {
            results = transactionRepository.countByTransactionTypeGrouped(branchIds);
        }

        List<DashboardResponse.LabelValue> distribution = new ArrayList<>();
        for (Object[] row : results) {
            TransactionType type = (TransactionType) row[0];
            long count = ((Number) row[1]).longValue();
            distribution.add(new DashboardResponse.LabelValue(type.name(), count, BD_ZERO));
        }
        return distribution;
    }

    private List<DashboardResponse.LabelValue> buildTransactionStatusDistribution(List<Long> branchIds) {
        List<Object[]> results;
        if (branchIds == null) {
            results = transactionRepository.countByStatusGroupedAll();
        } else {
            results = transactionRepository.countByStatusGrouped(branchIds);
        }

        List<DashboardResponse.LabelValue> distribution = new ArrayList<>();
        for (Object[] row : results) {
            TransactionStatus status = (TransactionStatus) row[0];
            long count = ((Number) row[1]).longValue();
            distribution.add(new DashboardResponse.LabelValue(status.name(), count, BD_ZERO));
        }
        return distribution;
    }

    private List<DashboardResponse.BranchSummary> buildBranchWiseSummary() {
        List<Object[]> results = accountRepository.getBranchWiseSummary();
        List<DashboardResponse.BranchSummary> summaries = new ArrayList<>();

        for (Object[] row : results) {
            Long branchId = ((Number) row[0]).longValue();
            String branchName = (String) row[1];
            long accountCount = ((Number) row[2]).longValue();
            long customerCount = ((Number) row[3]).longValue();
            BigDecimal totalBalance = (BigDecimal) row[4];

            List<Long> singleBranch = List.of(branchId);
            BigDecimal deposit = nvl(transactionRepository.sumDepositByBranchIds(singleBranch));
            BigDecimal withdraw = nvl(transactionRepository.sumWithdrawByBranchIds(singleBranch));
            BigDecimal transfer = nvl(transactionRepository.sumTransferByBranchIds(singleBranch));
            BigDecimal inflow = nvl(transactionRepository.sumCashInflowByBranchIds(singleBranch));
            BigDecimal outflow = nvl(transactionRepository.sumCashOutflowByBranchIds(singleBranch));
            BigDecimal revenue = nvl(transactionRepository.sumRevenueByBranchIds(singleBranch));
            BigDecimal assets = nvl(accountRepository.sumAssetBalanceByBranchIds(singleBranch));
            BigDecimal liabilities = nvl(accountRepository.sumLiabilityBalanceByBranchIds(singleBranch));

            summaries.add(new DashboardResponse.BranchSummary(
                    branchId,
                    branchName,
                    accountCount,
                    customerCount,
                    transactionRepository.countByBranchIds(singleBranch),
                    deposit,
                    withdraw,
                    totalBalance,
                    BD_ZERO,
                    0,
                    revenue,
                    outflow,
                    transfer,
                    assets,
                    liabilities,
                    inflow,
                    outflow,
                    employeeRepository.countByBranchIds(singleBranch)
            ));
        }
        return summaries;
    }

    private BigDecimal nvl(BigDecimal val) {
        return val == null ? BD_ZERO : val;
    }

    private void populatePendingApprovals(DashboardResponse response, List<Long> branchIds, Role role) {
        List<DashboardResponse.ApprovalQueueItem> approvals = new ArrayList<>();
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 1. KYC Reviews
        if (role == Role.SUPER_ADMIN || role == Role.ADMIN || role == Role.BRANCH_MANAGER || role == Role.CUSTOMER_SERVICE || role == Role.CASHIER) {
            List<com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer> kycCustomers = 
                customerRepository.findByKycStatusInAndBranchIn(List.of(KYCStatus.PENDING, KYCStatus.UNDER_REVIEW), branchIds);
            for (com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer c : kycCustomers) {
                String customerBranch = "Global";
                if (c.getKyc() != null && c.getKyc().getCustomer() != null) {
                    List<com.elitetech_inc.ensarkbank.account_management.account.entity.Account> customerAccounts = accountRepository.findDistinctByHoldersCustomerId(c.getId());
                    if (!customerAccounts.isEmpty() && customerAccounts.get(0).getBranch() != null) {
                        customerBranch = customerAccounts.get(0).getBranch().getName();
                    }
                }
                
                approvals.add(new DashboardResponse.ApprovalQueueItem(
                    c.getId(),
                    "KYC",
                    c.getName(),
                    BigDecimal.ZERO,
                    c.getKyc() != null && c.getKyc().getStatus() != null ? c.getKyc().getStatus().name() : "PENDING",
                    "/staff/customers/kyc",
                    customerBranch,
                    c.getCreatedAt() != null ? c.getCreatedAt().format(dtFormatter) : "N/A"
                ));
            }
        }

        // 2. Loans
        if (role == Role.SUPER_ADMIN || role == Role.ADMIN || role == Role.BRANCH_MANAGER || role == Role.LOAN_OFFICER) {
            List<com.elitetech_inc.ensarkbank.account_management.loan.entity.Loan> loans = 
                loanRepository.findByStatusAndBranchIn(LoanStatus.PENDING, branchIds);
            for (com.elitetech_inc.ensarkbank.account_management.loan.entity.Loan l : loans) {
                String customerName = !l.getAccount().getHolders().isEmpty() ? l.getAccount().getHolders().get(0).getCustomer().getName() : "Unknown";
                String branchName = l.getAccount().getBranch() != null ? l.getAccount().getBranch().getName() : "N/A";
                approvals.add(new DashboardResponse.ApprovalQueueItem(
                    l.getId(),
                    "LOAN",
                    customerName,
                    l.getPrincipalAmount(),
                    l.getStatus().name(),
                    "/staff/loans/" + l.getId(),
                    branchName,
                    l.getCreatedAt() != null ? l.getCreatedAt().format(dtFormatter) : "N/A"
                ));
            }
        }

        // 3. Accounts
        if (role == Role.SUPER_ADMIN || role == Role.ADMIN || role == Role.BRANCH_MANAGER || role == Role.CASHIER) {
            List<com.elitetech_inc.ensarkbank.account_management.account.entity.Account> accounts = 
                accountRepository.findByStatusAndBranchIn(AccountStatus.PENDING, branchIds);
            for (com.elitetech_inc.ensarkbank.account_management.account.entity.Account a : accounts) {
                String customerName = !a.getHolders().isEmpty() ? a.getHolders().get(0).getCustomer().getName() : "Unknown";
                String branchName = a.getBranch() != null ? a.getBranch().getName() : "N/A";
                approvals.add(new DashboardResponse.ApprovalQueueItem(
                    a.getId(),
                    "ACCOUNT",
                    customerName,
                    a.getAvailableBalance(),
                    a.getAccountStatus().name(),
                    "/staff/accounts/" + a.getId(),
                    branchName,
                    a.getCreatedAt() != null ? a.getCreatedAt().format(dtFormatter) : "N/A"
                ));
            }
        }

        // 4. ChequeBooks
        if (role == Role.SUPER_ADMIN || role == Role.ADMIN || role == Role.BRANCH_MANAGER || role == Role.CASHIER) {
            List<com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeBook> chequeBooks = 
                chequeBookRepository.findByStatusAndBranchIn(ChequeBookStatus.REQUESTED, branchIds);
            for (com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeBook cb : chequeBooks) {
                String customerName = !cb.getAccount().getHolders().isEmpty() ? cb.getAccount().getHolders().get(0).getCustomer().getName() : "Unknown";
                String branchName = cb.getAccount().getBranch() != null ? cb.getAccount().getBranch().getName() : "N/A";
                approvals.add(new DashboardResponse.ApprovalQueueItem(
                    cb.getId(),
                    "CHEQUE",
                    customerName,
                    BigDecimal.valueOf(cb.getNumberOfLeaves()),
                    cb.getStatus().name(),
                    "/staff/cheques/" + cb.getId(),
                    branchName,
                    cb.getCreatedAt() != null ? cb.getCreatedAt().format(dtFormatter) : "N/A"
                ));
            }
        }

        // 5. Fraud Flags
        if (role == Role.SUPER_ADMIN || role == Role.ADMIN || role == Role.BRANCH_MANAGER || role == Role.AUDITOR) {
            List<com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag> fraudFlags = 
                fraudFlagRepository.findByStatusAndBranchIn(FraudFlagStatus.PENDING, branchIds);
            for (com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag ff : fraudFlags) {
                String customerName = "System Flagged User";
                if (ff.getUserId() != null) {
                    customerName = customerRepository.findCustomerByUser_Id(ff.getUserId()).map(c -> c.getName()).orElse("User ID: " + ff.getUserId());
                }
                String branchName = "Global";
                if (ff.getAccountId() != null) {
                    branchName = accountRepository.findById(ff.getAccountId())
                            .map(acc -> acc.getBranch() != null ? acc.getBranch().getName() : "Global")
                            .orElse("Global");
                }
                approvals.add(new DashboardResponse.ApprovalQueueItem(
                    ff.getId(),
                    "FRAUD",
                    customerName,
                    ff.getFlaggedAmount() != null ? ff.getFlaggedAmount() : BigDecimal.ZERO,
                    ff.getRiskLevel().name(),
                    "/staff/fraud/review/" + ff.getId(),
                    branchName,
                    ff.getCreatedAt() != null ? ff.getCreatedAt().format(dtFormatter) : "N/A"
                ));
            }
        }

        // Sort approvals by createdAt descending
        approvals.sort((x, y) -> y.getCreatedAt().compareTo(x.getCreatedAt()));
        response.setPendingApprovals(approvals);
    }
}
