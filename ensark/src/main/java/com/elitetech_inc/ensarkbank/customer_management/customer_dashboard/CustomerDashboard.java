package com.elitetech_inc.ensarkbank.customer_management.customer_dashboard;

import com.elitetech_inc.ensarkbank.account_management.account.dto.mapper.AccountMapper;
import com.elitetech_inc.ensarkbank.account_management.account.dto.response.AccountResponse;
import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardResponse;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.account_management.card.service.CardService;
import com.elitetech_inc.ensarkbank.account_management.loan.repository.LoanRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JournalRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.service.JournalService;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.repository.BeneficiaryRepository;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDashboard {

    private final AccountRepository accountRepository;
    private final JournalRepository journalRepository;
    private final LoanRepository loanRepository;
    private final CardRepository cardRepository;
    private final CardService cardService;
    private final BeneficiaryRepository beneficiaryRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final JournalService journalService;

    public CustomerDashboardResponse toResponse(Long customerId) {
        if (customerId == null) {
            return null;
        }

        String email = customerRepository.findById(customerId).map(c -> c.getUser().getEmail()).toString();

        List<AccountResponse> accounts = accountRepository.findDistinctByHoldersCustomerId(customerId)
                .stream().map(accountMapper::toAccountResponse).toList();

        BigDecimal balance = accounts.stream()
                .map(AccountResponse::getAvailableBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<String> accountNumbers = accounts.stream()
                .map(AccountResponse::getAccountNumber)
                .toList();

        List<Journal> journals = journalRepository.findTransactionHistory(accountNumbers);

        BigDecimal totalCredit = journals.stream()
                .filter(j -> j.getEntryType() == EntryType.CREDIT)
                .map(Journal::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebit = journals.stream()
                .filter(j -> j.getEntryType() == EntryType.DEBIT)
                .map(Journal::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalLoan = loanRepository.getLoanBalanceByCustomer(customerId);

        int totalCard = cardRepository.countByCustomerId(customerId);

        int totalBeneficiary = beneficiaryRepository.findBeneficiaryByCustomer_id(customerId).size();

        List<JournalResponse> transactions = journalService.getJournalByCustomerId(customerId, LocalDateTime.now().minusDays(30), LocalDateTime.now())
                .stream().sorted(Comparator.comparing(JournalResponse::getDate).reversed()).toList();
        List<JournalResponse> recentTransactions = transactions.stream().limit(10).toList();

        List<CardResponse> cards = cardService.findByCustomerEmail(email);

        CustomerDashboardResponse response = new CustomerDashboardResponse();
        response.setBalance(balance);
        response.setTotalCredit(totalCredit);
        response.setTotalDebit(totalDebit);
        response.setTotalLoan(totalLoan);
        response.setTotalCard(totalCard);
        response.setTotalTransaction((long) journals.size());
        response.setTotalBeneficiary(totalBeneficiary);
        response.setTotalAccount(accounts.size());
        response.setAccounts(accounts);
        response.setLast30DaysTransactions(transactions);
        response.setRecentTransactions(recentTransactions);
        response.setCards(cards);
        return response;
    }
}
