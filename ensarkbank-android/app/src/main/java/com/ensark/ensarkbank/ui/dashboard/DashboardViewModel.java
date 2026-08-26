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

import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.session.SessionManager;

import java.util.List;

public class DashboardViewModel extends BaseViewModel {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final MutableLiveData<CustomerDashboardResponse> _dashboardData = new MutableLiveData<>();
    public final LiveData<CustomerDashboardResponse> dashboardData = _dashboardData;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.customerRepository = new CustomerRepository(application);
        this.accountRepository = new AccountRepository(application);
        this.sessionManager = new SessionManager(application);
    }

    public void fetchDashboard() {
        setLoading(true);
        customerRepository.getDashboard(new Callback<CustomerDashboardResponse>() {
            @Override
            public void onResponse(@NonNull Call<CustomerDashboardResponse> call, @NonNull Response<CustomerDashboardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CustomerDashboardResponse data = response.body();
                    // If dashboard is empty, try fetching accounts directly as fallback
                    if (data.getAccounts() == null || data.getAccounts().isEmpty()) {
                        fetchFallbackAccounts(data);
                    } else {
                        setLoading(false);
                        _dashboardData.postValue(data);
                    }
                } else {
                    setLoading(false);
                    setError("Failed to load dashboard data: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CustomerDashboardResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError("Connection Error: " + t.getMessage());
            }
        });
    }

    private void fetchFallbackAccounts(CustomerDashboardResponse dashboard) {
        if (sessionManager.getCustomer() == null) {
            setLoading(false);
            _dashboardData.postValue(dashboard);
            return;
        }

        accountRepository.findByCustomerEmail(sessionManager.getCustomer().getEmail(), new Callback<List<AccountResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<AccountResponse>> call, @NonNull Response<List<AccountResponse>> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    dashboard.setAccounts(response.body());
                    if (!response.body().isEmpty()) {
                        dashboard.setBalance(response.body().get(0).getAvailableBalance());
                    }
                }
                _dashboardData.postValue(dashboard);
            }

            @Override
            public void onFailure(@NonNull Call<List<AccountResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                _dashboardData.postValue(dashboard);
            }
        });
    }
}
