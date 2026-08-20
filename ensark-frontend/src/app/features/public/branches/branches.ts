import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { BranchService } from '../../../core/services/branch.service';
import { AddressService } from '../../../core/services/address.service';
import { BranchResponse } from '../../../core/models/branch.models';
import { PoliceStation } from '../../../core/models/address.models';

@Component({
  selector: 'app-branches',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './branches.html',
  styleUrl: './branches.css'
})
export class BranchesComponent implements OnInit {
  branches: BranchResponse[] = [];
  filteredBranches: BranchResponse[] = [];
  policeStations: PoliceStation[] = [];
  policeStationNames: string[] = [];
  isLoading = true;
  hasError = false;
  searchTerm = '';
  selectedPoliceStation = '';

  private branchService = inject(BranchService);
  private addressService = inject(AddressService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadBranches();
    this.loadPoliceStations();
  }

  loadBranches(): void {
    this.isLoading = true;
    this.hasError = false;
    this.branchService.getAll().subscribe({
      next: (data) => {
        this.branches = data;
        this.filteredBranches = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading branches', err);
        this.isLoading = false;
        this.hasError = true;
        this.cdr.markForCheck();
      }
    });
  }

  loadPoliceStations(): void {
    this.addressService.getAllPoliceStations().subscribe({
      next: (data) => {
        this.policeStations = data;
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
    let result = this.branches;

    if (this.selectedPoliceStation) {
      result = result.filter(b => b.policeStation?.name === this.selectedPoliceStation);
    }

    const term = this.searchTerm.toLowerCase().trim();
    if (term) {
      result = result.filter(b =>
        (b.name && b.name.toLowerCase().includes(term)) ||
        (b.address && b.address.toLowerCase().includes(term)) ||
        (b.branchCode && b.branchCode.toLowerCase().includes(term)) ||
        (b.email && b.email.toLowerCase().includes(term)) ||
        (b.phoneNumber && b.phoneNumber.toLowerCase().includes(term)) ||
        (b.policeStation?.name && b.policeStation.name.toLowerCase().includes(term))
      );
    }

    this.filteredBranches = result;
  }
}
