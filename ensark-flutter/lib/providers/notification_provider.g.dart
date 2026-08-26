// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'notification_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$unreadCountHash() => r'ce82f7ebdd5291ae7b7e934ed3803b270f5097b0';

/// See also [unreadCount].
@ProviderFor(unreadCount)
final unreadCountProvider = AutoDisposeFutureProvider<int>.internal(
  unreadCount,
  name: r'unreadCountProvider',
  debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
      ? null
      : _$unreadCountHash,
  dependencies: null,
  allTransitiveDependencies: null,
);

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
typedef UnreadCountRef = AutoDisposeFutureProviderRef<int>;
String _$notificationsHash() => r'419526c424d5cd996a9df44929339bef9b4aaec8';

/// See also [Notifications].
@ProviderFor(Notifications)
final notificationsProvider =
    AutoDisposeAsyncNotifierProvider<
      Notifications,
      List<NotificationResponse>
    >.internal(
      Notifications.new,
      name: r'notificationsProvider',
      debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
          ? null
          : _$notificationsHash,
      dependencies: null,
      allTransitiveDependencies: null,
    );

typedef _$Notifications = AutoDisposeAsyncNotifier<List<NotificationResponse>>;
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
