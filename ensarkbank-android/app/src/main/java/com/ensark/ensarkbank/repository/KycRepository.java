package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.KycApiService;
import com.ensark.ensarkbank.model.dto.KycResponse;
import com.ensark.ensarkbank.model.dto.KycDocumentResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class KycRepository {

    private final KycApiService apiService;

    public KycRepository(Context context) {
        apiService = ApiClient.getClient(context).create(KycApiService.class);
    }

    public void uploadMyDocuments(MultipartBody.Part nid, MultipartBody.Part passport,
                                  MultipartBody.Part drivingLicense, MultipartBody.Part birthCertificate,
                                  Callback<Void> callback) {
        apiService.uploadMyDocuments(nid, passport, drivingLicense, birthCertificate).enqueue(callback);
    }

    public void getMyKycStatus(Callback<Map<String, Object>> callback) {
        apiService.getMyKycStatus().enqueue(callback);
    }

    public void findByCustomerId(Long customerId, Callback<KycResponse> callback) {
        apiService.findByCustomerId(customerId).enqueue(callback);
    }

    public void savePassport(Long customerId, MultipartBody.Part passport, Callback<Void> callback) {
        apiService.savePassport(customerId, passport).enqueue(callback);
    }

    public void getDocumentInfo(Long documentId, Callback<KycDocumentResponse> callback) {
        apiService.getDocumentInfo(documentId).enqueue(callback);
    }

    public void getDocumentFile(Long documentId, Callback<ResponseBody> callback) {
        apiService.getDocumentFile(documentId).enqueue(callback);
    }
}
