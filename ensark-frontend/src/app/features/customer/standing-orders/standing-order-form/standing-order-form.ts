import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StandingOrderService } from '../../../../core/services/standing-order.service';
import { AccountService } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { StandingOrderFrequency } from '../../../../core/models/enums';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-standing-order-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft],
  templateUrl: './customer-standing-order-form.html',
  styleUrl: './customer-standing-order-form.css'
})
export class CustomerStandingOrderFormComponent implements OnInit {
  orderForm: FormGroup;
  isLoading = false;
  accounts: AccountResponse[] = [];
  frequencies = Object.values(StandingOrderFrequency);

  private fb = inject(FormBuilder);
  private standingOrderService = inject(StandingOrderService);
  private accountService = inject(AccountService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.orderForm = this.fb.group({
      sourceAccountId: [null, Validators.required],
      targetAccountNumber: ['', Validators.required],
      targetAccountName: ['', Validators.required],
      amount: [0, [Validators.required, Validators.min(0.01)]],
      frequency: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: [''],
      maxExecutions: [0],
      description: ['']
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

  onSubmit(): void {
    if (this.orderForm.invalid) {
      this.orderForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.standingOrderService.create(this.orderForm.value).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/customer/standing-orders']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Standing order creation failed', err);
        alert('Failed to create standing order');
        this.cdr.markForCheck();
      }
    });
  }
}
