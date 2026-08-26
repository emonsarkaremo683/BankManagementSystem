import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JournalService } from '../../../../core/services/journal.service';
import { AuthService } from '../../../../core/services/auth.service';
import { JournalEntry } from '../../../../core/models/dashboard.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideDownload, LucideArrowLeftRight, LucideArrowUpRight, LucideArrowDownRight } from '../../../../shared/icons';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  imports: [CommonModule, StatCard, Badge, LucideDownload, LucideArrowLeftRight, LucideArrowUpRight, LucideArrowDownRight],
  templateUrl: './transaction-history.html',
  styleUrl: './transaction-history.css'
})
export class TransactionHistoryComponent implements OnInit {
  transactions: JournalEntry[] = [];
  isLoading = true;

  private journalService = inject(JournalService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.isLoading = true;
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.journalService.getJournalByCustomerEmail(user.email).subscribe({
        next: (data) => {
          this.transactions = data;
          this.isLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error loading transactions', err);
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      });
    }
  }

  statusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'SUCCESS': return 'success';
      case 'PENDING': return 'warning';
      case 'FAILED': case 'CANCELLED': return 'danger';
      case 'REVERSED': return 'purple';
      default: return 'neutral';
    }
  }

  entryTypeColor(entryType: string | undefined): BadgeColor {
    return entryType === 'CREDIT' ? 'success' : 'danger';
  }

  get moneyIn(): number {
    return this.transactions.filter(t => t.entryType === 'CREDIT').reduce((s, t) => s + (t.amount || 0), 0);
  }

  get moneyOut(): number {
    return this.transactions.filter(t => t.entryType === 'DEBIT').reduce((s, t) => s + (t.amount || 0), 0);
  }

  exportToPdf(): void {
    const doc = new jsPDF('landscape', 'mm', 'a4');
    const pageWidth = doc.internal.pageSize.getWidth();
    const user = this.authService.currentUserValue;

    doc.setFont('helvetica', 'bold');
    doc.setFontSize(18);
    doc.setTextColor(79, 70, 229);
    doc.text('ENSARK BANK', 14, 15);

    doc.setFontSize(12);
    doc.setTextColor(100, 116, 139);
    doc.text('Personal Transaction History', 14, 22);

    doc.setFontSize(9);
    doc.text(`Account Holder: ${user?.name || user?.email || 'N/A'}`, 14, 28);
    doc.text(`Generated: ${new Date().toLocaleString()}`, pageWidth - 14, 28, { align: 'right' });

    doc.setDrawColor(226, 232, 240);
    doc.line(14, 32, pageWidth - 14, 32);

    const tableData = this.transactions.map(t => [
      t.transactionId || '-',
      t.transactionType || '-',
      t.counterpartyName || t.counterpartyAccountNumber || '-',
      t.entryType === 'CREDIT' ? '+' : '-',
      `${t.amount?.toFixed(2) || '0.00'}`,
      t.status || '-',
      t.date ? new Date(t.date).toLocaleDateString() : '-'
    ]);

    autoTable(doc, {
      startY: 36,
      head: [['Reference', 'Type', 'From / To', 'Sign', 'Amount', 'Status', 'Date']],
      body: tableData,
      theme: 'grid',
      headStyles: {
        fillColor: [79, 70, 229],
        textColor: [255, 255, 255],
        fontSize: 8,
        fontStyle: 'bold'
      },
      bodyStyles: {
        fontSize: 7,
        textColor: [30, 41, 59]
      },
      alternateRowStyles: {
        fillColor: [241, 245, 249]
      },
      margin: { left: 14, right: 14 },
      didDrawPage: (data) => {
        const footerY = doc.internal.pageSize.getHeight() - 10;
        doc.setFontSize(7);
        doc.setTextColor(148, 163, 184);
        doc.text('Ensark Bank - Confidential', 14, footerY);
        doc.text(`Page ${data.pageNumber}`, pageWidth - 14, footerY, { align: 'right' });
      }
    });

    doc.save(`my-transaction-history-${new Date().toISOString().split('T')[0]}.pdf`);
  }
}
