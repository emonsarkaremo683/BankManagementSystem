import { Component, OnInit, OnDestroy, Output, EventEmitter, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationService } from '../../../core/services/notification.service';
import { WebSocketService } from '../../../core/services/websocket.service';
import { NotificationResponse } from '../../../core/models/notification.models';
import { NotificationType } from '../../../core/models/enums';

const SUCCESS_TYPES = new Set<NotificationType>([
  NotificationType.TRANSACTION_SUCCESS,
  NotificationType.ACCOUNT_CREATED,
  NotificationType.LOAN_APPROVED,
  NotificationType.LOAN_REPAYMENT,
  NotificationType.KYC_VERIFIED,
  NotificationType.INTEREST_CREDITED,
  NotificationType.CHEQUE_BOOK_APPROVED,
  NotificationType.CHEQUE_BOOK_DELIVERED,
  NotificationType.CHEQUE_BOOK_ACTIVATED,
  NotificationType.CUSTOMER_REGISTERED,
]);

const DANGER_TYPES = new Set<NotificationType>([
  NotificationType.TRANSACTION_FAILED,
  NotificationType.ACCOUNT_SUSPENDED,
  NotificationType.LOAN_REJECTED,
  NotificationType.KYC_REJECTED,
  NotificationType.CHEQUE_BOOK_REJECTED,
  NotificationType.CHEQUE_BOOK_BLOCKED,
]);

@Component({
  selector: 'app-notification-dropdown',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './notification-dropdown.html',
  styleUrl: './notification-dropdown.css'
})
export class NotificationDropdownComponent implements OnInit, OnDestroy {
  private notificationService = inject(NotificationService);
  private wsService = inject(WebSocketService);
  private cdr = inject(ChangeDetectorRef);
  @Output() close = new EventEmitter<void>();

  notifications: NotificationResponse[] = [];
  unreadCount = 0;
  private notifSub!: Subscription;

  ngOnInit() {
    this.loadData();
    this.notifSub = this.wsService.notifications$.subscribe((notification) => {
      this.notifications = [{ ...notification, read: false }, ...this.notifications];
      this.unreadCount++;
      this.cdr.detectChanges();
    });
  }

  ngOnDestroy() {
    this.notifSub?.unsubscribe();
  }

  loadData() {
    this.notificationService.getNotifications().subscribe({
      next: (items) => {
        this.notifications = (items || []).map(n => ({
          ...n,
          read: n.read
        }));
        this.cdr.detectChanges();
      },
      error: () => { this.notifications = []; this.cdr.detectChanges(); }
    });
    this.notificationService.getUnreadCount().subscribe({
      next: (res) => { this.unreadCount = res.unreadCount; this.cdr.detectChanges(); },
      error: () => { this.unreadCount = 0; this.cdr.detectChanges(); }
    });
  }

  onItemClick(item: NotificationResponse) {
    if (!item.read) {
      this.notificationService.markAsRead(item.id).subscribe({
        next: () => {
          item.read = true;
          this.unreadCount = Math.max(0, this.unreadCount - 1);
          this.cdr.detectChanges();
        }
      });
    }
    this.close.emit();
  }

  /**
   * NotificationType is the backend's real domain enum (TRANSACTION_SUCCESS,
   * KYC_VERIFIED, GENERAL, ...), not a generic INFO/WARNING/ALERT/SUCCESS
   * union — this buckets it into the four badge colors for display.
   */
  notificationColor(type: NotificationType): 'success' | 'danger' | 'info' {
    if (SUCCESS_TYPES.has(type)) return 'success';
    if (DANGER_TYPES.has(type)) return 'danger';
    return 'info';
  }

  markAllRead() {
    this.notificationService.markAllAsRead().subscribe({
      next: () => {
        this.notifications.forEach(n => { n.read = true; });
        this.unreadCount = 0;
        this.cdr.detectChanges();
      }
    });
  }
}
