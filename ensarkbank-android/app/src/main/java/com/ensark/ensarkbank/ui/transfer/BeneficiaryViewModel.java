package com.ensark.ensarkbank.ui.transfer;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;
import com.ensark.ensarkbank.repository.BeneficiaryRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeneficiaryViewModel extends BaseViewModel {

    private final BeneficiaryRepository repository;
    private final MutableLiveData<List<BeneficiaryResponse>> _beneficiaries = new MutableLiveData<>();
    public final LiveData<List<BeneficiaryResponse>> beneficiaries = _beneficiaries;

    public BeneficiaryViewModel(@NonNull Application application) {
        super(application);
        this.repository = new BeneficiaryRepository(application);
    }

    public void fetchBeneficiaries(String email) {
        setLoading(true);
        repository.getByCustomerEmail(email, new Callback<List<BeneficiaryResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<BeneficiaryResponse>> call, @NonNull Response<List<BeneficiaryResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _beneficiaries.postValue(response.body());
                } else {
                    setError("Failed to fetch beneficiaries");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BeneficiaryResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void addBeneficiary(com.ensark.ensarkbank.model.dto.BeneficiaryRequest request, String email) {
        setLoading(true);
        repository.add(request, new Callback<com.ensark.ensarkbank.model.dto.BeneficiaryResponse>() {
            @Override
            public void onResponse(@NonNull Call<com.ensark.ensarkbank.model.dto.BeneficiaryResponse> call, @NonNull Response<com.ensark.ensarkbank.model.dto.BeneficiaryResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    fetchBeneficiaries(email);
                } else {
                    setError("Failed to add beneficiary");
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.ensark.ensarkbank.model.dto.BeneficiaryResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void deleteBeneficiary(Long id, String email) {
        setLoading(true);
        repository.delete(id, new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    fetchBeneficiaries(email);
                } else {
                    setError("Delete failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
