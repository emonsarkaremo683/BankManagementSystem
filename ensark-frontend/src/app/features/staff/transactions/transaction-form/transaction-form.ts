import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TransactionService } from '../../../../core/services/transaction.service';
import { AccountService } from '../../../../core/services/account.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { LucideArrowLeft, LucideLoaderCircle } from '../../../../shared/icons';

@Component({
  selector: 'app-transaction-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucideLoaderCircle],
  templateUrl: './transaction-form.html',
  styleUrl: './transaction-form.css'
})
export class TransactionFormComponent implements OnInit {
  transactionForm: FormGroup;
  isLoading = false;
  errorMessage = '';
  accounts: AccountResponse[] = [];

  private fb = inject(FormBuilder);
  private transactionService = inject(TransactionService);
  private accountService = inject(AccountService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.transactionForm = this.fb.group({
      senderAccountId: ['', Validators.required],
      receiverAccountNumber: ['', Validators.required],
      amount: [0, [Validators.required, Validators.min(0.01)]],
      remarks: ['']
    });
  }

  ngOnInit(): void {
    this.accountService.getAll().subscribe({
      next: (data) => {
        this.accounts = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error loading accounts', err)
    });
  }

  onSubmit(): void {
    if (this.transactionForm.invalid) {
      this.transactionForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    const formVal = this.transactionForm.value;

    const requestData = {
      senderAccountId: formVal.senderAccountId,
      receiverAccountNumber: formVal.receiverAccountNumber,
      request: {
        amount: formVal.amount,
        remarks: formVal.remarks
      }
    };

    this.transactionService.create(requestData).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/staff/transactions']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Transaction failed', err);
        // Covers all non-2xx statuses, including the 429 fraud-detection
        // block from TransactionServiceImpl.createTransaction, by always
        // surfacing the backend's message rather than a generic string.
        this.errorMessage = err.error?.message || 'Transaction failed. Please try again.';
        this.cdr.markForCheck();
      }
    });
  }
}
