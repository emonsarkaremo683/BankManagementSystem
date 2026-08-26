import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoanService } from '../../../../core/services/loan.service';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { LucideArrowLeft, LucideUpload, LucideFileText, LucideCamera, LucideX } from '../../../../shared/icons';

@Component({
  selector: 'app-loan-apply',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucideUpload, LucideFileText, LucideCamera, LucideX],
  templateUrl: './loan-apply.html',
  styleUrl: './loan-apply.css'
})
export class LoanApplyComponent implements OnInit {
  loanForm: FormGroup;
  guarantorForm: FormGroup;
  isLoading = false;
  accounts: AccountResponse[] = [];

  documents: File[] = [];
  guarantorPhoto: File | null = null;

  private fb = inject(FormBuilder);
  private loanService = inject(LoanService);
  private accountService = inject(AccountService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.loanForm = this.fb.group({
      accountId: [null, Validators.required],
      principalAmount: [0, [Validators.required, Validators.min(1000)]],
      annualInterestRate: [12, [Validators.required, Validators.min(1)]],
      tenureMonths: [12, [Validators.required, Validators.min(1)]]
    });

    this.guarantorForm = this.fb.group({
      name: ['', Validators.required],
      phone: ['', Validators.required],
      address: ['', Validators.required],
      nidNumber: ['', Validators.required],
      relation: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.accountService.getByCustomerEmail(user.email).subscribe({
        next: (data) => {
          this.accounts = data;
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Error loading accounts', err)
      });
    }
  }

  onDocumentsSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.documents = [...this.documents, ...Array.from(input.files)];
      input.value = '';
    }
  }

  removeDocument(index: number): void {
    this.documents = this.documents.filter((_, i) => i !== index);
  }

  onGuarantorPhotoSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.guarantorPhoto = input.files[0];
      input.value = '';
    }
  }

  removeGuarantorPhoto(): void {
    this.guarantorPhoto = null;
  }

  onSubmit(): void {
    if (this.loanForm.invalid || this.guarantorForm.invalid) {
      this.loanForm.markAllAsTouched();
      this.guarantorForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const requestData = {
      ...this.loanForm.value,
      guarantor: this.guarantorForm.value
    };

    this.loanService.apply(requestData, this.documents.length ? this.documents : undefined, this.guarantorPhoto ?? undefined).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/customer/loans']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Loan application failed', err);
        alert('Loan application failed');
        this.cdr.markForCheck();
      }
    });
  }
}
