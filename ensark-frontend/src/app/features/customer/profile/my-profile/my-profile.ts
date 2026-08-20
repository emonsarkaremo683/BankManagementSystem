import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, FormArray, Validators } from '@angular/forms';
import { CustomerService } from '../../../../core/services/customer.service';
import { AuthService } from '../../../../core/services/auth.service';
import { KycService } from '../../../../core/services/kyc.service';
import { CustomerResponse } from '../../../../core/models/customer.models';
import { Gender, CustomerOccupation } from '../../../../core/models/enums';
import { AddressService } from '../../../../core/services/address.service';
import { PoliceStation } from '../../../../core/models/address.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header';
import { LucidePencil } from '../../../../shared/icons';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, Badge, PageHeaderComponent, LucidePencil],
  templateUrl: './my-profile.html',
  styleUrl: './my-profile.css'
})
export class MyProfileComponent implements OnInit {
  customer: CustomerResponse | null = null;
  isLoading = true;
  isEditing = false;
  profileForm: FormGroup;
  selectedProfile: File | null = null;
  profilePreview: string | null = null;

  policeStations: PoliceStation[] = [];

  genders = Object.values(Gender);
  occupations = Object.values(CustomerOccupation);

  private customerService = inject(CustomerService);
  private authService = inject(AuthService);
  private kycService = inject(KycService);
  private addressService = inject(AddressService);
  private fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.profileForm = this.fb.group({
      name: ['', Validators.required],
      phone: ['', Validators.required],
      occupation: ['', Validators.required],
      gender: ['', Validators.required],
      dob: ['', Validators.required],
      addresses: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.loadPoliceStations();
    this.loadProfile();
  }

  get addresses(): FormArray {
    return this.profileForm.get('addresses') as FormArray;
  }

  loadPoliceStations(): void {
    this.addressService.getAllPoliceStations().subscribe({
      next: (data) => {
        this.policeStations = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error loading police stations', err)
    });
  }

  onPoliceStationChange(index: number, psId: number): void {
    const addr = this.addresses.at(index) as FormGroup;
    const ps = this.policeStations.find(p => p.id === psId);
    if (ps) {
      addr.patchValue({
        districtName: ps.district?.name || '',
        divisionName: ps.district?.division?.name || ''
      });
    } else {
      addr.patchValue({ districtName: '', divisionName: '' });
    }
  }

  loadProfile(): void {
    this.isLoading = true;
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.customerService.getByEmail(user.email).subscribe({
        next: (data) => {
          this.customer = data;
          this.populateForm(data);
          this.isLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error loading profile', err);
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      });
    }
  }

  populateForm(customer: CustomerResponse): void {
    this.profileForm.patchValue({
      name: customer.name,
      phone: customer.phone,
      occupation: customer.occupation,
      gender: customer.gender,
      dob: customer.dob
    });

    this.addresses.clear();
    if (customer.addresses?.length) {
      customer.addresses.forEach(addr => {
        this.addresses.push(this.fb.group({
          id: [addr.id],
          holdingNo: [addr.holdingNo, Validators.required],
          area: [addr.area, Validators.required],
          postalCode: [addr.postalCode, Validators.required],
          addressType: [addr.addressType, Validators.required],
          policeStationId: [addr.policeStationId || null],
          districtName: [addr.districtName || ''],
          divisionName: [addr.divisionName || '']
        }));
      });
    }
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    if (!this.isEditing && this.customer) {
      this.populateForm(this.customer);
    }
  }

  onProfileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.length) {
      this.selectedProfile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.profilePreview = reader.result as string;
        this.cdr.markForCheck();
      };
      reader.readAsDataURL(this.selectedProfile);
    }
  }

  onSubmit(): void {
    if (this.profileForm.invalid || !this.customer) return;

    const formValue = this.profileForm.value;
    const request = {
      email: this.customer.email,
      password: 'UnusedPlaceholder1',
      name: formValue.name,
      gender: formValue.gender,
      phone: formValue.phone,
      occupation: formValue.occupation,
      dob: formValue.dob,
      addresses: formValue.addresses.map((addr: any) => ({
        id: addr.id,
        holdingNo: addr.holdingNo,
        area: addr.area,
        postalCode: addr.postalCode,
        addressType: addr.addressType,
        policeStation: { id: addr.policeStationId }
      }))
    };

    this.customerService.update(this.customer.id, request, this.selectedProfile || undefined).subscribe({
      next: (data) => {
        this.customer = data;
        this.isEditing = false;
        this.selectedProfile = null;
        this.profilePreview = null;
        alert('Profile updated successfully');
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error updating profile', err);
        alert('Failed to update profile');
      }
    });
  }

  kycBadgeColor(): BadgeColor {
    switch (this.customer?.kycStatus) {
      case 'VERIFIED': return 'success';
      case 'PENDING':
      case 'UNDER_REVIEW': return 'warning';
      case 'REJECTED': return 'danger';
      default: return 'neutral';
    }
  }

  getProfileUrl(path?: string | null): string {
    return this.kycService.getProfileUrl(path);
  }
}
