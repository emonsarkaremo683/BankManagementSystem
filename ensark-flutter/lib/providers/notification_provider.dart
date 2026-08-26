import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../repositories/notification_repository.dart';

part 'notification_provider.g.dart';

@riverpod
class Notifications extends _$Notifications {
  @override
  FutureOr<List<NotificationResponse>> build() async {
    return ref.watch(notificationRepositoryProvider).getNotifications();
  }

  Future<void> refresh() async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() => ref.read(notificationRepositoryProvider).getNotifications());
  }

  Future<void> markAsRead(int id) async {
    await ref.read(notificationRepositoryProvider).markAsRead(id);
    // Optimistically update local state or just refresh
    ref.invalidateSelf();
    ref.invalidate(unreadCountProvider);
  }

  Future<void> markAllAsRead() async {
    await ref.read(notificationRepositoryProvider).markAllAsRead();
    ref.invalidateSelf();
    ref.invalidate(unreadCountProvider);
  }
}

@riverpod
Future<int> unreadCount(Ref ref) async {
  // Use a timer or listen to a stream for real-time in a real app
  // For now, simple fetch
  return ref.watch(notificationRepositoryProvider).getUnreadCount();
}
