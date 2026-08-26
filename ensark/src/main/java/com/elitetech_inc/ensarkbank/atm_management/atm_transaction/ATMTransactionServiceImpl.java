package com.elitetech_inc.ensarkbank.atm_management.atm_transaction;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.JoinHelper;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JoinHelperRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionService;
import com.elitetech_inc.ensarkbank.atm_management.atm.ATM;
import com.elitetech_inc.ensarkbank.atm_management.atm.ATMRepository;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionMapper;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionResponse;
import com.elitetech_inc.ensarkbank.common.enums.*;
import com.elitetech_inc.ensarkbank.util.BranchValidator;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ATMTransactionServiceImpl implements ATMTransactionService {

    private final ATMTransactionRepository atmTransactionRepository;
    private final ATMTransactionMapper atmTransactionMapper;
    private final CardRepository cardRepository;
    private final ATMRepository atmRepository;
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final TransactionMapper  transactionMapper;

    private final JoinHelperRepository joinHelperRepository;

    private final BranchValidator branchValidator;
    private final RequestValidator requestValidator;
    private final PasswordEncoder encoder;

    @Override
    public ATMTransactionResponse transaction(ATMTransactionRequest request) {
        requestValidator.validateATMTransaction(request);
        ATM atm = atmRepository.findById(request.getAtmId()).orElseThrow(
                () -> new RuntimeException("ATM Not Found")
        );

        if (atm.getStatus() != ATMStatus.ACTIVE) {
            throw new RuntimeException("ATM is not active");
        }

        if (atm.getBranch() != null) {
            branchValidator.assertNotAgentBank(atm.getBranch().getId());
        }
        ATMTransaction att = new ATMTransaction();

        Card card = null;

        if(atmTransactionMapper.checkCard(request.getCardNumber())){
            if(cardRepository.existsCardByCardNumber(request.getCardNumber())){
                card = cardRepository.findByCardNumber(request.getCardNumber()).orElseThrow(
                        () -> new RuntimeException("Card Not Found")
                );
                if (card.getExpiryDate().before(new Date())){
                    throw new RuntimeException("Card is expired");
                }
                if(!encoder.matches(request.getPin(), card.getPinHash())){
                    throw new RuntimeException("Card pin is Incorrect");
                }
                if(card.getStatus() != CardStatus.ACTIVE){
                    throw new RuntimeException("Card is not Active");
                }
            }
            else {
                throw new RuntimeException("This Card is not exists");
            }
        } else{
            throw new RuntimeException("Card Number not valid");
        }
        att.setAtm(atm);
        att.setCard(card);
        att.setTransactionType(request.getTransactionType());


        Transaction t = transactionMapper.toTransaction(request.getTransactionRequest());
        t.setChannel(TransactionChannel.ATM);

        switch (request.getTransactionType()) {
            case CASH_WITHDRAW -> {
                Account customerAccount = accountRepository.findAccountByAccountNumber(
                        card.getAccount().getAccountNumber()
                ).orElseThrow(() -> new RuntimeException("Customer account not found"));

                Account atmAccount = accountRepository.findAccountByAccountNumber(
                        atm.getAccount().getAccountNumber()
                ).orElseThrow(() -> new RuntimeException("ATM account not found"));

                BigDecimal withdrawAmount = request.getTransactionRequest().getAmount();
                if (atmAccount.getAvailableBalance().compareTo(withdrawAmount) < 0) {
                    throw new RuntimeException("Insufficient ATM balance");
                }

                if (customerAccount.getAvailableBalance().compareTo(withdrawAmount) < 0) {
                    throw new RuntimeException("Insufficient customer balance");
                }
                if (atm.getLimit() != null && withdrawAmount.compareTo(atm.getLimit()) > 0) {
                    throw new RuntimeException("Amount exceeds ATM limit");
                }
                t.setTransactionType(TransactionType.ATM_WITHDRAW);

                transactionService.createTransaction(request.getTransactionRequest(),
                        t,
                        customerAccount.getAccountNumber(),
                        atmAccount.getAccountNumber());
            }
            case CASH_DEPOSIT -> {
                Account customerAccount = accountRepository.findAccountByAccountNumber(
                        card.getAccount().getAccountNumber()
                ).orElseThrow(() -> new RuntimeException("Customer account not found"));

                Account atmAccount = accountRepository.findAccountByAccountNumber(
                        atm.getAccount().getAccountNumber()
                ).orElseThrow(() -> new RuntimeException("ATM account not found"));
                t.setTransactionType(TransactionType.ATM_DEPOSIT);

                transactionService.createTransaction(request.getTransactionRequest(),
                        t,
                        atmAccount.getAccountNumber(),
                        customerAccount.getAccountNumber());
            }

            case REFILL -> {
                Account atmAccount = accountRepository.findAccountByAccountNumber(
                        atm.getAccount().getAccountNumber()
                ).orElseThrow(() -> new RuntimeException("ATM account not found"));
                t.setTransactionType(TransactionType.ATM_DEPOSIT);

                transactionService.createTransaction(request.getTransactionRequest(),
                        t,
                        null,
                        atmAccount.getAccountNumber()
                        );

            }

            default -> throw new RuntimeException("Invalid Transaction Type");
        }

        att.setTransaction(t);

        JoinHelper jh = new JoinHelper();
        jh.setTransaction(t);
        jh.setAtmTransaction(att);
        joinHelperRepository.save(jh);
        return atmTransactionMapper.toResponse(atmTransactionRepository.save(att));
    }

    @Override
    public ATMTransactionResponse refill(Long atmId, BigDecimal amount) {
        ATM atm = atmRepository.findById(atmId).orElseThrow(
                () -> new RuntimeException("ATM Not Found")
        );

        if (atm.getBranch() != null) {
            branchValidator.assertNotAgentBank(atm.getBranch().getId());
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid refill amount");
        }

        Account atmAccount = accountRepository.findAccountByAccountNumber(
                atm.getAccount().getAccountNumber()
        ).orElseThrow(() -> new RuntimeException("ATM account not found"));

        atmAccount.setAvailableBalance(atmAccount.getAvailableBalance().add(amount));
        atmAccount.setCurrentBalance(atmAccount.getCurrentBalance().add(amount));
        accountRepository.save(atmAccount);

        Transaction t = new Transaction();
        t.setChannel(TransactionChannel.ATM);
        t.setTransactionType(TransactionType.DEPOSIT);
        t.setAmount(amount);
        t.setRemarks("ATM Refill");

        ATMTransaction att = new ATMTransaction();
        att.setAtm(atm);
        att.setTransaction(t);
        att.setTransactionType(com.elitetech_inc.ensarkbank.common.enums.ATMTransactionType.REFILL);

        return atmTransactionMapper.toResponse(atmTransactionRepository.save(att));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMTransactionResponse> getAll() {
        return atmTransactionRepository.findAll()
                .stream()
                .map(atmTransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ATMTransactionResponse getById(Long id) {
        ATMTransaction atmTransaction = atmTransactionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("ATM Transaction Not Found")
        );
        return atmTransactionMapper.toResponse(atmTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMTransactionResponse> getByAtmId(Long atmId) {
        return atmTransactionRepository.findByAtmId(atmId)
                .stream()
                .map(atmTransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal checkBalance(String cardNumber, String pin) {

        if(atmTransactionMapper.checkCard(cardNumber)){
            if(cardRepository.existsCardByCardNumber(cardNumber)){
                Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(
                        () -> new RuntimeException("Card Not Found")
                );
                if (card.getExpiryDate().before(new Date())) {
                    throw new RuntimeException("Card is expired");
                }
                if(!card.getPinHash().equals(pin)){
                    throw new RuntimeException("Card pin is Incorrect");
                }
                if(card.getStatus() != CardStatus.ACTIVE){
                    throw new RuntimeException("Card is not Active");
                }

                return accountRepository.findAccountByAccountNumber(card.getAccount().getAccountNumber())
                        .map(Account::getAvailableBalance)
                        .orElseThrow(()-> new RuntimeException("Not found account"));
            }
            else {
                throw new RuntimeException("This Card is not exists");
            }
        } else{
            throw new RuntimeException("Card Number not valid");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMTransactionResponse> getByCardNumber(String cardNumber) {
        return atmTransactionRepository.findAll().stream()
                .filter(t -> cardNumber.equals(t.getCardNumber()) || (t.getCard() != null && cardNumber.equals(t.getCard().getCardNumber())))
                .map(atmTransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMTransactionResponse> getByDateRange(java.time.LocalDate from, java.time.LocalDate to) {
        return atmTransactionRepository.findAll().stream()
                .filter(t -> {
                    if (t.getCreatedAt() == null) return false;
                    java.time.LocalDate d = t.getCreatedAt().toLocalDate();
                    return !d.isBefore(from) && !d.isAfter(to);
                })
                .map(atmTransactionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
