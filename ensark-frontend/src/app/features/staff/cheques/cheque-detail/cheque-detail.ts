import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ChequeService } from '../../../../core/services/cheque.service';
import { ChequeBookResponse, ChequeLeafResponse, ChequeLeafStatusHistory } from '../../../../core/models/cheque.models';
import { ChequeBookStatus, ChequeLeafStatus } from '../../../../core/models/enums';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideArrowLeft, LucideCheck, LucideCircleCheck, LucideCircleX,
  LucideLock, LucideLockOpen, LucideRefreshCw,
  LucideFileCheck
} from '../../../../shared/icons';

@Component({
  selector: 'app-cheque-detail',
  standalone: true,
  imports: [CommonModule, Badge, LucideArrowLeft, LucideCheck, LucideCircleCheck,
    LucideCircleX, LucideLock, LucideLockOpen, LucideRefreshCw,
    LucideFileCheck],
  templateUrl: './cheque-detail.html',
  styleUrl: './cheque-detail.css'
})
export class ChequeDetailComponent implements OnInit {
  ChequeBookStatus = ChequeBookStatus;
  ChequeLeafStatus = ChequeLeafStatus;

  chequeBook: ChequeBookResponse | null = null;
  isLoading = true;
  actionInProgress = false;

  expandedLeafId: number | null = null;
  leafHistory: Record<number, ChequeLeafStatusHistory[]> = {};
  historyLoading = false;

  private chequeService = inject(ChequeService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadChequeBook(id);
    }
  }

  loadChequeBook(id: number): void {
    this.isLoading = true;
    this.chequeService.getById(id).subscribe({
      next: (data) => {
        this.chequeBook = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading cheque book', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  // ---- Book lifecycle actions ----

  approveBook(): void {
    if (!this.chequeBook) return;
    this.runBookAction(this.chequeService.approve(this.chequeBook.chequeBookId), 'approve');
  }

  rejectBook(): void {
    if (!this.chequeBook) return;
    const reason = prompt('Enter rejection reason:');
    if (reason) this.runBookAction(this.chequeService.reject(this.chequeBook.chequeBookId, reason), 'reject');
  }

  markPrinted(): void {
    if (!this.chequeBook) return;
    this.runBookAction(this.chequeService.markPrinted(this.chequeBook.chequeBookId), 'mark as printed');
  }

  markReadyForDelivery(): void {
    if (!this.chequeBook) return;
    this.runBookAction(this.chequeService.markReadyForDelivery(this.chequeBook.chequeBookId), 'mark as ready for delivery');
  }

  markDelivered(): void {
    if (!this.chequeBook) return;
    this.runBookAction(this.chequeService.markDelivered(this.chequeBook.chequeBookId), 'mark as delivered');
  }

  activateBook(): void {
    if (!this.chequeBook) return;
    this.runBookAction(this.chequeService.activate(this.chequeBook.chequeBookId), 'activate');
  }

  blockBook(): void {
    if (!this.chequeBook) return;
    const reason = prompt('Enter reason for blocking this cheque book:');
    if (reason) this.runBookAction(this.chequeService.block(this.chequeBook.chequeBookId, reason), 'block');
  }

  reissueBook(): void {
    if (!this.chequeBook) return;
    if (!confirm('Reissue a replacement cheque book for this account?')) return;
    this.actionInProgress = true;
    this.chequeService.reissueChequeBook(this.chequeBook.chequeBookId).subscribe({
      next: (newBook) => {
        this.actionInProgress = false;
        alert(`New cheque book ${newBook.bookSerialNumber} issued.`);
        this.router.navigate(['/staff/cheques', newBook.chequeBookId]);
      },
      error: (err) => {
        this.actionInProgress = false;
        console.error('Error reissuing cheque book', err);
        alert(err.error?.message || 'Failed to reissue cheque book');
        this.cdr.markForCheck();
      }
    });
  }

  private runBookAction(obs: import('rxjs').Observable<ChequeBookResponse>, verb: string): void {
    this.actionInProgress = true;
    obs.subscribe({
      next: (updated) => {
        this.chequeBook = updated;
        this.actionInProgress = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.actionInProgress = false;
        console.error(`Error trying to ${verb}`, err);
        alert(err.error?.message || `Failed to ${verb}`);
        this.cdr.markForCheck();
      }
    });
  }

  // ---- Leaf actions ----

  presentLeaf(leaf: ChequeLeafResponse): void {
    const remarks = prompt('Enter remarks (optional):') || undefined;
    this.runLeafAction(this.chequeService.presentLeaf(leaf.leafId, remarks), 'present leaf');
  }

  clearLeaf(leaf: ChequeLeafResponse): void {
    const ref = prompt('Enter transaction reference:');
    if (ref) this.runLeafAction(this.chequeService.clearLeaf(leaf.leafId, ref), 'clear leaf');
  }

  bounceLeaf(leaf: ChequeLeafResponse): void {
    const reason = prompt('Enter bounce reason:');
    if (reason) this.runLeafAction(this.chequeService.bounceLeaf(leaf.leafId, reason), 'bounce leaf');
  }

  stopPayment(leaf: ChequeLeafResponse): void {
    const remarks = prompt('Enter remarks (optional):') || undefined;
    this.runLeafAction(this.chequeService.stopPayment(leaf.leafId, remarks), 'stop payment on leaf');
  }

  stopPaymentOnPresented(leaf: ChequeLeafResponse): void {
    const remarks = prompt('Enter remarks (optional):') || undefined;
    this.runLeafAction(this.chequeService.stopPaymentOnPresented(leaf.leafId, remarks), 'stop payment on presented leaf');
  }

  cancelLeaf(leaf: ChequeLeafResponse): void {
    const remarks = prompt('Enter remarks (optional):') || undefined;
    if (!confirm('Cancel this cheque leaf?')) return;
    this.runLeafAction(this.chequeService.cancelLeaf(leaf.leafId, remarks), 'cancel leaf');
  }

  revertToIssued(leaf: ChequeLeafResponse): void {
    const reason = prompt('Enter reason for reverting this leaf back to issued:');
    if (reason) this.runLeafAction(this.chequeService.revertToIssued(leaf.leafId, reason), 'revert leaf');
  }

  private runLeafAction(obs: import('rxjs').Observable<ChequeLeafResponse>, verb: string): void {
    if (!this.chequeBook) return;
    this.actionInProgress = true;
    obs.subscribe({
      next: (updatedLeaf) => {
        this.applyUpdatedLeaf(updatedLeaf);
        this.actionInProgress = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.actionInProgress = false;
        console.error(`Error trying to ${verb}`, err);
        alert(err.error?.message || `Failed to ${verb}`);
        this.cdr.markForCheck();
      }
    });
  }

  private applyUpdatedLeaf(updated: ChequeLeafResponse): void {
    if (!this.chequeBook) return;
    const idx = this.chequeBook.leaves?.findIndex(l => l.leafId === updated.leafId) ?? -1;
    if (idx >= 0) this.chequeBook.leaves[idx] = updated;
  }

  // ---- Status history ----

  toggleHistory(leaf: ChequeLeafResponse): void {
    if (this.expandedLeafId === leaf.leafId) {
      this.expandedLeafId = null;
      return;
    }
    this.expandedLeafId = leaf.leafId;
    if (!this.leafHistory[leaf.leafId]) {
      this.historyLoading = true;
      this.chequeService.getLeafStatusHistory(leaf.leafId).subscribe({
        next: (history) => {
          this.leafHistory[leaf.leafId] = history;
          this.historyLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error loading leaf status history', err);
          this.leafHistory[leaf.leafId] = [];
          this.historyLoading = false;
          this.cdr.markForCheck();
        }
      });
    }
  }

  // ---- Badge color mapping ----

  bookStatusColor(status: string | undefined): BadgeColor {
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

  leafStatusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case ChequeLeafStatus.CLEARED:
        return 'success';
      case ChequeLeafStatus.PRESENTED:
      case ChequeLeafStatus.ISSUED:
        return 'warning';
      case ChequeLeafStatus.BOUNCED:
      case ChequeLeafStatus.STOP_PAYMENT:
      case ChequeLeafStatus.CANCELLED:
      case ChequeLeafStatus.EXPIRED:
        return 'danger';
      case ChequeLeafStatus.UNUSED:
        return 'neutral';
      default:
        return 'neutral';
    }
  }

  goBack(): void {
    this.router.navigate(['/staff/cheques']);
  }
}
