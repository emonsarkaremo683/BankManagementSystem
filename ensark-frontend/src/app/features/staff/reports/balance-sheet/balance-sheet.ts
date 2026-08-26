import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild, ElementRef, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportService } from '../../../../core/services/report.service';
import { ReportPdfService } from '../../../../core/services/report-pdf.service';
import { BalanceSheetResponse } from '../../../../core/models/report.models';
import { Chart, registerables } from 'chart.js';
import { LucideLandmark, LucideDownload } from '../../../../shared/icons';

Chart.register(...registerables);

@Component({
  selector: 'app-balance-sheet',
  standalone: true,
  imports: [CommonModule, LucideLandmark, LucideDownload],
  templateUrl: './balance-sheet.html',
  styleUrl: './balance-sheet.css'
})
export class BalanceSheetComponent implements OnInit, AfterViewInit, OnDestroy {
  private reportService = inject(ReportService);
  private reportPdfService = inject(ReportPdfService);
  private cdr = inject(ChangeDetectorRef);

  report?: BalanceSheetResponse;
  isLoading = true;
  // Point-in-time getAll() report — the backend response has no asOfDate
  // field, so we honestly label this as "generated now" rather than
  // fabricating a business as-of date.
  today = new Date();

  @ViewChild('chartCanvas') chartCanvas?: ElementRef<HTMLCanvasElement>;
  private chart?: Chart;
  private viewReady = false;

  ngOnInit() {
    this.loadBalanceSheet();
  }

  ngAfterViewInit() {
    this.viewReady = true;
    this.renderChart();
  }

  ngOnDestroy() {
    this.chart?.destroy();
  }

  loadBalanceSheet() {
    this.isLoading = true;
    this.reportService.getBalanceSheet().subscribe({
      next: (data) => {
        this.report = data;
        this.isLoading = false;
        // Force the *ngIf-gated <canvas> to render before we try to grab it.
        this.cdr.detectChanges();
        this.renderChart();
      },
      error: (err) => {
        console.error('Error fetching balance sheet', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  private renderChart() {
    if (!this.viewReady || !this.report || !this.chartCanvas) {
      return;
    }
    this.chart?.destroy();

    const style = getComputedStyle(document.documentElement);
    const brand = style.getPropertyValue('--color-brand-600').trim() || '#4f46e5';
    const danger = style.getPropertyValue('--color-danger-500').trim() || '#ef4444';
    const info = style.getPropertyValue('--color-info-500').trim() || '#3b82f6';

    this.chart = new Chart(this.chartCanvas.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Assets', 'Liabilities', 'Equity'],
        datasets: [{
          data: [this.report.totalAssets, this.report.liabilities.total, this.report.equity.total],
          backgroundColor: [brand, danger, info],
          borderWidth: 0
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '65%',
        plugins: {
          legend: {
            position: 'bottom',
            labels: { color: style.getPropertyValue('--color-text-secondary').trim() || '#4b5563' }
          }
        }
      }
    });
  }

  exportToPdf() {
    if (this.report) this.reportPdfService.generateBalanceSheetPdf(this.report);
  }
}
