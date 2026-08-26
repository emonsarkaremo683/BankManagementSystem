package com.ensark.ensarkbank.ui.base;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public abstract class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    protected final MutableLiveData<String> _errorMessage = new MutableLiveData<>(null);
    public final LiveData<String> errorMessage = _errorMessage;

    public void setLoading(boolean loading) {
        _isLoading.postValue(loading);
    }

    protected void setError(String message) {
        _errorMessage.postValue(message);
    }
    
    public void clearError() {
        _errorMessage.postValue(null);
    }
}
