import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AddressService } from '../../../../core/services/address.service';
import { District } from '../../../../core/models/address.models';
import {
  LucideMapPin,
  LucideSearch,
  LucidePlus,
  LucidePencil,
  LucideTrash2,
} from '../../../../shared/icons';

@Component({
  selector: 'app-district-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, LucideMapPin, LucideSearch, LucidePlus, LucidePencil, LucideTrash2],
  templateUrl: './district-list.html',
  styleUrl: './district-list.css'
})
export class DistrictListComponent implements OnInit {
  districts: District[] = [];
  filteredDistricts: District[] = [];
  isLoading = true;
  searchTerm = '';

  private addressService = inject(AddressService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadDistricts();
  }

  loadDistricts(): void {
    this.isLoading = true;
    this.addressService.getAllDistricts().subscribe({
      next: (data) => {
        this.districts = data;
        this.filteredDistricts = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching districts', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredDistricts = this.districts;
      return;
    }
    this.filteredDistricts = this.districts.filter(d =>
      d.name.toLowerCase().includes(term) ||
      (d.division?.name || '').toLowerCase().includes(term)
    );
  }

  delete(district: District): void {
    if (!confirm(`Delete district "${district.name}"? This cannot be undone.`)) return;
    this.addressService.deleteDistrict(district.id).subscribe({
      next: () => {
        this.districts = this.districts.filter(d => d.id !== district.id);
        this.onSearch();
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error deleting district', err);
        alert('Failed to delete district. It may have associated police stations.');
      }
    });
  }
}
