import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CardService } from '../../../../core/services/card.service';
import { AccountService } from '../../../../core/services/account.service';
import { AccountResponse } from '../../../../core/models/account.models';
import { CardNetwork, CardType } from '../../../../core/models/enums';
import { LucideArrowLeft, LucideCreditCard } from '../../../../shared/icons';

@Component({
  selector: 'app-card-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucideCreditCard],
  templateUrl: './card-form.html',
  styleUrl: './card-form.css'
})
export class CardFormComponent implements OnInit {
  cardForm: FormGroup;
  isLoading = false;
  accounts: AccountResponse[] = [];
  cardNetworks = Object.values(CardNetwork);
  cardTypes = Object.values(CardType);

  private fb = inject(FormBuilder);
  private cardService = inject(CardService);
  private accountService = inject(AccountService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.cardForm = this.fb.group({
      accountId: ['', Validators.required],
      cardNetwork: ['', Validators.required],
      cardType: ['', Validators.required],
      pin: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(6)]],
      isInternationalEnabled: [false],
      isOnlineTransactionEnabled: [true]
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
    if (this.cardForm.invalid) {
      this.cardForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.cardService.apply(this.cardForm.value).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/staff/cards']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Card application failed', err);
        alert('Card application failed');
        this.cdr.markForCheck();
      }
    });
  }
}
