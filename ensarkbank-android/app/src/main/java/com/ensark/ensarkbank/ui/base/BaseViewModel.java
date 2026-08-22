package com.ensark.ensarkbank.ui.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    protected final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public final LiveData<Boolean> isLoading = _isLoading;

    protected final MutableLiveData<String> _errorMessage = new MutableLiveData<>(null);
    public final LiveData<String> errorMessage = _errorMessage;

    protected void setLoading(boolean loading) {
        _isLoading.postValue(loading);
    }

    protected void setError(String message) {
        _errorMessage.postValue(message);
    }
    
    public void clearError() {
        _errorMessage.postValue(null);
    }
}
