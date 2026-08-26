import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../../../core/services/account.service';
import { BranchService } from '../../../../core/services/branch.service';
import { KycService } from '../../../../core/services/kyc.service';
import { AuthService } from '../../../../core/services/auth.service';
import { BranchResponse } from '../../../../core/models/branch.models';
import { KYCStatus } from '../../../../core/models/enums';
import { LucideArrowLeft, LucideTriangleAlert, LucideSignature, LucideCamera, LucideIdCard, LucideLoaderCircle } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-account-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucideTriangleAlert, LucideSignature, LucideCamera, LucideIdCard, LucideLoaderCircle],
  templateUrl: './account-create.html',
  styleUrl: './account-create.css'
})
export class CustomerAccountCreateComponent implements OnInit {
  accountForm: FormGroup;
  isLoading = false;
  branches: BranchResponse[] = [];
  kycStatus: string = 'NOT_SUBMITTED';

  signatureFile: File | null = null;
  nomineePhotoFile: File | null = null;
  nomineeNidFrontFile: File | null = null;
  nomineeNidBackFile: File | null = null;

  private fb = inject(FormBuilder);
  private accountService = inject(AccountService);
  private branchService = inject(BranchService);
  private kycService = inject(KycService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.accountForm = this.fb.group({
      accountType: ['SAVINGS', Validators.required],
      availableBalance: [0, [Validators.required, Validators.min(0)]],
      branchId: ['', Validators.required],
      n_name: ['', Validators.required],
      n_email: ['', [Validators.required, Validators.email]],
      n_phone: ['', [Validators.required, Validators.pattern(/^01[3-9]\d{8}$/)]],
      relation: ['OTHER', Validators.required]
    });
  }

  ngOnInit(): void {
    this.branchService.getAll().subscribe({
      next: (data) => {
        this.branches = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error loading branches', err)
    });

    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.kycService.getMyKycStatus().subscribe({
        next: (data) => {
          this.kycStatus = data?.status || 'NOT_SUBMITTED';
          this.cdr.markForCheck();
        },
        error: () => {
          this.kycStatus = 'NOT_SUBMITTED';
          this.cdr.markForCheck();
        }
      });
    }
  }

  onSignatureSelected(event: any) {
    this.signatureFile = event.target.files?.[0] || null;
  }

  onNomineePhotoSelected(event: any) {
    this.nomineePhotoFile = event.target.files?.[0] || null;
  }

  onNomineeNidFrontSelected(event: any) {
    this.nomineeNidFrontFile = event.target.files?.[0] || null;
  }

  onNomineeNidBackSelected(event: any) {
    this.nomineeNidBackFile = event.target.files?.[0] || null;
  }

  onSubmit(): void {
    if (this.accountForm.invalid) {
      this.accountForm.markAllAsTouched();
      return;
    }

    if (!this.signatureFile) {
      alert('Please upload your signature.');
      return;
    }
    if (!this.nomineePhotoFile || !this.nomineeNidFrontFile || !this.nomineeNidBackFile) {
      alert('Please upload all nominee documents (photo, NID front, NID back).');
      return;
    }

    this.isLoading = true;

    const requestData = {
      ...this.accountForm.value,
      accountHolders: [{
        holderType: 'PRIMARY',
        canWithdraw: true,
        canDeposit: true,
        canApproveTransaction: false,
        customerId: 0
      }]
    };

    this.accountService.save(
      requestData,
      [this.signatureFile],
      this.nomineePhotoFile,
      this.nomineeNidFrontFile,
      this.nomineeNidBackFile
    ).subscribe({
      next: () => {
        this.isLoading = false;
        alert('Account application submitted successfully! It will be activated after staff review.');
        this.router.navigate(['/customer/accounts']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Failed to create account', err);
        alert(err.error?.message || 'Failed to create account. Your KYC may not be verified yet.');
        this.cdr.markForCheck();
      }
    });
  }
}
