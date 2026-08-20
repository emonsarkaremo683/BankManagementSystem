import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ChequeService } from '../../../../core/services/cheque.service';
import { ChequeBookResponse, ChequeLeafResponse } from '../../../../core/models/cheque.models';
import { ChequeBookStatus, ChequeLeafStatus } from '../../../../core/models/enums';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-cheque-detail',
  standalone: true,
  imports: [CommonModule, Badge, LucideArrowLeft],
  templateUrl: './cheque-detail.html',
  styleUrl: './cheque-detail.css'
})
export class CustomerChequeDetailComponent implements OnInit {
  ChequeBookStatus = ChequeBookStatus;

  chequeBook: ChequeBookResponse | null = null;
  isLoading = true;
  errorMessage = '';
  chequeBookId = 0;

  private chequeService = inject(ChequeService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.chequeBookId = id;
      this.loadChequeBook(id);
    }
  }

  loadChequeBook(id: number): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.chequeService.getById(id).subscribe({
      next: (data) => {
        this.chequeBook = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading cheque book', err);
        this.errorMessage = err?.error?.message || err?.message || 'Failed to load cheque book.';
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

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
    this.router.navigate(['/customer/cheques']);
  }
}
