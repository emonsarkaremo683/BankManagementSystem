package com.ensark.ensarkbank.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.NotificationResponse;
import com.ensark.ensarkbank.repository.NotificationRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends BaseViewModel {

    private final NotificationRepository repository;
    private final MutableLiveData<List<NotificationResponse>> _notifications = new MutableLiveData<>();
    public final LiveData<List<NotificationResponse>> notifications = _notifications;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        this.repository = new NotificationRepository(application);
    }

    public void fetchNotifications() {
        setLoading(true);
        repository.getNotifications(new Callback<List<NotificationResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<NotificationResponse>> call, @NonNull Response<List<NotificationResponse>> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _notifications.postValue(response.body());
                } else {
                    setError("Failed to fetch notifications");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NotificationResponse>> call, @NonNull Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }

    public void markAsRead(Long id) {
        repository.markAsRead(id, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchNotifications();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }
}
