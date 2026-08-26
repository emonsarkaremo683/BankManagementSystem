import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AddressService } from '../../../../core/services/address.service';
import { Division, District } from '../../../../core/models/address.models';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-police-station-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, LucideArrowLeft],
  templateUrl: './police-station-form.html',
  styleUrl: './police-station-form.css'
})
export class PoliceStationFormComponent implements OnInit {
  policeStationForm: FormGroup;
  isEditMode = false;
  policeStationId: number | null = null;
  isLoading = false;
  divisions: Division[] = [];
  districts: District[] = [];
  selectedDivisionId: number | null = null;

  private fb = inject(FormBuilder);
  private addressService = inject(AddressService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);

  constructor() {
    this.policeStationForm = this.fb.group({
      name: ['', Validators.required],
      divisionId: ['', Validators.required],
      districtId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadDivisions();
    this.policeStationId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.policeStationId) {
      this.isEditMode = true;
      this.loadPoliceStation();
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

  onDivisionChange(): void {
    const divisionId = this.policeStationForm.get('divisionId')?.value;
    this.selectedDivisionId = divisionId;
    this.policeStationForm.patchValue({ districtId: '' });
    if (divisionId) {
      this.addressService.getDistrictsByDivision(divisionId).subscribe({
        next: (data) => {
          this.districts = data;
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Error loading districts', err)
      });
    } else {
      this.districts = [];
    }
  }

  loadPoliceStation(): void {
    if (!this.policeStationId) return;
    this.isLoading = true;
    this.addressService.getPoliceStationById(this.policeStationId).subscribe({
      next: (ps) => {
        this.policeStationForm.patchValue({
          name: ps.name,
          divisionId: ps.district?.division?.id || '',
          districtId: ps.district?.id || ''
        });
        if (ps.district?.division?.id) {
          this.selectedDivisionId = ps.district.division.id;
          this.addressService.getDistrictsByDivision(ps.district.division.id).subscribe({
            next: (data) => {
              this.districts = data;
              this.policeStationForm.patchValue({ districtId: ps.district?.id || '' });
              this.isLoading = false;
              this.cdr.markForCheck();
            },
            error: (err) => {
              console.error('Error loading districts', err);
              this.isLoading = false;
              this.cdr.markForCheck();
            }
          });
        } else {
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      },
      error: (err) => {
        console.error('Failed to load police station', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSubmit(): void {
    if (this.policeStationForm.invalid) {
      this.policeStationForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const formVal = this.policeStationForm.value;

    const payload = {
      name: formVal.name,
      district: { id: formVal.districtId }
    };

    if (this.isEditMode && this.policeStationId) {
      this.addressService.updatePoliceStation(this.policeStationId, payload).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/police-stations']);
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
      this.addressService.savePoliceStation(payload).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/staff/police-stations']);
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
