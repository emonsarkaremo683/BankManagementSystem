package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.CustomerDashboardResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerApiService {

    @GET("api/customer/email/{email}")
    Call<CustomerResponse> findByEmail(@Path("email") String email);

    @GET("api/customer/{id}")
    Call<CustomerResponse> findById(@Path("id") Long id);

    @Multipart
    @PUT("api/customer/{id}")
    Call<CustomerResponse> updateByCustomer(
            @Path("id") Long id,
            @Part("data") RequestBody data,
            @Part MultipartBody.Part profile
    );

    @PATCH("api/customer/{id}/password")
    Call<CustomerResponse> updatePassword(
            @Path("id") Long id,
            @Query("oldPass") String oldPass,
            @Query("newPass") String newPass
    );

    @Multipart
    @PUT("api/customer/{id}/profile")
    Call<CustomerResponse> updateProfilePicture(
            @Path("id") Long id,
            @Part MultipartBody.Part profile
    );

    @GET("api/customer/state")
    Call<CustomerDashboardResponse> getDashboard();
}
