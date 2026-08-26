import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CustomerService } from '../../../../core/services/customer.service';
import { KycService } from '../../../../core/services/kyc.service';
import { CustomerResponse } from '../../../../core/models/customer.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft, LucideFileText } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, Badge, LucideArrowLeft, LucideFileText],
  templateUrl: './customer-detail.html',
  styleUrl: './customer-detail.css'
})
export class CustomerDetailComponent implements OnInit {
  customer: CustomerResponse | null = null;
  isLoading = true;

  private route = inject(ActivatedRoute);
  private customerService = inject(CustomerService);
  private kycService = inject(KycService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadCustomer(id);
    }
  }

  loadCustomer(id: number): void {
    this.isLoading = true;
    this.customerService.getById(id).subscribe({
      next: (data) => {
        this.customer = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading customer', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  getProfileUrl(path?: string): string {
    return this.kycService.getProfileUrl(path);
  }

  kycBadgeColor(status: string): BadgeColor {
    switch (status) {
      case 'VERIFIED': return 'success';
      case 'PENDING':
      case 'UNDER_REVIEW': return 'warning';
      case 'REJECTED':
      case 'EXPIRED': return 'danger';
      default: return 'neutral';
    }
  }
}
