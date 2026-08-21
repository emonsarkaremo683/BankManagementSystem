package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.AccountResponse;

import java.math.BigDecimal;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AccountApiService {

    @Multipart
    @POST("api/account/create")
    Call<AccountResponse> create(
            @Part("data") RequestBody data,
            @Part List<MultipartBody.Part> signatures,
            @Part MultipartBody.Part photo,
            @Part MultipartBody.Part nid_front,
            @Part MultipartBody.Part nid_back
    );

    @GET("api/account/{id}")
    Call<AccountResponse> findById(@Path("id") Long id);

    @GET("api/account/email/{email}")
    Call<List<AccountResponse>> findByCustomerEmail(@Path("email") String email);

    @GET("api/account/number/{accountNumber}")
    Call<AccountResponse> findByAccountNumber(@Path("accountNumber") String accountNumber);

    @GET("api/account/{accountNumber}/balance")
    Call<BigDecimal> getBalance(@Path("accountNumber") String accountNumber);
}
