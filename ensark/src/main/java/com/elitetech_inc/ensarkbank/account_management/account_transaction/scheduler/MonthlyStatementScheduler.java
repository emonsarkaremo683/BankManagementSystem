package com.elitetech_inc.ensarkbank.account_management.account_transaction.scheduler;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.accounting_system.journal.service.JournalService;
import com.elitetech_inc.ensarkbank.accounting_system.journal.service.TransactionHistoryExportService;
import com.elitetech_inc.ensarkbank.common.email.TransactionEmailService;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MonthlyStatementScheduler {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final JournalService journalService;
    private final TransactionHistoryExportService exportService;
    private final TransactionEmailService emailService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void sendMonthlyStatements() {
        log.info("Starting monthly statement generation for all customers...");

        List<Customer> customers = customerRepository.findAll();
        int successCount = 0;
        int failCount = 0;

        for (Customer customer : customers) {
            try {
                sendStatementToCustomer(customer);
                successCount++;
            } catch (Exception e) {
                failCount++;
                log.error("Failed to send statement to customer {} ({}): {}",
                        customer.getName(), customer.getId(), e.getMessage());
            }
        }

        log.info("Monthly statement generation completed. Success: {}, Failed: {}", successCount, failCount);
    }

    private void sendStatementToCustomer(Customer customer) {
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        LocalDateTime fromDate = previousMonth.withDayOfMonth(1).atStartOfDay();
        LocalDateTime toDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()).atTime(LocalTime.MAX);

        List<Account> accounts = accountRepository.findDistinctByHoldersCustomerId(customer.getId());
        if (accounts.isEmpty()) {
            log.debug("No accounts found for customer {}, skipping", customer.getName());
            return;
        }

        String accountDisplay = accounts.size() == 1
                ? accounts.getFirst().getAccountNumber()
                : "All Accounts (" + accounts.size() + " accounts)";

        List<JournalResponse> entries = journalService.getJournalByCustomerId(
                customer.getId(), fromDate, toDate);

        if (entries.isEmpty()) {
            log.debug("No transactions found for customer {} in {}, skipping",
                    customer.getName(), previousMonth.getMonth());
            return;
        }

        String password = formatDob(customer.getDob());

        byte[] pdfBytes = exportService.generatePdf(
                entries,
                accountDisplay,
                customer.getName(),
                fromDate,
                toDate,
                password
        );

        String monthName = previousMonth.getMonth().name().charAt(0)
                + previousMonth.getMonth().name().substring(1).toLowerCase();
        int year = previousMonth.getYear();

        emailService.sendMonthlyStatementEmail(
                customer.getUser().getEmail(),
                customer.getName(),
                monthName,
                year,
                pdfBytes,
                entries.size()
        );

        log.info("Sent monthly statement to {} for {} {}", customer.getName(), monthName, year);
    }

    private String formatDob(Date dob) {
        if (dob == null) return "00000000";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dob);
    }
}
