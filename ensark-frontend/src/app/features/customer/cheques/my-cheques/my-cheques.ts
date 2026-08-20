import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ChequeService } from '../../../../core/services/cheque.service';
import { AuthService } from '../../../../core/services/auth.service';
import { ChequeBookResponse } from '../../../../core/models/cheque.models';
import { ChequeBookStatus } from '../../../../core/models/enums';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucidePlus, LucideScrollText, LucideCircleCheck, LucideClock } from '../../../../shared/icons';

@Component({
  selector: 'app-my-cheques',
  standalone: true,
  imports: [CommonModule, RouterLink, StatCard, Badge, LucidePlus, LucideScrollText, LucideCircleCheck, LucideClock],
  templateUrl: './my-cheques.html',
  styleUrl: './my-cheques.css'
})
export class MyChequesComponent implements OnInit {
  chequeBooks: ChequeBookResponse[] = [];
  isLoading = true;

  private chequeService = inject(ChequeService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadChequeBooks();
  }

  loadChequeBooks(): void {
    this.isLoading = true;
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.chequeService.getByCustomerEmail(user.email).subscribe({
        next: (data) => {
          this.chequeBooks = data;
          this.isLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error fetching cheque books', err);
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      });
    } else {
      this.isLoading = false;
    }
  }

  statusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case ChequeBookStatus.ACTIVE:
      case ChequeBookStatus.APPROVED:
      case ChequeBookStatus.DELIVERED:
        return 'success';
      case ChequeBookStatus.REQUESTED:
      case ChequeBookStatus.PRINTED:
      case ChequeBookStatus.READY_FOR_DELIVERY:
        return 'warning';
      case ChequeBookStatus.REJECTED:
      case ChequeBookStatus.BLOCKED:
      case ChequeBookStatus.EXPIRED:
      case ChequeBookStatus.CANCELLED:
        return 'danger';
      case ChequeBookStatus.EXHAUSTED:
        return 'neutral';
      default:
        return 'neutral';
    }
  }

  get activeCount(): number {
    return this.chequeBooks.filter(cb => cb.status === ChequeBookStatus.ACTIVE).length;
  }

  get pendingCount(): number {
    return this.chequeBooks.filter(cb => cb.status === ChequeBookStatus.REQUESTED).length;
  }

  get blockedCount(): number {
    return this.chequeBooks.filter(cb => cb.status === ChequeBookStatus.BLOCKED).length;
  }
}
