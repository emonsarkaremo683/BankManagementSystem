package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.mapper;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.AccountTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTransactionMapper {

    private final TransactionMapper transactionMapper;

    public AccountTransactionResponse toResponse(AccountTransaction at, String currentAccountNumber) {
        Account senderAccount = at.getAccount();
        String senderName = resolveHolderName(senderAccount);
        String senderAccountNumber = senderAccount != null ? senderAccount.getAccountNumber() : "";
        String direction = currentAccountNumber != null && currentAccountNumber.equals(senderAccountNumber) ? "OUT" : "IN";

        return AccountTransactionResponse.builder()
                .id(at.getId())
                .transactionId(at.getTransaction().getTransactionId())
                .senderAccountNumber(senderAccountNumber)
                .senderName(senderName)
                .receiverAccountNumber(at.getReceiverAccountNumber())
                .receiverName(at.getReceiverName())
                .bankName(at.getBankName())
                .direction(direction)
                .response(transactionMapper.toResponse(at.getTransaction()))
                .build();
    }

    private String resolveHolderName(Account account) {
        if (account == null || account.getHolders() == null || account.getHolders().isEmpty()) {
            return "";
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
