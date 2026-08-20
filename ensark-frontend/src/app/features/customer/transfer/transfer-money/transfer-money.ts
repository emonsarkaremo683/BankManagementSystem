import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { TransactionService } from '../../../../core/services/transaction.service';
import { AccountService } from '../../../../core/services/account.service';
import { BeneficiaryService } from '../../../../core/services/beneficiary.service';
import { AuthService } from '../../../../core/services/auth.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { BeneficiaryResponse } from '../../../../core/models/beneficiary.models';
import { AccountTransactionResponse, OtpInitiateResponse } from '../../../../core/models/transaction.models';
import { LucideArrowLeft, LucideLoaderCircle, LucideLock, LucideCircleCheck } from '../../../../shared/icons';

@Component({
  selector: 'app-transfer-money',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucideLoaderCircle, LucideLock, LucideCircleCheck],
  templateUrl: './transfer-money.html',
  styleUrl: './transfer-money.css'
})
export class TransferMoneyComponent implements OnInit {
  transferForm: FormGroup;
  otpForm: FormGroup;
  step: 'form' | 'otp' | 'success' = 'form';
  isLoading = false;
  isVerifying = false;
  errorMessage = '';
  accounts: AccountResponse[] = [];
  beneficiaries: BeneficiaryResponse[] = [];
  selectedAccount: AccountResponse | null = null;
  otpResponse: OtpInitiateResponse | null = null;
  transactionResult: AccountTransactionResponse | null = null;
  useBeneficiary = true;

  private fb = inject(FormBuilder);
  private transactionService = inject(TransactionService);
  private accountService = inject(AccountService);
  private beneficiaryService = inject(BeneficiaryService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.transferForm = this.fb.group({
      senderAccountId: ['', Validators.required],
      receiverMode: ['beneficiary', Validators.required],
      beneficiaryId: [''],
      receiverAccountNumber: [''],
      receiverName: [''],
      bankName: [''],
      routingNumber: [''],
      amount: [0, [Validators.required, Validators.min(0.01)]],
      remarks: ['']
    });

    this.otpForm = this.fb.group({
      otpCode: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]]
    });
  }

  ngOnInit(): void {
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.accountService.getByCustomerEmail(user.email).subscribe({
        next: (accounts) => {
          this.accounts = accounts.filter(a => a.accountStatus === 'ACTIVE');
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Error loading accounts', err)
      });
      this.beneficiaryService.getByCustomerEmail(user.email).subscribe({
        next: (bens) => {
          this.beneficiaries = bens.filter(b => b.verified && !b.blocked);
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Error loading beneficiaries', err)
      });
    }
  }

  onAccountChange(): void {
    const accountId = Number(this.transferForm.get('senderAccountId')?.value);
    this.selectedAccount = this.accounts.find(a => a.id === accountId) || null;
    this.cdr.markForCheck();
  }

  onBeneficiarySelect(): void {
    const bId = Number(this.transferForm.get('beneficiaryId')?.value);
    const ben = this.beneficiaries.find(b => b.id === bId);
    if (ben) {
      this.transferForm.patchValue({
        receiverAccountNumber: ben.accNumber,
        receiverName: ben.name,
        bankName: ben.provider,
        routingNumber: ben.routingNumber || ''
      });
    }
  }

  onSubmit(): void {
    if (this.transferForm.invalid) {
      this.transferForm.markAllAsTouched();
      return;
    }

    if (this.selectedAccount && this.transferForm.value.amount > this.selectedAccount.availableBalance) {
      this.errorMessage = 'Insufficient balance.';
      this.cdr.markForCheck();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    const fv = this.transferForm.value;

    const requestData: any = {
      senderAccountId: fv.senderAccountId,
      request: {
        amount: fv.amount,
        remarks: fv.remarks || 'Transfer from customer'
      }
    };

    if (fv.receiverMode === 'beneficiary' && fv.beneficiaryId) {
      requestData.beneficiaryId = fv.beneficiaryId;
      requestData.receiverAccountNumber = fv.receiverAccountNumber;
      requestData.receiverName = fv.receiverName;
      requestData.bankName = fv.bankName;
      requestData.routingNumber = fv.routingNumber;
    } else {
      requestData.receiverAccountNumber = fv.receiverAccountNumber;
      requestData.receiverName = fv.receiverName || '';
      requestData.bankName = fv.bankName || 'Ensark Bank';
      requestData.routingNumber = fv.routingNumber || '';
    }

    this.transactionService.initiateOnline(requestData).subscribe({
      next: (res) => {
        this.otpResponse = res;
        this.step = 'otp';
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || err.error?.error || 'Failed to initiate transfer. Please try again.';
        this.cdr.markForCheck();
      }
    });
  }

  onVerifyOtp(): void {
    if (this.otpForm.invalid) {
      this.otpForm.markAllAsTouched();
      return;
    }

    this.isVerifying = true;
    this.errorMessage = '';

    this.transactionService.verifyOnline({
      otpReferenceId: this.otpResponse!.otpReferenceId,
      otpCode: this.otpForm.value.otpCode
    }).subscribe({
      next: (res) => {
        this.transactionResult = res;
        this.step = 'success';
        this.isVerifying = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isVerifying = false;
        this.errorMessage = err.error?.message || err.error?.error || 'Invalid OTP. Please try again.';
        this.cdr.markForCheck();
      }
    });
  }

  backToForm(): void {
    this.step = 'form';
    this.errorMessage = '';
    this.otpForm.reset();
    this.cdr.markForCheck();
  }

  get balanceAfterTransfer(): number {
    if (!this.selectedAccount) return 0;
    const amount = this.transferForm.get('amount')?.value || 0;
    return this.selectedAccount.availableBalance - amount;
  }
}
