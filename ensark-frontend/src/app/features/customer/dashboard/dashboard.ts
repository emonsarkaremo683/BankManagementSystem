import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DashboardService } from '../../../core/services/dashboard.service';
import { KycService } from '../../../core/services/kyc.service';
import { AuthService } from '../../../core/services/auth.service';
import { CustomerDashboardResponse } from '../../../core/models/dashboard.models';
import { StatCard } from '../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../shared/components/badge/badge';
import {
  LucideWallet,
  LucideTrendingUp,
  LucideTrendingDown,
  LucideCreditCard,
  LucideLandmark,
  LucideArrowLeftRight,
  LucideHandshake,
  LucideCalendarClock,
  LucideClock,
  LucideTriangleAlert,
  LucideCircleDollarSign,
  LucideFileCheck,
} from '../../../shared/icons';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    StatCard,
    Badge,
    LucideWallet,
    LucideTrendingUp,
    LucideTrendingDown,
    LucideCreditCard,
    LucideLandmark,
    LucideArrowLeftRight,
    LucideHandshake,
    LucideCalendarClock,
    LucideClock,
    LucideTriangleAlert,
    LucideCircleDollarSign,
    LucideFileCheck,
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class CustomerDashboardComponent implements OnInit {
  dashboardData: CustomerDashboardResponse | null = null;
  isLoading = true;
  error = '';
  kycStatus: string = '';
  kycLoading = true;
  today = new Date();
  customer = '';

  private dashboardService = inject(DashboardService);

  private kycService = inject(KycService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadDashboard();
    this.loadKycStatus();
  }

  get userName(): string {
    const user = this.authService.currentUserValue;



    console.log(user);
    return user?.name || user?.email || 'there';
  }

  loadDashboard(): void {
    this.isLoading = true;
    this.cdr.markForCheck();
    this.dashboardService.getCustomerDashboard().subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading dashboard', err);
        this.error = 'Failed to load dashboard data';
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  loadKycStatus(): void {
    this.kycService.getMyKycStatus().subscribe({
      next: (data) => {
        this.kycStatus = data?.status || 'NOT_SUBMITTED';
        this.kycLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.kycStatus = 'NOT_SUBMITTED';
        this.kycLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  get kycStatusColor(): BadgeColor {
    switch (this.kycStatus) {
      case 'VERIFIED': return 'success';
      case 'PENDING':
      case 'UNDER_REVIEW': return 'info';
      case 'REJECTED': return 'danger';
      default: return 'warning';
    }
  }

  accountStatusColor(status: string): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'PENDING': return 'warning';
      case 'BLOCKED':
      case 'CLOSED': return 'danger';
      default: return 'neutral';
    }
  }

  cardTypeColor(type: string): BadgeColor {
    switch (type) {
      case 'CREDIT': return 'purple';
      case 'DEBIT': return 'info';
      case 'PREPAID': return 'warning';
      default: return 'neutral';
    }
  }

  cardStatusColor(status: string): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'BLOCKED': return 'danger';
      case 'EXPIRED': return 'neutral';
      default: return 'neutral';
    }
  }

  entryTypeColor(entryType: string): BadgeColor {
    return entryType === 'CREDIT' ? 'success' : 'danger';
  }

  txnStatusColor(status: string): BadgeColor {
    switch (status) {
      case 'SUCCESS': return 'success';
      case 'PENDING': return 'warning';
      case 'FAILED': return 'danger';
      default: return 'neutral';
    }
  }
}
