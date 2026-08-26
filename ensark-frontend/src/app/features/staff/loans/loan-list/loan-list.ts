import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { LoanService } from '../../../../core/services/loan.service';
import { LoanApplicationResponse } from '../../../../core/models/loan.models';
import { LoanStatus } from '../../../../core/models/enums';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideHandCoins, LucideCircleCheck, LucideClock, LucideBanknote, LucideSearch } from '../../../../shared/icons';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucideHandCoins, LucideCircleCheck, LucideClock, LucideBanknote, LucideSearch],
  templateUrl: './loan-list.html',
  styleUrl: './loan-list.css'
})
export class LoanListComponent implements OnInit {
  loans: LoanApplicationResponse[] = [];
  filteredLoans: LoanApplicationResponse[] = [];
  isLoading = true;
  searchTerm = '';

  private loanService = inject(LoanService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadLoans();
  }

  loadLoans(): void {
    this.isLoading = true;
    this.loanService.getAll().subscribe({
      next: (data) => {
        this.loans = data;
        this.filteredLoans = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching loans', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredLoans = this.loans;
      return;
    }
    this.filteredLoans = this.loans.filter(l =>
      l.accountNumber?.toLowerCase().includes(term) ||
      l.loanId?.toString().includes(term)
    );
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
