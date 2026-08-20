import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CardService } from '../../../../core/services/card.service';
import { CardResponse, CardUsageResponse } from '../../../../core/models/card.models';
import { CardStatus } from '../../../../core/models/enums';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideArrowLeft,
  LucideLockOpen,
  LucideBan,
  LucideShieldAlert,
  LucideRefreshCw,
} from '../../../../shared/icons';

@Component({
  selector: 'app-card-detail',
  standalone: true,
  imports: [CommonModule, Badge, LucideArrowLeft, LucideLockOpen, LucideBan, LucideShieldAlert, LucideRefreshCw],
  templateUrl: './card-detail.html',
  styleUrl: './card-detail.css'
})
export class CardDetailComponent implements OnInit {
  CardStatus = CardStatus;
  card: CardResponse | null = null;
  isLoading = true;

  usage: CardUsageResponse | null = null;
  isUsageLoading = true;

  isActionLoading = false;

  private cardService = inject(CardService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadCard(id);
      this.loadUsage(id);
    }
  }

  loadCard(id: number): void {
    this.isLoading = true;
    this.cardService.getById(id).subscribe({
      next: (data) => {
        this.card = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading card', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  loadUsage(id: number): void {
    this.isUsageLoading = true;
    this.cardService.getUsage(id).subscribe({
      next: (data) => {
        this.usage = data;
        this.isUsageLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading card usage', err);
        this.isUsageLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  updateStatus(status: CardStatus): void {
    if (!this.card) return;
    this.isActionLoading = true;
    this.cardService.updateStatus(this.card.cardId, status).subscribe({
      next: (updated) => {
        this.card = updated;
        this.isActionLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isActionLoading = false;
        console.error('Error updating card status', err);
        alert('Failed to update card status');
        this.cdr.markForCheck();
      }
    });
  }

  renewCard(): void {
    if (!this.card) return;
    if (!confirm('Renew this card? A new expiry date will be issued.')) return;
    this.isActionLoading = true;
    this.cardService.renewCard(this.card.cardId).subscribe({
      next: (updated) => {
        this.card = updated;
        this.isActionLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isActionLoading = false;
        console.error('Error renewing card', err);
        alert('Failed to renew card');
        this.cdr.markForCheck();
      }
    });
  }

  reportLostOrStolen(): void {
    if (!this.card) return;
    const reason = prompt('Reason for reporting this card lost/stolen:');
    if (!reason) return;
    this.isActionLoading = true;
    this.cardService.reportLostOrStolen(this.card.cardId, reason).subscribe({
      next: (updated) => {
        this.card = updated;
        this.isActionLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isActionLoading = false;
        console.error('Error reporting card lost/stolen', err);
        alert('Failed to report card lost/stolen');
        this.cdr.markForCheck();
      }
    });
  }

  statusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'PENDING': return 'warning';
      case 'BLOCKED': case 'DISABLED': case 'EXPIRED': return 'danger';
      case 'CLOSED': return 'neutral';
      default: return 'neutral';
    }
  }

  usagePercent(current: number, limit: number): number {
    if (!limit) return 0;
    return Math.min(100, Math.round((current / limit) * 100));
  }

  usageBarColor(percent: number): string {
    if (percent >= 90) return 'var(--color-danger-500)';
    if (percent >= 70) return 'var(--color-warning-500)';
    return 'var(--color-success-500)';
  }

  goBack(): void {
    this.router.navigate(['/staff/cards']);
  }
}
