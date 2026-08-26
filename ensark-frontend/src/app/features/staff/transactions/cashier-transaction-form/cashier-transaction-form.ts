import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CashierTransactionService } from '../../../../core/services/cashier-transaction.service';
import { AccountService } from '../../../../core/services/account.service';
import { EmployeeService } from '../../../../core/services/employee.service';
import { AuthService } from '../../../../core/services/auth.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { EmployeeResponse } from '../../../../core/models/employee.models';
import { TransactionType } from '../../../../core/models/enums';
import { LucideArrowLeft, LucideSearch, LucideLandmark, LucideLoaderCircle, LucideArrowUpRight, LucideArrowDownRight, LucideShieldCheck, LucideCircleCheck, LucideCircleX } from '../../../../shared/icons';

@Component({
  selector: 'app-cashier-transaction-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucideSearch, LucideLandmark, LucideLoaderCircle, LucideArrowUpRight, LucideArrowDownRight, LucideShieldCheck, LucideCircleCheck, LucideCircleX],
  templateUrl: './cashier-transaction-form.html',
  styleUrl: './cashier-transaction-form.css'
})
export class CashierTransactionFormComponent implements OnInit {
  form: FormGroup;
  isLoading = false;
  isLoadingEmployee = true;
  employeeError = '';
  
  accounts: AccountResponse[] = [];
  filteredAccounts: AccountResponse[] = [];
  accountSearchQuery = '';
  selectedAccount: AccountResponse | null = null;
  showAccountDropdown = false;
  
  employee: EmployeeResponse | null = null;
  transactionTypes = [TransactionType.DEPOSIT, TransactionType.WITHDRAW];

  quickAmounts = [100, 250, 500, 1000, 5000];

  toastMessage: { type: 'success' | 'error'; text: string } | null = null;

  private fb = inject(FormBuilder);
  private cashierTxService = inject(CashierTransactionService);
  private accountService = inject(AccountService);
  private employeeService = inject(EmployeeService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.form = this.fb.group({
      type: [TransactionType.DEPOSIT, Validators.required],
      accountNumber: ['', Validators.required],
      accountName: [{ value: '', disabled: true }],
      bankName: ['Ensark Bank', Validators.required],
      routingNumber: ['ENSRK001', Validators.required],
      checkNo: [''],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      remarks: ['']
    });
  }

  ngOnInit(): void {
    this.loadAccounts();
    this.loadEmployeeData();
  }

  loadAccounts(): void {
    this.accountService.getAll().subscribe({
      next: (data) => {
        this.accounts = data || [];
        this.filteredAccounts = [...this.accounts];
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading accounts', err);
        this.showToast('error', 'Failed to load branch accounts.');
      }
    });
  }

  loadEmployeeData(): void {
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.employeeService.findByEmail(user.email).subscribe({
        next: (emp) => {
          this.employee = emp;
          this.isLoadingEmployee = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error loading employee profile', err);
          this.employeeError = 'Failed to load cashier profile. Please try again.';
          this.isLoadingEmployee = false;
          this.cdr.markForCheck();
        }
      });
    } else {
      this.employeeError = 'No user session found. Please log in again.';
      this.isLoadingEmployee = false;
      this.cdr.markForCheck();
    }
  }

  setTransactionType(type: TransactionType): void {
    this.form.patchValue({ type });
  }

  filterAccounts(query: string): void {
    this.accountSearchQuery = query;
    const term = query.toLowerCase().trim();
    if (!term) {
      this.filteredAccounts = [...this.accounts];
      return;
    }

    this.filteredAccounts = this.accounts.filter(a => 
      a.accountNumber?.toLowerCase().includes(term) ||
      a.branchName?.toLowerCase().includes(term) ||
      a.holderResponses?.some(h => h.accountHolderName?.toLowerCase().includes(term))
    );
  }

  onAccountInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.accountSearchQuery = value;
    this.filterAccounts(value);
    this.form.patchValue({ accountNumber: value });
    if (this.selectedAccount && this.selectedAccount.accountNumber !== value) {
      this.selectedAccount = null;
      this.form.patchValue({ accountName: '' });
    }
  }

  selectAccount(account: AccountResponse): void {
    this.selectedAccount = account;
    const holderName = account.holderResponses?.[0]?.accountHolderName || account.n_name || 'Account Holder';
    this.form.patchValue({ 
      accountNumber: account.accountNumber,
      accountName: holderName 
    });
    this.accountSearchQuery = account.accountNumber;
    this.filteredAccounts = [];
    this.showAccountDropdown = false;
  }

  hideAccountDropdown(): void {
    setTimeout(() => {
      this.showAccountDropdown = false;
    }, 200);
  }

  setQuickAmount(amt: number): void {
    this.form.patchValue({ amount: amt });
  }

  onSubmit(): void {
    if (this.form.invalid || !this.employee) {
      this.form.markAllAsTouched();
      this.showToast('error', 'Please fill out all required transaction fields.');
      return;
    }

    this.isLoading = true;
    const val = this.form.getRawValue();

    const requestData = {
      type: val.type,
      accountNumber: val.accountNumber,
      accountName: val.accountName,
      bankName: val.bankName,
      routingNumber: val.routingNumber,
      checkNo: val.checkNo || undefined,
      branchId: this.employee.branchId,
      employeeId: this.employee.id,
      transactionRequest: {
        amount: val.amount,
        remarks: val.remarks
      }
    };

    this.cashierTxService.create(requestData).subscribe({
      next: () => {
        this.isLoading = false;
        this.showToast('success', `${val.type} transaction of $${val.amount} processed successfully!`);
        setTimeout(() => {
          this.router.navigate(['/staff/transactions']);
        }, 1200);
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Cashier transaction failed', err);
        this.showToast('error', 'Transaction failed: ' + (err.error?.message || 'Server error'));
        this.cdr.markForCheck();
      }
    });
  }

  showToast(type: 'success' | 'error', text: string): void {
    this.toastMessage = { type, text };
    setTimeout(() => {
      this.toastMessage = null;
      this.cdr.markForCheck();
    }, 4000);
  }
}
