import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChequeService } from '../../../../core/services/cheque.service';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-cheque-apply',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft],
  templateUrl: './cheque-apply.html',
  styleUrl: './cheque-apply.css'
})
export class ChequeApplyComponent implements OnInit {
  chequeForm: FormGroup;
  isLoading = false;
  accounts: AccountResponse[] = [];
  leafOptions = [10, 25, 50, 100];

  private fb = inject(FormBuilder);
  private chequeService = inject(ChequeService);
  private accountService = inject(AccountService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.chequeForm = this.fb.group({
      accountId: [null, Validators.required],
      numberOfLeaves: [10, Validators.required]
    });
  }

  get feeAmount(): number {
    const leaves = this.chequeForm.get('numberOfLeaves')?.value;
    if (leaves <= 25) return 50;
    if (leaves <= 50) return 100;
    return 150;
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

  onSubmit(): void {
    if (this.chequeForm.invalid) {
      this.chequeForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.chequeService.apply(this.chequeForm.value).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/customer/cheques']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Cheque book application failed', err);
        alert('Cheque book application failed');
        this.cdr.markForCheck();
      }
    });
  }
}
