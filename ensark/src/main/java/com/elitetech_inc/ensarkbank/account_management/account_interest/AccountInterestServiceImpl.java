package com.elitetech_inc.ensarkbank.account_management.account_interest;

import com.elitetech_inc.ensarkbank.account_management.account_interest.dto.AccountInterestMapper;
import com.elitetech_inc.ensarkbank.account_management.account_interest.dto.AccountInterestRequest;
import com.elitetech_inc.ensarkbank.account_management.account_interest.dto.AccountInterestResponse;
import com.elitetech_inc.ensarkbank.account_management.account_interest.service.InterestAccrualService;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountInterestServiceImpl implements AccountInterestService {

    private final AccountInterestRepository interestRepository;
    private final AccountInterestMapper mapper;
    private final InterestAccrualService interestAccrualService;
    private final com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountInterestResponse save(AccountInterestRequest request) {
        if (request == null) {
            return null;
        }
        List<AccountType> allowedTypes = List.of(AccountType.FIXED_DEPOSIT, AccountType.SAVINGS);
        if (!allowedTypes.contains(request.getAccountType())) {
            return null;
        }

        AccountInterest ai = new AccountInterest();
        ai.setAccountType(request.getAccountType());
        ai.setTimeSpan(request.getTimeSpan());
        ai.setInterestRate(request.getInterestRate());
        return mapper.toResponse(interestRepository.save(ai));
    }

    @Override
    @Transactional
    public AccountInterestResponse update(Long id, AccountInterestRequest request) {
        AccountInterest ai = interestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountInterest", id));
        ai.setTimeSpan(request.getTimeSpan());
        ai.setInterestRate(request.getInterestRate());
        ai.setAccountType(request.getAccountType());
        return mapper.toResponse(interestRepository.save(ai));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountInterestResponse> getAll() {
        return interestRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountInterestResponse getById(Long id) {
        AccountInterest ai = interestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountInterest", id));
        return mapper.toResponse(ai);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCurrentMonth(Long accountTypeId) {
        return interestRepository.findById(accountTypeId).isPresent();
    }

    @Override
    @Transactional
    public int sendInterestToEligibleAccount() {
        return interestAccrualService.accrueAll();
    }

    @Override
    @Transactional
    public int sendInterestToEligibleAccountById(Long id) {
        AccountInterest ai = interestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountInterest", id));
        return interestAccrualService.accrueForType(ai.getAccountType());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AccountInterest ai = interestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccountInterest", id));
        interestRepository.delete(ai);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountInterestResponse findByAccountId(Long accountId) {
        com.elitetech_inc.ensarkbank.account_management.account.entity.Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", accountId));
        AccountInterest ai = interestRepository.findByAccountType(account.getAccountType())
                .orElseThrow(() -> new ResourceNotFoundException("AccountInterest for type: " + account.getAccountType(), accountId));
        return mapper.toResponse(ai);
    }
}
