import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoanService } from '../../../../core/services/loan.service';
import { LoanApplicationResponse, LoanRepaymentResponse, LoanScheduleResponse } from '../../../../core/models/loan.models';
import { LoanStatus } from '../../../../core/models/enums';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft, LucideHandshake, LucideFileText, LucideTriangleAlert } from '../../../../shared/icons';

@Component({
  selector: 'app-loan-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, Badge, LucideArrowLeft, LucideHandshake, LucideFileText, LucideTriangleAlert],
  templateUrl: './loan-detail.html',
  styleUrl: './loan-detail.css'
})
export class LoanDetailComponent implements OnInit {
  loan: LoanApplicationResponse | null = null;
  repayments: LoanRepaymentResponse[] = [];
  schedule: LoanScheduleResponse[] = [];
  isLoading = true;
  showRejectModal = false;
  rejectReason = '';

  showForecloseModal = false;
  sweepFromAccountId: number | null = null;
  isForeclosing = false;
  forecloseError = '';

  private loanService = inject(LoanService);
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

  approveLoan(): void {
    if (!this.loan) return;
    this.loanService.updateStatus(this.loan.loanId, LoanStatus.APPROVED).subscribe({
      next: () => {
        this.loadLoan(this.loan!.loanId);
      },
      error: (err) => {
        console.error('Error approving loan', err);
        alert('Failed to approve loan');
      }
    });
  }

  rejectLoan(): void {
    if (!this.loan) return;
    this.loanService.updateStatus(this.loan.loanId, LoanStatus.REJECTED, this.rejectReason).subscribe({
      next: () => {
        this.showRejectModal = false;
        this.rejectReason = '';
        this.loadLoan(this.loan!.loanId);
      },
      error: (err) => {
        console.error('Error rejecting loan', err);
        alert('Failed to reject loan');
      }
    });
  }

  get canForeclose(): boolean {
    if (!this.loan) return false;
    return this.loan.status === LoanStatus.ACTIVE ||
      this.loan.status === LoanStatus.DISBURSED ||
      this.loan.status === LoanStatus.OVERDUE;
  }

  forecloseLoan(): void {
    if (!this.loan || !this.sweepFromAccountId) {
      this.forecloseError = 'Please enter a valid account ID to sweep the payoff amount from.';
      return;
    }
    this.isForeclosing = true;
    this.forecloseError = '';
    this.loanService.foreclose(this.loan.loanId, this.sweepFromAccountId).subscribe({
      next: () => {
        this.isForeclosing = false;
        this.showForecloseModal = false;
        this.sweepFromAccountId = null;
        this.loadLoan(this.loan!.loanId);
      },
      error: (err) => {
        console.error('Error foreclosing loan', err);
        this.isForeclosing = false;
        this.forecloseError = err?.error?.message || 'Failed to foreclose loan.';
        this.cdr.markForCheck();
      }
    });
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
    this.router.navigate(['/staff/loans']);
  }
}
