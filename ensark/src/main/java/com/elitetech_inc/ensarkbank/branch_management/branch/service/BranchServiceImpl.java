package com.elitetech_inc.ensarkbank.branch_management.branch.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.AccountTransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionPostingService;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.address.policestation.entity.PoliceStation;
import com.elitetech_inc.ensarkbank.common.address.policestation.repository.PoliceStationRepository;
import com.elitetech_inc.ensarkbank.common.enums.*;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.util.AccountNumberGenerator;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final PoliceStationRepository policeStationRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionPostingService transactionPostingService;
    private final TransactionRepository transactionRepository;
    private final RequestValidator requestValidator;
    private final AccountNumberGenerator accountNumberGenerator;
    private final Utils utils;

    @Override
    @Transactional
    public Branch create(Branch branch) {
        requestValidator.validateBranch(branch);

        if (branch.getStatus() == null) {
            branch.setStatus(BranchStatus.ACTIVE);
        }

        branch.setBranchCode(generateBranchCode(branch.getName()));
        branch.setRoutingNumber(utils.generateRouteNumber());

        resolveParentBranch(branch);
        resolvePoliceStation(branch);

        Branch saved = branchRepository.save(branch);
        createVaultAccount(saved);
        createInitialTransaction(saved);

        return saved;
    }

    @Override
    @Transactional
    public Branch update(Long id, Branch branch) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));

        requestValidator.validateBranch(branch);

        existing.setName(branch.getName());
        existing.setAddress(branch.getAddress());
        existing.setEmail(branch.getEmail());
        existing.setPhoneNumber(branch.getPhoneNumber());
        existing.setStatus(branch.getStatus());

        resolvePoliceStation(branch);
        existing.setPoliceStation(branch.getPoliceStation());

        return branchRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Branch findById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Branch> findByBranchType(BranchType type) {
        return branchRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Branch> findByStatus(BranchStatus status) {
        return branchRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Branch> search(String query) {
        return branchRepository.search(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Branch> findByPoliceStationId(Long policeStationId) {
        return branchRepository.findByPoliceStationId(policeStationId);
    }

    private void resolveParentBranch(Branch branch) {
        if (branch.getType() == BranchType.AGENT_BANK
                && branch.getParentBranch() != null
                && branch.getParentBranch().getId() != null) {
            Branch parent = branchRepository.findById(branch.getParentBranch().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent branch", branch.getParentBranch().getId()));
            branch.setParentBranch(parent);
        }
    }

    private void resolvePoliceStation(Branch branch) {
        if (branch.getPoliceStation() != null && branch.getPoliceStation().getId() != null) {
            PoliceStation ps = policeStationRepository.findById(branch.getPoliceStation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Police station", branch.getPoliceStation().getId()));
            branch.setPoliceStation(ps);
        }
    }

    private void createVaultAccount(Branch saved) {
        boolean isAgentBank = saved.getType() == BranchType.AGENT_BANK;
        if (isAgentBank) return;

        boolean isHeadOffice = saved.getType() == BranchType.HEAD_OFFICE;

        Account account = new Account();
        account.setBranch(saved);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setCategory(AccountCategory.ASSET);
        account.setAccountType(isHeadOffice ? AccountType.INTER_BANK_VAULT : AccountType.BRANCH_VAULT);
        account.setAvailableBalance(BigDecimal.ZERO);
        account.setCurrentBalance(BigDecimal.ZERO);
        account.setHoldBalance(BigDecimal.ZERO);
        account.setAccountNumber(accountNumberGenerator.generateBranchAccountNumber(
                saved.getRoutingNumber(), isHeadOffice ? "head-" : "br-"));

        AccountHolder holder = new AccountHolder();
        holder.setCanWithdraw(true);
        holder.setCanDeposit(true);
        holder.setCanApproveTransaction(true);
        holder.setHolderType(HolderType.INTER_BRANCH_SETTLEMENT);

        account.addHolder(holder);
        accountRepository.save(account);
    }

    private void createInitialTransaction(Branch saved) {
        boolean isHeadOffice = saved.getType() == BranchType.HEAD_OFFICE;
        boolean isAgentBank = saved.getType() == BranchType.AGENT_BANK;
        if (isAgentBank) return;

        BigDecimal initialBalance = isHeadOffice
                ? BigDecimal.valueOf(100000000000.00)
                : BigDecimal.valueOf(5000000.00);

        String headNumber = accountRepository.findAll()
                .stream()
                .map(Account::getAccountNumber)
                .filter(num -> num.startsWith("head"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Head office vault account not found"));

        if (isHeadOffice) {
            TransactionRequest request = new TransactionRequest();
            request.setAmount(initialBalance);
            request.setRemarks("Head Office initialization - Capital entry");

            Transaction transaction = transactionMapper.toTransaction(request);
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setStatus(TransactionStatus.SUCCESS);
            transaction.setChannel(TransactionChannel.SYSTEM);
            transaction.setTransactionId(utils.generateReference());
            transaction.setReferenceNo(utils.generateReference());

            transactionPostingService.headOfficeCreationEntry(transaction, headNumber, "Owners Equity", initialBalance);
            transactionRepository.save(transaction);
        } else {
            String branchNumber = "br-" + saved.getRoutingNumber();

            TransactionRequest request = new TransactionRequest();
            request.setAmount(initialBalance);
            request.setRemarks("Initial deposit for Branch " + branchNumber);

            Transaction transaction = transactionMapper.toTransaction(request);
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setChannel(TransactionChannel.SYSTEM);
            transaction.setTransactionId(utils.generateReference());
            transaction.setReferenceNo(utils.generateReference());
            transaction.setStatus(TransactionStatus.SUCCESS);

            transactionPostingService.branchCreationEntry(transaction, headNumber, branchNumber, initialBalance);
            transactionRepository.save(transaction);
        }
    }

    private String generateBranchCode(String name) {
        String prefix = name.substring(0, Math.min(3, name.length())).toUpperCase();
        return prefix + String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean branchCodeExists(String code) {
        return branchRepository.findByBranchCode(code).isPresent();
    }

    @Override
    @Transactional
    public Branch deactivate(Long id) {
        Branch branch = findById(id);
        branch.setStatus(BranchStatus.CLOSED);
        return branchRepository.save(branch);
    }
}
