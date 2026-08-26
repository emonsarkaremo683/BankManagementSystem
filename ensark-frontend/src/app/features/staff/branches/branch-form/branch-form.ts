import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BranchService } from '../../../../core/services/branch.service';
import { AddressService } from '../../../../core/services/address.service';
import { BranchResponse, BranchRequest } from '../../../../core/models/branch.models';
import { PoliceStation } from '../../../../core/models/address.models';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-branch-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft],
  templateUrl: './branch-form.html',
  styleUrl: './branch-form.css'
})
export class BranchFormComponent implements OnInit {
  branchForm: FormGroup;
  isEditMode = false;
  branchId: number | null = null;
  isLoading = false;

  branches: BranchResponse[] = [];
  policeStations: PoliceStation[] = [];

  private fb = inject(FormBuilder);
  private branchService = inject(BranchService);
  private addressService = inject(AddressService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.branchForm = this.fb.group({
      name: ['', Validators.required],
      address: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      type: ['BRANCH', Validators.required],
      status: ['ACTIVE'],
      parentBranchId: [''],
      policeStationId: ['']
    });
  }

  ngOnInit(): void {
    this.loadBranches();
    this.loadPoliceStations();

    this.branchId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.branchId) {
      this.isEditMode = true;
      this.loadBranch();
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

  /** Only AGENT_BANK branches honour parentBranch server-side (BranchServiceImpl
   *  #resolveParentBranch) — hide the field otherwise so the form doesn't imply it does
   *  something it won't for HEAD_OFFICE/BRANCH types. */
  get isAgentBank(): boolean {
    return this.branchForm.get('type')?.value === 'AGENT_BANK';
  }

  loadBranch(): void {
    if (!this.branchId) return;
    this.isLoading = true;
    this.branchService.getById(this.branchId).subscribe({
      next: (branch) => {
        this.branchForm.patchValue({
          name: branch.name,
          address: branch.address,
          email: branch.email,
          phoneNumber: branch.phoneNumber,
          type: branch.type,
          status: branch.status,
          // Note: `parentBranch` is @JsonProperty(Access.WRITE_ONLY) on the backend entity,
          // so it is never present on a loaded branch response even if it was set on create —
          // this field will always come back empty when editing an existing agent-bank branch.
          parentBranchId: branch.parentBranch?.id || '',
          policeStationId: branch.policeStation?.id || ''
        });
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Failed to load branch', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSubmit(): void {
    if (this.branchForm.invalid) {
      this.branchForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const formVal = this.branchForm.value;

    // BranchController takes the raw `Branch` JPA entity, not a flat DTO, so
    // parentBranch/policeStation must be nested { id } objects for Jackson to populate
    // the entity's relation fields — flat parentBranchId/policeStationId keys are silently
    // ignored, which is why branch creation previously never actually linked either.
    const requestData: BranchRequest = {
      name: formVal.name,
      address: formVal.address,
      email: formVal.email,
      phoneNumber: formVal.phoneNumber,
      type: formVal.type,
      status: formVal.status,
      parentBranch: formVal.type === 'AGENT_BANK' && formVal.parentBranchId ? { id: formVal.parentBranchId } : null,
      policeStation: formVal.policeStationId ? { id: formVal.policeStationId } : null
    };

    if (this.isEditMode && this.branchId) {
      this.branchService.update(this.branchId, requestData).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/branches']);
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
      this.branchService.save(requestData).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/branches']);
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
