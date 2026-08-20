import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideArrowUpRight, LucideArrowDownRight } from '../../icons';

export type StatCardColor = 'brand' | 'success' | 'purple' | 'warning' | 'danger' | 'info';

/**
 * Enterprise stat tile: label + large value + solid-color icon circle +
 * optional up/down delta line. The icon is passed via content projection
 * (`<svg icon lucideWallet></svg>`) rather than an @Input, so this component
 * doesn't need to import every icon that might ever be used in a stat card.
 *
 * Usage:
 *   <app-stat-card label="Total Accounts" value="1,284" color="brand" [deltaValue]="4.2" deltaLabel="vs last month">
 *     <svg icon lucideWallet></svg>
 *   </app-stat-card>
 */
@Component({
  selector: 'app-stat-card',
  standalone: true,
  imports: [CommonModule, LucideArrowUpRight, LucideArrowDownRight],
  templateUrl: './stat-card.html',
  styleUrl: './stat-card.css',
})
export class StatCard {
  @Input() label = '';
  // Nullable because Angular's number/currency/percent pipes type their
  // output as `string | null` (null when the bound value is null/undefined
  // mid-load) — every consumer binds pipe output directly here.
  @Input() value: string | number | null | undefined = '';
  @Input() color: StatCardColor = 'brand';
  @Input() deltaValue?: number;
  @Input() deltaLabel = '';
  @Input() loading = false;

  get deltaDirection(): 'up' | 'down' | null {
    if (this.deltaValue === undefined || this.deltaValue === null) return null;
    return this.deltaValue >= 0 ? 'up' : 'down';
  }

  get colorClasses(): string {
    const map: Record<StatCardColor, string> = {
      brand: 'bg-[var(--color-brand-600)]',
      success: 'bg-[var(--color-success-600)]',
      purple: 'bg-[var(--color-purple-600)]',
      warning: 'bg-[var(--color-warning-600)]',
      danger: 'bg-[var(--color-danger-600)]',
      info: 'bg-[var(--color-info-600)]',
    };
    return map[this.color];
  }
}
