import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AccountService } from '../../../../core/services/account.service';
import { TransactionService } from '../../../../core/services/transaction.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { AccountTransactionResponse } from '../../../../core/models/transaction.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft, LucideArrowDownRight, LucideArrowUpRight } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-account-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, Badge, LucideArrowLeft, LucideArrowDownRight, LucideArrowUpRight],
  templateUrl: './account-detail.html',
  styleUrl: './account-detail.css'
})
export class CustomerAccountDetailComponent implements OnInit {
  account: AccountResponse | null = null;
  transactions: AccountTransactionResponse[] = [];
  isLoading = true;

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
