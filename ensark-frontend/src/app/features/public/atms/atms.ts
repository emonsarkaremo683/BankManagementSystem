import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AtmService } from '../../../core/services/atm.service';
import { AddressService } from '../../../core/services/address.service';
import { ATMResponse } from '../../../core/models/atm.models';
import { PoliceStation } from '../../../core/models/address.models';

@Component({
  selector: 'app-atms',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './atms.html',
  styleUrl: './atms.css'
})
export class AtmsComponent implements OnInit {
  atms: ATMResponse[] = [];
  filteredAtms: ATMResponse[] = [];
  policeStationNames: string[] = [];
  isLoading = true;
  hasError = false;
  searchTerm = '';
  selectedPoliceStation = '';

  private atmService = inject(AtmService);
  private addressService = inject(AddressService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadAtms();
    this.loadPoliceStations();
  }

  loadAtms(): void {
    this.isLoading = true;
    this.hasError = false;
    this.atmService.getAll().subscribe({
      next: (data) => {
        this.atms = data;
        this.filteredAtms = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading ATMs', err);
        this.isLoading = false;
        this.hasError = true;
        this.cdr.markForCheck();
      }
    });
  }

  loadPoliceStations(): void {
    this.addressService.getAllPoliceStations().subscribe({
      next: (data) => {
        this.policeStationNames = data.map(ps => ps.name);
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading police stations', err);
      }
    });
  }

  onSearch(): void {
    this.applyFilters();
  }

  onPoliceStationFilter(): void {
    this.applyFilters();
  }

  private applyFilters(): void {
    let result = this.atms;

    if (this.selectedPoliceStation) {
      result = result.filter(atm => atm.policeStationName === this.selectedPoliceStation);
    }

    const term = this.searchTerm.toLowerCase().trim();
    if (term) {
      result = result.filter(atm =>
        (atm.address && atm.address.toLowerCase().includes(term)) ||
        (atm.branchName && atm.branchName.toLowerCase().includes(term)) ||
        (atm.routingNumber && atm.routingNumber.toLowerCase().includes(term)) ||
        (atm.policeStationName && atm.policeStationName.toLowerCase().includes(term))
      );
    }

    this.filteredAtms = result;
  }
}
