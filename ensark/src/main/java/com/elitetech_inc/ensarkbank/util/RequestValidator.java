package com.elitetech_inc.ensarkbank.util;

import com.elitetech_inc.ensarkbank.account_management.account.dto.request.AccountRequest;
import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.request.AccountHolderRequest;
import com.elitetech_inc.ensarkbank.account_management.account_holder.repository.AccountHolderRepository;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.AccountTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.card.dto.request.CardRequest;
import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanApplicationRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm.ATMRepository;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.BalanceCheckRequest;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.address.address.dto.request.AddressRequest;
import com.elitetech_inc.ensarkbank.common.address.policestation.repository.PoliceStationRepository;
import com.elitetech_inc.ensarkbank.common.enums.BeneficiaryType;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.common.enums.HolderType;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.request.BeneficiaryRequest;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.repository.BeneficiaryRepository;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.request.CustomerRequest;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.customer_management.kyc.dto.request.KycRequest;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.request.EmployeeRequest;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Central request validator for the full project.
 *
 * Each {@code validate*} method performs field-level, cross-field and
 * referential-integrity (DB existence) checks for one incoming request DTO and
 * throws {@link IllegalArgumentException} on the first violation. Services call
 * these methods at the start of their create/update flows.
 */
@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountHolderRepository accountHolderRepository;
    private final CardRepository cardRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final EmployeeRepository employeeRepository;
    private final ATMRepository atmRepository;
    private final PoliceStationRepository policeStationRepository;

    /* ----------------------------- Customer ----------------------------- */

    public void validateCustomer(CustomerRequest req) {
        ValidationUtils.notNull(req, "CustomerRequest");
        ValidationUtils.notBlank(req.getName(), "Customer name");
        ValidationUtils.maxLength(req.getName(), 100, "Customer name");
        ValidationUtils.validEmail(req.getEmail(), "Customer email");
        ValidationUtils.notBlank(req.getPassword(), "Password");
        if (req.getPassword() != null && req.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        ValidationUtils.validPhone(req.getPhone(), "Customer phone");
        ValidationUtils.notNull(req.getGender(), "Customer gender");
        ValidationUtils.notNull(req.getOccupation(), "Customer occupation");
        ValidationUtils.notFuture(req.getDob(), "Customer date of birth");
        if (req.getAddresses() != null) {
            req.getAddresses().forEach(this::validateAddress);
        }
        if (req.getKycRequests() != null) {
            req.getKycRequests().forEach(this::validateKyc);
        }
    }

    /* ------------------------------- KYC -------------------------------- */

    public void validateKyc(KycRequest req) {
        ValidationUtils.notNull(req, "KycRequest");
        ValidationUtils.notBlank(req.getPath(), "KYC document path");
        ValidationUtils.notNull(req.getDoc_type(), "KYC document type");
    }

    /* ----------------------------- Address ------------------------------ */

    public void validateAddress(AddressRequest req) {
        ValidationUtils.notNull(req, "AddressRequest");
        ValidationUtils.notBlank(req.getHoldingNo(), "Holding number");
        ValidationUtils.notBlank(req.getArea(), "Area");
        ValidationUtils.notNull(req.getAddressType(), "Address type");
        if (req.getPoliceStation() != null && req.getPoliceStation().getId() != null) {
            Long psId = req.getPoliceStation().getId();
            if (!policeStationRepository.existsById(psId)) {
                throw new IllegalArgumentException("Police station not found: " + psId);
            }
        }
    }

    /* ----------------------------- Account ------------------------------ */

    public void validateAccount(AccountRequest req) {
        ValidationUtils.notNull(req, "AccountRequest");
        ValidationUtils.notNull(req.getAccountType(), "Account type");
        ValidationUtils.notNull(req.getBranchId(), "Account branch id");
        if (!branchRepository.existsById(req.getBranchId())) {
            throw new IllegalArgumentException("Branch not found: " + req.getBranchId());
        }
        if (req.getAvailableBalance() != null) {
            ValidationUtils.nonNegative(req.getAvailableBalance(), "Available balance");
        }
        if (req.getAccountHolders() != null) {
            if (req.getAccountHolders().isEmpty()) {
                throw new IllegalArgumentException("At least one account holder is required");
            }
            req.getAccountHolders().forEach(this::validateAccountHolder);
        }
    }

    public void validateAccountHolder(AccountHolderRequest req) {
        ValidationUtils.notNull(req, "AccountHolderRequest");
        ValidationUtils.notNull(req.getHolderType(), "Holder type");
        ValidationUtils.notNull(req.getCustomerId(), "Holder customer id");
        if (!customerRepository.existsById(req.getCustomerId())) {
            throw new IllegalArgumentException("Customer not found: " + req.getCustomerId());
        }
    }

    /* ------------------------------- Card ------------------------------- */

    public void validateCard(CardRequest req) {
        ValidationUtils.notNull(req, "CardRequest");
        ValidationUtils.notNull(req.getAccountId(), "Card account id");
        if (!accountRepository.existsById(req.getAccountId())) {
            throw new IllegalArgumentException("Account not found: " + req.getAccountId());
        }
        ValidationUtils.notNull(req.getCardType(), "Card type");
        ValidationUtils.notNull(req.getCardNetwork(), "Card network");
        ValidationUtils.notBlank(req.getPin(), "Card PIN");
        if (req.getPin() != null && req.getPin().length() != 4) {
            throw new IllegalArgumentException("Card PIN must be exactly 4 digits");
        }

    }

    /* --------------------------- Transaction ---------------------------- */

    public void validateTransactionRequest(
            com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest req) {
        ValidationUtils.notNull(req, "TransactionRequest");
        ValidationUtils.notNull(req.getAmount(), "Transaction amount");
        ValidationUtils.positive(req.getAmount(), "Transaction amount");

    }

    public void validateAccountTransaction(AccountTransactionRequest req) {
        ValidationUtils.notNull(req, "AccountTransactionRequest");
        ValidationUtils.notNull(req.getRequest(), "Transaction request");
        validateTransactionRequest(req.getRequest());

        boolean hasReceiverAccount = !ValidationUtils.isBlank(req.getReceiverAccountNumber());
        boolean hasReceiverId = req.getReceiverAccountId() != null;
        boolean hasBeneficiary = req.getBeneficiaryId() != null;
        boolean isExternalTransfer = !ValidationUtils.isBlank(req.getBankName());

        if (!hasReceiverAccount && !hasReceiverId && !hasBeneficiary) {
            throw new IllegalArgumentException(
                    "A receiver must be specified (receiverAccountNumber, receiverAccountId or beneficiaryId)");
        }
        if (hasReceiverAccount && !isExternalTransfer &&
                !accountRepository.existsByAccountNumber(req.getReceiverAccountNumber())) {
            throw new IllegalArgumentException("Receiver account not found: " + req.getReceiverAccountNumber());
        }
        if (req.getSenderAccountId() != null &&
                !accountRepository.existsById(req.getSenderAccountId())) {
            throw new IllegalArgumentException("Sender account not found: " + req.getSenderAccountId());
        }
        if (hasReceiverId && !accountRepository.existsById(req.getReceiverAccountId())) {
            throw new IllegalArgumentException("Receiver account not found: " + req.getReceiverAccountId());
        }
        if (hasBeneficiary && !beneficiaryRepository.existsById(req.getBeneficiaryId())) {
            throw new IllegalArgumentException("Beneficiary not found: " + req.getBeneficiaryId());
        }
    }

    /* --------------------------- Cashier TXN -------------------------- */

    public void validateCashierTransaction(CashierTransactionRequest req) {
        ValidationUtils.notNull(req, "CashierTransactionRequest");
        ValidationUtils.notNull(req.getBranchId(), "Cashier branch id");
        if (!branchRepository.existsById(req.getBranchId())) {
            throw new IllegalArgumentException("Branch not found: " + req.getBranchId());
        }
        ValidationUtils.notBlank(req.getAccountNumber(), "Account number");
        if (!accountRepository.existsByAccountNumber(req.getAccountNumber())) {
            throw new IllegalArgumentException("Account not found: " + req.getAccountNumber());
        }
        ValidationUtils.notNull(req.getTransactionRequest(), "Transaction request");
        validateTransactionRequest(req.getTransactionRequest());
    }

    /* ----------------------------- Beneficiary ------------------------- */

    public void validateBeneficiary(BeneficiaryRequest req) {
        ValidationUtils.notNull(req, "BeneficiaryRequest");
        ValidationUtils.notBlank(req.getAccNumber(), "Beneficiary account number");
        ValidationUtils.notBlank(req.getName(), "Beneficiary name");
        ValidationUtils.notNull(req.getBeneficiaryType(), "Beneficiary type");
        ValidationUtils.notNull(req.getCustomerId(), "Beneficiary owner customer id");
        if (!customerRepository.existsById(req.getCustomerId())) {
            throw new IllegalArgumentException("Customer not found: " + req.getCustomerId());
        }
        if (req.getBeneficiaryType() == BeneficiaryType.INTER_BANK) {
            ValidationUtils.notBlank(req.getProvider(), "Beneficiary bank name");
            ValidationUtils.notBlank(req.getRoutingNumber(), "Beneficiary routing number");
        }
    }

    /* ------------------------------ Employee --------------------------- */

    public void validateEmployee(EmployeeRequest req) {
        ValidationUtils.notNull(req, "EmployeeRequest");
        ValidationUtils.notBlank(req.getName(), "Employee name");
        ValidationUtils.validEmail(req.getEmail(), "Employee email");
        ValidationUtils.validPhone(req.getPhone(), "Employee phone");
        ValidationUtils.notNull(req.getGender(), "Employee gender");
        ValidationUtils.notNull(req.getRole(), "Employee role");
        ValidationUtils.notNull(req.getDesignation(), "Employee designation");
        ValidationUtils.notFuture(req.getDob(), "Employee date of birth");
        ValidationUtils.notNull(req.getBranchId(), "Employee branch id");
        if (!branchRepository.existsById(req.getBranchId())) {
            throw new IllegalArgumentException("Branch not found: " + req.getBranchId());
        }
        if (req.getAddresses() != null) {
            req.getAddresses().forEach(this::validateAddress);
        }
    }

    /* ------------------------------- Loan ------------------------------ */

    public void validateLoanApplication(LoanApplicationRequest req) {
        ValidationUtils.notNull(req, "LoanApplicationRequest");
        ValidationUtils.notNull(req.getAccountId(), "Loan account id");
        if (!accountRepository.existsById(req.getAccountId())) {
            throw new IllegalArgumentException("Account not found: " + req.getAccountId());
        }
        ValidationUtils.notNull(req.getPrincipalAmount(), "Principal amount");
        ValidationUtils.positive(req.getPrincipalAmount(), "Principal amount");
        ValidationUtils.notNull(req.getAnnualInterestRate(), "Annual interest rate");
        ValidationUtils.nonNegative(req.getAnnualInterestRate(), "Annual interest rate");
        ValidationUtils.notNull(req.getTenureMonths(), "Tenure (months)");
        ValidationUtils.inRange(req.getTenureMonths(), 1, 600, "Tenure (months)");
    }

    /* ------------------------------- ATM ------------------------------- */

    public void validateATM(ATMRequest req) {
        ValidationUtils.notNull(req, "ATMRequest");
        ValidationUtils.notNull(req.getStatus(), "ATM status");
        ValidationUtils.notNull(req.getBranchId(), "ATM branch id");
        if (!branchRepository.existsById(req.getBranchId())) {
            throw new IllegalArgumentException("Branch not found: " + req.getBranchId());
        }
        if (req.getBalance() != null) {
            ValidationUtils.nonNegative(req.getBalance(), "ATM balance");
        }
        if (req.getLimit() != null) {
            ValidationUtils.nonNegative(req.getLimit(), "ATM limit");
        }
        ValidationUtils.notBlank(req.getAddress(), "ATM address");
    }

    public void validateATMTransaction(ATMTransactionRequest req) {
        ValidationUtils.notNull(req, "ATMTransactionRequest");
        ValidationUtils.notNull(req.getAtmId(), "ATM id");
        if (!atmRepository.existsById(req.getAtmId())) {
            throw new IllegalArgumentException("ATM not found: " + req.getAtmId());
        }
        ValidationUtils.notBlank(req.getCardNumber(), "Card number");
        ValidationUtils.notBlank(req.getPin(), "Card PIN");
        ValidationUtils.notNull(req.getTransactionType(), "ATM transaction type");
        ValidationUtils.notNull(req.getTransactionRequest(), "Transaction request");
        validateTransactionRequest(req.getTransactionRequest());
    }

    public void validateBalanceCheck(BalanceCheckRequest req) {
        ValidationUtils.notNull(req, "BalanceCheckRequest");
        ValidationUtils.notBlank(req.getCardNumber(), "Card number");
        ValidationUtils.notBlank(req.getPin(), "Card PIN");
    }

    /* ------------------------------ Branch ----------------------------- */

    public void validateBranchExists(Long branchId) {
        if (branchId == null) {
            throw new IllegalArgumentException("Branch id is required");
        }
        if (!branchRepository.existsById(branchId)) {
            throw new IllegalArgumentException("Branch not found: " + branchId);
        }
    }

    public void validateBranch(Branch branch) {
        ValidationUtils.notNull(branch, "Branch");
        ValidationUtils.notBlank(branch.getName(), "Branch name");
        ValidationUtils.notBlank(branch.getEmail(), "Branch email");
        ValidationUtils.validEmail(branch.getEmail(), "Branch email");
        ValidationUtils.notBlank(branch.getPhoneNumber(), "Branch phone");
        ValidationUtils.validPhone(branch.getPhoneNumber(), "Branch phone");
        ValidationUtils.notNull(branch.getType(), "Branch type");
        ValidationUtils.notNull(branch.getStatus(), "Branch status");
    }
}
