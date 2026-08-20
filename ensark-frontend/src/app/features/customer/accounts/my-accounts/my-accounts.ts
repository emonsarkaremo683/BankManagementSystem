import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucidePlus, LucideWallet, LucideCircleCheck, LucideArrowRight, LucideLandmark } from '../../../../shared/icons';

@Component({
  selector: 'app-my-accounts',
  standalone: true,
  imports: [CommonModule, RouterLink, StatCard, Badge, LucidePlus, LucideWallet, LucideCircleCheck, LucideArrowRight, LucideLandmark],
  templateUrl: './my-accounts.html',
  styleUrl: './my-accounts.css'
})
export class MyAccountsComponent implements OnInit {
  accounts: AccountResponse[] = [];
  isLoading = true;

  private accountService = inject(AccountService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.loadAccounts(user.email);
    }
  }

  loadAccounts(email: string): void {
    this.isLoading = true;
    this.accountService.getByCustomerEmail(email).subscribe({
      next: (data) => {
        this.accounts = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading accounts', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  getTotalBalance(): number {
    return this.accounts.reduce((sum, a) => sum + (a.availableBalance || 0), 0);
  }

  get activeCount(): number {
    return this.accounts.filter(a => a.accountStatus === 'ACTIVE').length;
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
