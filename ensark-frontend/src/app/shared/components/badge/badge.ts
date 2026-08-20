import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

export type BadgeColor = 'success' | 'danger' | 'warning' | 'info' | 'neutral' | 'purple' | 'brand';

/**
 * Rounded status pill. Color is never the only signal — pass `dotted` to
 * render alongside a small colored dot, since color alone isn't accessible
 * to colorblind users; the label text itself is always shown too.
 *
 * Usage: <app-badge label="ACTIVE" color="success"></app-badge>
 */
@Component({
  selector: 'app-badge',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './badge.html',
  styleUrl: './badge.css',
})
export class Badge {
  @Input() label = '';
  @Input() color: BadgeColor = 'neutral';
  @Input() dotted = true;

  get colorClasses(): string {
    const map: Record<BadgeColor, string> = {
      success: 'bg-[var(--color-success-50)] text-[var(--color-success-700)]',
      danger: 'bg-[var(--color-danger-50)] text-[var(--color-danger-700)]',
      warning: 'bg-[var(--color-warning-50)] text-[var(--color-warning-700)]',
      info: 'bg-[var(--color-info-50)] text-[var(--color-info-700)]',
      purple: 'bg-[var(--color-purple-50)] text-[var(--color-purple-600)]',
      brand: 'bg-[var(--color-brand-50)] text-[var(--color-brand-700)]',
      neutral: 'bg-gray-100 text-gray-600',
    };
    return map[this.color];
  }

  get dotClasses(): string {
    const map: Record<BadgeColor, string> = {
      success: 'bg-[var(--color-success-500)]',
      danger: 'bg-[var(--color-danger-500)]',
      warning: 'bg-[var(--color-warning-500)]',
      info: 'bg-[var(--color-info-500)]',
      purple: 'bg-[var(--color-purple-500)]',
      brand: 'bg-[var(--color-brand-500)]',
      neutral: 'bg-gray-400',
    };
    return map[this.color];
  }
}
