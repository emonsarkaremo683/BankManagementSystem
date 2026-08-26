package com.ensark.ensarkbank.ui.loan;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.LoanApplicationResponse;
import com.ensark.ensarkbank.repository.LoanRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanViewModel extends BaseViewModel {

    private final LoanRepository loanRepository;
    private final MutableLiveData<List<LoanApplicationResponse>> _loans = new MutableLiveData<>();
    public final LiveData<List<LoanApplicationResponse>> loans = _loans;

    private final MutableLiveData<List<com.ensark.ensarkbank.model.dto.LoanScheduleResponse>> _emiSchedule = new MutableLiveData<>();
    public final LiveData<List<com.ensark.ensarkbank.model.dto.LoanScheduleResponse>> emiSchedule = _emiSchedule;

    private final MutableLiveData<Boolean> _repaymentSuccess = new MutableLiveData<>();
    public final LiveData<Boolean> repaymentSuccess = _repaymentSuccess;

    private final MutableLiveData<Boolean> _applicationSuccess = new MutableLiveData<>();
    public final LiveData<Boolean> applicationSuccess = _applicationSuccess;

    public LoanViewModel(@NonNull Application application) {
        super(application);
        this.loanRepository = new LoanRepository(application);
    }

    public void fetchLoans(String email) {
        setLoading(true);
        loanRepository.findByCustomerEmail(email, new Callback<List<LoanApplicationResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoanApplicationResponse>> call, @NonNull Response<List<LoanApplicationResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _loans.postValue(response.body());
                } else {
                    setError("Failed to fetch loans");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LoanApplicationResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void fetchSchedule(Long loanId) {
        setLoading(true);
        loanRepository.getSchedule(loanId, new Callback<List<com.ensark.ensarkbank.model.dto.LoanScheduleResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<com.ensark.ensarkbank.model.dto.LoanScheduleResponse>> call, @NonNull Response<List<com.ensark.ensarkbank.model.dto.LoanScheduleResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _emiSchedule.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<com.ensark.ensarkbank.model.dto.LoanScheduleResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void payEmi(Long repaymentId, String email) {
        setLoading(true);
        loanRepository.payInstallment(repaymentId, new Callback<com.ensark.ensarkbank.model.dto.LoanRepaymentResponse>() {
            @Override
            public void onResponse(@NonNull Call<com.ensark.ensarkbank.model.dto.LoanRepaymentResponse> call, @NonNull Response<com.ensark.ensarkbank.model.dto.LoanRepaymentResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _repaymentSuccess.postValue(true);
                    fetchLoans(email);
                } else {
                    setError("Repayment failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.ensark.ensarkbank.model.dto.LoanRepaymentResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void applyForLoan(okhttp3.RequestBody data, okhttp3.MultipartBody.Part photo, String email) {
        setLoading(true);
        loanRepository.apply(data, null, photo, new Callback<LoanApplicationResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoanApplicationResponse> call, @NonNull Response<LoanApplicationResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _applicationSuccess.postValue(true);
                    fetchLoans(email);
                } else {
                    setError("Application failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoanApplicationResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
