package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.StandingOrderApiService;
import com.ensark.ensarkbank.model.dto.StandingOrderRequest;
import com.ensark.ensarkbank.model.dto.StandingOrderResponse;
import com.ensark.ensarkbank.model.dto.TransactionResponse;

import java.util.List;

import retrofit2.Callback;

public class StandingOrderRepository {

    private final StandingOrderApiService apiService;

    public StandingOrderRepository(Context context) {
        apiService = ApiClient.getClient(context).create(StandingOrderApiService.class);
    }

    public void create(StandingOrderRequest request, Callback<StandingOrderResponse> callback) {
        apiService.create(request).enqueue(callback);
    }

    public void cancel(Long id, Callback<StandingOrderResponse> callback) {
        apiService.cancel(id).enqueue(callback);
    }

    public void pause(Long id, Callback<StandingOrderResponse> callback) {
        apiService.pause(id).enqueue(callback);
    }

    public void resume(Long id, Callback<StandingOrderResponse> callback) {
        apiService.resume(id).enqueue(callback);
    }

    public void findById(Long id, Callback<StandingOrderResponse> callback) {
        apiService.findById(id).enqueue(callback);
    }

    public void findByAccountId(Long accountId, Callback<List<StandingOrderResponse>> callback) {
        apiService.findByAccountId(accountId).enqueue(callback);
    }

    public void update(Long id, StandingOrderRequest request, Callback<StandingOrderResponse> callback) {
        apiService.update(id, request).enqueue(callback);
    }

    public void getExecutionHistory(Long id, Callback<List<TransactionResponse>> callback) {
        apiService.getExecutionHistory(id).enqueue(callback);
    }
}
