package com.ensark.ensarkbank.ui.utility;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.CurrencyResponse;
import com.ensark.ensarkbank.repository.GeneralRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConverterViewModel extends BaseViewModel {

    private final GeneralRepository generalRepository;

    private final MutableLiveData<List<CurrencyResponse>> _currencies = new MutableLiveData<>();
    public final LiveData<List<CurrencyResponse>> currencies = _currencies;

    private final MutableLiveData<BigDecimal> _convertedAmount = new MutableLiveData<>();
    public final LiveData<BigDecimal> convertedAmount = _convertedAmount;

    public ConverterViewModel(@NonNull Application application) {
        super(application);
        this.generalRepository = new GeneralRepository(application);
    }

    public void fetchCurrencies() {
        setLoading(true);
        generalRepository.getAllCurrencies("USD", new Callback<List<CurrencyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CurrencyResponse>> call, @NonNull Response<List<CurrencyResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _currencies.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CurrencyResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void convert(String from, String to, BigDecimal amount) {
        setLoading(true);
        generalRepository.convertCurrency(from, to, amount, new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    Object result = response.body().get("convertedAmount");
                    if (result instanceof Double) {
                        _convertedAmount.postValue(BigDecimal.valueOf((Double) result));
                    } else if (result instanceof String) {
                        _convertedAmount.postValue(new BigDecimal((String) result));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
