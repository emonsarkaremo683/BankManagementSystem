import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_shadows.dart';
import '../../../models/enums.dart';
import '../../../models/other/other_models.dart';
import '../../../providers/notification_provider.dart';

class NotificationListScreen extends ConsumerWidget {
  const NotificationListScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final notificationsAsync = ref.watch(notificationsProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Notifications'),
        backgroundColor: Colors.transparent,
        actions: [
          TextButton(
            onPressed: () => ref.read(notificationsProvider.notifier).markAllAsRead(),
            child: const Text('Mark all read', style: TextStyle(color: AppColors.neonCyan)),
          ),
        ],
      ),
      body: RefreshIndicator(
        onRefresh: () => ref.read(notificationsProvider.notifier).refresh(),
        child: notificationsAsync.when(
          data: (list) => list.isEmpty
              ? const _EmptyNotifications()
              : ListView.builder(
                  padding: const EdgeInsets.all(24),
                  itemCount: list.length,
                  itemBuilder: (context, index) => _NotificationTile(notification: list[index]),
                ),
          loading: () => const Center(child: CircularProgressIndicator()),
          error: (e, s) => Center(child: Text('Error: $e')),
        ),
      ),
    );
  }
}

class _NotificationTile extends ConsumerWidget {
  final NotificationResponse notification;
  const _NotificationTile({required this.notification});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final bool isUnread = notification.read != true;

    return GestureDetector(
      onTap: isUnread && notification.id != null ? () => ref.read(notificationsProvider.notifier).markAsRead(notification.id!) : null,
      child: Container(
        margin: const EdgeInsets.only(bottom: 16),
        padding: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          color: isUnread ? AppColors.darkSurface : AppColors.darkBackground,
          borderRadius: BorderRadius.circular(16),
          boxShadow: isUnread ? AppShadows.embossed : AppShadows.debossed,
          border: isUnread ? Border.all(color: AppColors.neonCyan.withValues(alpha: 0.3)) : null,
        ),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _TypeIcon(type: notification.type),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        notification.title ?? 'Alert',
                        style: TextStyle(
                          fontWeight: isUnread ? FontWeight.bold : FontWeight.normal,
                          color: isUnread ? Colors.white : AppColors.textSecondary,
                        ),
                      ),
                      if (isUnread)
                        Container(
                          width: 8,
                          height: 8,
                          decoration: const BoxDecoration(color: AppColors.neonPink, shape: BoxShape.circle),
                        ),
                    ],
                  ),
                  const SizedBox(height: 4),
                  Text(
                    notification.message ?? '',
                    style: TextStyle(color: isUnread ? Colors.white70 : Colors.white24, fontSize: 13),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    notification.createdAt ?? '',
                    style: const TextStyle(color: Colors.white10, fontSize: 10),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _TypeIcon extends StatelessWidget {
  final NotificationType? type;
  const _TypeIcon({this.type});

  @override
  Widget build(BuildContext context) {
    IconData icon;
    Color color;

    switch (type) {
      case NotificationType.TRANSACTION_SUCCESS:
      case NotificationType.DEPOSIT:
      case NotificationType.WITHDRAW:
      case NotificationType.TRANSFER:
      case NotificationType.INTEREST_CREDITED:
        icon = Icons.swap_horiz;
        color = AppColors.neonGreen;
        break;
      case NotificationType.TRANSACTION_FAILED:
        icon = Icons.error_outline;
        color = AppColors.neonPink;
        break;
      case NotificationType.ACCOUNT_CREATED:
      case NotificationType.ACCOUNT_SUSPENDED:
      case NotificationType.ACCOUNT_STATUS_CHANGED:
      case NotificationType.CUSTOMER_REGISTERED:
        icon = Icons.account_balance;
        color = AppColors.neonCyan;
        break;
      case NotificationType.CARD_APPLICATION:
      case NotificationType.CARD_STATUS_CHANGED:
        icon = Icons.credit_card;
        color = AppColors.neonCyan;
        break;
      case NotificationType.LOAN_APPLICATION:
      case NotificationType.LOAN_APPROVED:
      case NotificationType.LOAN_REJECTED:
        icon = Icons.receipt_long;
        color = AppColors.neonPink;
        break;
      case NotificationType.KYC_SUBMITTED:
      case NotificationType.KYC_VERIFIED:
      case NotificationType.KYC_REJECTED:
        icon = Icons.verified_user_outlined;
        color = AppColors.neonCyan;
        break;
      case NotificationType.CHEQUE_BOOK_REQUEST:
      case NotificationType.CHEQUE_BOOK_APPROVED:
      case NotificationType.CHEQUE_BOOK_REJECTED:
      case NotificationType.CHEQUE_BOOK_DELIVERED:
      case NotificationType.CHEQUE_BOOK_ACTIVATED:
      case NotificationType.CHEQUE_BOOK_BLOCKED:
        icon = Icons.menu_book;
        color = AppColors.neonCyan;
        break;
      default:
        icon = Icons.notifications_none;
        color = AppColors.neonCyan;
    }

    return Container(
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.1),
        shape: BoxShape.circle,
      ),
      child: Icon(icon, color: color, size: 20),
    );
  }
}

class _EmptyNotifications extends StatelessWidget {
  const _EmptyNotifications();
  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(Icons.notifications_off_outlined, size: 64, color: Colors.white10),
          SizedBox(height: 16),
          Text('No notifications found.', style: TextStyle(color: AppColors.textSecondary)),
        ],
      ),
    );
  }
}
