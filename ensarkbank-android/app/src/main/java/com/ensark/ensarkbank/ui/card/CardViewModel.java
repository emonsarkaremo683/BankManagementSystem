package com.ensark.ensarkbank.ui.card;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.CardRequest;
import com.ensark.ensarkbank.model.dto.CardResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.repository.CardRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardViewModel extends BaseViewModel {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    private final MutableLiveData<List<CardResponse>> _cards = new MutableLiveData<>();
    public final LiveData<List<CardResponse>> cards = _cards;

    private final MutableLiveData<List<AccountResponse>> _accounts = new MutableLiveData<>();
    public final LiveData<List<AccountResponse>> accounts = _accounts;

    private final MutableLiveData<Boolean> _cardApplied = new MutableLiveData<>();
    public final LiveData<Boolean> cardApplied = _cardApplied;

    public CardViewModel(@NonNull Application application) {
        super(application);
        this.cardRepository = new CardRepository(application);
        this.accountRepository = new AccountRepository(application);
    }

    public void fetchCards(String email) {
        setLoading(true);
        cardRepository.findByCustomerEmail(email, new Callback<List<CardResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CardResponse>> call, @NonNull Response<List<CardResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _cards.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CardResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void fetchAccounts(String email) {
        accountRepository.findByCustomerEmail(email, new Callback<List<AccountResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<AccountResponse>> call, @NonNull Response<List<AccountResponse>> response) {
                if (response.isSuccessful()) {
                    _accounts.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AccountResponse>> call, @NonNull Throwable t) {
                setError(t.getMessage());
            }
        });
    }

    public void applyForCard(CardRequest request) {
        setLoading(true);
        cardRepository.apply(request, new Callback<CardResponse>() {
            @Override
            public void onResponse(@NonNull Call<CardResponse> call, @NonNull Response<CardResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _cardApplied.postValue(true);
                } else {
                    setError("Application failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CardResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
