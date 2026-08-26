package com.ensark.ensarkbank.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import com.ensark.ensarkbank.databinding.FragmentEditProfileBinding;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.ensark.ensarkbank.repository.CustomerRepository;
import com.google.gson.Gson;

public class EditProfileFragment extends BaseFragment<FragmentEditProfileBinding> {

    private CustomerRepository customerRepository;
    private CustomerResponse currentCustomer;

    @Override
    protected FragmentEditProfileBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditProfileBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        customerRepository = new CustomerRepository(requireContext());
        
        if (sessionManager.getCustomer() != null) {
            currentCustomer = sessionManager.getCustomer();
            binding.nameEditText.setText(currentCustomer.getName());
            binding.phoneEditText.setText(currentCustomer.getPhone());
        }

        binding.btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = binding.nameEditText.getText() != null ? binding.nameEditText.getText().toString().trim() : "";
        String phone = binding.phoneEditText.getText() != null ? binding.phoneEditText.getText().toString().trim() : "";

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(requireContext(), "Name and Phone cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSaveProfile.setEnabled(false);

        if (currentCustomer != null) {
            currentCustomer.setName(name);
            currentCustomer.setPhone(phone);
            
            // Note: The backend updateByCustomer expects the whole customer JSON as a RequestBody and an optional profile MultipartBody.Part
            RequestBody data = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(currentCustomer));
            MultipartBody.Part profileImage = null; // No image change for now

            customerRepository.updateByCustomer(currentCustomer.getId(), data, profileImage, new Callback<CustomerResponse>() {
                @Override
                public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnSaveProfile.setEnabled(true);
                    
                    if (response.isSuccessful() && response.body() != null) {
                        sessionManager.saveCustomer(response.body());
                        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed(); // Navigate back
                    } else {
                        Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CustomerResponse> call, Throwable t) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnSaveProfile.setEnabled(true);
                    Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
