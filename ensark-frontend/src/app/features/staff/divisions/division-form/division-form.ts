import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AddressService } from '../../../../core/services/address.service';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-division-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft],
  templateUrl: './division-form.html',
  styleUrl: './division-form.css'
})
export class DivisionFormComponent implements OnInit {
  divisionForm: FormGroup;
  isEditMode = false;
  divisionId: number | null = null;
  isLoading = false;

  private fb = inject(FormBuilder);
  private addressService = inject(AddressService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.divisionForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.divisionId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.divisionId) {
      this.isEditMode = true;
      this.loadDivision();
    }
  }

  loadDivision(): void {
    if (!this.divisionId) return;
    this.isLoading = true;
    this.addressService.getDivisionById(this.divisionId).subscribe({
      next: (division) => {
        this.divisionForm.patchValue({ name: division.name });
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Failed to load division', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSubmit(): void {
    if (this.divisionForm.invalid) {
      this.divisionForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const formVal = this.divisionForm.value;

    if (this.isEditMode && this.divisionId) {
      this.addressService.updateDivision(this.divisionId, { name: formVal.name }).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/divisions']);
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
      this.addressService.saveDivision({ name: formVal.name }).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/divisions']);
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
