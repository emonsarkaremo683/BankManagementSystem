package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.StandingOrderRequest;
import com.ensark.ensarkbank.model.dto.StandingOrderResponse;
import com.ensark.ensarkbank.model.dto.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StandingOrderApiService {

    @POST("api/standing-orders/")
    Call<StandingOrderResponse> create(@Body StandingOrderRequest request);

    @PUT("api/standing-orders/{id}/cancel")
    Call<StandingOrderResponse> cancel(@Path("id") Long id);

    @PUT("api/standing-orders/{id}/pause")
    Call<StandingOrderResponse> pause(@Path("id") Long id);

    @PUT("api/standing-orders/{id}/resume")
    Call<StandingOrderResponse> resume(@Path("id") Long id);

    @GET("api/standing-orders/{id}")
    Call<StandingOrderResponse> findById(@Path("id") Long id);

    @GET("api/standing-orders/account/{accountId}")
    Call<List<StandingOrderResponse>> findByAccountId(@Path("accountId") Long accountId);

    @PUT("api/standing-orders/{id}")
    Call<StandingOrderResponse> update(@Path("id") Long id, @Body StandingOrderRequest request);

    @GET("api/standing-orders/{id}/history")
    Call<List<TransactionResponse>> getExecutionHistory(@Path("id") Long id);
}
