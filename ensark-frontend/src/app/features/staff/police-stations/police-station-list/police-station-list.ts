import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AddressService } from '../../../../core/services/address.service';
import { PoliceStation } from '../../../../core/models/address.models';
import {
  LucideMapPin,
  LucideSearch,
  LucidePlus,
  LucidePencil,
  LucideTrash2,
} from '../../../../shared/icons';

@Component({
  selector: 'app-police-station-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, LucideMapPin, LucideSearch, LucidePlus, LucidePencil, LucideTrash2],
  templateUrl: './police-station-list.html',
  styleUrl: './police-station-list.css'
})
export class PoliceStationListComponent implements OnInit {
  policeStations: PoliceStation[] = [];
  filteredPoliceStations: PoliceStation[] = [];
  isLoading = true;
  searchTerm = '';

  private addressService = inject(AddressService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadPoliceStations();
  }

  loadPoliceStations(): void {
    this.isLoading = true;
    this.addressService.getAllPoliceStations().subscribe({
      next: (data) => {
        this.policeStations = data;
        this.filteredPoliceStations = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching police stations', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredPoliceStations = this.policeStations;
      return;
    }
    this.filteredPoliceStations = this.policeStations.filter(ps =>
      ps.name.toLowerCase().includes(term) ||
      (ps.district?.name || '').toLowerCase().includes(term)
    );
  }

  delete(policeStation: PoliceStation): void {
    if (!confirm(`Delete police station "${policeStation.name}"? This cannot be undone.`)) return;
    this.addressService.deletePoliceStation(policeStation.id).subscribe({
      next: () => {
        this.policeStations = this.policeStations.filter(ps => ps.id !== policeStation.id);
        this.onSearch();
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error deleting police station', err);
        alert('Failed to delete police station. It may be linked to branches.');
      }
    });
  }
}
