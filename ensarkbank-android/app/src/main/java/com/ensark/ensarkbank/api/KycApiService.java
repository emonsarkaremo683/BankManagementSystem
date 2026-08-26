package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.KycResponse;
import com.ensark.ensarkbank.model.dto.KycDocumentResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface KycApiService {

    @Multipart
    @POST("api/kyc/my-documents")
    Call<Void> uploadMyDocuments(
            @Part MultipartBody.Part nid,
            @Part MultipartBody.Part passport,
            @Part MultipartBody.Part drivingLicense,
            @Part MultipartBody.Part birthCertificate
    );

    @GET("api/kyc/my-status")
    Call<Map<String, Object>> getMyKycStatus();

    @GET("api/kyc/customer/{customerId}")
    Call<KycResponse> findByCustomerId(@Path("customerId") Long customerId);

    @Multipart
    @POST("api/kyc/{customerId}/passport")
    Call<Void> savePassport(
            @Path("customerId") Long customerId,
            @Part MultipartBody.Part passport
    );

    @GET("api/kyc/documents/{documentId}/info")
    Call<KycDocumentResponse> getDocumentInfo(@Path("documentId") Long documentId);

    @GET("api/kyc/documents/{documentId}")
    Call<ResponseBody> getDocumentFile(@Path("documentId") Long documentId);
}
