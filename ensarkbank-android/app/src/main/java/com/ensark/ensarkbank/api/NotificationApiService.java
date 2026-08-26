package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.NotificationResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotificationApiService {

    @GET("api/notifications")
    Call<List<NotificationResponse>> getNotifications();

    @GET("api/notifications/unread-count")
    Call<Map<String, Long>> getUnreadCount();

    @PUT("api/notifications/{id}/read")
    Call<Void> markAsRead(@Path("id") Long id);

    @PUT("api/notifications/read-all")
    Call<Void> markAllAsRead();
}
