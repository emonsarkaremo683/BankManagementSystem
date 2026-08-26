package com.ensark.ensarkbank.ui.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.AccountRequest;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.BranchResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.repository.GeneralRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountViewModel extends BaseViewModel {

    private final AccountRepository accountRepository;
    private final GeneralRepository generalRepository;

    private final MutableLiveData<List<BranchResponse>> _branches = new MutableLiveData<>();
    public final LiveData<List<BranchResponse>> branches = _branches;

    private final MutableLiveData<AccountResponse> _accountCreated = new MutableLiveData<>();
    public final LiveData<AccountResponse> accountCreated = _accountCreated;

    public CreateAccountViewModel(@NonNull Application application) {
        super(application);
        this.accountRepository = new AccountRepository(application);
        this.generalRepository = new GeneralRepository(application);
    }

    public void fetchBranches() {
        setLoading(true);
        generalRepository.getAllBranches(new Callback<List<BranchResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<BranchResponse>> call, @NonNull Response<List<BranchResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _branches.postValue(response.body());
                } else {
                    setError("Failed to fetch branches");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BranchResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void createAccount(AccountRequest request, List<MultipartBody.Part> signatures, MultipartBody.Part photo, 
                             MultipartBody.Part nidFront, MultipartBody.Part nidBack) {
        setLoading(true);
        String json = com.ensark.ensarkbank.api.ApiClient.getGson().toJson(request);
        RequestBody data = RequestBody.create(MediaType.parse("application/json"), json);

        accountRepository.create(data, signatures, photo, nidFront, nidBack, new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _accountCreated.postValue(response.body());
                } else {
                    setError("Failed to create account: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}