package com.ensark.ensarkbank.ui.profile;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ensark.ensarkbank.databinding.FragmentKycBinding;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class KycFragment extends BaseFragment<FragmentKycBinding> {

    private KycViewModel viewModel;
    private KycDocumentAdapter adapter;

    private MultipartBody.Part nidPart, passportPart, licensePart, birthPart;
    private String currentUploadType;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    prepareImagePart(uri);
                }
            }
    );

    @Override
    protected FragmentKycBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentKycBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(KycViewModel.class);
        adapter = new KycDocumentAdapter();

        setupRecyclerView();
        observeViewModel();

        fetchData();

        binding.btnUploadNid.setOnClickListener(v -> { currentUploadType = "nid"; imagePickerLauncher.launch("image/*"); });
        binding.btnUploadPassport.setOnClickListener(v -> { currentUploadType = "passport"; imagePickerLauncher.launch("image/*"); });
        binding.btnUploadLicense.setOnClickListener(v -> { currentUploadType = "license"; imagePickerLauncher.launch("image/*"); });
        binding.btnUploadBirth.setOnClickListener(v -> { currentUploadType = "birth"; imagePickerLauncher.launch("image/*"); });

        binding.submitKycButton.setOnClickListener(v -> {
            if (nidPart == null && passportPart == null && licensePart == null && birthPart == null) {
                Toast.makeText(requireContext(), "Select at least one document", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.uploadDocuments(nidPart, passportPart, licensePart, birthPart);
        });
    }

    private void fetchData() {
        if (sessionManager.getCustomer() != null) {
            viewModel.fetchKycStatus();
            viewModel.fetchFullKycData(sessionManager.getCustomer().getId());
        }
    }

    @Override
    protected void onRefresh() {
        fetchData();
    }

    private void setupRecyclerView() {
        binding.documentsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.documentsRecyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.kycStatus.observe(getViewLifecycleOwner(), status -> {
            setRefreshing(false);
            if (status != null && status.containsKey("status")) {
                binding.kycStatusText.setText(String.valueOf(status.get("status")));
            }
        });

        viewModel.fullKycData.observe(getViewLifecycleOwner(), data -> {
            setRefreshing(false);
            if (data != null && data.getDocuments() != null) {
                adapter.setDocuments(data.getDocuments());
            }
        });

        viewModel.uploadSuccess.observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Documents uploaded successfully!", Toast.LENGTH_SHORT).show();
                fetchData();
                resetUploads();
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            setRefreshing(false);
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.submitKycButton.setEnabled(!isLoading);
            binding.submitKycButton.setText(isLoading ? "Uploading..." : "Upload All Documents");
        });
    }

    private void prepareImagePart(Uri uri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            File file = new File(requireContext().getCacheDir(), "kyc_" + currentUploadType + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(getPartName(), file.getName(), requestFile);

            switch (currentUploadType) {
                case "nid": 
                    nidPart = part; 
                    binding.imgNid.setImageURI(uri); 
                    binding.imgNid.setPadding(0,0,0,0); 
                    binding.imgNid.setColorFilter(null);
                    binding.txtNid.setVisibility(android.view.View.GONE);
                    break;
                case "passport": 
                    passportPart = part; 
                    binding.imgPassport.setImageURI(uri); 
                    binding.imgPassport.setPadding(0,0,0,0); 
                    binding.imgPassport.setColorFilter(null);
                    binding.txtPassport.setVisibility(android.view.View.GONE);
                    break;
                case "license": 
                    licensePart = part; 
                    binding.imgLicense.setImageURI(uri); 
                    binding.imgLicense.setPadding(0,0,0,0); 
                    binding.imgLicense.setColorFilter(null);
                    binding.txtLicense.setVisibility(android.view.View.GONE);
                    break;
                case "birth": 
                    birthPart = part; 
                    binding.imgBirth.setImageURI(uri); 
                    binding.imgBirth.setPadding(0,0,0,0); 
                    binding.imgBirth.setColorFilter(null);
                    binding.txtBirth.setVisibility(android.view.View.GONE);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to process image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getPartName() {
        switch (currentUploadType) {
            case "nid": return "NID";
            case "passport": return "PASSPORT";
            case "license": return "DRIVING_LICENSE";
            case "birth": return "BIRTH_CERTIFICATE";
            default: return currentUploadType;
        }
    }

    private void resetUploads() {
        nidPart = passportPart = licensePart = birthPart = null;
        binding.imgNid.setImageResource(android.R.drawable.ic_menu_camera);
        binding.imgPassport.setImageResource(android.R.drawable.ic_menu_camera);
        binding.imgLicense.setImageResource(android.R.drawable.ic_menu_camera);
        binding.imgBirth.setImageResource(android.R.drawable.ic_menu_camera);
        // Add back padding if needed
    }
}
