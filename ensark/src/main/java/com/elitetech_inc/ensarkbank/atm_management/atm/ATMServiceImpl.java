package com.elitetech_inc.ensarkbank.atm_management.atm;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMMapper;
import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMResponse;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.ATMStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import com.elitetech_inc.ensarkbank.util.AccountNumberGenerator;
import com.elitetech_inc.ensarkbank.util.BranchValidator;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ATMServiceImpl implements ATMService {

    private final ATMRepository atmRepository;
    private final ATMMapper atmMapper;
    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;
    private final Utils utils;
    private final AccountNumberGenerator generator;
    private final BranchValidator branchValidator;
    private final RequestValidator requestValidator;

    @Override
    public ATMResponse createATM(ATMRequest atmRequest) {
        requestValidator.validateATM(atmRequest);

        ATM atm = atmMapper.toATM(atmRequest);

        Branch branch = branchRepository.findById(atmRequest.getBranchId()).orElseThrow(
                ()-> new RuntimeException("Branch not found")
        );

        branchValidator.assertNotAgentBank(branch.getId());

        atm.setBranch(branch);
        atm.setAtmRouting(utils.generateRouteNumber());

        // Creating Account for ATM Vault
        Account acc = new Account();
        acc.setAccountNumber(generator.generateBranchAccountNumber(atm.getAtmRouting(), "atm-"));
        acc.setAccountType(AccountType.ATM_VAULT);
        acc.setAccountStatus(atmMapper.toAccountStatus(atm.getStatus()));
        acc.setCurrentBalance(atmRequest.getBalance());
        acc.setAvailableBalance(atmRequest.getBalance());
        if(atm.getStatus() != ATMStatus.ACTIVE){
            acc.setHoldBalance(atmRequest.getBalance());
        } else{
            acc.setHoldBalance(BigDecimal.ZERO);
        }

        atm.setAccount(accountRepository.save(acc));

        return atmMapper.toResponse(atmRepository.save(atm));
    }

    @Override
    public ATMResponse updateATM(Long id, ATMRequest atmRequest) {
        ATM atm = atmRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("ATM not found")
        );
        if (atm.getBranch() != null) {
            branchValidator.assertNotAgentBank(atm.getBranch().getId());
        }

        if (atmRequest.getStatus() != null) {
            atm.setStatus(atmRequest.getStatus());
        }
        if (atmRequest.getLimit() != null) {
            atm.setLimit(atmRequest.getLimit());
        }
        if (atmRequest.getAddress() != null) {
            atm.setAddress(atmRequest.getAddress());
        }

        if (atmRequest.getBranchId() != null) {
            Branch branch = branchRepository.findById(atmRequest.getBranchId()).orElseThrow(
                    ()-> new RuntimeException("Branch not found")
            );
            branchValidator.assertNotAgentBank(branch.getId());
            atm.setBranch(branch);
        }

        Account acc = atm.getAccount();
        if (acc != null) {
            acc.setAccountStatus(atmMapper.toAccountStatus(atm.getStatus()));
            if (atmRequest.getBalance() != null) {
                acc.setCurrentBalance(atmRequest.getBalance());
                acc.setAvailableBalance(atmRequest.getBalance());
            }
            accountRepository.save(acc);
        }

        return atmMapper.toResponse(atmRepository.save(atm));
    }

    @Override
    public ATMResponse outOfServiceATM(Long id, ATMStatus atmStatus) {
        ATM atm = atmRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("ATM not found")
        );
        if (atm.getBranch() != null) {
            branchValidator.assertNotAgentBank(atm.getBranch().getId());
        }
        atm.setStatus(atmStatus);

        Account acc = atm.getAccount();
        acc.setAccountStatus(atmMapper.toAccountStatus(atm.getStatus()));
        atm.setAccount(accountRepository.save(acc));
        return atmMapper.toResponse(atmRepository.save(atm));

    }

    @Override
    public Optional<ATMResponse> getATM(Long id) {
        return atmRepository.findById(id).map(atmMapper::toResponse);
    }

    @Override
    public List<ATMResponse> getATMByBranchId(Long branchId) {
        return atmRepository.getATMByBranchId(branchId)
                .stream()
                .map(atmMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ATMResponse> getAll() {
        return atmRepository.findAll()
                .stream()
                .map(atmMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteATM(Long id) {
        ATM atm = atmRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("ATM", id)
        );
        atmRepository.delete(atm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMResponse> findByStatus(ATMStatus status) {
        return atmRepository.findAll().stream()
                .filter(atm -> atm.getStatus() == status)
                .map(atmMapper::toResponse)
                .collect(Collectors.toList());
    }
}
