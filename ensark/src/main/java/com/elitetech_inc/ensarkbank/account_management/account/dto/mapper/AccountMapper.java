package com.elitetech_inc.ensarkbank.account_management.account.dto.mapper;

import com.elitetech_inc.ensarkbank.account_management.account.dto.request.AccountRequest;
import com.elitetech_inc.ensarkbank.account_management.account.dto.response.AccountResponse;
import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.mapper.AccountHolderMapper;
import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.response.AccountHolderResponse;
import com.elitetech_inc.ensarkbank.account_management.nominee.entity.Nominee;
import com.elitetech_inc.ensarkbank.account_management.nominee.repository.NomineeRepository;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.util.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final AccountHolderMapper accountHolderMapper;
    private final AccountNumberGenerator  accountNumberGenerator;
    private final BranchRepository branchRepository;
    private final NomineeRepository nomineeRepository;

    public AccountResponse toAccountResponse(Account acc) {
        AccountResponse ar = new AccountResponse();
        ar.setId(acc.getId());
        ar.setAccountNumber(acc.getAccountNumber());
        ar.setAccountType(acc.getAccountType());
        ar.setAccountStatus(acc.getAccountStatus());
        ar.setAvailableBalance(acc.getAvailableBalance());
        ar.setCurrentBalance(acc.getCurrentBalance());
        ar.setHoldBalance(acc.getHoldBalance());
        ar.setBranchName(acc.getBranch() != null ? acc.getBranch().getName() : "");
        ar.setBranchRoutingNumber(acc.getBranch() != null ? acc.getBranch().getRoutingNumber() : "");

        // Account Holder Response
        List<AccountHolderResponse> holders = acc.getHolders()
                .stream()
                .filter(h -> h.getCustomer() != null)
                .collect(Collectors.toMap(
                        h -> h.getCustomer().getId(),
                        h -> h,
                        (first, second) -> first))
                .values()
                .stream()
                .map(accountHolderMapper::toAccountHolderResponse)
                .toList();

        nomineeRepository.findNomineeByAccount_id(acc.getId()).ifPresent(nominee -> {
            ar.setN_email(nominee.getEmail());
            ar.setN_name(nominee.getName());
            ar.setN_photo(nominee.getPhoto());
            ar.setN_nid_front(nominee.getNid_front());
            ar.setN_nid_back(nominee.getNid_back());
            ar.setN_phone(nominee.getPhone());
            ar.setRelation(nominee.getRelation());
        });

        ar.setHolderResponses(holders);

//        List<Signature> signatures = acc.getHolders()
//                .stream()
//                .map(h -> signatureRepository.findSignaturesByAccountHolderId(h.getId()).orElse(null))
//                .filter(java.util.Objects::nonNull)
//                .toList();
//        ar.setSignatures(signatures);

        return ar;
    }

    @Transactional
    public Account toAccount(AccountRequest ar) {
        Account acc = new Account();
        acc.setAccountNumber("acc-"+accountNumberGenerator.generateAccountNumber(ar.getBranchId(), ar.getAccountType()));
        acc.setAccountType(ar.getAccountType());
        acc.setAvailableBalance(BigDecimal.ZERO);
        acc.setCurrentBalance(BigDecimal.ZERO);
        acc.setCategory(AccountCategory.LIABILITY);
        acc.setHoldBalance(ar.getAvailableBalance());
        acc.setBranch(branchRepository.findById(ar.getBranchId()).orElse(null));

        return acc;
    }


    public Nominee toNominee(AccountRequest ar){
        return Nominee.builder()
                .name(ar.getN_name())
                .email(ar.getN_email())
                .phone(ar.getN_phone())
                .relation(ar.getRelation())
                .build();
    }

    public void updateAccountFromRequest(AccountRequest request, Account account) {
        if (request.getAccountType() != null) account.setAccountType(request.getAccountType());
        if (request.getAvailableBalance() != null) account.setAvailableBalance(request.getAvailableBalance());
        if (request.getBranchId() != null) {
            branchRepository.findById(request.getBranchId()).ifPresent(account::setBranch);
        }
    }

}
