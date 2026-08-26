import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { StandingOrderService } from '../../../../core/services/standing-order.service';
import { StandingOrderResponse } from '../../../../core/models/standing-order.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import {
  LucideRepeat,
  LucideSearch,
  LucidePlus,
  LucideCircleCheck,
  LucideClock,
  LucideBan,
} from '../../../../shared/icons';

@Component({
  selector: 'app-standing-order-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    Badge,
    StatCard,
    LucideRepeat,
    LucideSearch,
    LucidePlus,
    LucideCircleCheck,
    LucideClock,
    LucideBan,
  ],
  templateUrl: './standing-order-list.html',
  styleUrl: './standing-order-list.css'
})
export class StandingOrderListComponent implements OnInit {
  orders: StandingOrderResponse[] = [];
  filteredOrders: StandingOrderResponse[] = [];
  isLoading = true;
  searchTerm = '';

  private standingOrderService = inject(StandingOrderService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.isLoading = true;
    this.standingOrderService.getActive().subscribe({
      next: (data) => {
        this.orders = data;
        this.filteredOrders = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching standing orders', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredOrders = this.orders;
      return;
    }
    this.filteredOrders = this.orders.filter(o =>
      o.sourceAccountNumber?.toLowerCase().includes(term) ||
      o.targetAccountNumber?.toLowerCase().includes(term) ||
      o.description?.toLowerCase().includes(term)
    );
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
