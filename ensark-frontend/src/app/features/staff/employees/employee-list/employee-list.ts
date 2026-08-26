import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { EmployeeService } from '../../../../core/services/employee.service';
import { EmployeeResponse, DESIGNATION_LABELS } from '../../../../core/models/employee.models';
import { EmployeeStatus } from '../../../../core/models/enums';
import { environment } from '../../../../../environments/environment';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideUsers,
  LucideUserCheck,
  LucideUserCog,
  LucideUserPlus,
  LucideSearch,
  LucidePencil,
} from '../../../../shared/icons';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucideUsers, LucideUserCheck, LucideUserCog, LucideUserPlus, LucideSearch, LucidePencil],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css'
})
export class EmployeeListComponent implements OnInit {
  employees: EmployeeResponse[] = [];
  filteredEmployees: EmployeeResponse[] = [];
  isLoading = true;
  searchTerm = '';
  uploadsUrl = environment.uploadsUrl + 'employee/';
  designationLabels = DESIGNATION_LABELS;

  private employeeService = inject(EmployeeService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.isLoading = true;
    this.employeeService.getAll().subscribe({
      next: (data) => {
        this.employees = data;
        this.filteredEmployees = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching employees', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredEmployees = this.employees;
      return;
    }
    this.filteredEmployees = this.employees.filter(emp =>
      emp.name.toLowerCase().includes(term) ||
      emp.email.toLowerCase().includes(term) ||
      emp.phone.includes(term) ||
      emp.designation.toLowerCase().includes(term)
    );
  }

  toggleStatus(employee: EmployeeResponse): void {
    const newStatus = employee.status === EmployeeStatus.ACTIVE ? EmployeeStatus.INACTIVE : EmployeeStatus.ACTIVE;

    if (confirm(`Are you sure you want to change status to ${newStatus}?`)) {
      this.employeeService.updateStatus(employee.id, newStatus).subscribe({
        next: (updatedEmployee) => {
          const index = this.employees.findIndex(e => e.id === employee.id);
          if (index !== -1) {
            this.employees[index] = updatedEmployee;
          }
          this.onSearch();
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error updating status', err);
          alert('Failed to update status.');
        }
      });
    }
  }

  get activeCount(): number {
    return this.employees.filter(e => e.status === EmployeeStatus.ACTIVE).length;
  }

  get onLeaveCount(): number {
    return this.employees.filter(e => e.status === EmployeeStatus.ON_LEAVE).length;
  }

  get inactiveCount(): number {
    return this.employees.filter(e =>
      e.status === EmployeeStatus.INACTIVE ||
      e.status === EmployeeStatus.SUSPENDED ||
      e.status === EmployeeStatus.TERMINATED
    ).length;
  }

  statusBadgeColor(status: EmployeeStatus): BadgeColor {
    switch (status) {
      case EmployeeStatus.ACTIVE: return 'success';
      case EmployeeStatus.ON_LEAVE: return 'warning';
      case EmployeeStatus.INACTIVE:
      case EmployeeStatus.SUSPENDED:
      case EmployeeStatus.TERMINATED:
        return 'danger';
      default: return 'neutral';
    }
  }

  designationLabel(designation: string): string {
    return (this.designationLabels as Record<string, string>)[designation] || designation;
  }
}
