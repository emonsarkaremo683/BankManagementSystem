package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.NotificationApiService;
import com.ensark.ensarkbank.model.dto.NotificationResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Callback;

public class NotificationRepository {

    private final NotificationApiService apiService;

    public NotificationRepository(Context context) {
        apiService = ApiClient.getClient(context).create(NotificationApiService.class);
    }

    public void getNotifications(Callback<List<NotificationResponse>> callback) {
        apiService.getNotifications().enqueue(callback);
    }

    public void getUnreadCount(Callback<Map<String, Long>> callback) {
        apiService.getUnreadCount().enqueue(callback);
    }

    public void markAsRead(Long id, Callback<Void> callback) {
        apiService.markAsRead(id).enqueue(callback);
    }

    public void markAllAsRead(Callback<Void> callback) {
        apiService.markAllAsRead().enqueue(callback);
    }
}
