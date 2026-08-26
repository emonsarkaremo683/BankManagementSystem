import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AddressService } from '../../../../core/services/address.service';
import { Division } from '../../../../core/models/address.models';
import {
  LucideMapPin,
  LucideSearch,
  LucidePlus,
  LucidePencil,
  LucideTrash2,
} from '../../../../shared/icons';

@Component({
  selector: 'app-division-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, LucideMapPin, LucideSearch, LucidePlus, LucidePencil, LucideTrash2],
  templateUrl: './division-list.html',
  styleUrl: './division-list.css'
})
export class DivisionListComponent implements OnInit {
  divisions: Division[] = [];
  filteredDivisions: Division[] = [];
  isLoading = true;
  searchTerm = '';

  private addressService = inject(AddressService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadDivisions();
  }

  loadDivisions(): void {
    this.isLoading = true;
    this.addressService.getDivisions().subscribe({
      next: (data) => {
        this.divisions = data;
        this.filteredDivisions = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching divisions', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredDivisions = this.divisions;
      return;
    }
    this.filteredDivisions = this.divisions.filter(d =>
      d.name.toLowerCase().includes(term)
    );
  }

  delete(division: Division): void {
    if (!confirm(`Delete division "${division.name}"? This cannot be undone.`)) return;
    this.addressService.deleteDivision(division.id).subscribe({
      next: () => {
        this.divisions = this.divisions.filter(d => d.id !== division.id);
        this.onSearch();
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error deleting division', err);
        alert('Failed to delete division. It may have associated districts.');
      }
    });
  }
}
