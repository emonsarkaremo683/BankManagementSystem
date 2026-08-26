package com.ensark.ensarkbank.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.databinding.FragmentProfileBinding;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.ui.auth.LoginActivity;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {

    private ProfileViewModel viewModel;
    private CustomerResponse currentCustomer;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    uploadProfileImage(uri);
                }
            }
    );

    @Override
    protected FragmentProfileBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ProfileViewModel.class);

        observeViewModel();

        if (sessionManager.getCustomer() != null) {
            currentCustomer = sessionManager.getCustomer();
            viewModel.fetchProfile(currentCustomer.getEmail());
        }

        binding.btnChangeProfileImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        binding.editProfileAction.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment));

        binding.changePasswordAction.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_changePasswordFragment));

        binding.personalInfoAction.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_personalInfoFragment));

        binding.kycAction.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_kycFragment));

        binding.notificationsAction.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_notificationsFragment));

        binding.logoutButton.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void uploadProfileImage(Uri uri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            File file = new File(requireContext().getCacheDir(), "profile_upload.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("profile", file.getName(), requestFile);

            if (currentCustomer != null) {
                viewModel.updateProfileImage(currentCustomer.getId(), part);
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to process image", Toast.LENGTH_SHORT).show();
        }
    }

    private void observeViewModel() {
        viewModel.customerData.observe(getViewLifecycleOwner(), customer -> {
            if (customer != null) {
                currentCustomer = customer;
                binding.userName.setText(customer.getName());
                binding.userEmail.setText(customer.getEmail());
                binding.kycBadge.setText(customer.getKycStatus().name());

                if (customer.getProfile() != null && !customer.getProfile().isEmpty()) {
                    GlideUrl glideUrl = new GlideUrl(ApiClient.IMAGE_URL + customer.getProfile(), new LazyHeaders.Builder()
                            .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                            .build());
                    Glide.with(this)
                            .load(glideUrl)
                            .circleCrop()
                            .placeholder(R.drawable.ic_profile)
                            .error(R.drawable.ic_profile)
                            .into(binding.profileImage);
                }
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            // Optional: Show a full-screen loader or disable interaction
            if (isLoading) {
                Toast.makeText(requireContext(), "Processing...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
