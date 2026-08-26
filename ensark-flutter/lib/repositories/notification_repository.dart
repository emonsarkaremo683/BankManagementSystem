import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../providers/core_providers.dart';

part 'notification_repository.g.dart';

class NotificationRepository {
  final Dio _dio;

  NotificationRepository(this._dio);

  Future<List<NotificationResponse>> getNotifications() async {
    final response = await _dio.get('api/notifications');
    return (response.data as List).map((e) => NotificationResponse.fromJson(e)).toList();
  }

  Future<int> getUnreadCount() async {
    final response = await _dio.get('api/notifications/unread-count');
    return (response.data['count'] as num).toInt();
  }

  Future<void> markAsRead(int id) async {
    await _dio.put('api/notifications/$id/read');
  }

  Future<void> markAllAsRead() async {
    await _dio.put('api/notifications/read-all');
  }
}

@riverpod
NotificationRepository notificationRepository(Ref ref) {
  return NotificationRepository(ref.watch(dioProvider));
}
