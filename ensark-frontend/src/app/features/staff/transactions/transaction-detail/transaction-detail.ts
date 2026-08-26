import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TransactionService } from '../../../../core/services/transaction.service';
import { AccountTransactionResponse } from '../../../../core/models/transaction.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft, LucideRefreshCw } from '../../../../shared/icons';

@Component({
  selector: 'app-transaction-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, Badge, LucideArrowLeft, LucideRefreshCw],
  templateUrl: './transaction-detail.html',
  styleUrl: './transaction-detail.css'
})
export class TransactionDetailComponent implements OnInit {
  transaction: AccountTransactionResponse | null = null;
  isLoading = true;
  isReversing = false;

  private route = inject(ActivatedRoute);
  private transactionService = inject(TransactionService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const referenceNo = this.route.snapshot.paramMap.get('referenceNo');
    if (referenceNo) {
      this.loadTransaction(referenceNo);
    }
  }

  loadTransaction(referenceNo: string): void {
    this.isLoading = true;
    this.transactionService.getByReferenceNo(referenceNo).subscribe({
      next: (data) => {
        this.transaction = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading transaction', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  statusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'SUCCESS': return 'success';
      case 'PENDING': return 'warning';
      case 'FAILED': case 'CANCELLED': return 'danger';
      case 'REVERSED': return 'purple';
      default: return 'neutral';
    }
  }

  reverseTransaction(): void {
    if (!this.transaction || this.isReversing) return;
    const refNo = this.transaction.response?.referenceNo;
    if (!refNo) return;
    if (confirm('Are you sure you want to reverse this transaction?')) {
      this.isReversing = true;
      this.transactionService.reverseByReferenceNo(refNo).subscribe({
        next: (data) => {
          this.transaction = data;
          this.isReversing = false;
          alert('Transaction reversed successfully');
          this.cdr.markForCheck();
        },
        error: (err) => {
          this.isReversing = false;
          console.error('Reverse failed', err);
          alert(err.error?.message || 'Failed to reverse transaction');
          this.cdr.markForCheck();
        }
      });
    }
  }
}
