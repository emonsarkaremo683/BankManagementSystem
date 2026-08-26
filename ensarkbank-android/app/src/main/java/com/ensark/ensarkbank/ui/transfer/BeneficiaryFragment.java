package com.ensark.ensarkbank.ui.transfer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentBeneficiariesBinding;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;
import com.ensark.ensarkbank.ui.base.BaseFragment;

public class BeneficiaryFragment extends BaseFragment<FragmentBeneficiariesBinding> {

    private BeneficiaryViewModel viewModel;
    private BeneficiaryAdapter adapter;

    @Override
    protected FragmentBeneficiariesBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentBeneficiariesBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(BeneficiaryViewModel.class);
        adapter = new BeneficiaryAdapter();

        setupRecyclerView();
        observeViewModel();

        fetchData();

        binding.addBeneficiaryButton.setOnClickListener(v -> showAddBeneficiaryDialog());
    }

    private void fetchData() {
        if (sessionManager.getCustomer() != null) {
            viewModel.fetchBeneficiaries(sessionManager.getCustomer().getEmail());
        }
    }

    @Override
    protected void onRefresh() {
        fetchData();
    }

    private void showAddBeneficiaryDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_add_beneficiary, null);
        
        builder.setView(view);
        android.app.AlertDialog dialog = builder.create();
        
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        android.widget.AutoCompleteTextView typeAutoComplete = view.findViewById(R.id.typeAutoComplete);
        android.widget.EditText nameEditText = view.findViewById(R.id.nameEditText);
        android.widget.EditText accNoEditText = view.findViewById(R.id.accNoEditText);
        android.widget.EditText providerEditText = view.findViewById(R.id.providerEditText);
        android.widget.EditText routingEditText = view.findViewById(R.id.routingEditText);
        android.widget.Button saveButton = view.findViewById(R.id.saveButton);

        // Setup Dropdown
        com.ensark.ensarkbank.model.enums.BeneficiaryType[] types = com.ensark.ensarkbank.model.enums.BeneficiaryType.values();
        android.widget.ArrayAdapter<com.ensark.ensarkbank.model.enums.BeneficiaryType> typeAdapter = 
                new android.widget.ArrayAdapter<>(requireContext(), com.ensark.ensarkbank.R.layout.item_dropdown, types);
        typeAutoComplete.setAdapter(typeAdapter);

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String accNo = accNoEditText.getText().toString();
            String provider = providerEditText.getText().toString();
            String typeStr = typeAutoComplete.getText().toString();

            if (name.isEmpty() || accNo.isEmpty() || provider.isEmpty() || typeStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            com.ensark.ensarkbank.model.dto.BeneficiaryRequest request = com.ensark.ensarkbank.model.dto.BeneficiaryRequest.builder()
                    .name(name)
                    .accNumber(accNo)
                    .provider(provider)
                    .routingNumber(routingEditText.getText().toString())
                    .beneficiaryType(com.ensark.ensarkbank.model.enums.BeneficiaryType.valueOf(typeStr))
                    .customerId(sessionManager.getCustomer().getId())
                    .build();

            viewModel.addBeneficiary(request, sessionManager.getCustomer().getEmail());
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setupRecyclerView() {
        binding.beneficiaryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.beneficiaryRecyclerView.setAdapter(adapter);

        adapter.setListener(beneficiary -> {
            if (sessionManager.getCustomer() != null) {
                viewModel.deleteBeneficiary(beneficiary.getId(), sessionManager.getCustomer().getEmail());
            }
        });
    }

    private void observeViewModel() {
        viewModel.beneficiaries.observe(getViewLifecycleOwner(), items -> {
            setRefreshing(false);
            if (items != null) {
                adapter.setBeneficiaries(items);
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            setRefreshing(false);
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }
}
