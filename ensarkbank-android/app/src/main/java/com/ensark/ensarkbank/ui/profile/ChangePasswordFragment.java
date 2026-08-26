package com.ensark.ensarkbank.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ensark.ensarkbank.databinding.FragmentChangePasswordBinding;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.repository.CustomerRepository;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends BaseFragment<FragmentChangePasswordBinding> {

    private CustomerRepository customerRepository;

    @Override
    protected FragmentChangePasswordBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChangePasswordBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        customerRepository = new CustomerRepository(requireContext());
        binding.btnUpdatePassword.setOnClickListener(v -> handleUpdatePassword());
    }

    private void handleUpdatePassword() {
        String oldPass = binding.oldPasswordEditText.getText().toString();
        String newPass = binding.newPasswordEditText.getText().toString();
        String confirmPass = binding.confirmPasswordEditText.getText().toString();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(confirmPass)) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sessionManager.getCustomer() == null) return;

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnUpdatePassword.setEnabled(false);

        customerRepository.updatePassword(sessionManager.getCustomer().getId(), oldPass, newPass, new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                binding.btnUpdatePassword.setEnabled(true);

                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireContext(), "Failed to update password. Check your current password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.btnUpdatePassword.setEnabled(true);
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
