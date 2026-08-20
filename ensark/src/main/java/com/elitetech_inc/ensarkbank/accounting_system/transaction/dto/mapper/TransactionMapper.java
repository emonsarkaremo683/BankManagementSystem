package com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction t) {
        TransactionResponse tr = new TransactionResponse();
        tr.setTransactionId(t.getTransactionId());
        tr.setTransactionType(t.getTransactionType());
        tr.setAmount(t.getAmount());
        tr.setChannel(t.getChannel());
        tr.setRemarks(t.getRemarks());
        tr.setStatus(t.getStatus());
        tr.setChargeAmount(t.getChargeAmount());
        tr.setVatAmount(t.getVatAmount());
        tr.setReferenceNo(t.getReferenceNo());
        tr.setCreatedAt(t.getCreatedAt());

        return tr;
    }

    public Transaction toTransaction(TransactionRequest tr) {
        Transaction t = new Transaction();
        t.setAmount(tr.getAmount());
        t.setRemarks(tr.getRemarks());
        return t;
    }
}
