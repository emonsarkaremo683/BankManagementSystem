package com.ensark.ensarkbank.ui.transfer;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentCreateStandingOrderBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.StandingOrderRequest;
import com.ensark.ensarkbank.model.enums.StandingOrderFrequency;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateStandingOrderFragment extends BaseFragment<FragmentCreateStandingOrderBinding> {

    private StandingOrderViewModel viewModel;
    private final Calendar calendar = Calendar.getInstance();
    private Date startDate;
    private Date endDate;
    private List<AccountResponse> accountList = new ArrayList<>();
    private Long selectedAccountId;

    @Override
    protected FragmentCreateStandingOrderBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCreateStandingOrderBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(StandingOrderViewModel.class);

        setupDropdowns();
        setupDatePickers();
        observeViewModel();

        binding.submitButton.setOnClickListener(v -> handleSubmit());

        // Fetch accounts for source selection
        if (sessionManager.getCustomer() != null) {
            viewModel.fetchAccounts(sessionManager.getCustomer().getEmail());
        }
    }

    private void setupDropdowns() {
        // Frequency
        List<String> frequencies = new ArrayList<>();
        for (StandingOrderFrequency f : StandingOrderFrequency.values()) {
            frequencies.add(f.name());
        }
        ArrayAdapter<String> freqAdapter = new ArrayAdapter<>(requireContext(), R.layout.item_dropdown, frequencies);
        binding.frequencyAutoComplete.setAdapter(freqAdapter);
    }

    private void setupDatePickers() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        binding.startDateEditText.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view, year, month, day) -> {
                calendar.set(year, month, day);
                startDate = calendar.getTime();
                binding.startDateEditText.setText(sdf.format(startDate));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        binding.endDateEditText.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view, year, month, day) -> {
                calendar.set(year, month, day);
                endDate = calendar.getTime();
                binding.endDateEditText.setText(sdf.format(endDate));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void observeViewModel() {
        viewModel.accounts.observe(getViewLifecycleOwner(), accounts -> {
            if (accounts != null) {
                accountList = accounts;
                List<String> names = new ArrayList<>();
                for (AccountResponse a : accounts) names.add(a.getAccountNumber() + " (৳" + a.getAvailableBalance() + ")");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.item_dropdown, names);
                binding.sourceAccountAutoComplete.setAdapter(adapter);
                binding.sourceAccountAutoComplete.setOnItemClickListener((parent, view, position, id) -> 
                    selectedAccountId = accounts.get(position).getId());
            }
        });

        viewModel.orderCreated.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(requireContext(), "Standing Order Set Successfully!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.submitButton.setEnabled(!isLoading);
            binding.submitButton.setText(isLoading ? "" : "Set Standing Order");
            binding.progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }

    private void handleSubmit() {
        String targetAcc = binding.targetAccNoEditText.getText().toString();
        String targetName = binding.targetNameEditText.getText().toString();
        String amount = binding.amountEditText.getText().toString();
        String freq = binding.frequencyAutoComplete.getText().toString();

        if (targetAcc.isEmpty() || targetName.isEmpty() || amount.isEmpty() || freq.isEmpty() || selectedAccountId == null || startDate == null) {
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        StandingOrderRequest request = StandingOrderRequest.builder()
                .sourceAccountId(selectedAccountId)
                .targetAccountNumber(targetAcc)
                .targetAccountName(targetName)
                .amount(new BigDecimal(amount))
                .frequency(StandingOrderFrequency.valueOf(freq))
                .startDate(sdf.format(startDate))
                .endDate(endDate != null ? sdf.format(endDate) : null)
                .build();

        viewModel.createStandingOrder(request);
    }
}
