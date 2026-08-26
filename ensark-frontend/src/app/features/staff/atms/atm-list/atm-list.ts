import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AtmService } from '../../../../core/services/atm.service';
import { ATMResponse } from '../../../../core/models/atm.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucidePlus,
  LucideSearch,
  LucideFunnel,
  LucideLandmark,
  LucideCircleCheck,
  LucideTriangleAlert,
  LucideCircleX,
  LucideMapPin,
  LucideRefreshCw
} from '../../../../shared/icons';

@Component({
  selector: 'app-atm-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    StatCard,
    Badge,
    LucidePlus,
    LucideSearch,
    LucideFunnel,
    LucideLandmark,
    LucideCircleCheck,
    LucideTriangleAlert,
    LucideCircleX,
    LucideMapPin,
    LucideRefreshCw
  ],
  templateUrl: './atm-list.html',
  styleUrl: './atm-list.css'
})
export class AtmListComponent implements OnInit {
  private atmService = inject(AtmService);
  private cdr  = inject(ChangeDetectorRef);
  atms: ATMResponse[] = [];
  isLoading = true;
  searchQuery = '';
  selectedStatus = 'ALL';

  ngOnInit() {
    this.fetchAtms();
  }

  fetchAtms() {
    this.isLoading = true;
    this.atmService.getAll().subscribe({
      next: (data) => {
        this.atms = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  get filteredAtms(): ATMResponse[] {
    return this.atms.filter(atm => {
      const matchesSearch = !this.searchQuery ||
        atm.address.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        (atm.branchName && atm.branchName.toLowerCase().includes(this.searchQuery.toLowerCase())) ||
        atm.atmId.toString().includes(this.searchQuery);

      const matchesStatus = this.selectedStatus === 'ALL' || atm.status === this.selectedStatus;
      return matchesSearch && matchesStatus;
    });
  }

  get activeCount(): number {
    return this.atms.filter(a => a.status === 'ACTIVE').length;
  }

  get maintenanceOfflineCount(): number {
    return this.atms.filter(a => a.status === 'MAINTENANCE' || a.status === 'OFFLINE').length;
  }

  get outOfServiceCount(): number {
    return this.atms.filter(a => a.status === 'OUT_OF_SERVICE').length;
  }

  cashLevelPercent(atm: ATMResponse): number {
    return atm.limit ? (atm.availableBalance / atm.limit) * 100 : 0;
  }

  statusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'MAINTENANCE': return 'warning';
      case 'OFFLINE': case 'OUT_OF_SERVICE': return 'danger';
      default: return 'neutral';
    }
  }
}
