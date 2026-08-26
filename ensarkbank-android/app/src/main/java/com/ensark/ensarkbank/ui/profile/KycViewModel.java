package com.ensark.ensarkbank.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.KycResponse;
import com.ensark.ensarkbank.repository.KycRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycViewModel extends BaseViewModel {

    private final KycRepository kycRepository;
    
    private final MutableLiveData<Map<String, Object>> _kycStatus = new MutableLiveData<>();
    public final LiveData<Map<String, Object>> kycStatus = _kycStatus;

    private final MutableLiveData<KycResponse> _fullKycData = new MutableLiveData<>();
    public final LiveData<KycResponse> fullKycData = _fullKycData;

    private final MutableLiveData<Boolean> _uploadSuccess = new MutableLiveData<>();
    public final LiveData<Boolean> uploadSuccess = _uploadSuccess;

    public KycViewModel(@NonNull Application application) {
        super(application);
        this.kycRepository = new KycRepository(application);
    }

    public void fetchKycStatus() {
        setLoading(true);
        kycRepository.getMyKycStatus(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _kycStatus.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void fetchFullKycData(Long customerId) {
        setLoading(true);
        kycRepository.findByCustomerId(customerId, new Callback<KycResponse>() {
            @Override
            public void onResponse(@NonNull Call<KycResponse> call, @NonNull Response<KycResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _fullKycData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<KycResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void uploadDocuments(MultipartBody.Part nid, MultipartBody.Part passport,
                                MultipartBody.Part license, MultipartBody.Part birth) {
        setLoading(true);
        kycRepository.uploadMyDocuments(nid, passport, license, birth, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _uploadSuccess.postValue(true);
                } else {
                    setError("Upload failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
