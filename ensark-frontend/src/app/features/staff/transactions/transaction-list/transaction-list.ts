import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { TransactionService } from '../../../../core/services/transaction.service';
import { AccountTransactionResponse } from '../../../../core/models/transaction.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideDownload, LucideBanknote, LucideSearch, LucideArrowLeftRight, LucideCircleCheck, LucideClock } from '../../../../shared/icons';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-transaction-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucideDownload, LucideBanknote, LucideSearch, LucideArrowLeftRight, LucideCircleCheck, LucideClock],
  templateUrl: './transaction-list.html',
  styleUrl: './transaction-list.css'
})
export class TransactionListComponent implements OnInit {
  transactions: AccountTransactionResponse[] = [];
  filteredTransactions: AccountTransactionResponse[] = [];
  isLoading = true;
  searchTerm = '';

  private transactionService = inject(TransactionService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.isLoading = true;
    this.transactionService.getAll().subscribe({
      next: (data) => {
        this.transactions = data;
        this.filteredTransactions = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching transactions', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredTransactions = this.transactions;
      return;
    }
    this.filteredTransactions = this.transactions.filter(t =>
      t.response?.referenceNo?.toLowerCase().includes(term) ||
      t.senderAccountNumber?.toLowerCase().includes(term) ||
      t.receiverAccountNumber?.toLowerCase().includes(term) ||
      t.senderName?.toLowerCase().includes(term) ||
      t.receiverName?.toLowerCase().includes(term)
    );
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

  get successCount(): number {
    return this.transactions.filter(t => t.response?.status === 'SUCCESS').length;
  }

  get pendingCount(): number {
    return this.transactions.filter(t => t.response?.status === 'PENDING').length;
  }

  get totalVolume(): number {
    return this.transactions.reduce((sum, t) => sum + (t.response?.amount || 0), 0);
  }

  exportToPdf(): void {
    const doc = new jsPDF('landscape', 'mm', 'a4');
    const pageWidth = doc.internal.pageSize.getWidth();

    doc.setFont('helvetica', 'bold');
    doc.setFontSize(18);
    doc.setTextColor(79, 70, 229);
    doc.text('ENSARK BANK', 14, 15);

    doc.setFontSize(12);
    doc.setTextColor(100, 116, 139);
    doc.text('Transaction History Report', 14, 22);

    doc.setFontSize(9);
    doc.text(`Generated: ${new Date().toLocaleString()}`, 14, 28);
    doc.text(`Total Transactions: ${this.filteredTransactions.length}`, pageWidth - 14, 28, { align: 'right' });

    doc.setDrawColor(226, 232, 240);
    doc.line(14, 32, pageWidth - 14, 32);

    const tableData = this.filteredTransactions.map(t => [
      t.response?.referenceNo || '-',
      t.senderAccountNumber || '-',
      t.senderName || '-',
      t.receiverAccountNumber || '-',
      t.receiverName || '-',
      t.response?.transactionType || '-',
      `${t.response?.amount?.toFixed(2) || '0.00'}`,
      t.response?.status || '-',
      t.response?.createdAt ? new Date(t.response.createdAt).toLocaleDateString() : '-'
    ]);

    autoTable(doc, {
      startY: 36,
      head: [['Reference', 'Sender Acc', 'Sender Name', 'Receiver Acc', 'Receiver Name', 'Type', 'Amount', 'Status', 'Date']],
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

    doc.save(`transaction-history-${new Date().toISOString().split('T')[0]}.pdf`);
  }
}
