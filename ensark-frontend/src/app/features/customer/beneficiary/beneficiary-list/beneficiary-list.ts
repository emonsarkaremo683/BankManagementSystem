import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { BeneficiaryService } from '../../../../core/services/beneficiary.service';
import { AuthService } from '../../../../core/services/auth.service';
import { CustomerService } from '../../../../core/services/customer.service';
import { BeneficiaryResponse } from '../../../../core/models/beneficiary.models';
import { Badge } from '../../../../shared/components/badge/badge';
import { LucideUsers, LucidePlus, LucidePencil, LucideTrash2, LucideBadgeCheck, LucideX } from '../../../../shared/icons';

@Component({
  selector: 'app-beneficiary-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, Badge, LucideUsers, LucidePlus, LucidePencil, LucideTrash2, LucideBadgeCheck, LucideX],
  templateUrl: './beneficiary-list.html',
  styleUrl: './beneficiary-list.css'
})
export class BeneficiaryListComponent implements OnInit {
  beneficiaries: BeneficiaryResponse[] = [];
  isLoading = true;

  verifyingId: number | null = null;
  otpCode = '';
  otpLoading = false;
  otpSuccess = '';
  otpError = '';

  private beneficiaryService = inject(BeneficiaryService);
  private authService = inject(AuthService);
  private customerService = inject(CustomerService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadBeneficiaries();
  }

  loadBeneficiaries(): void {
    this.isLoading = true;
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.customerService.getByEmail(user.email).subscribe({
        next: (customer) => {
          this.beneficiaryService.getByCustomerId(customer.id).subscribe({
            next: (data) => {
              this.beneficiaries = data;
              this.isLoading = false;
              this.cdr.markForCheck();
            },
            error: (err) => {
              console.error('Error loading beneficiaries', err);
              this.isLoading = false;
              this.cdr.markForCheck();
            }
          });
        },
        error: (err) => {
          console.error('Error loading customer', err);
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      });
    }
  }

  deleteBeneficiary(id: number): void {
    if (!confirm('Are you sure you want to delete this beneficiary?')) return;
    this.beneficiaryService.delete(id).subscribe({
      next: () => {
        this.beneficiaries = this.beneficiaries.filter(b => b.id !== id);
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error deleting beneficiary', err);
        alert('Failed to delete beneficiary');
      }
    });
  }

  startVerification(id: number): void {
    this.verifyingId = id;
    this.otpCode = '';
    this.otpSuccess = '';
    this.otpError = '';
    this.beneficiaryService.initiateVerification(id).subscribe({
      next: (message) => {
        this.otpSuccess = message;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error initiating verification', err);
        this.otpError = err?.error || 'Failed to initiate verification';
        this.cdr.markForCheck();
      }
    });
  }

  cancelVerification(): void {
    this.verifyingId = null;
    this.otpCode = '';
    this.otpSuccess = '';
    this.otpError = '';
  }

  verifyBeneficiary(id: number): void {
    if (!this.otpCode.trim()) return;
    this.otpLoading = true;
    this.otpError = '';
    this.beneficiaryService.verify(id, this.otpCode).subscribe({
      next: () => {
        this.verifyingId = null;
        this.otpCode = '';
        this.otpSuccess = '';
        this.otpError = '';
        this.otpLoading = false;
        this.loadBeneficiaries();
      },
      error: (err) => {
        console.error('Error verifying beneficiary', err);
        this.otpError = err?.error || 'Failed to verify beneficiary';
        this.otpLoading = false;
        this.cdr.markForCheck();
      }
    });
  }
}
