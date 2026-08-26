import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { BranchService } from '../../../../core/services/branch.service';
import { BranchResponse } from '../../../../core/models/branch.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideBuilding2,
  LucideBuilding,
  LucideLandmark,
  LucideCircleCheck,
  LucideSearch,
  LucidePlus,
  LucidePencil,
  LucideBan,
} from '../../../../shared/icons';

@Component({
  selector: 'app-branch-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucideBuilding2, LucideBuilding, LucideLandmark, LucideCircleCheck, LucideSearch, LucidePlus, LucidePencil, LucideBan],
  templateUrl: './branch-list.html',
  styleUrl: './branch-list.css'
})
export class BranchListComponent implements OnInit {
  branches: BranchResponse[] = [];
  filteredBranches: BranchResponse[] = [];
  isLoading = true;
  searchTerm = '';

  private branchService = inject(BranchService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadBranches();
  }

  loadBranches(): void {
    this.isLoading = true;
    this.branchService.getAll().subscribe({
      next: (data) => {
        this.branches = data;
        this.filteredBranches = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching branches', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredBranches = this.branches;
      return;
    }
    this.filteredBranches = this.branches.filter(b =>
      b.name.toLowerCase().includes(term) ||
      b.branchCode.toLowerCase().includes(term) ||
      b.email.toLowerCase().includes(term) ||
      b.phoneNumber.includes(term) ||
      b.address.toLowerCase().includes(term)
    );
  }

  deactivate(branch: BranchResponse): void {
    if (branch.status === 'CLOSED') return;
    if (!confirm(`Close branch "${branch.name}"? This cannot be undone from here.`)) return;
    this.branchService.deactivate(branch.id).subscribe({
      next: (updated) => {
        const index = this.branches.findIndex(b => b.id === branch.id);
        if (index !== -1) {
          this.branches[index] = updated;
        }
        this.onSearch();
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error deactivating branch', err);
        alert('Failed to close branch.');
      }
    });
  }

  get activeCount(): number {
    return this.branches.filter(b => b.status === 'ACTIVE').length;
  }

  get closedCount(): number {
    return this.branches.filter(b => b.status === 'CLOSED').length;
  }

  get agentBankCount(): number {
    return this.branches.filter(b => b.type === 'AGENT_BANK').length;
  }

  statusBadgeColor(status: string): BadgeColor {
    return status === 'ACTIVE' ? 'success' : 'neutral';
  }

  typeBadgeColor(type: string): BadgeColor {
    switch (type) {
      case 'HEAD_OFFICE': return 'info';
      case 'BRANCH': return 'purple';
      case 'AGENT_BANK': return 'warning';
      default: return 'neutral';
    }
  }
}
