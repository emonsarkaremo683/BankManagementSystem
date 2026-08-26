import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { AddressService } from '../../../core/services/address.service';
import { Division, District, PoliceStation } from '../../../core/models/address.models';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  isLoading = false;
  currentStep = 1;
  private skipPermanentCascade = false;

  profileFile: File | null = null;

  divisions: Division[] = [];
  presentDistricts: District[] = [];
  presentPoliceStations: PoliceStation[] = [];
  permanentDistricts: District[] = [];
  permanentPoliceStations: PoliceStation[] = [];

  profilePreview: string | null = null;

  private authService = inject(AuthService);
  private addressService = inject(AddressService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.registerForm = this.fb.group({
      personal: this.fb.group({
        name: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        phone: ['', [Validators.required, Validators.pattern(/^01[3-9]\d{8}$/)]],
        dob: ['', Validators.required],
        gender: ['MALE', Validators.required],
        occupation: ['SERVICE_HOLDER', Validators.required]
      }),
      presentAddress: this.fb.group({
        holdingNo: ['', Validators.required],
        area: ['', Validators.required],
        postalCode: ['', Validators.required],
        divisionId: ['', Validators.required],
        districtId: ['', Validators.required],
        policeStationId: ['', Validators.required]
      }),
      sameAsPresent: [false],
      permanentAddress: this.fb.group({
        holdingNo: ['', Validators.required],
        area: ['', Validators.required],
        postalCode: ['', Validators.required],
        divisionId: ['', Validators.required],
        districtId: ['', Validators.required],
        policeStationId: ['', Validators.required]
      })
    });
  }

  ngOnInit(): void {
    this.loadDivisions();

    this.registerForm.get('presentAddress.divisionId')?.valueChanges.subscribe(id => {
      this.presentDistricts = [];
      this.presentPoliceStations = [];
      this.registerForm.get('presentAddress.districtId')?.reset('');
      this.registerForm.get('presentAddress.policeStationId')?.reset('');
      if (id) this.loadPresentDistricts(id);
    });

    this.registerForm.get('presentAddress.districtId')?.valueChanges.subscribe(id => {
      this.presentPoliceStations = [];
      this.registerForm.get('presentAddress.policeStationId')?.reset('');
      if (id) this.loadPresentPoliceStations(id);
    });

    this.registerForm.get('permanentAddress.divisionId')?.valueChanges.subscribe(id => {
      if (this.skipPermanentCascade) return;
      this.permanentDistricts = [];
      this.permanentPoliceStations = [];
      this.registerForm.get('permanentAddress.districtId')?.reset('');
      this.registerForm.get('permanentAddress.policeStationId')?.reset('');
      if (id) this.loadPermanentDistricts(id);
    });

    this.registerForm.get('permanentAddress.districtId')?.valueChanges.subscribe(id => {
      if (this.skipPermanentCascade) return;
      this.permanentPoliceStations = [];
      this.registerForm.get('permanentAddress.policeStationId')?.reset('');
      if (id) this.loadPermanentPoliceStations(id);
    });

    this.registerForm.get('sameAsPresent')?.valueChanges.subscribe(same => {
      const perm = this.registerForm.get('permanentAddress');
      if (same) {
        this.skipPermanentCascade = true;
        const pres = this.registerForm.get('presentAddress')!.value;
        perm?.patchValue({
          holdingNo: pres.holdingNo,
          area: pres.area,
          postalCode: pres.postalCode,
          divisionId: pres.divisionId,
          districtId: pres.districtId,
          policeStationId: pres.policeStationId
        });
        this.permanentDistricts = [...this.presentDistricts];
        this.permanentPoliceStations = [...this.presentPoliceStations];
        perm?.disable();
        this.skipPermanentCascade = false;
      } else {
        perm?.enable();
        perm?.reset();
      }
    });
  }

  loadDivisions(): void {
    this.addressService.getDivisions().subscribe({
      next: (data) => this.divisions = data,
      error: (err) => console.error('Error loading divisions', err)
    });
  }

  loadPresentDistricts(divisionId: number): void {
    this.addressService.getDistrictsByDivision(divisionId).subscribe({
      next: (data) => this.presentDistricts = data,
      error: (err) => console.error(err)
    });
  }

  loadPresentPoliceStations(districtId: number): void {
    this.addressService.getPoliceStationsByDistrict(districtId).subscribe({
      next: (data) => this.presentPoliceStations = data,
      error: (err) => console.error(err)
    });
  }

  loadPermanentDistricts(divisionId: number): void {
    this.addressService.getDistrictsByDivision(divisionId).subscribe({
      next: (data) => this.permanentDistricts = data,
      error: (err) => console.error(err)
    });
  }

  loadPermanentPoliceStations(districtId: number): void {
    this.addressService.getPoliceStationsByDistrict(districtId).subscribe({
      next: (data) => this.permanentPoliceStations = data,
      error: (err) => console.error(err)
    });
  }

  onProfileSelected(event: any) {
    const file = event.target.files?.[0];
    if (file) {
      this.profileFile = file;
      const reader = new FileReader();
      reader.onload = (e) => this.profilePreview = e.target?.result as string;
      reader.readAsDataURL(file);
    }
  }

  nextStep() {
    if (this.currentStep < 2) this.currentStep++;
    this.cdr.markForCheck();
  }

  prevStep() {
    if (this.currentStep > 1) this.currentStep--;
    this.cdr.markForCheck();
  }

  get personalGroup(): FormGroup {
    return this.registerForm.get('personal') as FormGroup;
  }

  get presentAddressGroup(): FormGroup {
    return this.registerForm.get('presentAddress') as FormGroup;
  }

  get permanentAddressGroup(): FormGroup {
    return this.registerForm.get('permanentAddress') as FormGroup;
  }

  get isAddressStepInvalid(): boolean {
    const presentInvalid = this.presentAddressGroup.invalid;
    const sameAsPresent = this.registerForm.get('sameAsPresent')?.value;
    if (sameAsPresent) {
      return presentInvalid;
    }
    return presentInvalid || this.permanentAddressGroup.invalid;
  }

  get isPermanentAddressDisabled(): boolean {
    return this.registerForm.get('sameAsPresent')?.value === true;
  }

  onSubmit() {
    if (this.personalGroup.invalid) {
      this.currentStep = 1;
      this.personalGroup.markAllAsTouched();
      alert('Please fix the errors in the Personal section.');
      this.cdr.markForCheck();
      return;
    }

    if (this.isAddressStepInvalid) {
      this.currentStep = 2;
      this.presentAddressGroup.markAllAsTouched();
      if (!this.registerForm.get('sameAsPresent')?.value) {
        this.permanentAddressGroup.markAllAsTouched();
      }
      alert('Please fix the errors in the Address section.');
      this.cdr.markForCheck();
      return;
    }

    this.isLoading = true;
    const v = this.registerForm.getRawValue();

    if (v.sameAsPresent) {
      v.permanentAddress = { ...v.presentAddress };
    }

    const formData = {
      name: v.personal.name,
      email: v.personal.email,
      password: v.personal.password,
      phone: v.personal.phone,
      dob: v.personal.dob,
      gender: v.personal.gender,
      occupation: v.personal.occupation,
      addresses: [
        {
          holdingNo: v.presentAddress.holdingNo,
          area: v.presentAddress.area,
          postalCode: v.presentAddress.postalCode,
          addressType: 'PRESENT',
          policeStation: { id: +v.presentAddress.policeStationId }
        },
        {
          holdingNo: v.permanentAddress.holdingNo,
          area: v.permanentAddress.area,
          postalCode: v.permanentAddress.postalCode,
          addressType: 'PERMANENT',
          policeStation: { id: +v.permanentAddress.policeStationId }
        }
      ]
    };

    this.authService.registerCustomer(formData, this.profileFile).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/verify-email-sent']);
      },
      error: (err: any) => {
        this.isLoading = false;
        console.error(err);
        alert(err.error?.message || 'Registration failed. Please check inputs.');
      }
    });
  }
}
