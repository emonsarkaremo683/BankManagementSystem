package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.LoanApplicationResponse;
import com.ensark.ensarkbank.model.dto.LoanRepaymentResponse;
import com.ensark.ensarkbank.model.dto.LoanScheduleResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoanApiService {

    @Multipart
    @POST("api/loan/apply")
    Call<LoanApplicationResponse> apply(
            @Part("data") RequestBody data,
            @Part List<MultipartBody.Part> documents,
            @Part MultipartBody.Part guarantorPhoto
    );

    @GET("api/loan/customer/{email}")
    Call<List<LoanApplicationResponse>> findByCustomerEmail(@Path("email") String email);

    @GET("api/loan/{id}")
    Call<LoanApplicationResponse> findById(@Path("id") Long id);

    @GET("api/loan/{id}/repayments")
    Call<List<LoanRepaymentResponse>> getRepaymentsByLoan(@Path("id") Long id);

    @GET("api/loan/{id}/schedule")
    Call<List<LoanScheduleResponse>> getSchedule(@Path("id") Long id);

    @POST("api/loan/repayments/{repaymentId}/pay")
    Call<LoanRepaymentResponse> payInstallment(@Path("repaymentId") Long repaymentId);

    @GET("api/loan/{id}/summary")
    Call<Map<String, Object>> getLoanSummary(@Path("id") Long id);

    @POST("api/loan/repayments/{repaymentId}/pay-account")
    Call<LoanRepaymentResponse> payInstallmentByAccount(
            @Path("repaymentId") Long repaymentId,
            @Query("accountId") Long accountId
    );
}
