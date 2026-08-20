import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoanService } from '../../../../core/services/loan.service';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { LoanApplicationResponse, LoanRepaymentResponse, LoanScheduleResponse } from '../../../../core/models/loan.models';
import { AccountResponse } from '../../../../core/models/account.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft, LucideHandshake, LucideFileText, LucideCircleDollarSign, LucideX } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-loan-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, Badge, LucideArrowLeft, LucideHandshake, LucideFileText, LucideCircleDollarSign, LucideX],
  templateUrl: './loan-detail.html',
  styleUrl: './loan-detail.css'
})
export class CustomerLoanDetailComponent implements OnInit {
  loan: LoanApplicationResponse | null = null;
  repayments: LoanRepaymentResponse[] = [];
  schedule: LoanScheduleResponse[] = [];
  isLoading = true;

  accounts: AccountResponse[] = [];
  selectedAccountId: number | null = null;
  showPayModal = false;
  payingRepaymentId: number | null = null;
  isPaying = false;
  payError = '';

  toastMessage: { type: 'success' | 'error'; text: string } | null = null;

  private loanService = inject(LoanService);
  private accountService = inject(AccountService);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadLoan(id);
    }
  }

  loadLoan(id: number): void {
    this.loanService.getById(id).subscribe({
      next: (data) => {
        this.loan = data;
        this.loadRepayments(id);
        this.loadSchedule(id);
        this.loadAccounts();
      },
      error: (err) => {
        console.error('Error loading loan', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  loadRepayments(loanId: number): void {
    this.loanService.getRepayments(loanId).subscribe({
      next: (data) => {
        this.repayments = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  loadSchedule(loanId: number): void {
    this.loanService.getSchedule(loanId).subscribe({
      next: (data) => {
        this.schedule = data;
        this.cdr.markForCheck();
      },
      error: () => this.cdr.markForCheck()
    });
  }

  loadAccounts(): void {
    const user = this.authService.currentUserValue;
    if (!user?.email) return;
    this.accountService.getByCustomerEmail(user.email).subscribe({
      next: (data) => {
        this.accounts = data.filter(a => a.accountStatus === 'ACTIVE');
        this.cdr.markForCheck();
      },
      error: () => this.cdr.markForCheck()
    });
  }

  openPayModal(scheduleItemId: number): void {
    this.payingRepaymentId = scheduleItemId;
    this.selectedAccountId = null;
    this.payError = '';
    this.showPayModal = true;
  }

  closePayModal(): void {
    this.showPayModal = false;
    this.payingRepaymentId = null;
    this.selectedAccountId = null;
    this.payError = '';
  }

  payInstallment(): void {
    if (!this.payingRepaymentId || !this.selectedAccountId) {
      this.payError = 'Please select an account to pay from.';
      return;
    }
    this.isPaying = true;
    this.payError = '';
    this.loanService.payInstallmentByAccount(this.payingRepaymentId, this.selectedAccountId).subscribe({
      next: () => {
        this.isPaying = false;
        this.closePayModal();
        this.showToast('success', 'EMI payment successful!');
        if (this.loan) {
          this.loadLoan(this.loan.loanId);
        }
      },
      error: (err) => {
        this.isPaying = false;
        this.payError = err?.error?.message || 'Payment failed. Please try again.';
        this.cdr.markForCheck();
      }
    });
  }

  showToast(type: 'success' | 'error', text: string): void {
    this.toastMessage = { type, text };
    setTimeout(() => {
      this.toastMessage = null;
      this.cdr.markForCheck();
    }, 4000);
  }

  canPayInstallment(s: LoanScheduleResponse): boolean {
    return (s.status === 'PENDING' || s.status === 'LATE') && this.accounts.length > 0;
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

  getRepaymentStatusColor(status: string): BadgeColor {
    switch (status) {
      case 'PAID': return 'success';
      case 'PENDING': return 'warning';
      case 'LATE': return 'warning';
      case 'MISSED': return 'danger';
      default: return 'neutral';
    }
  }

  goBack(): void {
    this.router.navigate(['/customer/loans']);
  }
}
