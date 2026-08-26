package com.elitetech_inc.ensarkbank.util;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.card.dto.request.CardRequest;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.enums.KYCStatus;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.KycDocuments;
import com.elitetech_inc.ensarkbank.customer_management.kyc.repository.KycDocumentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Validator {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final KycDocumentsRepository kycDocumentsRepository;


    public void checkKycStatus(Long customerId) {
                 customerRepository.findById(customerId)
                    .map(c -> {
                    if (c.getKyc() == null) {
                        throw new BadRequestException("KYC data is missing for this customer");
                    }

                    String msg = "Kyc verification is " + c.getKyc().getStatus();
                    return switch (c.getKyc().getStatus()) {
                        case PENDING, EXPIRED, UNDER_REVIEW, REJECTED -> throw new BadRequestException(msg);
                        case VERIFIED -> true;
                        default -> throw new BadRequestException("Unknown KYC status: " + c.getKyc().getStatus());
                    };
                })
                .orElseThrow(() -> new BadRequestException("Customer not found with id: " + customerId));
    }

    public void checkAccountStatus(String accountNumber){
                accountRepository.findAccountByAccountNumber(accountNumber)
                .map(a->{
                    String msg = "Account is " + a.getAccountStatus();
                    return switch (a.getAccountStatus()){
                        case ACTIVE -> true;
                        default -> throw new BadRequestException(msg);
                    };
                })
                .orElseThrow(()-> new BadRequestException("Account Not Found"));
    }

    public void checkCardStatus(String cardNumber){
                 cardRepository.findByCardNumber(cardNumber)
                .map(card->{
                    String msg = "Account is " + card.getStatus();
                    return switch (card.getStatus()){
                        case ACTIVE -> true;
                        default -> throw new BadRequestException(msg);
                    };

                })
                .orElseThrow(()-> new BadRequestException("Card Not Found"));
    }


    public boolean checkAccountExists(String accountNumber){
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    private static final Set<AccountType> VAULT_TYPES = Set.of(
            AccountType.BRANCH_VAULT,
            AccountType.AGENT_BANK_VAULT,
            AccountType.ATM_VAULT,
            AccountType.INTER_BANK_VAULT
    );

    public void checkAccountType(String accountNumber) {
        accountRepository.findAccountByAccountNumber(accountNumber)
                .filter(acc -> !VAULT_TYPES.contains(acc.getAccountType()))
                .orElseThrow(()-> new RuntimeException((" Invalid Account")));
    }

    public void checkPassportAvailable(Long id) {

        List<KycDocuments> list = kycDocumentsRepository.findKycDocumentsByCustomerId(id);

        boolean hasPassport = list.stream()
                .anyMatch(kd -> kd.getDoc_type() == DocumentType.PASSPORT);

        if (!hasPassport) {
            throw new RuntimeException("Need passport");
        }


    }





}
