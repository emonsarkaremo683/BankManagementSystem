package com.ensark.ensarkbank.ui.transfer;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.StandingOrderRequest;
import com.ensark.ensarkbank.model.dto.StandingOrderResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.repository.StandingOrderRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandingOrderViewModel extends BaseViewModel {

    private final StandingOrderRepository repository;
    private final AccountRepository accountRepository;
    
    private final MutableLiveData<List<StandingOrderResponse>> _standingOrders = new MutableLiveData<>();
    public final LiveData<List<StandingOrderResponse>> standingOrders = _standingOrders;

    private final MutableLiveData<List<AccountResponse>> _accounts = new MutableLiveData<>();
    public final LiveData<List<AccountResponse>> accounts = _accounts;

    private final MutableLiveData<StandingOrderResponse> _orderCreated = new MutableLiveData<>();
    public final LiveData<StandingOrderResponse> orderCreated = _orderCreated;

    public StandingOrderViewModel(@NonNull Application application) {
        super(application);
        this.repository = new StandingOrderRepository(application);
        this.accountRepository = new AccountRepository(application);
    }

    public void fetchStandingOrders(Long accountId) {
        setLoading(true);
        repository.findByAccountId(accountId, new Callback<List<StandingOrderResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<StandingOrderResponse>> call, @NonNull Response<List<StandingOrderResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _standingOrders.postValue(response.body());
                } else {
                    setError("Failed to fetch standing orders");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StandingOrderResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void pauseOrder(Long id, Long accountId) {
        setLoading(true);
        repository.pause(id, new Callback<StandingOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<StandingOrderResponse> call, @NonNull Response<StandingOrderResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    fetchStandingOrders(accountId);
                } else {
                    setError("Failed to pause order");
                }
            }

            @Override
            public void onFailure(@NonNull Call<StandingOrderResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void resumeOrder(Long id, Long accountId) {
        setLoading(true);
        repository.resume(id, new Callback<StandingOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<StandingOrderResponse> call, @NonNull Response<StandingOrderResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    fetchStandingOrders(accountId);
                } else {
                    setError("Failed to resume order");
                }
            }

            @Override
            public void onFailure(@NonNull Call<StandingOrderResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void fetchAccounts(String email) {
        setLoading(true);
        accountRepository.findByCustomerEmail(email, new Callback<List<AccountResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<AccountResponse>> call, @NonNull Response<List<AccountResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _accounts.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AccountResponse>> call, @NonNull Throwable t) {
                setLoading(false);
            }
        });
    }

    public void createStandingOrder(StandingOrderRequest request) {
        setLoading(true);
        repository.create(request, new Callback<StandingOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<StandingOrderResponse> call, @NonNull Response<StandingOrderResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _orderCreated.postValue(response.body());
                } else {
                    setError("Failed to set standing order");
                }
            }

            @Override
            public void onFailure(@NonNull Call<StandingOrderResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
