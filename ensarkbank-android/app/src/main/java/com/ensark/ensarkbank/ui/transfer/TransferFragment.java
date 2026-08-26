package com.ensark.ensarkbank.ui.transfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentTransferBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.AccountTransactionRequest;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;
import com.ensark.ensarkbank.model.dto.TransactionRequest;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferFragment extends BaseFragment<FragmentTransferBinding> {

    private TransferViewModel viewModel;
    private List<BeneficiaryResponse> beneficiaryList = new ArrayList<>();
    private List<AccountResponse> accountList = new ArrayList<>();

    @Override
    protected FragmentTransferBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentTransferBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(TransferViewModel.class);

        setupToggles();
        observeViewModel();
        
        binding.sendButton.setOnClickListener(v -> handleTransfer());

        if (sessionManager.getCustomer() != null) {
            viewModel.fetchBeneficiaries(sessionManager.getCustomer().getEmail());
            viewModel.fetchAccounts(sessionManager.getCustomer().getEmail());
        }
    }

    private void setupToggles() {
        binding.transferModeToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnBeneficiary) {
                    binding.beneficiaryDropdownLayout.setVisibility(android.view.View.VISIBLE);
                    binding.directFieldsLayout.setVisibility(android.view.View.GONE);
                } else if (checkedId == R.id.btnDirect) {
                    binding.beneficiaryDropdownLayout.setVisibility(android.view.View.GONE);
                    binding.directFieldsLayout.setVisibility(android.view.View.VISIBLE);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.accounts.observe(getViewLifecycleOwner(), accounts -> {
            if (accounts != null) {
                accountList = accounts;
                List<String> accountStrings = new ArrayList<>();
                for (AccountResponse a : accounts) {
                    accountStrings.add(a.getAccountNumber() + " (৳" + a.getAvailableBalance() + ")");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        R.layout.item_dropdown, accountStrings);
                binding.sourceAccountAutoComplete.setAdapter(adapter);
            }
        });

        viewModel.beneficiaries.observe(getViewLifecycleOwner(), beneficiaries -> {
            if (beneficiaries != null) {
                beneficiaryList = beneficiaries;
                List<String> names = new ArrayList<>();
                for (BeneficiaryResponse b : beneficiaries) {
                    names.add(b.getName() + " (" + b.getAccNumber() + ")");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        R.layout.item_dropdown, names);
                binding.beneficiaryAutoComplete.setAdapter(adapter);
            }
        });

        viewModel.transferInitiated.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Bundle args = new Bundle();
                args.putLong("referenceId", response.getOtpReferenceId());
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_transferFragment_to_otpFragment, args);
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.sendButton.setEnabled(!isLoading);
            binding.sendButton.setText(isLoading ? "" : "Transfer Now");
            binding.progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        });
    }

    private void handleTransfer() {
        String amountStr = binding.amountEditText.getText().toString();
        String selectedAccount = binding.sourceAccountAutoComplete.getText().toString();
        String remarks = binding.remarksEditText.getText().toString();

        if (amountStr.isEmpty() || selectedAccount.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill amount and source account", Toast.LENGTH_SHORT).show();
            return;
        }

        BigDecimal amount = new BigDecimal(amountStr);
        AccountResponse sourceAcc = null;
        for (AccountResponse a : accountList) {
            if (selectedAccount.contains(a.getAccountNumber())) {
                sourceAcc = a;
                break;
            }
        }

        if (sourceAcc == null) {
            Toast.makeText(requireContext(), "Invalid source account", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountTransactionRequest.AccountTransactionRequestBuilder requestBuilder = AccountTransactionRequest.builder()
                .senderAccountId(sourceAcc.getId());

        TransactionRequest tr = TransactionRequest.builder()
                .amount(amount)
                .remarks(remarks.isEmpty() ? "Mobile Transfer" : remarks)
                .build();
        requestBuilder.request(tr);

        if (binding.transferModeToggle.getCheckedButtonId() == R.id.btnBeneficiary) {
            String selectedBeneficiary = binding.beneficiaryAutoComplete.getText().toString();
            if (selectedBeneficiary.isEmpty()) {
                Toast.makeText(requireContext(), "Please select a beneficiary", Toast.LENGTH_SHORT).show();
                return;
            }

            BeneficiaryResponse beneficiary = null;
            for (BeneficiaryResponse b : beneficiaryList) {
                if (selectedBeneficiary.contains(b.getAccNumber())) {
                    beneficiary = b;
                    break;
                }
            }

            if (beneficiary == null) {
                Toast.makeText(requireContext(), "Invalid beneficiary", Toast.LENGTH_SHORT).show();
                return;
            }

            requestBuilder.receiverAccountNumber(beneficiary.getAccNumber())
                    .receiverName(beneficiary.getName())
                    .bankName(beneficiary.getProvider())
                    .routingNumber(beneficiary.getRoutingNumber())
                    .beneficiaryId(beneficiary.getId());

        } else {
            String recAcc = binding.receiverAccNoEditText.getText().toString();
            String recName = binding.receiverNameEditText.getText().toString();
            String bankName = binding.bankNameEditText.getText().toString();

            if (recAcc.isEmpty() || recName.isEmpty() || bankName.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill receiver details", Toast.LENGTH_SHORT).show();
                return;
            }

            requestBuilder.receiverAccountNumber(recAcc)
                    .receiverName(recName)
                    .bankName(bankName)
                    .routingNumber(binding.routingNoEditText.getText().toString());
        }

        viewModel.initiateTransfer(requestBuilder.build());
    }
}
