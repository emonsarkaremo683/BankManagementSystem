import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AccountService } from '../../../../core/services/account.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { AccountStatus } from '../../../../core/models/enums';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucidePlus, LucideSearch, LucideWallet, LucideCircleCheck, LucideClock } from '../../../../shared/icons';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucidePlus, LucideSearch, LucideWallet, LucideCircleCheck, LucideClock],
  templateUrl: './account-list.html',
  styleUrl: './account-list.css'
})
export class AccountListComponent implements OnInit {
  AccountStatus = AccountStatus;
  accounts: AccountResponse[] = [];
  filteredAccounts: AccountResponse[] = [];
  isLoading = true;
  searchTerm = '';
  updatingId: number | null = null;

  private accountService = inject(AccountService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.isLoading = true;
    this.accountService.getAll().subscribe({
      next: (data) => {
        this.accounts = data;
        this.filteredAccounts = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching accounts', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredAccounts = this.accounts;
      return;
    }
    this.filteredAccounts = this.accounts.filter(a =>
      a.accountNumber.toLowerCase().includes(term) ||
      a.branchName?.toLowerCase().includes(term) ||
      a.n_name?.toLowerCase().includes(term)
    );
  }

  updateStatus(account: AccountResponse, status: AccountStatus): void {
    const action = status === 'ACTIVE' ? 'approve' : 'reject';
    if (!confirm(`Are you sure you want to ${action} account ${account.accountNumber}?`)) return;

    this.updatingId = account.id;
    this.accountService.updateStatus(account.id, status).subscribe({
      next: (updated) => {
        const idx = this.accounts.findIndex(a => a.id === updated.id);
        if (idx >= 0) this.accounts[idx] = updated;
        this.onSearch();
        this.updatingId = null;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.updatingId = null;
        console.error('Failed to update status', err);
        alert(err.error?.message || 'Failed to update account status');
        this.cdr.markForCheck();
      }
    });
  }

  get totalBalance(): number {
    return this.accounts.reduce((sum, a) => sum + (a.availableBalance || 0), 0);
  }

  get activeCount(): number {
    return this.accounts.filter(a => a.accountStatus === 'ACTIVE').length;
  }

  get pendingCount(): number {
    return this.accounts.filter(a => a.accountStatus === 'PENDING').length;
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
}
