package com.ensark.ensarkbank.ui.utility;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import com.ensark.ensarkbank.databinding.FragmentConverterBinding;
import com.ensark.ensarkbank.model.dto.CurrencyResponse;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConverterFragment extends BaseFragment<FragmentConverterBinding> {

    private ConverterViewModel viewModel;

    @Override
    protected FragmentConverterBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentConverterBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ConverterViewModel.class);

        observeViewModel();
        
        binding.convertButton.setOnClickListener(v -> handleConvert());

        viewModel.fetchCurrencies();
    }

    private void observeViewModel() {
        viewModel.currencies.observe(getViewLifecycleOwner(), currencies -> {
            if (currencies != null) {
                List<String> codes = new ArrayList<>();
                for (CurrencyResponse c : currencies) {
                    codes.add(c.getCurrency().name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        com.ensark.ensarkbank.R.layout.item_dropdown, codes);
                binding.fromCurrency.setAdapter(adapter);
                binding.toCurrency.setAdapter(adapter);
            }
        });

        viewModel.convertedAmount.observe(getViewLifecycleOwner(), amount -> {
            if (amount != null) {
                binding.resultValue.setText(amount.toString());
                binding.resultCard.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleConvert() {
        String amountStr = binding.amountInput.getText().toString();
        String from = binding.fromCurrency.getText().toString();
        String to = binding.toCurrency.getText().toString();

        if (amountStr.isEmpty() || from.isEmpty() || to.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.convert(from, to, new BigDecimal(amountStr));
    }
}
