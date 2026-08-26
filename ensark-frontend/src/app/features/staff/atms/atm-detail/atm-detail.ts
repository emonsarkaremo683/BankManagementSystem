import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AtmService } from '../../../../core/services/atm.service';
import { ATMResponse, ATMTransactionResponse } from '../../../../core/models/atm.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideArrowLeft,
  LucideRefreshCw,
  LucideLandmark,
  LucideMapPin,
  LucideTriangleAlert
} from '../../../../shared/icons';

@Component({
  selector: 'app-atm-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, Badge, LucideArrowLeft, LucideRefreshCw, LucideLandmark, LucideMapPin, LucideTriangleAlert],
  templateUrl: './atm-detail.html',
  styleUrl: './atm-detail.css'
})
export class AtmDetailComponent implements OnInit {
  private atmService = inject(AtmService);
  private route = inject(ActivatedRoute);

  atm?: ATMResponse;
  transactions: ATMTransactionResponse[] = [];
  isLoading = true;
  notFound = false;
  transactionsError = false;

  ngOnInit() {
    const id = +this.route.snapshot.params['id'];
    this.isLoading = true;

    this.atmService.getById(id).subscribe({
      next: (data) => {
        this.atm = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.notFound = true;
      }
    });

    // NOTE (backend bug): ATMTransactionServiceImpl.refill() never sets a Card on the
    // ATMTransaction it creates, but ATMTransactionMapper.toResponse() unconditionally
    // calls transaction.getCard().getCardNumber(). Any ATM that has ever been refilled
    // will 500 (NPE) when its transaction list is fetched, since getTransactions maps
    // the WHOLE list and one bad REFILL row kills the entire response. Handle it
    // gracefully client-side rather than crashing/leaving stale UI.
    this.atmService.getTransactions(id).subscribe({
      next: (txns) => {
        this.transactions = txns;
        this.transactionsError = false;
      },
      error: () => {
        this.transactions = [];
        this.transactionsError = true;
      }
    });
  }

  setStatus(newStatus: string) {
    if (!this.atm) return;
    this.atmService.updateStatus(this.atm.atmId, newStatus as any).subscribe(updatedAtm => {
      if (this.atm && updatedAtm) {
        this.atm.status = updatedAtm.status;
      }
    });
  }

  cashPercent(): number {
    if (!this.atm || !this.atm.limit) return 0;
    return Math.round((this.atm.availableBalance / this.atm.limit) * 100);
  }

  atmStatusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'MAINTENANCE': return 'warning';
      case 'OFFLINE': case 'OUT_OF_SERVICE': return 'danger';
      default: return 'neutral';
    }
  }

  txnStatusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'SUCCESS': return 'success';
      case 'FAILED': return 'danger';
      case 'PENDING': return 'warning';
      case 'CANCELLED': return 'neutral';
      case 'REVERSED': return 'info';
      default: return 'neutral';
    }
  }
}
