package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.ForgetPasswordRequest;
import com.ensark.ensarkbank.model.dto.LoginRequest;
import com.ensark.ensarkbank.model.dto.LoginResponse;
import com.ensark.ensarkbank.model.dto.ResetPasswordRequest;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface AuthApiService {

    @POST("api/auth/login")
    Call<LoginResponse<CustomerResponse>> login(@Body LoginRequest dto);

    @POST("api/auth/verify-mfa")
    Call<LoginResponse<CustomerResponse>> verifyMfa(@Body Map<String, String> body);

    @POST("api/auth/setup-mfa")
    Call<LoginResponse<CustomerResponse>> setupMfa(@Body Map<String, String> body);

    @POST("api/auth/confirm-mfa")
    Call<Map<String, String>> confirmMfa(@Body Map<String, String> body);

    @POST("api/auth/disable-mfa")
    Call<Map<String, String>> disableMfa(@Body Map<String, String> body);

    @POST("api/auth/logout")
    Call<Map<String, String>> logout(@Header("Authorization") String authHeader);

    @POST("api/auth/refresh")
    Call<LoginResponse<CustomerResponse>> refreshToken(@Body Map<String, String> body);

    @Multipart
    @POST("api/auth/register")
    Call<CustomerResponse> register(
            @Part("data") RequestBody data,
            @Part MultipartBody.Part profile,
            @Part MultipartBody.Part nid,
            @Part MultipartBody.Part passport,
            @Part MultipartBody.Part drivingLicense,
            @Part MultipartBody.Part birthCertificate
    );

    @GET("api/auth/verify-email")
    Call<String> verifyEmail(@Query("token") String token);

    @POST("api/auth/send-verification")
    Call<String> sendVerification(@Body ForgetPasswordRequest dto);

    @POST("api/auth/forgot-password")
    Call<String> forgotPassword(@Body ForgetPasswordRequest dto);

    @POST("api/auth/reset-password")
    Call<String> resetPassword(@Body ResetPasswordRequest dto);
}
