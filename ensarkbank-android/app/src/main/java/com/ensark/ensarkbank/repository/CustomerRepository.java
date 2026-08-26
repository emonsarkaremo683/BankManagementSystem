package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.CustomerApiService;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.CustomerDashboardResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class CustomerRepository {

    private final CustomerApiService apiService;

    public CustomerRepository(Context context) {
        apiService = ApiClient.getClient(context).create(CustomerApiService.class);
    }

    public void findByEmail(String email, Callback<CustomerResponse> callback) {
        apiService.findByEmail(email).enqueue(callback);
    }

    public void findById(Long id, Callback<CustomerResponse> callback) {
        apiService.findById(id).enqueue(callback);
    }

    public void updateByCustomer(Long id, RequestBody data, MultipartBody.Part profile, Callback<CustomerResponse> callback) {
        apiService.updateByCustomer(id, data, profile).enqueue(callback);
    }

    public void updatePassword(Long id, String oldPass, String newPass, Callback<CustomerResponse> callback) {
        apiService.updatePassword(id, oldPass, newPass).enqueue(callback);
    }

    public void updateProfilePicture(Long id, MultipartBody.Part profile, Callback<CustomerResponse> callback) {
        apiService.updateProfilePicture(id, profile).enqueue(callback);
    }

    public void getDashboard(Callback<CustomerDashboardResponse> callback) {
        apiService.getDashboard().enqueue(callback);
    }
}
