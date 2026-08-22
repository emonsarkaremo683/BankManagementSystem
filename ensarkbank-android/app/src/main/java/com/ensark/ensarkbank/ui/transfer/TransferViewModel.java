package com.ensark.ensarkbank.ui.transfer;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.AccountTransactionRequest;
import com.ensark.ensarkbank.model.dto.AccountTransactionResponse;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;
import com.ensark.ensarkbank.model.dto.OtpInitiateResponse;
import com.ensark.ensarkbank.repository.BeneficiaryRepository;
import com.ensark.ensarkbank.repository.TransactionRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferViewModel extends BaseViewModel {

    private final BeneficiaryRepository beneficiaryRepository;
    private final TransactionRepository transactionRepository;

    private final MutableLiveData<List<BeneficiaryResponse>> _beneficiaries = new MutableLiveData<>();
    public final LiveData<List<BeneficiaryResponse>> beneficiaries = _beneficiaries;

    private final MutableLiveData<OtpInitiateResponse> _transferInitiated = new MutableLiveData<>();
    public final LiveData<OtpInitiateResponse> transferInitiated = _transferInitiated;

    public TransferViewModel(Application application) {
        this.beneficiaryRepository = new BeneficiaryRepository(application);
        this.transactionRepository = new TransactionRepository(application);
    }

    public void fetchBeneficiaries(String email) {
        setLoading(true);
        beneficiaryRepository.getByCustomerEmail(email, new Callback<List<BeneficiaryResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<BeneficiaryResponse>> call, @NonNull Response<List<BeneficiaryResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _beneficiaries.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BeneficiaryResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void initiateTransfer(AccountTransactionRequest request) {
        setLoading(true);
        transactionRepository.initiateOnlineTransaction(request, new Callback<OtpInitiateResponse>() {
            @Override
            public void onResponse(@NonNull Call<OtpInitiateResponse> call, @NonNull Response<OtpInitiateResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _transferInitiated.postValue(response.body());
                } else {
                    setError("Transfer initiation failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<OtpInitiateResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
