import { Component, OnInit, AfterViewInit, OnDestroy, inject, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { timeout, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Chart, registerables } from 'chart.js';
import { DashboardService } from '../../../core/services/dashboard.service';
import { AuthService } from '../../../core/services/auth.service';
import { DashboardResponse, LabelValue, TrendData } from '../../../core/models/dashboard.models';
import { StatCard } from '../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../shared/components/badge/badge';
import {
  LucideUsers,
  LucideWallet,
  LucideLandmark,
  LucideArrowLeftRight,
  LucideHandshake,
  LucideBanknote,
  LucidePiggyBank,
  LucideReceipt,
  LucideCreditCard,
  LucideTrendingUp,
  LucideTrendingDown,
  LucideChartColumn,
  LucideBuilding2,
  LucideShieldAlert,
  LucideTriangleAlert,
  LucideCheck,
  LucidePlus,
} from '../../../shared/icons';

Chart.register(...registerables);

@Component({
  selector: 'app-staff-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    StatCard,
    Badge,
    LucideUsers,
    LucideWallet,
    LucideLandmark,
    LucideArrowLeftRight,
    LucideHandshake,
    LucideBanknote,
    LucidePiggyBank,
    LucideReceipt,
    LucideCreditCard,
    LucideTrendingUp,
    LucideTrendingDown,
    LucideChartColumn,
    LucideBuilding2,
    LucideShieldAlert,
    LucideTriangleAlert,
    LucideCheck,
    LucidePlus,
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class StaffDashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  private dashboardService = inject(DashboardService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  @ViewChild('trendCanvas') trendCanvas?: ElementRef<HTMLCanvasElement>;
  @ViewChild('accountTypeCanvas') accountTypeCanvas?: ElementRef<HTMLCanvasElement>;
  @ViewChild('loanStatusCanvas') loanStatusCanvas?: ElementRef<HTMLCanvasElement>;
  @ViewChild('txnTypeCanvas') txnTypeCanvas?: ElementRef<HTMLCanvasElement>;
  @ViewChild('txnStatusCanvas') txnStatusCanvas?: ElementRef<HTMLCanvasElement>;
  @ViewChild('revenueExpenseCanvas') revenueExpenseCanvas?: ElementRef<HTMLCanvasElement>;

  stats?: DashboardResponse;
  isLoading = true;
  loadError = false;
  today = new Date();

  private charts: Chart[] = [];
  private viewReady = false;

  ngOnInit(): void {
    this.loadStats();
  }

  ngAfterViewInit(): void {
    this.viewReady = true;
    if (this.stats) {
      setTimeout(() => this.buildCharts());
    }
  }

  ngOnDestroy(): void {
    this.destroyCharts();
  }

  get userName(): string {
    const user = this.authService.currentUserValue;
    console.log(user);
    return user?.name ||'there';
  }

  loadStats(): void {
    this.isLoading = true;
    this.loadError = false;
    this.dashboardService.getStaffStats().pipe(
      timeout(10000),
      catchError(() => {
        this.isLoading = false;
        this.loadError = true;
        this.cdr.detectChanges();
        return of(undefined);
      })
    ).subscribe({
      next: (data) => {
        if (data) {
          this.stats = data;
          this.isLoading = false;
          this.cdr.detectChanges();
          if (this.viewReady) {
            setTimeout(() => this.buildCharts());
          }
        }
      },
      error: () => {
        this.isLoading = false;
        this.loadError = true;
        this.cdr.detectChanges();
      }
    });
  }

  retry(): void {
    this.loadStats();
  }

  get netCashFlow(): number {
    return (this.stats?.cashInflow || 0) - (this.stats?.cashOutflow || 0);
  }

  /** app-stat-card infers direction from sign, but TrendData.percentageChange is
   *  always a positive magnitude with a separate `up` boolean — so flip the sign
   *  here when the trend is actually down. */
  trendDelta(trend: TrendData): number {
    return trend.up ? trend.percentageChange : -trend.percentageChange;
  }

  getApprovalStatusColor(status: string): BadgeColor {
    switch (status) {
      case 'APPROVED':
      case 'VERIFIED':
      case 'ACTIVE':
        return 'success';
      case 'PENDING':
      case 'REQUESTED':
      case 'UNDER_REVIEW':
        return 'warning';
      case 'REJECTED':
      case 'FAILED':
        return 'danger';
      default:
        return 'neutral';
    }
  }

  formatCompact(num: number): string {
    if (num >= 1e12) return (num / 1e12).toFixed(1) + 'T';
    if (num >= 1e9) return (num / 1e9).toFixed(1) + 'B';
    if (num >= 1e6) return (num / 1e6).toFixed(1) + 'M';
    if (num >= 1e3) return (num / 1e3).toFixed(1) + 'K';
    return num.toFixed(0);
  }

  private destroyCharts(): void {
    this.charts.forEach(c => c.destroy());
    this.charts = [];
  }

  private buildCharts(): void {
    if (!this.stats) return;
    this.destroyCharts();

    const styles = getComputedStyle(document.documentElement);
    const brand = styles.getPropertyValue('--color-brand-600').trim() || '#4f46e5';
    const success = styles.getPropertyValue('--color-success-600').trim() || '#16a34a';
    const warning = styles.getPropertyValue('--color-warning-600').trim() || '#d97706';
    const danger = styles.getPropertyValue('--color-danger-600').trim() || '#dc2626';
    const purple = styles.getPropertyValue('--color-purple-600').trim() || '#9333ea';
    const info = styles.getPropertyValue('--color-info-600').trim() || '#2563eb';
    const gridColor = '#e5e7eb';
    const textMuted = '#6b7280';
    const palette = [brand, success, warning, purple, info, danger];

    this.buildTrendChart(success, danger, gridColor, textMuted);
    this.buildDistributionChart(this.accountTypeCanvas, this.stats.accountTypeDistribution, palette, textMuted);
    this.buildDistributionChart(this.loanStatusCanvas, this.stats.loanStatusDistribution, palette, textMuted);
    this.buildDistributionChart(this.txnTypeCanvas, this.stats.transactionTypeDistribution, palette, textMuted);
    this.buildDistributionChart(this.txnStatusCanvas, this.stats.transactionStatusDistribution, palette, textMuted);
    this.buildRevenueExpenseChart(success, danger, gridColor, textMuted);
  }

  private buildTrendChart(success: string, danger: string, gridColor: string, textMuted: string): void {
    const trends = this.stats?.transactionTrends;
    if (!this.trendCanvas || !trends || trends.length === 0) return;

    this.charts.push(new Chart(this.trendCanvas.nativeElement, {
      type: 'line',
      data: {
        labels: trends.map(t => t.date),
        datasets: [
          {
            label: 'Inflow',
            data: trends.map(t => t.inflow),
            borderColor: success,
            backgroundColor: success + '1a',
            fill: true,
            tension: 0.35,
            pointRadius: 3,
            pointBackgroundColor: success,
            borderWidth: 2,
          },
          {
            label: 'Outflow',
            data: trends.map(t => t.outflow),
            borderColor: danger,
            backgroundColor: danger + '1a',
            fill: true,
            tension: 0.35,
            pointRadius: 3,
            pointBackgroundColor: danger,
            borderWidth: 2,
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        interaction: { mode: 'index', intersect: false },
        plugins: {
          legend: { position: 'bottom', labels: { usePointStyle: true, boxWidth: 8, color: textMuted, font: { size: 11 } } }
        },
        scales: {
          x: { grid: { display: false }, ticks: { color: textMuted, font: { size: 11 } } },
          y: { grid: { color: gridColor }, border: { display: false }, ticks: { color: textMuted, font: { size: 11 } } }
        }
      }
    }));
  }

  private buildDistributionChart(
    canvasRef: ElementRef<HTMLCanvasElement> | undefined,
    data: LabelValue[] | undefined,
    palette: string[],
    textMuted: string
  ): void {
    if (!canvasRef || !data || data.length === 0) return;
    this.charts.push(new Chart(canvasRef.nativeElement, {
      type: 'doughnut',
      data: {
        labels: data.map(d => d.label),
        datasets: [{
          data: data.map(d => d.value),
          backgroundColor: data.map((_, i) => palette[i % palette.length]),
          borderWidth: 2,
          borderColor: '#ffffff'
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '65%',
        plugins: {
          legend: { position: 'bottom', labels: { usePointStyle: true, boxWidth: 8, color: textMuted, font: { size: 10 } } }
        }
      }
    }));
  }

  private buildRevenueExpenseChart(success: string, danger: string, gridColor: string, textMuted: string): void {
    if (!this.revenueExpenseCanvas || !this.stats) return;
    this.charts.push(new Chart(this.revenueExpenseCanvas.nativeElement, {
      type: 'bar',
      data: {
        labels: ['Revenue', 'Expense'],
        datasets: [{
          data: [this.stats.totalRevenue, this.stats.totalExpense],
          backgroundColor: [success, danger],
          borderRadius: 6,
          maxBarThickness: 64
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          x: { grid: { display: false }, ticks: { color: textMuted, font: { size: 11 } } },
          y: { grid: { color: gridColor }, border: { display: false }, ticks: { color: textMuted, font: { size: 11 } } }
        }
      }
    }));
  }
}
