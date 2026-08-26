package com.ensark.ensarkbank.ui.account;

import android.app.AlertDialog;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentCreateAccountBinding;
import com.ensark.ensarkbank.model.dto.AccountHolderRequest;
import com.ensark.ensarkbank.model.dto.AccountRequest;
import com.ensark.ensarkbank.model.dto.BranchResponse;
import com.ensark.ensarkbank.model.enums.AccountType;
import com.ensark.ensarkbank.model.enums.NomineeRelation;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateAccountFragment extends BaseFragment<FragmentCreateAccountBinding> {

    private CreateAccountViewModel viewModel;
    private List<BranchResponse> branchList = new ArrayList<>();
    private Long selectedBranchId;

    private MultipartBody.Part signaturePart;
    private MultipartBody.Part photoPart;
    private MultipartBody.Part nidFrontPart;
    private MultipartBody.Part nidBackPart;

    private String currentImageType;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    handleImageSelected(uri);
                }
            }
    );

    @Override
    protected FragmentCreateAccountBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCreateAccountBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        setupDropdowns();
        observeViewModel();

        binding.btnUploadSignature.setOnClickListener(v -> {
            currentImageType = "signature";
            imagePickerLauncher.launch("image/*");
        });

        binding.btnUploadNomineePhoto.setOnClickListener(v -> {
            currentImageType = "photo";
            imagePickerLauncher.launch("image/*");
        });

        binding.btnUploadNomineeNidFront.setOnClickListener(v -> {
            currentImageType = "nidFront";
            imagePickerLauncher.launch("image/*");
        });

        binding.btnUploadNomineeNidBack.setOnClickListener(v -> {
            currentImageType = "nidBack";
            imagePickerLauncher.launch("image/*");
        });

        binding.btnCreateAccount.setOnClickListener(v -> handleCreateAccount());

        viewModel.fetchBranches();
    }

    private void setupDropdowns() {
        // Account Type with custom layout and formatted names
        List<String> accountTypes = new ArrayList<>();
        for (AccountType type : AccountType.values()) {
            accountTypes.add(formatEnumName(type.name()));
        }
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(),
                R.layout.item_dropdown, accountTypes);
        binding.accountTypeAutoComplete.setAdapter(typeAdapter);

        // Nominee Relation with custom layout and formatted names
        List<String> relations = new ArrayList<>();
        for (NomineeRelation relation : NomineeRelation.values()) {
            relations.add(formatEnumName(relation.name()));
        }
        ArrayAdapter<String> relationAdapter = new ArrayAdapter<>(requireContext(),
                R.layout.item_dropdown, relations);
        binding.relationAutoComplete.setAdapter(relationAdapter);
    }

    private String formatEnumName(String name) {
        if (name == null || name.isEmpty()) return "";
        String formatted = name.replace("_", " ").toLowerCase();
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }

    private void observeViewModel() {
        viewModel.branches.observe(getViewLifecycleOwner(), branches -> {
            if (branches != null) {
                branchList = branches;
                List<String> names = new ArrayList<>();
                for (BranchResponse b : branches) names.add(b.getName());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        R.layout.item_dropdown, names);
                binding.branchAutoComplete.setAdapter(adapter);
                binding.branchAutoComplete.setOnItemClickListener((parent, view, position, id) -> 
                    selectedBranchId = branches.get(position).getId());
            }
        });

        viewModel.accountCreated.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Success")
                        .setMessage("Account Application Submitted Successfully!\nAccount Number: " + response.getAccountNumber())
                        .setPositiveButton("OK", (d, w) -> Navigation.findNavController(requireView()).popBackStack())
                        .show();
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.btnCreateAccount.setEnabled(!isLoading);
            binding.btnCreateAccount.setText(isLoading ? "" : "Submit Application");
            binding.progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        });
    }

    private void handleImageSelected(Uri uri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            File file = new File(requireContext().getCacheDir(), "temp_" + currentImageType + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            String partName = currentImageType;
            if (currentImageType.equals("nidFront")) partName = "nid_front";
            if (currentImageType.equals("nidBack")) partName = "nid_back";
            if (currentImageType.equals("signature")) partName = "signatures";

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(partName, file.getName(), requestFile);

            switch (currentImageType) {
                case "signature":
                    signaturePart = part;
                    binding.imgSignature.setImageURI(uri);
                    binding.imgSignature.setPadding(0, 0, 0, 0);
                    binding.imgSignature.setColorFilter(null);
                    binding.txtSignature.setVisibility(android.view.View.GONE);
                    break;
                case "photo":
                    photoPart = part;
                    binding.imgNomineePhoto.setImageURI(uri);
                    binding.imgNomineePhoto.setPadding(0, 0, 0, 0);
                    binding.imgNomineePhoto.setColorFilter(null);
                    binding.txtPhoto.setVisibility(android.view.View.GONE);
                    break;
                case "nidFront":
                    nidFrontPart = part;
                    binding.imgNidFront.setImageURI(uri);
                    binding.imgNidFront.setPadding(0, 0, 0, 0);
                    binding.imgNidFront.setColorFilter(null);
                    binding.txtNidFront.setVisibility(android.view.View.GONE);
                    break;
                case "nidBack":
                    nidBackPart = part;
                    binding.imgNidBack.setImageURI(uri);
                    binding.imgNidBack.setPadding(0, 0, 0, 0);
                    binding.imgNidBack.setColorFilter(null);
                    binding.txtNidBack.setVisibility(android.view.View.GONE);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to process image", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCreateAccount() {
        String deposit = binding.initialDepositEditText.getText().toString();
        String typeStr = binding.accountTypeAutoComplete.getText().toString();
        String relStr = binding.relationAutoComplete.getText().toString();

        if (deposit.isEmpty() || typeStr.isEmpty() || relStr.isEmpty() || selectedBranchId == null) {
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert formatted string back to Enum name
        String typeEnumName = typeStr.replace(" ", "_").toUpperCase();
        String relEnumName = relStr.replace(" ", "_").toUpperCase();

        AccountRequest request = AccountRequest.builder()
                .accountType(AccountType.valueOf(typeEnumName))
                .availableBalance(new BigDecimal(deposit))
                .branchId(selectedBranchId)
                .n_name(binding.nomineeNameEditText.getText().toString())
                .n_email(binding.nomineeEmailEditText.getText().toString())
                .n_phone(binding.nomineePhoneEditText.getText().toString())
                .relation(NomineeRelation.valueOf(relEnumName))
                .accountHolders(Collections.singletonList(AccountHolderRequest.builder()
                        .customerId(sessionManager.getCustomer().getId())
                        .holderType(com.ensark.ensarkbank.model.enums.HolderType.PRIMARY)
                        .canWithdraw(true)
                        .canDeposit(true)
                        .canApproveTransaction(true)
                        .build()))
                .build();

        List<MultipartBody.Part> signatures = new ArrayList<>();
        if (signaturePart != null) signatures.add(signaturePart);

        viewModel.createAccount(request, signatures, photoPart, nidFrontPart, nidBackPart);
    }
}