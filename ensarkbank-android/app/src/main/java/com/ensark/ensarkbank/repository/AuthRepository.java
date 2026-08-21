package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.AuthApiService;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.ForgetPasswordRequest;
import com.ensark.ensarkbank.model.dto.LoginRequest;
import com.ensark.ensarkbank.model.dto.LoginResponse;
import com.ensark.ensarkbank.model.dto.ResetPasswordRequest;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AuthRepository {

    private final AuthApiService apiService;

    public AuthRepository(Context context) {
        apiService = ApiClient.getClient(context).create(AuthApiService.class);
    }

    public void login(LoginRequest dto, Callback<LoginResponse<CustomerResponse>> callback) {
        apiService.login(dto).enqueue(callback);
    }

    public void verifyMfa(Map<String, String> body, Callback<LoginResponse<CustomerResponse>> callback) {
        apiService.verifyMfa(body).enqueue(callback);
    }

    public void setupMfa(Map<String, String> body, Callback<LoginResponse<CustomerResponse>> callback) {
        apiService.setupMfa(body).enqueue(callback);
    }

    public void confirmMfa(Map<String, String> body, Callback<Map<String, String>> callback) {
        apiService.confirmMfa(body).enqueue(callback);
    }

    public void disableMfa(Map<String, String> body, Callback<Map<String, String>> callback) {
        apiService.disableMfa(body).enqueue(callback);
    }

    public void logout(String authHeader, Callback<Map<String, String>> callback) {
        apiService.logout(authHeader).enqueue(callback);
    }

    public void refreshToken(Map<String, String> body, Callback<LoginResponse<CustomerResponse>> callback) {
        apiService.refreshToken(body).enqueue(callback);
    }

    public void register(RequestBody data, MultipartBody.Part profile, MultipartBody.Part nid,
                         MultipartBody.Part passport, MultipartBody.Part drivingLicense,
                         MultipartBody.Part birthCertificate, Callback<CustomerResponse> callback) {
        apiService.register(data, profile, nid, passport, drivingLicense, birthCertificate).enqueue(callback);
    }

    public void verifyEmail(String token, Callback<String> callback) {
        apiService.verifyEmail(token).enqueue(callback);
    }

    public void sendVerification(ForgetPasswordRequest dto, Callback<String> callback) {
        apiService.sendVerification(dto).enqueue(callback);
    }

    public void forgotPassword(ForgetPasswordRequest dto, Callback<String> callback) {
        apiService.forgotPassword(dto).enqueue(callback);
    }

    public void resetPassword(ResetPasswordRequest dto, Callback<String> callback) {
        apiService.resetPassword(dto).enqueue(callback);
    }
}
