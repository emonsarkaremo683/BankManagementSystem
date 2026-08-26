import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportService } from '../../../../core/services/report.service';
import { ReportPdfService } from '../../../../core/services/report-pdf.service';
import { LedgerAccountReport, LedgerEntry } from '../../../../core/models/report.models';
import { LucideFileText, LucideSearch, LucideDownload } from '../../../../shared/icons';

@Component({
  selector: 'app-ledger',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideFileText, LucideSearch, LucideDownload],
  templateUrl: './ledger.html',
  styleUrl: './ledger.css'
})
export class LedgerComponent implements OnInit {
  private reportService = inject(ReportService);
  private reportPdfService = inject(ReportPdfService);
  private cdr = inject(ChangeDetectorRef);

  accounts: LedgerAccountReport[] = [];
  entries: LedgerEntry[] = [];
  searchQuery = '';
  isLoading = true;

  ngOnInit() {
    this.loadLedger();
  }

  loadLedger() {
    this.isLoading = true;
    this.reportService.getLedger().subscribe({
      next: (data) => {
        this.accounts = data || [];
        const flat: LedgerEntry[] = [];
        this.accounts.forEach(acc => {
          (acc.entries || []).forEach(e => {
            // Map straight from the real backend fields — transactionId and
            // particulars are genuine per-line data, not fabricated.
            flat.push({
              ...e,
              accountNumber: e.accountNumber || acc.accountNumber,
              accountName: e.accountName || acc.accountNumber,
              debit: e.debit || 0,
              credit: e.credit || 0,
              balance: e.balance ?? acc.closingBalance ?? 0
            });
          });
        });
        this.entries = flat;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading ledger entries', err);
        this.entries = [];
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  get filteredEntries(): LedgerEntry[] {
    return this.entries.filter(e => {
      const accName = e.accountName || '';
      const accNum = e.accountNumber || '';
      const txnId = e.transactionId || '';
      const particulars = e.particulars || '';

      return !this.searchQuery ||
        accName.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        accNum.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        txnId.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        particulars.toLowerCase().includes(this.searchQuery.toLowerCase());
    });
  }

  exportToPdf() {
    this.reportPdfService.generateLedgerPdf(this.accounts);
  }
}
