package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.CardRequest;
import com.ensark.ensarkbank.model.dto.CardResponse;
import com.ensark.ensarkbank.model.dto.CardUsageResponse;
import com.ensark.ensarkbank.model.dto.CardSettingsRequest;
import com.ensark.ensarkbank.model.dto.PinChangeRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CardApiService {

    @POST("api/card/apply")
    Call<CardResponse> apply(@Body CardRequest request);

    @GET("api/card/customer/{email}")
    Call<List<CardResponse>> findByCustomerEmail(@Path("email") String email);

    @PATCH("api/card/{id}/pin")
    Call<CardResponse> updatePin(@Path("id") Long id, @Body PinChangeRequest request);

    @GET("api/card/{id}")
    Call<CardResponse> findById(@Path("id") Long id);

    @PATCH("api/card/{id}/limit")
    Call<CardResponse> setTransactionLimit(
            @Path("id") Long id,
            @Query("dailyLimit") BigDecimal dailyLimit,
            @Query("monthlyLimit") BigDecimal monthlyLimit
    );

    @POST("api/card/{id}/report-lost-stolen")
    Call<CardResponse> reportLostOrStolen(@Path("id") Long id, @Query("reason") String reason);

    @GET("api/card/{id}/usage")
    Call<CardUsageResponse> getUsage(@Path("id") Long id);

    @POST("api/card-settings-requests/")
    Call<CardSettingsRequest> createSettingsRequest(@Body Map<String, Object> body);

    @GET("api/card-settings-requests/customer/{customerId}")
    Call<List<CardSettingsRequest>> getMySettingsRequests(@Path("customerId") Long customerId);
}
