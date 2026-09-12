import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import '../models/other/other_models.dart';
import '../repositories/notification_repository.dart';
import 'auth_provider.dart';

part 'notification_provider.g.dart';

@riverpod
class Notifications extends _$Notifications {
  @override
  FutureOr<List<NotificationResponse>> build() async {
    final user = ref.watch(authProvider).value?.user;
    if (user == null) return [];
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
  final user = ref.watch(authProvider).value?.user;
  if (user == null) return 0;
  return ref.watch(notificationRepositoryProvider).getUnreadCount();
}
