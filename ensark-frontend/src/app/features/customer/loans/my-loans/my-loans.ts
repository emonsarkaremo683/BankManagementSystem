import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { LoanService } from '../../../../core/services/loan.service';
import { AuthService } from '../../../../core/services/auth.service';
import { LoanApplicationResponse } from '../../../../core/models/loan.models';
import { LoanStatus } from '../../../../core/models/enums';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideHandCoins, LucideCircleCheck, LucideClock, LucideBanknote, LucidePlus, LucideScrollText } from '../../../../shared/icons';

@Component({
  selector: 'app-my-loans',
  standalone: true,
  imports: [CommonModule, RouterLink, StatCard, Badge, LucideHandCoins, LucideCircleCheck, LucideClock, LucideBanknote, LucidePlus, LucideScrollText],
  templateUrl: './my-loans.html',
  styleUrl: './my-loans.css'
})
export class MyLoansComponent implements OnInit {
  loans: LoanApplicationResponse[] = [];
  isLoading = true;

  private loanService = inject(LoanService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadLoans();
  }

  loadLoans(): void {
    this.isLoading = true;
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.loanService.getByCustomerEmail(user.email).subscribe({
        next: (data) => {
          this.loans = data;
          this.isLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error fetching loans', err);
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      });
    } else {
      this.isLoading = false;
    }
  }

  get totalLoans(): number {
    return this.loans.length;
  }

  get activeCount(): number {
    return this.loans.filter(l => l.status === LoanStatus.ACTIVE || l.status === LoanStatus.DISBURSED).length;
  }

  get pendingCount(): number {
    return this.loans.filter(l => l.status === LoanStatus.PENDING).length;
  }

  get totalOutstanding(): number {
    return this.loans.reduce((sum, l) => sum + (l.outstandingBalance || 0), 0);
  }

  getStatusColor(status: string): BadgeColor {
    switch (status) {
      case 'ACTIVE': case 'APPROVED': case 'DISBURSED': return 'success';
      case 'PENDING': return 'warning';
      case 'REJECTED': case 'DEFAULTED': case 'OVERDUE': return 'danger';
      case 'CLOSED': return 'neutral';
      default: return 'neutral';
    }
  }
}
