package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.ChequeApiService;
import com.ensark.ensarkbank.model.dto.ChequeBookRequest;
import com.ensark.ensarkbank.model.dto.ChequeBookResponse;
import com.ensark.ensarkbank.model.dto.ChequeLeafResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class ChequeRepository {

    private final ChequeApiService apiService;

    public ChequeRepository(Context context) {
        apiService = ApiClient.getClient(context).create(ChequeApiService.class);
    }

    public void apply(ChequeBookRequest request, Callback<ChequeBookResponse> callback) {
        apiService.apply(request).enqueue(callback);
    }

    public void findByCustomerEmail(String email, Callback<List<ChequeBookResponse>> callback) {
        apiService.findByCustomerEmail(email).enqueue(callback);
    }

    public void findById(Long id, Callback<ChequeBookResponse> callback) {
        apiService.findById(id).enqueue(callback);
    }

    public void stopPayment(Long leafId, String remarks, Callback<ChequeLeafResponse> callback) {
        apiService.stopPayment(leafId, remarks).enqueue(callback);
    }

    public void cancelLeaf(Long leafId, String remarks, Callback<ChequeLeafResponse> callback) {
        apiService.cancelLeaf(leafId, remarks).enqueue(callback);
    }

    public void getLeavesByCustomerId(Long customerId, String status, Callback<List<ChequeLeafResponse>> callback) {
        apiService.getLeavesByCustomerId(customerId, status).enqueue(callback);
    }

    public void getUnusedLeafCount(Long chequeBookId, Callback<Long> callback) {
        apiService.getUnusedLeafCount(chequeBookId).enqueue(callback);
    }

    public void getChequeBookSummary(Long accountId, Callback<Map<String, Long>> callback) {
        apiService.getChequeBookSummary(accountId).enqueue(callback);
    }

    public void stopPaymentOnPresented(Long leafId, String remarks, Callback<ChequeLeafResponse> callback) {
        apiService.stopPaymentOnPresented(leafId, remarks).enqueue(callback);
    }
}
