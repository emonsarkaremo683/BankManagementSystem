package com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response;

import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionResponse {
    private String transactionId;

    private String referenceNo;
    private TransactionType transactionType;
    private TransactionChannel channel;

    private TransactionStatus status;
    private BigDecimal amount;
    private BigDecimal chargeAmount;
    private BigDecimal vatAmount;
    private String remarks;
    private LocalDateTime createdAt;

    private List<JournalResponse> journals = new ArrayList<>();
}
