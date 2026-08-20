package com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto;

import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.ATMTransaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.util.CardGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ATMTransactionMapper {

    private final TransactionMapper transactionMapper;
    private final CardGenerator generator;

    public ATMTransactionResponse toResponse(ATMTransaction transaction) {
        return ATMTransactionResponse.builder()
                .ATMTransactionId(transaction.getId())
                .address(transaction.getAtm().getAddress())
                // Refill transactions (see ATMTransactionServiceImpl.refill) have no
                // associated Card, only withdrawals/deposits/balance checks do.
                .cardNumber(transaction.getCard() != null
                        ? transaction.getCard().getCardNumber()
                        : transaction.getCardNumber())
                .transactionType(transaction.getTransactionType())
                .transactionResponse(transaction.getTransaction() != null
                        ? transactionMapper.toResponse(transaction.getTransaction())
                        : null)
                .build();
    }

    public ATMTransaction toATMTransaction(ATMTransactionRequest att) {
        ATMTransaction transaction = new ATMTransaction();
        transaction.setTransactionType(att.getTransactionType());
        return transaction;
    }


    public boolean checkCard(String cardNumber){
        return generator.isCardValid(cardNumber);
    }
}
