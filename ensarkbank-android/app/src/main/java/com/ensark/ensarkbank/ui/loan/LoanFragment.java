package com.ensark.ensarkbank.ui.loan;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentLoanBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.LoanApplicationRequest;
import com.ensark.ensarkbank.model.dto.LoanApplicationResponse;
import com.ensark.ensarkbank.model.dto.LoanScheduleResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.ui.base.BaseFragment;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanFragment extends BaseFragment<FragmentLoanBinding> {

    private LoanViewModel viewModel;
    private LoanAdapter loanAdapter;
    private EmiAdapter emiAdapter;
    
    private List<AccountResponse> accountList = new ArrayList<>();
    private Long selectedAccountId;
    private MultipartBody.Part guarantorPhotoPart;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    prepareGuarantorPhoto(uri);
                }
            }
    );

    @Override
    protected FragmentLoanBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoanBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(LoanViewModel.class);
        loanAdapter = new LoanAdapter();
        emiAdapter = new EmiAdapter();

        setupRecyclerViews();
        observeViewModel();

        binding.applyLoanButton.setOnClickListener(v -> showApplyLoanDialog());

        fetchData();
    }

    private void fetchData() {
        if (sessionManager.getCustomer() != null) {
            viewModel.fetchLoans(sessionManager.getCustomer().getEmail());
            fetchAccounts();
        }
    }

    @Override
    protected void onRefresh() {
        fetchData();
    }

    private void setupRecyclerViews() {
        binding.loansRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.loansRecyclerView.setAdapter(loanAdapter);
        loanAdapter.setListener(loan -> {
            binding.emiSectionLayout.setVisibility(View.VISIBLE);
            viewModel.fetchSchedule(loan.getLoanId());
        });

        binding.emiRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.emiRecyclerView.setAdapter(emiAdapter);
        emiAdapter.setListener(emi -> {
            if (sessionManager.getCustomer() != null) {
                viewModel.payEmi(emi.getRepaymentId(), sessionManager.getCustomer().getEmail());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void observeViewModel() {
        viewModel.loans.observe(getViewLifecycleOwner(), loans -> {
            setRefreshing(false);
            if (loans != null) {
                loanAdapter.setLoans(loans);
                updateSummary(loans);
            }
        });

        viewModel.emiSchedule.observe(getViewLifecycleOwner(), schedule -> {
            if (schedule != null) {
                emiAdapter.setEmis(schedule);
            }
        });

        viewModel.repaymentSuccess.observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Payment Successful!", Toast.LENGTH_SHORT).show();
                fetchData(); // Refresh list after payment
            }
        });

        viewModel.applicationSuccess.observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Loan Application Submitted!", Toast.LENGTH_LONG).show();
                fetchData(); // Refresh list after application
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

    private void fetchAccounts() {
        new AccountRepository(requireContext()).findByCustomerEmail(sessionManager.getCustomer().getEmail(), new Callback<List<AccountResponse>>() {
            @Override
            public void onResponse(Call<List<AccountResponse>> call, Response<List<AccountResponse>> response) {
                if (response.isSuccessful()) accountList = response.body();
            }
            @Override
            public void onFailure(Call<List<AccountResponse>> call, Throwable t) {}
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateSummary(List<LoanApplicationResponse> loans) {
        BigDecimal totalOutstanding = BigDecimal.ZERO;
        int activeCount = 0;
        for (LoanApplicationResponse loan : loans) {
            totalOutstanding = totalOutstanding.add(loan.getOutstandingBalance());
            activeCount++;
        }
        binding.totalDebtValue.setText(getString(R.string.currency_format, totalOutstanding.toString()));
        binding.loanCountText.setText("Active Loans: " + activeCount);
    }

    private void showApplyLoanDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_apply_loan, null);
        builder.setView(view);
        android.app.AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        android.widget.AutoCompleteTextView accDropdown = view.findViewById(R.id.accountAutoComplete);
        android.widget.EditText amountEdit = view.findViewById(R.id.amountEditText);
        android.widget.EditText tenureEdit = view.findViewById(R.id.tenureEditText);
        android.widget.EditText gNameEdit = view.findViewById(R.id.guarantorNameEdit);
        android.widget.EditText gPhoneEdit = view.findViewById(R.id.guarantorPhoneEdit);
        View uploadPhotoBtn = view.findViewById(R.id.btnUploadGuarantorPhoto);
        android.widget.Button applyBtn = view.findViewById(R.id.applyButton);

        List<String> accNames = new ArrayList<>();
        for (AccountResponse a : accountList) accNames.add(a.getAccountNumber());
        accDropdown.setAdapter(new ArrayAdapter<>(requireContext(), com.ensark.ensarkbank.R.layout.item_dropdown, accNames));
        accDropdown.setOnItemClickListener((p, v, pos, id) -> selectedAccountId = accountList.get(pos).getId());

        uploadPhotoBtn.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        applyBtn.setOnClickListener(v -> {
            String amount = amountEdit.getText().toString();
            String tenure = tenureEdit.getText().toString();
            if (selectedAccountId == null || amount.isEmpty() || tenure.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            LoanApplicationRequest request = LoanApplicationRequest.builder()
                    .accountId(selectedAccountId)
                    .principalAmount(new BigDecimal(amount))
                    .tenureMonths(Integer.parseInt(tenure))
                    .annualInterestRate(new BigDecimal("9.5")) // Default rate
                    .guarantor(LoanApplicationRequest.GuarantorRequest.builder()
                            .name(gNameEdit.getText().toString())
                            .phone(gPhoneEdit.getText().toString())
                            .build())
                    .build();

            String json = new Gson().toJson(request);
            RequestBody data = RequestBody.create(MediaType.parse("application/json"), json);
            viewModel.applyForLoan(data, guarantorPhotoPart, sessionManager.getCustomer().getEmail());
            dialog.dismiss();
        });

        dialog.show();
    }

    private void prepareGuarantorPhoto(Uri uri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            File file = new File(requireContext().getCacheDir(), "guarantor_photo.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            guarantorPhotoPart = MultipartBody.Part.createFormData("guarantorPhoto", file.getName(), requestFile);
            Toast.makeText(requireContext(), "Photo attached", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to process image", Toast.LENGTH_SHORT).show();
        }
    }
}
