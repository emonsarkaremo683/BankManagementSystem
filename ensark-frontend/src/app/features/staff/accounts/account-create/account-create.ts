import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../../../core/services/account.service';
import { BranchService } from '../../../../core/services/branch.service';
import { CustomerService } from '../../../../core/services/customer.service';
import { BranchResponse } from '../../../../core/models/branch.models';
import { CustomerResponse } from '../../../../core/models/customer.models';
import { LucideArrowLeft, LucidePlus, LucideLoaderCircle, LucideImage, LucideIdCard } from '../../../../shared/icons';

@Component({
  selector: 'app-account-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucidePlus, LucideLoaderCircle, LucideImage, LucideIdCard],
  templateUrl: './account-create.html',
  styleUrl: './account-create.css'
})
export class AccountCreateComponent implements OnInit {
  accountForm: FormGroup;
  isLoading = false;
  errorMessage = '';
  branches: BranchResponse[] = [];

  customers: CustomerResponse[] = [];
  filteredCustomers: CustomerResponse[] = [];
  customerSearchQueries: { [key: number]: string } = {};
  showCustomerDropdowns: { [key: number]: boolean } = {};
  selectedCustomers: { [key: number]: CustomerResponse | null } = {};

  // The backend requires one signature file per account holder (matched by
  // array index) plus nominee photo/NID-front/NID-back — all as required
  // multipart parts on POST /api/account/create.
  holderSignatureFiles: (File | null)[] = [null];
  nomineePhotoFile: File | null = null;
  nomineeNidFrontFile: File | null = null;
  nomineeNidBackFile: File | null = null;

  private fb = inject(FormBuilder);
  private accountService = inject(AccountService);
  private branchService = inject(BranchService);
  private customerService = inject(CustomerService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.accountForm = this.fb.group({
      accountType: ['SAVINGS', Validators.required],
      availableBalance: [0, [Validators.required, Validators.min(0)]],
      branchId: ['', Validators.required],
      n_name: ['', Validators.required],
      n_email: ['', [Validators.required, Validators.email]],
      n_phone: ['', Validators.required],
      relation: ['OTHER', Validators.required],
      accountHolders: this.fb.array([this.createHolder()])
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
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.customerService.getAll().subscribe({
      next: (data) => {
        this.customers = data || [];
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error loading customers', err)
    });
  }

  get accountHolders(): FormArray {
    return this.accountForm.get('accountHolders') as FormArray;
  }

  createHolder(): FormGroup {
    return this.fb.group({
      holderType: ['PRIMARY', Validators.required],
      canWithdraw: [true],
      canDeposit: [true],
      canApproveTransaction: [false],
      customerId: ['', Validators.required]
    });
  }

  addHolder(): void {
    this.accountHolders.push(this.createHolder());
    this.holderSignatureFiles.push(null);
  }

  removeHolder(index: number): void {
    if (this.accountHolders.length > 1) {
      this.accountHolders.removeAt(index);
      this.holderSignatureFiles.splice(index, 1);
      delete this.customerSearchQueries[index];
      delete this.showCustomerDropdowns[index];
      delete this.selectedCustomers[index];
    }
  }

  onHolderSignatureSelected(event: Event, index: number): void {
    const file = (event.target as HTMLInputElement).files?.[0] || null;
    this.holderSignatureFiles[index] = file;
  }

  onNomineePhotoSelected(event: Event): void {
    this.nomineePhotoFile = (event.target as HTMLInputElement).files?.[0] || null;
  }

  onNomineeNidFrontSelected(event: Event): void {
    this.nomineeNidFrontFile = (event.target as HTMLInputElement).files?.[0] || null;
  }

  onNomineeNidBackSelected(event: Event): void {
    this.nomineeNidBackFile = (event.target as HTMLInputElement).files?.[0] || null;
  }

  onCustomerInput(event: Event, index: number): void {
    const value = (event.target as HTMLInputElement).value;
    this.customerSearchQueries[index] = value;
    this.showCustomerDropdowns[index] = true;

    const term = value.toLowerCase().trim();
    if (!term) {
      this.filteredCustomers = [...this.customers];
    } else {
      this.filteredCustomers = this.customers.filter(c =>
        c.email?.toLowerCase().includes(term) ||
        c.name?.toLowerCase().includes(term) ||
        c.phone?.toLowerCase().includes(term)
      );
    }

    if (this.selectedCustomers[index] && this.selectedCustomers[index]!.email !== value) {
      this.selectedCustomers[index] = null;
      this.accountHolders.at(index).patchValue({ customerId: '' });
    }

    this.cdr.markForCheck();
  }

  selectCustomer(customer: CustomerResponse, index: number): void {
    this.selectedCustomers[index] = customer;
    this.customerSearchQueries[index] = customer.email;
    this.accountHolders.at(index).patchValue({ customerId: customer.id });
    this.showCustomerDropdowns[index] = false;
    this.filteredCustomers = [];
    this.cdr.markForCheck();
  }

  hideCustomerDropdown(index: number): void {
    setTimeout(() => {
      this.showCustomerDropdowns[index] = false;
    }, 200);
  }

  get allSignaturesProvided(): boolean {
    return this.holderSignatureFiles.slice(0, this.accountHolders.length).every(f => !!f);
  }

  get allNomineeDocsProvided(): boolean {
    return !!this.nomineePhotoFile && !!this.nomineeNidFrontFile && !!this.nomineeNidBackFile;
  }

  onSubmit(): void {
    this.errorMessage = '';

    if (this.accountForm.invalid) {
      this.accountForm.markAllAsTouched();
      return;
    }

    if (!this.allSignaturesProvided) {
      this.errorMessage = 'Please upload a signature image for every account holder.';
      return;
    }
    if (!this.allNomineeDocsProvided) {
      this.errorMessage = 'Please upload the nominee photo, NID front, and NID back.';
      return;
    }

    this.isLoading = true;
    const signatures = this.holderSignatureFiles.slice(0, this.accountHolders.length) as File[];

    this.accountService.save(
      this.accountForm.value,
      signatures,
      this.nomineePhotoFile!,
      this.nomineeNidFrontFile!,
      this.nomineeNidBackFile!
    ).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/staff/accounts']);
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Failed to create account', err);
        this.errorMessage = err.error?.message || 'Failed to create account.';
        this.cdr.markForCheck();
      }
    });
  }
}
