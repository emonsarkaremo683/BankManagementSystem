package com.ensark.ensarkbank.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.repository.CustomerRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModel {

    private final CustomerRepository customerRepository;
    private final MutableLiveData<CustomerResponse> _customerData = new MutableLiveData<>();
    public final LiveData<CustomerResponse> customerData = _customerData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        this.customerRepository = new CustomerRepository(application);
    }

    public void fetchProfile(String email) {
        setLoading(true);
        customerRepository.findByEmail(email, new Callback<CustomerResponse>() {
            @Override
            public void onResponse(@NonNull Call<CustomerResponse> call, @NonNull Response<CustomerResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _customerData.postValue(response.body());
                } else {
                    setError("Failed to fetch profile");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CustomerResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void updateProfileImage(Long id, MultipartBody.Part part) {
        setLoading(true);
        customerRepository.updateProfilePicture(id, part, new Callback<CustomerResponse>() {
            @Override
            public void onResponse(@NonNull Call<CustomerResponse> call, @NonNull Response<CustomerResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    _customerData.postValue(response.body());
                } else {
                    setError("Failed to upload image");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CustomerResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
