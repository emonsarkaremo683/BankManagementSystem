package com.elitetech_inc.ensarkbank.account_management.account.service;

import com.elitetech_inc.ensarkbank.account_management.account.dto.request.AccountRequest;
import com.elitetech_inc.ensarkbank.account_management.account.dto.response.AccountResponse;
import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {

    AccountResponse create(AccountRequest request, List<MultipartFile> signatures, Map<String, MultipartFile> nominees);

    AccountResponse findById(Long id);

    List<AccountResponse> getAll();

    List<AccountResponse> getAllByBranchIds(List<Long> branchIds);

    List<AccountResponse> findByCustomerEmail(String email);

    List<AccountResponse> getAllBranchVault();

    List<AccountResponse> findAccountByBranchId(Long branchId);

    AccountResponse findBranchVaultByBranchId(Long branchId);

    List<AccountResponse> search(String query);

    AccountResponse findByAccountNumber(String accountNumber);

    boolean accountNumberExists(String accountNumber);

    AccountResponse updateByEmployee(Long id, AccountRequest request);

    AccountResponse updateStatus(Long id, AccountStatus status);

    BigDecimal getBalance(String accountNumber);

    Account getOrCreateVaultAccount(com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch branch);

    Account getOrCreateLoanControlAccount(com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch branch);

    Account getOrCreateFeeIncomeAccount(com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch branch);

    List<Long> resolveBranchAndChildIds(Long branchId);

    AccountResponse closeAccount(Long id, Long sweepToAccountId);

    List<AccountResponse> findByStatus(AccountStatus status);

    List<AccountResponse> findByAccountType(com.elitetech_inc.ensarkbank.common.enums.AccountType type);

    void validateMinimumBalance(String accountNumber, BigDecimal debitAmount);
}
