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

    @Override
    protected FragmentTransferBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentTransferBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(TransferViewModel.class);

        observeViewModel();
        
        binding.sendButton.setOnClickListener(v -> handleTransfer());

        if (sessionManager.getCustomer() != null) {
            viewModel.fetchBeneficiaries(sessionManager.getCustomer().getEmail());
        }
    }

    private void observeViewModel() {
        viewModel.beneficiaries.observe(getViewLifecycleOwner(), beneficiaries -> {
            if (beneficiaries != null) {
                beneficiaryList = beneficiaries;
                List<String> names = new ArrayList<>();
                for (BeneficiaryResponse b : beneficiaries) {
                    names.add(b.getName() + " (" + b.getAccNumber() + ")");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_dropdown_item_1line, names);
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
    }

    private void handleTransfer() {
        String amountStr = binding.amountEditText.getText().toString();
        String selected = binding.beneficiaryAutoComplete.getText().toString();

        if (amountStr.isEmpty() || selected.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        BigDecimal amount = new BigDecimal(amountStr);
        String accountNumber = "";
        for (BeneficiaryResponse b : beneficiaryList) {
            if (selected.contains(b.getAccNumber())) {
                accountNumber = b.getAccNumber();
                break;
            }
        }

        if (accountNumber.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid beneficiary", Toast.LENGTH_SHORT).show();
            return;
        }

        TransactionRequest tr = TransactionRequest.builder()
                .amount(amount)
                .remarks("Mobile Transfer")
                .build();

        AccountTransactionRequest request = AccountTransactionRequest.builder()
                .receiverAccountNumber(accountNumber)
                .request(tr)
                .build();

        viewModel.initiateTransfer(request);
    }
}
