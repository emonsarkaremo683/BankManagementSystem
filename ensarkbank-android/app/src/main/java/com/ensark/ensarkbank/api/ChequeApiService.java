package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.ChequeBookRequest;
import com.ensark.ensarkbank.model.dto.ChequeBookResponse;
import com.ensark.ensarkbank.model.dto.ChequeLeafResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChequeApiService {

    @POST("api/cheque/apply")
    Call<ChequeBookResponse> apply(@Body ChequeBookRequest request);

    @GET("api/cheque/customer/email/{email}")
    Call<List<ChequeBookResponse>> findByCustomerEmail(@Path("email") String email);

    @GET("api/cheque/{id}")
    Call<ChequeBookResponse> findById(@Path("id") Long id);

    @POST("api/cheque/leaves/{leafId}/stop-payment")
    Call<ChequeLeafResponse> stopPayment(@Path("leafId") Long leafId, @Query("remarks") String remarks);

    @POST("api/cheque/leaves/{leafId}/cancel")
    Call<ChequeLeafResponse> cancelLeaf(@Path("leafId") Long leafId, @Query("remarks") String remarks);

    @GET("api/cheque/customer/{customerId}/leaves")
    Call<List<ChequeLeafResponse>> getLeavesByCustomerId(@Path("customerId") Long customerId, @Query("status") String status);

    @GET("api/cheque/{chequeBookId}/unused-count")
    Call<Long> getUnusedLeafCount(@Path("chequeBookId") Long chequeBookId);

    @GET("api/cheque/account/{accountId}/summary")
    Call<Map<String, Long>> getChequeBookSummary(@Path("accountId") Long accountId);

    @POST("api/cheque/leaves/{leafId}/stop-payment-presented")
    Call<ChequeLeafResponse> stopPaymentOnPresented(@Path("leafId") Long leafId, @Query("remarks") String remarks);
}
