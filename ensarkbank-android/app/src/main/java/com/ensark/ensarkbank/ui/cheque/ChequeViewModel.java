package com.ensark.ensarkbank.ui.cheque;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.ChequeBookResponse;
import com.ensark.ensarkbank.repository.ChequeRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChequeViewModel extends BaseViewModel {

    private final ChequeRepository chequeRepository;
    private final MutableLiveData<List<ChequeBookResponse>> _chequeBooks = new MutableLiveData<>();
    public final LiveData<List<ChequeBookResponse>> chequeBooks = _chequeBooks;

    public ChequeViewModel(@NonNull Application application) {
        super(application);
        this.chequeRepository = new ChequeRepository(application);
    }

    public void fetchChequeBooks(String email) {
        setLoading(true);
        chequeRepository.findByCustomerEmail(email, new Callback<List<ChequeBookResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ChequeBookResponse>> call, @NonNull Response<List<ChequeBookResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _chequeBooks.postValue(response.body());
                } else {
                    setError("Failed to fetch cheque books");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ChequeBookResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
