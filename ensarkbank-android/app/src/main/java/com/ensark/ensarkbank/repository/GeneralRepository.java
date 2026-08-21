package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.GeneralApiService;
import com.ensark.ensarkbank.model.dto.BranchResponse;
import com.ensark.ensarkbank.model.dto.CurrencyResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class GeneralRepository {

    private final GeneralApiService apiService;

    public GeneralRepository(Context context) {
        apiService = ApiClient.getClient(context).create(GeneralApiService.class);
    }

    public void getAllBranches(Callback<List<BranchResponse>> callback) {
        apiService.getAllBranches().enqueue(callback);
    }

    public void getBranchById(Long id, Callback<BranchResponse> callback) {
        apiService.getBranchById(id).enqueue(callback);
    }

    public void findBranchesByPoliceStation(Long policeStationId, Callback<List<BranchResponse>> callback) {
        apiService.findBranchesByPoliceStation(policeStationId).enqueue(callback);
    }

    public void convertCurrency(String from, String to, BigDecimal amount, Callback<Map<String, Object>> callback) {
        apiService.convertCurrency(from, to, amount).enqueue(callback);
    }

    public void getAllCurrencies(String base, Callback<List<CurrencyResponse>> callback) {
        apiService.getAllCurrencies(base).enqueue(callback);
    }

    public void getCreditAccountBalance(Long id, Callback<Map<String, Object>> callback) {
        apiService.getCreditAccountBalance(id).enqueue(callback);
    }
}
