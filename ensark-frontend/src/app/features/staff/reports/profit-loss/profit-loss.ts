import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ReportService } from '../../../../core/services/report.service';
import { ReportPdfService } from '../../../../core/services/report-pdf.service';
import { ProfitLossResponse } from '../../../../core/models/report.models';
import { LucideChartColumn, LucideDownload, LucideScrollText, LucideLandmark, LucideCircleAlert } from '../../../../shared/icons';

@Component({
  selector: 'app-profit-loss',
  standalone: true,
  imports: [CommonModule, RouterLink, LucideChartColumn, LucideDownload, LucideScrollText, LucideLandmark, LucideCircleAlert],
  templateUrl: './profit-loss.html',
  styleUrl: './profit-loss.css'
})
export class ProfitLossComponent implements OnInit {
  private reportService = inject(ReportService);
  private reportPdfService = inject(ReportPdfService);
  private cdr = inject(ChangeDetectorRef);

  // report.service.ts's getProfitLoss() always emits null today — there is
  // no ProfitAndLoss/income-statement controller anywhere in the backend
  // (report_management only has ledger, trial-balance, and balance-sheet).
  // The interface + rendering path below are kept so this screen can light
  // up if/when a real endpoint is added, but the default UI state must be
  // the honest "not available" empty state, never fabricated numbers.
  report?: ProfitLossResponse | null;
  isLoading = true;

  ngOnInit() {
    this.loadProfitLoss();
  }

  loadProfitLoss() {
    this.isLoading = true;
    this.reportService.getProfitLoss().subscribe({
      next: (data) => {
        this.report = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching profit loss report', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  exportToPdf() {
    if (this.report) this.reportPdfService.generateProfitLossPdf(this.report);
  }
}
