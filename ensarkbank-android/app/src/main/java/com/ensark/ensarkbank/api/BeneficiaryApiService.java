package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.BeneficiaryRequest;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BeneficiaryApiService {

    @POST("api/beneficiary/")
    Call<BeneficiaryResponse> add(@Body BeneficiaryRequest request);

    @PUT("api/beneficiary/{id}")
    Call<BeneficiaryResponse> update(@Path("id") Long id, @Body BeneficiaryRequest request);

    @GET("api/beneficiary/customer/email/{email}")
    Call<List<BeneficiaryResponse>> getByCustomerEmail(@Path("email") String email);

    @GET("api/beneficiary/customer/{customerId}")
    Call<List<BeneficiaryResponse>> getByCustomerId(@Path("customerId") Long customerId);

    @GET("api/beneficiary/{id}")
    Call<BeneficiaryResponse> findById(@Path("id") Long id);

    @POST("api/beneficiary/{id}/initiate-verify")
    Call<Map<String, String>> initiateVerification(@Path("id") Long id);

    @POST("api/beneficiary/{id}/verify")
    Call<Map<String, String>> verify(@Path("id") Long id, @Query("otpCode") String otpCode);

    @DELETE("api/beneficiary/{id}")
    Call<Map<String, String>> delete(@Path("id") Long id);

    @GET("api/beneficiary/account/{accountId}")
    Call<List<BeneficiaryResponse>> findByAccountId(@Path("accountId") Long accountId);
}
