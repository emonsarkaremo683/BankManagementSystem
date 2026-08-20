import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { StandingOrderService } from '../../../../core/services/standing-order.service';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { StandingOrderResponse } from '../../../../core/models/standing-order.models';
import { AccountResponse } from '../../../../core/models/account.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import {
  LucideRepeat,
  LucidePlus,
  LucideCircleCheck,
  LucideClock,
  LucideBan,
} from '../../../../shared/icons';

@Component({
  selector: 'app-my-standing-orders',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    Badge,
    StatCard,
    LucideRepeat,
    LucidePlus,
    LucideCircleCheck,
    LucideClock,
    LucideBan,
  ],
  templateUrl: './my-standing-orders.html',
  styleUrl: './my-standing-orders.css'
})
export class MyStandingOrdersComponent implements OnInit {
  orders: StandingOrderResponse[] = [];
  isLoading = true;
  accounts: AccountResponse[] = [];

  private standingOrderService = inject(StandingOrderService);
  private accountService = inject(AccountService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.isLoading = true;
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.accountService.getByCustomerEmail(user.email).subscribe({
        next: (accounts) => {
          this.accounts = accounts;
          if (accounts.length > 0) {
            const allOrders: StandingOrderResponse[] = [];
            let completed = 0;
            accounts.forEach(acc => {
              this.standingOrderService.getByAccountId(acc.id).subscribe({
                next: (orders) => {
                  allOrders.push(...orders);
                  completed++;
                  if (completed === accounts.length) {
                    this.orders = allOrders;
                    this.isLoading = false;
                    this.cdr.markForCheck();
                  }
                },
                error: () => {
                  completed++;
                  if (completed === accounts.length) {
                    this.orders = allOrders;
                    this.isLoading = false;
                    this.cdr.markForCheck();
                  }
                }
              });
            });
          } else {
            this.isLoading = false;
            this.cdr.markForCheck();
          }
        },
        error: () => {
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      });
    }
  }

  getBadgeColor(status: string): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'PAUSED': return 'warning';
      case 'COMPLETED': return 'neutral';
      case 'CANCELLED': case 'FAILED': return 'danger';
      default: return 'neutral';
    }
  }

  get totalCount(): number {
    return this.orders.length;
  }

  get activeCount(): number {
    return this.orders.filter(o => o.status === 'ACTIVE').length;
  }

  get pausedCount(): number {
    return this.orders.filter(o => o.status === 'PAUSED').length;
  }

  get cancelledCount(): number {
    return this.orders.filter(o => o.status === 'CANCELLED' || o.status === 'FAILED').length;
  }

  cancel(id: number): void {
    if (confirm('Are you sure you want to cancel this standing order?')) {
      this.standingOrderService.cancel(id).subscribe({
        next: () => this.loadOrders(),
        error: (err) => {
          console.error('Error cancelling', err);
          alert('Failed to cancel');
        }
      });
    }
  }

  pause(id: number): void {
    this.standingOrderService.pause(id).subscribe({
      next: () => this.loadOrders(),
      error: (err) => {
        console.error('Error pausing', err);
        alert('Failed to pause');
      }
    });
  }

  resume(id: number): void {
    this.standingOrderService.resume(id).subscribe({
      next: () => this.loadOrders(),
      error: (err) => {
        console.error('Error resuming', err);
        alert('Failed to resume');
      }
    });
  }
}
