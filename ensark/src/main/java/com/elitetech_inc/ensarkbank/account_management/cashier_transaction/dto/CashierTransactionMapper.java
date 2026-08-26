package com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.mapper.AccountTransactionMapper;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.AccountTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.CashierTransaction;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalMapper;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.common.enums.Role;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CashierTransactionMapper {

    private final EmployeeRepository employeeRepository;
    private final AccountTransactionMapper accountTransactionMapper;
    private final TransactionMapper transactionMapper;
    private final JournalMapper journalMapper;

    public CashierTransactionResponse toResponse(CashierTransaction ct) {
        TransactionResponse transactionResponse = null;
        List<JournalResponse> journalResponses = Collections.emptyList();

        if (ct.getTransaction() != null) {
            transactionResponse = transactionMapper.toResponse(ct.getTransaction());
            journalResponses = mapJournals(ct.getTransaction());
        }
        String cashierName = "";
        String branchName = "";
        if (ct.getBranch() != null) {
            branchName = ct.getBranch().getName();
        }
        cashierName = ct.getEmployee().getName();


        return CashierTransactionResponse.builder()
                .id(ct.getId())
                .transactionEntityId(ct.getTransaction() != null ? ct.getTransaction().getId() : null)
                .checkNo(ct.getCheckNo())
                .cashierName(cashierName)
                .branchName(branchName)
                .transaction(transactionResponse)
                .journals(journalResponses)
                .build();
    }

    private List<JournalResponse> mapJournals(Transaction transaction) {
        if (transaction.getEntries() == null || transaction.getEntries().isEmpty()) {
            return Collections.emptyList();
        }
        return transaction.getEntries().stream()
                .map(journalMapper::toResponse
                )
                .collect(Collectors.toList());
    }

    private String resolveAccountName(com.elitetech_inc.ensarkbank.account_management.account.entity.Account account) {
        if (account.getHolders() == null || account.getHolders().isEmpty()) {
            return account.getAccountNumber();
        }
        return account.getHolders().stream()
                .filter(h -> h.getHolderType() != null && "PRIMARY".equals(h.getHolderType().name()))
                .findFirst()
                .map(h -> h.getCustomer() != null ? h.getCustomer().getName() : null)
                .filter(name -> name != null)
                .orElseGet(() -> {
                    var firstHolder = account.getHolders().getFirst();
                    return firstHolder.getCustomer() != null ? firstHolder.getCustomer().getName() : account.getAccountNumber();
                });
    }
}
