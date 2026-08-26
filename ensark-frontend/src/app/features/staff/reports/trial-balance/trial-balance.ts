import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportService } from '../../../../core/services/report.service';
import { ReportPdfService } from '../../../../core/services/report-pdf.service';
import { TrialBalanceResponse } from '../../../../core/models/report.models';
import { Badge } from '../../../../shared/components/badge/badge';
import { LucideScrollText, LucideDownload } from '../../../../shared/icons';

@Component({
  selector: 'app-trial-balance',
  standalone: true,
  imports: [CommonModule, FormsModule, Badge, LucideScrollText, LucideDownload],
  templateUrl: './trial-balance.html',
  styleUrl: './trial-balance.css'
})
export class TrialBalanceComponent implements OnInit {
  private reportService = inject(ReportService);
  private reportPdfService = inject(ReportPdfService);
  private cdr = inject(ChangeDetectorRef);

  report?: TrialBalanceResponse;
  isLoading = true;
  errorMessage = '';
  today = new Date();

  ngOnInit() {
    this.loadTrialBalance();
  }

  loadTrialBalance() {
    this.isLoading = true;
    this.errorMessage = '';
    this.reportService.getTrialBalance().subscribe({
      next: (data) => {
        this.report = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching trial balance', err);
        this.errorMessage = err?.error?.message || err?.message || 'Failed to load trial balance. Please try again.';
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  exportToPdf() {
    if (this.report) this.reportPdfService.generateTrialBalancePdf(this.report);
  }
}
