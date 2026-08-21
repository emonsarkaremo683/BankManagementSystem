package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.CardApiService;
import com.ensark.ensarkbank.model.dto.CardRequest;
import com.ensark.ensarkbank.model.dto.CardResponse;
import com.ensark.ensarkbank.model.dto.CardUsageResponse;
import com.ensark.ensarkbank.model.dto.CardSettingsRequest;
import com.ensark.ensarkbank.model.dto.PinChangeRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class CardRepository {

    private final CardApiService apiService;

    public CardRepository(Context context) {
        apiService = ApiClient.getClient(context).create(CardApiService.class);
    }

    public void apply(CardRequest request, Callback<CardResponse> callback) {
        apiService.apply(request).enqueue(callback);
    }

    public void findByCustomerEmail(String email, Callback<List<CardResponse>> callback) {
        apiService.findByCustomerEmail(email).enqueue(callback);
    }

    public void updatePin(Long id, PinChangeRequest request, Callback<CardResponse> callback) {
        apiService.updatePin(id, request).enqueue(callback);
    }

    public void findById(Long id, Callback<CardResponse> callback) {
        apiService.findById(id).enqueue(callback);
    }

    public void setTransactionLimit(Long id, BigDecimal dailyLimit, BigDecimal monthlyLimit, Callback<CardResponse> callback) {
        apiService.setTransactionLimit(id, dailyLimit, monthlyLimit).enqueue(callback);
    }

    public void reportLostOrStolen(Long id, String reason, Callback<CardResponse> callback) {
        apiService.reportLostOrStolen(id, reason).enqueue(callback);
    }

    public void getUsage(Long id, Callback<CardUsageResponse> callback) {
        apiService.getUsage(id).enqueue(callback);
    }

    public void createSettingsRequest(Map<String, Object> body, Callback<CardSettingsRequest> callback) {
        apiService.createSettingsRequest(body).enqueue(callback);
    }

    public void getMySettingsRequests(Long customerId, Callback<List<CardSettingsRequest>> callback) {
        apiService.getMySettingsRequests(customerId).enqueue(callback);
    }
}
