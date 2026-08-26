package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.AccountApiService;
import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.model.dto.AccountResponse;

import java.math.BigDecimal;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class AccountRepository {

    private final AccountApiService apiService;

    public AccountRepository(Context context) {
        apiService = ApiClient.getClient(context).create(AccountApiService.class);
    }

    public void create(RequestBody data, List<MultipartBody.Part> signatures, MultipartBody.Part photo,
                       MultipartBody.Part nid_front, MultipartBody.Part nid_back, Callback<AccountResponse> callback) {
        apiService.create(data, signatures, photo, nid_front, nid_back).enqueue(callback);
    }

    public void findById(Long id, Callback<AccountResponse> callback) {
        apiService.findById(id).enqueue(callback);
    }

    public void findByCustomerEmail(String email, Callback<List<AccountResponse>> callback) {
        apiService.findByCustomerEmail(email).enqueue(callback);
    }

    public void findByAccountNumber(String accountNumber, Callback<AccountResponse> callback) {
        apiService.findByAccountNumber(accountNumber).enqueue(callback);
    }

    public void getBalance(String accountNumber, Callback<BigDecimal> callback) {
        apiService.getBalance(accountNumber).enqueue(callback);
    }
}
