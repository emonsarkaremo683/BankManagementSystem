import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { AtmService } from '../../../../core/services/atm.service';
import { BranchService } from '../../../../core/services/branch.service';
import { BranchResponse } from '../../../../core/models/branch.models';
import { ATMRequest, ATMResponse } from '../../../../core/models/atm.models';
import { ATMStatus } from '../../../../core/models/enums';
import { LucideArrowLeft, LucideLandmark } from '../../../../shared/icons';

@Component({
  selector: 'app-atm-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, LucideArrowLeft, LucideLandmark],
  templateUrl: './atm-form.html',
  styleUrl: './atm-form.css'
})
export class AtmFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private atmService = inject(AtmService);
  private branchService = inject(BranchService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form!: FormGroup;
  isEditMode = false;
  atmId?: number;
  isSubmitting = false;
  branches: BranchResponse[] = [];
  atm?: ATMResponse;

  ngOnInit() {
    this.form = this.fb.group({
      address: ['', Validators.required],
      branchId: [null],
      limit: [500000, [Validators.required, Validators.min(10000)]],
      balance: [0, [Validators.required, Validators.min(0)]],
      status: [ATMStatus.ACTIVE, Validators.required]
    });

    this.branchService.getAll().subscribe(b => this.branches = b);

    const idParam = this.route.snapshot.params['id'];
    if (idParam) {
      this.isEditMode = true;
      this.atmId = +idParam;
      // In edit mode branchId cannot be honestly pre-selected — ATMResponse only
      // exposes branchName, not a numeric id — so it stays optional/null and the
      // dropdown offers a "keep current branch" option instead.
      this.form.get('branchId')?.clearValidators();
      this.form.get('branchId')?.updateValueAndValidity();

      this.atmService.getById(this.atmId).subscribe(atm => {
        if (atm) {
          this.atm = atm;
          this.form.patchValue({
            address: atm.address,
            branchId: null,
            limit: atm.limit,
            balance: atm.availableBalance,
            status: atm.status
          });
        }
      });
    } else {
      this.form.get('branchId')?.setValidators(Validators.required);
      this.form.get('branchId')?.updateValueAndValidity();
    }
  }

  onSubmit() {
    if (this.form.invalid) return;
    this.isSubmitting = true;
    const raw = this.form.value;

    const req: ATMRequest = {
      address: raw.address,
      limit: raw.limit,
      balance: raw.balance,
      status: raw.status,
      // branchId is only included if the user actually picked one — leaving it
      // undefined on edit matches ATMServiceImpl.updateATM's "no change" behavior.
      ...(raw.branchId !== null && raw.branchId !== undefined ? { branchId: raw.branchId } : {})
    } as ATMRequest;

    const action$ = this.isEditMode && this.atmId
      ? this.atmService.update(this.atmId, req)
      : this.atmService.create(req);

    action$.subscribe({
      next: () => {
        this.router.navigate(['/staff/atms']);
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }
}
