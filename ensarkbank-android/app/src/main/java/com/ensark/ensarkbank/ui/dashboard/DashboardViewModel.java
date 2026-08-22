package com.ensark.ensarkbank.ui.dashboard;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.CustomerDashboardResponse;
import com.ensark.ensarkbank.repository.CustomerRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends BaseViewModel {

    private final CustomerRepository customerRepository;
    private final MutableLiveData<CustomerDashboardResponse> _dashboardData = new MutableLiveData<>();
    public final LiveData<CustomerDashboardResponse> dashboardData = _dashboardData;

    public DashboardViewModel(Application application) {
        this.customerRepository = new CustomerRepository(application);
    }

    public void fetchDashboard() {
        setLoading(true);
        customerRepository.getDashboard(new Callback<CustomerDashboardResponse>() {
            @Override
            public void onResponse(@NonNull Call<CustomerDashboardResponse> call, @NonNull Response<CustomerDashboardResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    _dashboardData.postValue(response.body());
                } else {
                    setError("Failed to load dashboard data");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CustomerDashboardResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
