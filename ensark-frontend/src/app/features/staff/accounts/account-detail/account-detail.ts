import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AccountService } from '../../../../core/services/account.service';
import { TransactionService } from '../../../../core/services/transaction.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { AccountTransactionResponse } from '../../../../core/models/transaction.models';
import { AccountStatus } from '../../../../core/models/enums';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft, LucideCheck, LucideX } from '../../../../shared/icons';

@Component({
  selector: 'app-account-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, Badge, LucideArrowLeft, LucideCheck, LucideX],
  templateUrl: './account-detail.html',
  styleUrl: './account-detail.css'
})
export class AccountDetailComponent implements OnInit {
  AccountStatus = AccountStatus;
  account: AccountResponse | null = null;
  transactions: AccountTransactionResponse[] = [];
  isLoading = true;
  isUpdatingStatus = false;

  private route = inject(ActivatedRoute);
  private accountService = inject(AccountService);
  private transactionService = inject(TransactionService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadAccount(id);
    }
  }

  loadAccount(id: number): void {
    this.isLoading = true;
    this.accountService.getById(id).subscribe({
      next: (account) => {
        this.account = account;
        this.loadTransactions(account.accountNumber);
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading account', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  loadTransactions(accountNumber: string): void {
    this.transactionService.getByAccountNumber(accountNumber).subscribe({
      next: (data) => {
        this.transactions = data.slice(0, 10);
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  updateStatus(status: AccountStatus): void {
    if (!this.account || this.isUpdatingStatus) return;
    const action = status === 'ACTIVE' ? 'approve' : 'reject';
    if (!confirm(`Are you sure you want to ${action} this account?`)) return;

    this.isUpdatingStatus = true;
    this.accountService.updateStatus(this.account.id, status).subscribe({
      next: (updated) => {
        this.account = updated;
        this.isUpdatingStatus = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isUpdatingStatus = false;
        console.error('Failed to update status', err);
        alert(err.error?.message || 'Failed to update account status');
        this.cdr.markForCheck();
      }
    });
  }

  statusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'PENDING': return 'warning';
      case 'FAILED': case 'CLOSED': case 'BLOCKED': case 'FREEZE': return 'danger';
      case 'INACTIVE': case 'CANCELLED': return 'neutral';
      default: return 'neutral';
    }
  }

  txnStatusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'SUCCESS': return 'success';
      case 'PENDING': return 'warning';
      case 'FAILED': case 'CANCELLED': return 'danger';
      case 'REVERSED': return 'purple';
      default: return 'neutral';
    }
  }
}
