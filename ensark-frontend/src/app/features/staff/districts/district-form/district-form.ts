import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AddressService } from '../../../../core/services/address.service';
import { Division } from '../../../../core/models/address.models';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-district-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft],
  templateUrl: './district-form.html',
  styleUrl: './district-form.css'
})
export class DistrictFormComponent implements OnInit {
  districtForm: FormGroup;
  isEditMode = false;
  districtId: number | null = null;
  isLoading = false;
  divisions: Division[] = [];

  private fb = inject(FormBuilder);
  private addressService = inject(AddressService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.districtForm = this.fb.group({
      name: ['', Validators.required],
      divisionId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadDivisions();
    this.districtId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.districtId) {
      this.isEditMode = true;
      this.loadDistrict();
    }
  }

  loadDivisions(): void {
    this.addressService.getDivisions().subscribe({
      next: (data) => {
        this.divisions = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error loading divisions', err)
    });
  }

  loadDistrict(): void {
    if (!this.districtId) return;
    this.isLoading = true;
    this.addressService.getAllDistricts().subscribe({
      next: (districts) => {
        const district = districts.find(d => d.id === this.districtId);
        if (district) {
          this.districtForm.patchValue({
            name: district.name,
            divisionId: district.division?.id || ''
          });
        }
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Failed to load district', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSubmit(): void {
    if (this.districtForm.invalid) {
      this.districtForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const formVal = this.districtForm.value;

    if (this.isEditMode && this.districtId) {
      this.addressService.updateDistrict(this.districtId, {
        name: formVal.name,
        division: { id: formVal.divisionId }
      }).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/districts']);
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
      this.addressService.saveDistrict({
        name: formVal.name,
        division: { id: formVal.divisionId }
      }).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/districts']);
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
