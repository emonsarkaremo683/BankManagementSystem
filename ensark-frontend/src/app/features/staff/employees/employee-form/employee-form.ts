import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { EmployeeService } from '../../../../core/services/employee.service';
import { BranchService } from '../../../../core/services/branch.service';
import { AddressService } from '../../../../core/services/address.service';
import { BranchResponse } from '../../../../core/models/branch.models';
import { PoliceStation } from '../../../../core/models/address.models';
import { Designation, Gender, Role } from '../../../../core/models/enums';
import { DESIGNATION_DEFAULT_ROLE, DESIGNATION_LABELS } from '../../../../core/models/employee.models';
import { environment } from '../../../../../environments/environment';
import { LucideArrowLeft, LucideCircleUser } from '../../../../shared/icons';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft, LucideCircleUser],
  templateUrl: './employee-form.html',
  styleUrl: './employee-form.css'
})
export class EmployeeFormComponent implements OnInit {
  employeeForm: FormGroup;
  isEditMode = false;
  employeeId: number | null = null;
  isLoading = false;

  profileFile: File | null = null;
  currentProfileUrl: string | null = null;

  branches: BranchResponse[] = [];
  policeStations: PoliceStation[] = [];

  readonly roles = Object.values(Role);
  readonly designations = Object.values(Designation);
  readonly designationLabels = DESIGNATION_LABELS;

  private fb = inject(FormBuilder);
  private employeeService = inject(EmployeeService);
  private branchService = inject(BranchService);
  private addressService = inject(AddressService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.employeeForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      role: [Role.CASHIER, Validators.required],
      branchId: ['', Validators.required],
      name: ['', Validators.required],
      gender: [Gender.MALE, Validators.required],
      phone: ['', Validators.required],
      designation: [Designation.TELLER, Validators.required],
      dob: ['', Validators.required],

      address: this.fb.group({
        holdingNo: ['', Validators.required],
        area: ['', Validators.required],
        postalCode: ['', Validators.required],
        addressType: ['PRESENT', Validators.required],
        policeStationId: ['', Validators.required]
      })
    });
  }

  ngOnInit(): void {
    this.loadBranches();
    this.loadPoliceStations();

    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.employeeId) {
      this.isEditMode = true;
      this.employeeForm.get('password')?.clearValidators();
      this.employeeForm.get('password')?.updateValueAndValidity();
      this.loadEmployee();
    }
  }

  loadBranches(): void {
    this.branchService.getAll().subscribe({
      next: (data) => {
        this.branches = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error loading branches', err)
    });
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

  designationLabel(designation: string): string {
    return (this.designationLabels as Record<string, string>)[designation] || designation;
  }

  onDesignationChange(): void {
    const designation = this.employeeForm.get('designation')?.value as Designation;
    const defaultRole = DESIGNATION_DEFAULT_ROLE[designation];
    if (defaultRole) {
      this.employeeForm.get('role')?.setValue(defaultRole);
    }
  }

  loadEmployee(): void {
    if (!this.employeeId) return;
    this.isLoading = true;
    this.employeeService.getById(this.employeeId).subscribe({
      next: (emp) => {
        // Format DOB for date input
        const dobDate = emp.dob ? new Date(emp.dob).toISOString().split('T')[0] : '';

        let address = emp.addresses && emp.addresses.length > 0 ? emp.addresses[0] : null;

        this.employeeForm.patchValue({
          email: emp.email,
          role: emp.role,
          branchId: emp.branchId,
          name: emp.name,
          gender: emp.gender,
          phone: emp.phone,
          designation: emp.designation,
          dob: dobDate,
          address: {
            holdingNo: address?.holdingNo || '',
            area: address?.area || '',
            postalCode: address?.postalCode || '',
            addressType: address?.addressType || 'PRESENT',
            policeStationId: address?.policeStation?.id || ''
          }
        });

        if (emp.profile) {
          // Files are served statically from {uploadsUrl}/employee/{filename} — see
          // Utils#uploadFile(file, "employee", ...) and CorsConfig's /uploads/** resource
          // handler on the backend. environment.apiUrl (which ends in /api/) is NOT the
          // right base — that previously produced a 404 for every existing photo.
          this.currentProfileUrl = environment.uploadsUrl + 'employee/' + emp.profile;
        }
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Failed to load employee', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onFileSelected(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.profileFile = event.target.files[0];

      // Local preview
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.currentProfileUrl = e.target.result;
        this.cdr.markForCheck();
      };
      reader.readAsDataURL(this.profileFile!);
    }
  }

  onSubmit(): void {
    if (this.employeeForm.invalid) {
      this.employeeForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;

    const formVal = this.employeeForm.value;
    const requestData: any = {
      email: formVal.email,
      role: formVal.role,
      branchId: formVal.branchId,
      name: formVal.name,
      gender: formVal.gender,
      phone: formVal.phone,
      designation: formVal.designation,
      dob: formVal.dob,
      addresses: [
        {
          holdingNo: formVal.address.holdingNo,
          area: formVal.address.area,
          postalCode: formVal.address.postalCode,
          addressType: formVal.address.addressType,
          policeStation: { id: formVal.address.policeStationId }
        }
      ]
    };

    if (this.isEditMode) {
      if (formVal.password) {
        requestData.password = formVal.password;
      }
    } else {
      requestData.password = formVal.password;
    }

    if (this.isEditMode && this.employeeId) {
      this.employeeService.update(this.employeeId, requestData, this.profileFile || undefined).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/employees']);
          this.cdr.markForCheck();
        },
        error: (err) => {
          this.isLoading = false;
          console.error('Update failed', err);
          alert('Update failed');
          this.cdr.markForCheck();
        }
      });
    } else {
      this.employeeService.save(requestData, this.profileFile || undefined).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/employees']);
          this.cdr.markForCheck();
        },
        error: (err) => {
          this.isLoading = false;
          console.error('Save failed', err);
          alert('Save failed');
          this.cdr.markForCheck();
        }
      });
    }
  }
}
