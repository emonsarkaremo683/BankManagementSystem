package com.elitetech_inc.ensarkbank.account_management.account_holder.dto.mapper;

import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.request.AccountHolderRequest;
import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.response.AccountHolderResponse;
import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountHolderMapper {

    private final CustomerRepository customerRepository;

    public AccountHolderResponse toAccountHolderResponse(AccountHolder accountHolder) {
        AccountHolderResponse response = new AccountHolderResponse();
        response.setId(accountHolder.getId());
        response.setAccountHolderName(accountHolder.getCustomer() != null ? accountHolder.getCustomer().getName() : "");
        response.setHolderType(accountHolder.getHolderType());
        response.setCanDeposit(accountHolder.getCanDeposit());
        response.setCanWithdraw(accountHolder.getCanWithdraw());
        response.setCanApproveTransaction(accountHolder.getCanApproveTransaction());
        response.setSignature(accountHolder.getSignature());
        return response;
    }

   public AccountHolder toAccountHolder(AccountHolderRequest ahr) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setHolderType(ahr.getHolderType());
        accountHolder.setCanDeposit(ahr.getCanDeposit());
        accountHolder.setCanWithdraw(ahr.getCanWithdraw());
        accountHolder.setCanApproveTransaction(ahr.getCanApproveTransaction());

        Customer customer = customerRepository.findById(ahr.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + ahr.getCustomerId()));

        accountHolder.setCustomer(customer);

        return accountHolder;
    }

}
