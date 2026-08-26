package com.ensark.ensarkbank.ui.transfer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentOtpBinding;
import com.ensark.ensarkbank.ui.base.BaseFragment;
import com.ensark.ensarkbank.ui.component.SuccessDialog;

public class OtpFragment extends BaseFragment<FragmentOtpBinding> {

    private OtpViewModel viewModel;
    private Long referenceId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            referenceId = getArguments().getLong("referenceId");
        }
    }

    @Override
    protected FragmentOtpBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOtpBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(OtpViewModel.class);

        setupOtpInputs();
        observeViewModel();

        binding.verifyButton.setOnClickListener(v -> handleVerify());
    }

    private void setupOtpInputs() {
        EditText[] digits = {
                binding.otpDigit1, binding.otpDigit2, binding.otpDigit3,
                binding.otpDigit4, binding.otpDigit5, binding.otpDigit6
        };

        for (int i = 0; i < digits.length; i++) {
            final int index = i;
            digits[i].addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < digits.length - 1) {
                        digits[index + 1].requestFocus();
                    }
                }
            });
        }
    }

    private void observeViewModel() {
        viewModel.verificationResult.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                SuccessDialog dialog = new SuccessDialog();
                dialog.setOnDismiss(() -> Navigation.findNavController(requireView()).popBackStack(R.id.dashboardFragment, false));
                dialog.show(getChildFragmentManager(), "success");
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.verifyButton.setEnabled(!isLoading);
            binding.verifyButton.setText(isLoading ? "" : "Verify & Confirm");
            binding.progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        });
    }

    private void handleVerify() {
        StringBuilder otp = new StringBuilder();
        otp.append(binding.otpDigit1.getText());
        otp.append(binding.otpDigit2.getText());
        otp.append(binding.otpDigit3.getText());
        otp.append(binding.otpDigit4.getText());
        otp.append(binding.otpDigit5.getText());
        otp.append(binding.otpDigit6.getText());

        if (otp.length() < 6) {
            Toast.makeText(requireContext(), "Please enter full 6-digit code", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.verifyOtp(referenceId, otp.toString());
    }
}
