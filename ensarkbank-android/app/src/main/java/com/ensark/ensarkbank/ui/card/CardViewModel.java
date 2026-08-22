package com.ensark.ensarkbank.ui.card;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.CardResponse;
import com.ensark.ensarkbank.repository.CardRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardViewModel extends BaseViewModel {

    private final CardRepository cardRepository;
    private final MutableLiveData<List<CardResponse>> _cards = new MutableLiveData<>();
    public final LiveData<List<CardResponse>> cards = _cards;

    public CardViewModel(Application application) {
        this.cardRepository = new CardRepository(application);
    }

    public void fetchCards(String email) {
        setLoading(true);
        cardRepository.findByCustomerEmail(email, new Callback<List<CardResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CardResponse>> call, @NonNull Response<List<CardResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _cards.postValue(response.body());
                } else {
                    setError("Failed to fetch cards");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CardResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void reportLost(Long cardId, String reason) {
        setLoading(true);
        cardRepository.reportLostOrStolen(cardId, reason, new Callback<CardResponse>() {
            @Override
            public void onResponse(@NonNull Call<CardResponse> call, @NonNull Response<CardResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    // Update card list or specific card status
                    setError("Card reported successfully");
                } else {
                    setError("Operation failed");
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
