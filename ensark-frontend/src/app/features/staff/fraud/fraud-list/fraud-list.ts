import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { FraudService } from '../../../../core/services/fraud.service';
import { FraudFlagResponse } from '../../../../core/models/fraud.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideShieldAlert,
  LucideShieldCheck,
  LucideSearch,
  LucideFunnel,
  LucideTriangleAlert,
} from '../../../../shared/icons';

@Component({
  selector: 'app-fraud-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    StatCard,
    Badge,
    LucideShieldAlert,
    LucideShieldCheck,
    LucideSearch,
    LucideFunnel,
    LucideTriangleAlert,
  ],
  templateUrl: './fraud-list.html',
  styleUrl: './fraud-list.css'
})
export class FraudListComponent implements OnInit {
  private fraudService = inject(FraudService);
  private cdr = inject(ChangeDetectorRef);
  alerts: FraudFlagResponse[] = [];
  searchQuery = '';
  selectedRisk = 'ALL';
  isLoading = true;
  errorMessage = '';

  ngOnInit() {
    this.loadAlerts();
  }

  loadAlerts() {
    this.isLoading = true;
    this.errorMessage = '';
    this.cdr.detectChanges();
    this.fraudService.getFraudAlerts().subscribe({
      next: (data) => {
        this.alerts = data || [];
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading fraud alerts', err);
        this.alerts = [];
        this.errorMessage = err?.error?.message || 'Failed to load fraud alerts. Please try again.';
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  get filteredAlerts(): FraudFlagResponse[] {
    return this.alerts.filter(a => {
      const reason = (a.reason || '').toLowerCase();
      const details = (a.details || '').toLowerCase();
      const userId = String(a.userId ?? '');
      const accountId = String(a.accountId ?? '');
      const q = this.searchQuery.toLowerCase();

      const matchesSearch = !this.searchQuery ||
        reason.includes(q) ||
        details.includes(q) ||
        userId.includes(q) ||
        accountId.includes(q);

      const matchesRisk = this.selectedRisk === 'ALL' || a.riskLevel === this.selectedRisk;
      return matchesSearch && matchesRisk;
    });
  }

  get totalFlags(): number {
    return this.alerts.length;
  }

  get criticalHighCount(): number {
    return this.alerts.filter(a => a.riskLevel === 'HIGH' || a.riskLevel === 'CRITICAL').length;
  }

  get pendingCount(): number {
    return this.alerts.filter(a => a.status === 'PENDING').length;
  }

  riskColor(risk: string): BadgeColor {
    switch (risk) {
      case 'CRITICAL':
      case 'HIGH':
        return 'danger';
      case 'MEDIUM':
        return 'warning';
      default:
        return 'neutral';
    }
  }

  statusColor(status: string): BadgeColor {
    switch (status) {
      case 'PENDING':
        return 'warning';
      case 'CONFIRMED_FRAUD':
        return 'danger';
      case 'FALSE_POSITIVE':
      case 'RESOLVED':
        return 'success';
      case 'REVIEWED':
        return 'info';
      default:
        return 'neutral';
    }
  }
}
