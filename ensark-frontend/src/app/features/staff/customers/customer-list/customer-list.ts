import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CustomerService } from '../../../../core/services/customer.service';
import { KycService } from '../../../../core/services/kyc.service';
import { CustomerResponse } from '../../../../core/models/customer.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideUsers, LucideShieldCheck, LucideShieldAlert, LucideUserCheck, LucideSearch, LucideEye } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucideUsers, LucideShieldCheck, LucideShieldAlert, LucideUserCheck, LucideSearch, LucideEye],
  templateUrl: './customer-list.html',
  styleUrl: './customer-list.css'
})
export class CustomerListComponent implements OnInit {
  customers: CustomerResponse[] = [];
  filteredCustomers: CustomerResponse[] = [];
  isLoading = true;
  searchTerm = '';

  private customerService = inject(CustomerService);
  private kycService = inject(KycService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.isLoading = true;
    this.customerService.getAll().subscribe({
      next: (data) => {
        this.customers = data;
        this.filteredCustomers = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching customers', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredCustomers = this.customers;
      return;
    }
    this.filteredCustomers = this.customers.filter(c =>
      c.name?.toLowerCase().includes(term) ||
      c.email?.toLowerCase().includes(term) ||
      c.phone?.toLowerCase().includes(term)
    );
  }

  getProfileUrl(path?: string): string {
    return this.kycService.getProfileUrl(path);
  }

  get verifiedCount(): number {
    return this.customers.filter(c => c.kycStatus === 'VERIFIED').length;
  }

  get pendingCount(): number {
    return this.customers.filter(c => c.kycStatus === 'PENDING' || c.kycStatus === 'UNDER_REVIEW').length;
  }

  get activeCount(): number {
    return this.customers.filter(c => c.active).length;
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
