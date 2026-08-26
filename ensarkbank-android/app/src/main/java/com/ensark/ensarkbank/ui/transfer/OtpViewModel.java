package com.ensark.ensarkbank.ui.transfer;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.AccountTransactionResponse;
import com.ensark.ensarkbank.model.dto.OtpVerifyRequest;
import com.ensark.ensarkbank.repository.TransactionRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpViewModel extends BaseViewModel {

    private final TransactionRepository transactionRepository;

    private final MutableLiveData<AccountTransactionResponse> _verificationResult = new MutableLiveData<>();
    public final LiveData<AccountTransactionResponse> verificationResult = _verificationResult;

    public OtpViewModel(@NonNull Application application) {
        super(application);
        this.transactionRepository = new TransactionRepository(application);
    }

    public void verifyOtp(Long referenceId, String otpCode) {
        setLoading(true);
        OtpVerifyRequest request = new OtpVerifyRequest(referenceId, otpCode);
        transactionRepository.verifyOnlineTransaction(request, new Callback<AccountTransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountTransactionResponse> call, @NonNull Response<AccountTransactionResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _verificationResult.postValue(response.body());
                } else {
                    setError("Verification failed. Please check your code.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountTransactionResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
