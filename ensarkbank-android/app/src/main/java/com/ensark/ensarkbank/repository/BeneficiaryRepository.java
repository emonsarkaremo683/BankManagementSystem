package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.BeneficiaryApiService;
import com.ensark.ensarkbank.model.dto.BeneficiaryRequest;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;

import java.util.List;

import retrofit2.Callback;

public class BeneficiaryRepository {

    private final BeneficiaryApiService apiService;

    public BeneficiaryRepository(Context context) {
        apiService = ApiClient.getClient(context).create(BeneficiaryApiService.class);
    }

    public void add(BeneficiaryRequest request, Callback<BeneficiaryResponse> callback) {
        apiService.add(request).enqueue(callback);
    }

    public void update(Long id, BeneficiaryRequest request, Callback<BeneficiaryResponse> callback) {
        apiService.update(id, request).enqueue(callback);
    }

    public void getByCustomerEmail(String email, Callback<List<BeneficiaryResponse>> callback) {
        apiService.getByCustomerEmail(email).enqueue(callback);
    }

    public void getByCustomerId(Long customerId, Callback<List<BeneficiaryResponse>> callback) {
        apiService.getByCustomerId(customerId).enqueue(callback);
    }

    public void findById(Long id, Callback<BeneficiaryResponse> callback) {
        apiService.findById(id).enqueue(callback);
    }

    public void initiateVerification(Long id, Callback<String> callback) {
        apiService.initiateVerification(id).enqueue(callback);
    }

    public void verify(Long id, String otpCode, Callback<String> callback) {
        apiService.verify(id, otpCode).enqueue(callback);
    }

    public void delete(Long id, Callback<String> callback) {
        apiService.delete(id).enqueue(callback);
    }

    public void findByAccountId(Long accountId, Callback<List<BeneficiaryResponse>> callback) {
        apiService.findByAccountId(accountId).enqueue(callback);
    }
}
