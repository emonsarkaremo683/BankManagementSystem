import { Component, OnInit, OnDestroy, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription, interval } from 'rxjs';
import { NotificationService } from '../../../core/services/notification.service';
import { WebSocketService } from '../../../core/services/websocket.service';
import { NotificationDropdownComponent } from '../notification-dropdown/notification-dropdown';
import { LucideBell } from '../../icons';

@Component({
  selector: 'app-notification-bell',
  standalone: true,
  imports: [CommonModule, NotificationDropdownComponent, LucideBell],
  templateUrl: './notification-bell.html',
  styleUrl: './notification-bell.css'
})
export class NotificationBellComponent implements OnInit, OnDestroy {
  private notificationService = inject(NotificationService);
  private wsService = inject(WebSocketService);
  private cdr = inject(ChangeDetectorRef);

  isOpen = false;
  unreadCount = 0;
  private notifSub!: Subscription;
  private pollSub!: Subscription;

  ngOnInit() {
    this.fetchCount();
    this.wsService.connect();
    this.notifSub = this.wsService.notifications$.subscribe(() => {
      this.fetchCount();
      this.cdr.detectChanges();
    });
    this.pollSub = interval(30000).subscribe(() => this.fetchCount());
  }

  ngOnDestroy() {
    this.notifSub?.unsubscribe();
    this.pollSub?.unsubscribe();
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
    if (!this.isOpen) {
      this.fetchCount();
    }
  }

  onCloseDropdown() {
    this.isOpen = false;
    this.fetchCount();
  }

  fetchCount() {
    this.notificationService.getUnreadCount().subscribe({
      next: (res) => { this.unreadCount = res.unreadCount; this.cdr.detectChanges(); },
      error: () => { this.unreadCount = 0; this.cdr.detectChanges(); }
    });
  }
}
