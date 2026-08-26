import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { BeneficiaryService } from '../../../../core/services/beneficiary.service';
import { AuthService } from '../../../../core/services/auth.service';
import { CustomerService } from '../../../../core/services/customer.service';
import { BeneficiaryRequest } from '../../../../core/models/beneficiary.models';
import { BeneficiaryType } from '../../../../core/models/enums';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-beneficiary-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft],
  templateUrl: './beneficiary-form.html',
  styleUrl: './beneficiary-form.css'
})
export class BeneficiaryFormComponent implements OnInit {
  beneficiaryForm: FormGroup;
  isLoading = false;
  isEditMode = false;
  beneficiaryId: number | null = null;
  customerId: number | null = null;

  readonly beneficiaryTypes = Object.values(BeneficiaryType);

  private fb = inject(FormBuilder);
  private beneficiaryService = inject(BeneficiaryService);
  private authService = inject(AuthService);
  private customerService = inject(CustomerService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.beneficiaryForm = this.fb.group({
      name: ['', Validators.required],
      accNumber: ['', Validators.required],
      provider: [''],
      routingNumber: [''],
      beneficiaryType: [BeneficiaryType.BANK, Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCustomerId();
    this.applyProviderRequirement(this.beneficiaryForm.get('beneficiaryType')?.value);
    this.beneficiaryForm.get('beneficiaryType')?.valueChanges.subscribe(type => this.applyProviderRequirement(type));

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.beneficiaryId = Number(id);
      this.loadBeneficiary(this.beneficiaryId);
    }
  }

  /** RequestValidator#validateBeneficiary requires provider + routingNumber only when
   *  beneficiaryType === INTER_BANK (see BeneficiaryRequest validation on the backend). */
  applyProviderRequirement(type: BeneficiaryType): void {
    const providerCtrl = this.beneficiaryForm.get('provider');
    const routingCtrl = this.beneficiaryForm.get('routingNumber');
    if (type === BeneficiaryType.INTER_BANK) {
      providerCtrl?.setValidators([Validators.required]);
      routingCtrl?.setValidators([Validators.required]);
    } else {
      providerCtrl?.setValidators([]);
      routingCtrl?.setValidators([]);
    }
    providerCtrl?.updateValueAndValidity();
    routingCtrl?.updateValueAndValidity();
  }

  loadCustomerId(): void {
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.customerService.getByEmail(user.email).subscribe({
        next: (customer) => {
          this.customerId = customer.id;
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Error loading customer', err)
      });
    }
  }

  loadBeneficiary(id: number): void {
    this.beneficiaryService.getById(id).subscribe({
      next: (data) => {
        this.beneficiaryForm.patchValue({
          name: data.name,
          accNumber: data.accNumber,
          provider: data.provider,
          routingNumber: data.routingNumber,
          beneficiaryType: data.beneficiaryType
        });
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading beneficiary', err);
        alert('Failed to load beneficiary');
        this.router.navigate(['/customer/beneficiaries']);
      }
    });
  }

  onSubmit(): void {
    if (this.beneficiaryForm.invalid || !this.customerId) return;

    this.isLoading = true;
    const request: BeneficiaryRequest = {
      ...this.beneficiaryForm.value,
      customerId: this.customerId
    };

    const operation = this.isEditMode && this.beneficiaryId
      ? this.beneficiaryService.update(this.beneficiaryId, request)
      : this.beneficiaryService.create(request);

    operation.subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/customer/beneficiaries']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Error saving beneficiary', err);
        alert(err?.error?.message || 'Failed to save beneficiary');
        this.cdr.markForCheck();
      }
    });
  }
}
