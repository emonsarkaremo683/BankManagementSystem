package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.LoanApiService;
import com.ensark.ensarkbank.model.dto.LoanApplicationResponse;
import com.ensark.ensarkbank.model.dto.LoanRepaymentResponse;
import com.ensark.ensarkbank.model.dto.LoanScheduleResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class LoanRepository {

    private final LoanApiService apiService;

    public LoanRepository(Context context) {
        apiService = ApiClient.getClient(context).create(LoanApiService.class);
    }

    public void apply(RequestBody data, List<MultipartBody.Part> documents, MultipartBody.Part guarantorPhoto, Callback<LoanApplicationResponse> callback) {
        apiService.apply(data, documents, guarantorPhoto).enqueue(callback);
    }

    public void findByCustomerEmail(String email, Callback<List<LoanApplicationResponse>> callback) {
        apiService.findByCustomerEmail(email).enqueue(callback);
    }

    public void findById(Long id, Callback<LoanApplicationResponse> callback) {
        apiService.findById(id).enqueue(callback);
    }

    public void getRepaymentsByLoan(Long id, Callback<List<LoanRepaymentResponse>> callback) {
        apiService.getRepaymentsByLoan(id).enqueue(callback);
    }

    public void getSchedule(Long id, Callback<List<LoanScheduleResponse>> callback) {
        apiService.getSchedule(id).enqueue(callback);
    }

    public void payInstallment(Long repaymentId, Callback<LoanRepaymentResponse> callback) {
        apiService.payInstallment(repaymentId).enqueue(callback);
    }

    public void getLoanSummary(Long id, Callback<Map<String, Object>> callback) {
        apiService.getLoanSummary(id).enqueue(callback);
    }

    public void payInstallmentByAccount(Long repaymentId, Long accountId, Callback<LoanRepaymentResponse> callback) {
        apiService.payInstallmentByAccount(repaymentId, accountId).enqueue(callback);
    }
}
