import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ChequeService } from '../../../../core/services/cheque.service';
import { ChequeBookResponse } from '../../../../core/models/cheque.models';
import { ChequeBookStatus } from '../../../../core/models/enums';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideSearch, LucideScrollText, LucideCircleCheck, LucideClock, LucideBan } from '../../../../shared/icons';

@Component({
  selector: 'app-cheque-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucideSearch, LucideScrollText, LucideCircleCheck, LucideClock, LucideBan],
  templateUrl: './cheque-list.html',
  styleUrl: './cheque-list.css'
})
export class ChequeListComponent implements OnInit {
  chequeBooks: ChequeBookResponse[] = [];
  filteredChequeBooks: ChequeBookResponse[] = [];
  isLoading = true;
  searchTerm = '';
  updatingId: number | null = null;

  private chequeService = inject(ChequeService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadChequeBooks();
  }

  loadChequeBooks(): void {
    this.isLoading = true;
    this.chequeService.getAll().subscribe({
      next: (data) => {
        this.chequeBooks = data;
        this.filteredChequeBooks = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching cheque books', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredChequeBooks = this.chequeBooks;
      return;
    }
    this.filteredChequeBooks = this.chequeBooks.filter(cb =>
      cb.bookSerialNumber?.toLowerCase().includes(term) ||
      cb.accountNumber?.toLowerCase().includes(term)
    );
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

  approve(id: number): void {
    this.updatingId = id;
    this.chequeService.approve(id).subscribe({
      next: (updated) => this.applyUpdatedBook(updated),
      error: (err) => {
        console.error('Error approving', err);
        alert(err.error?.message || 'Failed to approve');
        this.updatingId = null;
        this.cdr.markForCheck();
      }
    });
  }

  reject(id: number): void {
    const reason = prompt('Enter rejection reason:');
    if (reason) {
      this.updatingId = id;
      this.chequeService.reject(id, reason).subscribe({
        next: (updated) => this.applyUpdatedBook(updated),
        error: (err) => {
          console.error('Error rejecting', err);
          alert(err.error?.message || 'Failed to reject');
          this.updatingId = null;
          this.cdr.markForCheck();
        }
      });
    }
  }

  private applyUpdatedBook(updated: ChequeBookResponse): void {
    const idx = this.chequeBooks.findIndex(cb => cb.chequeBookId === updated.chequeBookId);
    if (idx >= 0) this.chequeBooks[idx] = updated;
    this.onSearch();
    this.updatingId = null;
    this.cdr.markForCheck();
  }

  get activeCount(): number {
    return this.chequeBooks.filter(cb => cb.status === ChequeBookStatus.ACTIVE).length;
  }

  get requestedCount(): number {
    return this.chequeBooks.filter(cb => cb.status === ChequeBookStatus.REQUESTED).length;
  }

  get blockedCount(): number {
    return this.chequeBooks.filter(cb => cb.status === ChequeBookStatus.BLOCKED).length;
  }
}
