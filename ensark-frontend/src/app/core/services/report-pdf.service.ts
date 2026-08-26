import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import {
  TrialBalanceResponse,
  BalanceSheetResponse,
  LedgerAccountReport,
  ProfitLossResponse
} from '../models/report.models';

const BRAND = { r: 79, g: 70, b: 229 };
const DARK = { r: 15, g: 23, b: 42 };
const MUTED = { r: 100, g: 116, b: 139 };
const LIGHT_BG = { r: 248, g: 250, b: 252 };
const WHITE = { r: 255, g: 255, b: 255 };
const SUCCESS = { r: 22, g: 163, b: 74 };
const DANGER = { r: 220, g: 38, b: 38 };
const GOLD = { r: 180, g: 145, b: 50 };
const HEADER_BG = { r: 241, g: 245, b: 249 };

@Injectable({ providedIn: 'root' })
export class ReportPdfService {

  private printHeader(doc: jsPDF, title: string, subtitle: string, pageWidth: number) {
    doc.setFillColor(15, 23, 42);
    doc.rect(0, 0, pageWidth, 38, 'F');

    doc.setFont('helvetica', 'bold');
    doc.setFontSize(20);
    doc.setTextColor(WHITE.r, WHITE.g, WHITE.b);
    doc.text('ENSARK BANK', 20, 16);

    doc.setFontSize(8);
    doc.setFont('helvetica', 'normal');
    doc.setTextColor(148, 163, 184);
    doc.text('Digital Banking & Financial Services', 20, 22);

    doc.setFillColor(GOLD.r, GOLD.g, GOLD.b);
    doc.rect(0, 38, pageWidth, 1.5, 'F');

    doc.setFont('helvetica', 'bold');
    doc.setFontSize(16);
    doc.setTextColor(DARK.r, DARK.g, DARK.b);
    doc.text(title, 20, 52);

    doc.setFont('helvetica', 'normal');
    doc.setFontSize(9);
    doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
    doc.text(subtitle, 20, 59);

    return 66;
  }

  private printMetaRow(doc: jsPDF, y: number, pageWidth: number, meta: { label: string; value: string }[]) {
    const colW = (pageWidth - 40) / meta.length;
    meta.forEach((m, i) => {
      const x = 20 + i * colW;
      doc.setFont('helvetica', 'normal');
      doc.setFontSize(7);
      doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
      doc.text(m.label.toUpperCase(), x, y);
      doc.setFont('helvetica', 'bold');
      doc.setFontSize(8);
      doc.setTextColor(DARK.r, DARK.g, DARK.b);
      doc.text(m.value, x, y + 5);
    });
    return y + 12;
  }

  private printFooter(doc: jsPDF, pageNum: number, pageCount: number, pageWidth: number, pageHeight: number) {
    doc.setFillColor(GOLD.r, GOLD.g, GOLD.b);
    doc.rect(0, pageHeight - 16, pageWidth, 0.4, 'F');

    doc.setFontSize(7);
    doc.setFont('helvetica', 'normal');
    doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
    doc.text('Ensark Bank \u2014 Confidential', 20, pageHeight - 10);
    doc.text(`Page ${pageNum} of ${pageCount}`, pageWidth - 20, pageHeight - 10, { align: 'right' });

    const now = new Date();
    const dateStr = now.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
    const timeStr = now.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
    doc.text(`Generated: ${dateStr} at ${timeStr}`, pageWidth / 2, pageHeight - 10, { align: 'center' });
  }

  private formatCurrency(val: number): string {
    if (val === 0 || val === null || val === undefined) return '-';
    return val.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  }

  private sectionDivider(doc: jsPDF, y: number, pageWidth: number, label: string): number {
    doc.setFillColor(HEADER_BG.r, HEADER_BG.g, HEADER_BG.b);
    doc.roundedRect(20, y - 3, pageWidth - 40, 8, 1, 1, 'F');
    doc.setFont('helvetica', 'bold');
    doc.setFontSize(8);
    doc.setTextColor(BRAND.r, BRAND.g, BRAND.b);
    doc.text(label, 24, y + 2);
    return y + 10;
  }

  generateTrialBalancePdf(data: TrialBalanceResponse): void {
    const doc = new jsPDF('landscape', 'mm', 'a4');
    const pageWidth = doc.internal.pageSize.getWidth();
    const pageHeight = doc.internal.pageSize.getHeight();

    let y = this.printHeader(doc, 'Trial Balance Statement', 'General ledger account balances: debit vs credit reconciliation', pageWidth);

    const meta = [
      { label: 'Report Date', value: new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' }) },
      { label: 'Branch', value: data.branchName || 'All Branches' },
      { label: 'Total Accounts', value: String(data.lines?.length || 0) },
      { label: 'Status', value: data.totalDebit === data.totalCredit ? 'Reconciled' : 'Out of Balance' }
    ];
    y = this.printMetaRow(doc, y, pageWidth, meta);

    doc.setDrawColor(GOLD.r, GOLD.g, GOLD.b);
    doc.setLineWidth(0.4);
    doc.line(20, y, pageWidth - 20, y);
    y += 4;

    const bodyRows = (data.lines || []).map((line, idx) => [
      String(idx + 1),
      line.glCode || '-',
      line.accountNumber || '-',
      line.accountName || '-',
      line.debit > 0 ? this.formatCurrency(line.debit) : '-',
      line.credit > 0 ? this.formatCurrency(line.credit) : '-'
    ]);

    bodyRows.push([
      '', '', '',
      'TOTAL',
      this.formatCurrency(data.totalDebit),
      this.formatCurrency(data.totalCredit)
    ]);

    autoTable(doc, {
      startY: y,
      head: [['#', 'GL Code', 'Account No', 'Account Title', 'Debit (BDT)', 'Credit (BDT)']],
      body: bodyRows,
      theme: 'grid',
      styles: {
        fontSize: 7.5,
        cellPadding: 3,
        textColor: DARK.r,
        lineColor: [226, 232, 240],
        lineWidth: 0.2
      },
      headStyles: {
        fillColor: BRAND.r,
        textColor: WHITE.r,
        fontStyle: 'bold',
        fontSize: 7.5,
        cellPadding: 3
      },
      alternateRowStyles: {
        fillColor: LIGHT_BG.r
      },
      columnStyles: {
        0: { cellWidth: 12, halign: 'center' },
        1: { cellWidth: 28, font: 'helvetica', fontStyle: 'normal' },
        2: { cellWidth: 32, font: 'courier', fontSize: 7 },
        3: { cellWidth: 'auto' },
        4: { cellWidth: 38, halign: 'right', font: 'courier', fontSize: 7.5 },
        5: { cellWidth: 38, halign: 'right', font: 'courier', fontSize: 7.5 }
      },
      margin: { left: 20, right: 20 },
      didParseCell: (hookData) => {
        if (hookData.row.index === bodyRows.length - 1) {
          hookData.cell.styles.fontStyle = 'bold';
          hookData.cell.styles.fillColor = [241, 245, 249];
          hookData.cell.styles.textColor = BRAND.r;
        }
      },
      didDrawPage: (data) => {
        this.printFooter(doc, data.pageNumber, doc.getNumberOfPages(), pageWidth, pageHeight);
      }
    });

    const filename = `trial-balance-${data.branchName ? data.branchName.replace(/\s+/g, '-').toLowerCase() + '-' : ''}${new Date().toISOString().split('T')[0]}.pdf`;
    doc.save(filename);
  }

  generateBalanceSheetPdf(data: BalanceSheetResponse): void {
    const doc = new jsPDF('portrait', 'mm', 'a4');
    const pageWidth = doc.internal.pageSize.getWidth();
    const pageHeight = doc.internal.pageSize.getHeight();

    let y = this.printHeader(doc, 'Balance Sheet', 'Statement of Financial Position: Assets = Liabilities + Equity', pageWidth);

    const meta = [
      { label: 'Report Date', value: new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' }) },
      { label: 'Branch', value: data.branchName || 'All Branches' }
    ];
    y = this.printMetaRow(doc, y, pageWidth, meta);

    doc.setDrawColor(GOLD.r, GOLD.g, GOLD.b);
    doc.setLineWidth(0.4);
    doc.line(20, y, pageWidth - 20, y);
    y += 6;

    const halfW = (pageWidth - 44) / 2;

    const drawSection = (
      sectionTitle: string,
      sectionCode: string,
      lines: { glCode: string; accountName: string; amount: number }[],
      total: number,
      x: number,
      startY: number,
      accentColor: { r: number; g: number; b: number }
    ): number => {
      doc.setFillColor(accentColor.r, accentColor.g, accentColor.b);
      doc.roundedRect(x, startY, halfW, 7, 1, 1, 'F');
      doc.setFont('helvetica', 'bold');
      doc.setFontSize(8);
      doc.setTextColor(WHITE.r, WHITE.g, WHITE.b);
      doc.text(sectionTitle, x + 3, startY + 5);
      doc.setFont('helvetica', 'normal');
      doc.setFontSize(6);
      doc.setTextColor(220, 220, 220);
      doc.text(sectionCode, x + halfW - 3, startY + 5, { align: 'right' });

      let sy = startY + 11;
      doc.setFont('helvetica', 'normal');
      doc.setFontSize(7.5);

      if (!lines || lines.length === 0) {
        doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
        doc.text('No accounts', x + 3, sy);
        sy += 6;
      } else {
        for (const line of lines) {
          if (sy > pageHeight - 30) {
            doc.addPage();
            sy = 30;
          }
          doc.setTextColor(DARK.r, DARK.g, DARK.b);
          doc.text(line.accountName, x + 3, sy);
          doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
          doc.setFontSize(6);
          if (line.glCode) doc.text(`(${line.glCode})`, x + 3 + doc.getTextWidth(line.accountName) + 2, sy);
          doc.setFont('courier');
          doc.setFontSize(7.5);
          doc.setTextColor(DARK.r, DARK.g, DARK.b);
          doc.text(this.formatCurrency(line.amount), x + halfW - 3, sy, { align: 'right' });
          doc.setFont('helvetica');
          doc.setFontSize(7.5);
          sy += 6;

          doc.setDrawColor(226, 232, 240);
          doc.setLineWidth(0.15);
          doc.line(x + 3, sy - 3, x + halfW - 3, sy - 3);
        }
      }

      doc.setFillColor(accentColor.r, accentColor.g, accentColor.b);
      doc.setGState(new (doc as any).GState({ opacity: 0.12 }));
      doc.roundedRect(x, sy - 1, halfW, 7, 1, 1, 'F');
      doc.setGState(new (doc as any).GState({ opacity: 1 }));
      doc.setFont('helvetica', 'bold');
      doc.setFontSize(7.5);
      doc.setTextColor(accentColor.r, accentColor.g, accentColor.b);
      doc.text('Subtotal', x + 3, sy + 4);
      doc.setFont('courier');
      doc.text(this.formatCurrency(total), x + halfW - 3, sy + 4, { align: 'right' });

      return sy + 14;
    };

    let leftY = y;
    let rightY = y;

    leftY = drawSection(
      'I. Assets', 'ENSR-BS-01',
      data.assets?.lines || [], data.totalAssets,
      20, leftY, SUCCESS
    );

    rightY = drawSection(
      'II. Liabilities', 'ENSR-BS-02',
      data.liabilities?.lines || [], data.liabilities?.total || 0,
      20 + halfW + 4, rightY, DANGER
    );

    rightY = drawSection(
      'III. Equity', 'ENSR-BS-03',
      data.equity?.lines || [], data.equity?.total || 0,
      20 + halfW + 4, rightY, BRAND
    );

    const bottomY = Math.max(leftY, rightY) + 4;
    if (bottomY > pageHeight - 30) {
      doc.addPage();
    }

    const summaryY = Math.max(leftY, rightY) + 6;

    doc.setFillColor(SUCCESS.r, SUCCESS.g, SUCCESS.b);
    doc.roundedRect(20, summaryY, halfW, 10, 1.5, 1.5, 'F');
    doc.setFont('helvetica', 'bold');
    doc.setFontSize(9);
    doc.setTextColor(WHITE.r, WHITE.g, WHITE.b);
    doc.text('TOTAL ASSETS', 24, summaryY + 6.5);
    doc.setFont('courier');
    doc.text(this.formatCurrency(data.totalAssets), 20 + halfW - 4, summaryY + 6.5, { align: 'right' });

    doc.setFillColor(DANGER.r, DANGER.g, DANGER.b);
    doc.roundedRect(20 + halfW + 4, summaryY, halfW, 10, 1.5, 1.5, 'F');
    doc.setFont('helvetica', 'bold');
    doc.setFontSize(9);
    doc.setTextColor(WHITE.r, WHITE.g, WHITE.b);
    doc.text('TOTAL LIABILITIES & EQUITY', 24 + halfW + 4, summaryY + 6.5);
    doc.setFont('courier');
    doc.text(this.formatCurrency(data.totalLiabilitiesAndEquity), 20 + halfW * 2, summaryY + 6.5, { align: 'right' });

    this.printFooter(doc, 1, doc.getNumberOfPages(), pageWidth, pageHeight);

    const filename = `balance-sheet-${data.branchName ? data.branchName.replace(/\s+/g, '-').toLowerCase() + '-' : ''}${new Date().toISOString().split('T')[0]}.pdf`;
    doc.save(filename);
  }

  generateLedgerPdf(data: LedgerAccountReport[]): void {
    const doc = new jsPDF('landscape', 'mm', 'a4');
    const pageWidth = doc.internal.pageSize.getWidth();
    const pageHeight = doc.internal.pageSize.getHeight();

    let y = this.printHeader(doc, 'General Ledger', 'Audit log of all double-entry debit and credit journal postings', pageWidth);

    const totalEntries = data.reduce((sum, a) => sum + (a.entries?.length || 0), 0);
    const meta = [
      { label: 'Report Date', value: new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' }) },
      { label: 'Accounts', value: String(data.length) },
      { label: 'Total Entries', value: String(totalEntries) },
      { label: 'Branch', value: data[0]?.branchName || 'All Branches' }
    ];
    y = this.printMetaRow(doc, y, pageWidth, meta);

    doc.setDrawColor(GOLD.r, GOLD.g, GOLD.b);
    doc.setLineWidth(0.4);
    doc.line(20, y, pageWidth - 20, y);
    y += 4;

    for (const account of data) {
      if (y > pageHeight - 40) {
        doc.addPage();
        y = 30;
      }

      doc.setFillColor(BRAND.r, BRAND.g, BRAND.b);
      doc.roundedRect(20, y, pageWidth - 40, 7, 1, 1, 'F');
      doc.setFont('helvetica', 'bold');
      doc.setFontSize(8);
      doc.setTextColor(WHITE.r, WHITE.g, WHITE.b);
      doc.text(`Account: ${account.accountNumber}`, 24, y + 5);
      doc.setFont('helvetica', 'normal');
      doc.setFontSize(7);
      doc.text(`Opening: ${this.formatCurrency(account.openingBalance)}  |  Closing: ${this.formatCurrency(account.closingBalance)}`, pageWidth - 24, y + 5, { align: 'right' });
      y += 10;

      if (!account.entries || account.entries.length === 0) {
        doc.setFont('helvetica', 'italic');
        doc.setFontSize(7);
        doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
        doc.text('No entries', 24, y + 3);
        y += 8;
        continue;
      }

      const bodyRows = account.entries.map((e, idx) => [
        e.date ? new Date(e.date).toLocaleDateString() : '-',
        e.transactionId || '-',
        e.accountName || account.accountNumber,
        e.particulars || '-',
        e.debit > 0 ? this.formatCurrency(e.debit) : '-',
        e.credit > 0 ? this.formatCurrency(e.credit) : '-',
        this.formatCurrency(e.balance)
      ]);

      autoTable(doc, {
        startY: y,
        head: [['Date', 'Ref #', 'Account', 'Particulars', 'Debit (BDT)', 'Credit (BDT)', 'Balance (BDT)']],
        body: bodyRows,
        theme: 'grid',
        styles: {
          fontSize: 7,
          cellPadding: 2.5,
          textColor: DARK.r,
          lineColor: [226, 232, 240],
          lineWidth: 0.15
        },
        headStyles: {
          fillColor: BRAND.r,
          textColor: WHITE.r,
          fontStyle: 'bold',
          fontSize: 7,
          cellPadding: 2.5
        },
        alternateRowStyles: {
          fillColor: LIGHT_BG.r
        },
        columnStyles: {
          0: { cellWidth: 24 },
          1: { cellWidth: 28, font: 'courier', fontSize: 6.5 },
          2: { cellWidth: 42 },
          3: { cellWidth: 'auto' },
          4: { cellWidth: 32, halign: 'right', font: 'courier', fontSize: 7 },
          5: { cellWidth: 32, halign: 'right', font: 'courier', fontSize: 7 },
          6: { cellWidth: 32, halign: 'right', font: 'courier', fontSize: 7, fontStyle: 'bold' }
        },
        margin: { left: 20, right: 20 },
        didDrawPage: (hookData) => {
          this.printFooter(doc, hookData.pageNumber, doc.getNumberOfPages(), pageWidth, pageHeight);
        }
      });

      y = (doc as any).lastAutoTable.finalY + 8;
    }

    const filename = `general-ledger-${new Date().toISOString().split('T')[0]}.pdf`;
    doc.save(filename);
  }

  generateProfitLossPdf(data: ProfitLossResponse): void {
    const doc = new jsPDF('portrait', 'mm', 'a4');
    const pageWidth = doc.internal.pageSize.getWidth();
    const pageHeight = doc.internal.pageSize.getHeight();

    let y = this.printHeader(doc, 'Profit & Loss Statement', 'Financial performance summary: Income minus Expenses', pageWidth);

    const meta = [
      { label: 'Report Date', value: new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' }) },
      { label: 'Income Lines', value: String(data.income?.lines?.length || 0) },
      { label: 'Expense Lines', value: String(data.expenses?.lines?.length || 0) }
    ];
    y = this.printMetaRow(doc, y, pageWidth, meta);

    doc.setDrawColor(GOLD.r, GOLD.g, GOLD.b);
    doc.setLineWidth(0.4);
    doc.line(20, y, pageWidth - 20, y);
    y += 6;

    const drawPnlSection = (
      title: string,
      lines: { accountNumber: string; accountName: string; amount: number }[],
      total: number,
      accent: { r: number; g: number; b: number },
      startY: number
    ): number => {
      doc.setFillColor(accent.r, accent.g, accent.b);
      doc.roundedRect(20, startY, pageWidth - 40, 7, 1, 1, 'F');
      doc.setFont('helvetica', 'bold');
      doc.setFontSize(8);
      doc.setTextColor(WHITE.r, WHITE.g, WHITE.b);
      doc.text(title.toUpperCase(), 24, startY + 5);
      let sy = startY + 12;

      if (!lines || lines.length === 0) {
        doc.setFont('helvetica', 'italic');
        doc.setFontSize(7);
        doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
        doc.text('No accounts found', 24, sy);
        return sy + 8;
      }

      doc.setFont('helvetica', 'normal');
      for (const line of lines) {
        if (sy > pageHeight - 30) {
          doc.addPage();
          sy = 30;
        }
        doc.setFontSize(7.5);
        doc.setTextColor(DARK.r, DARK.g, DARK.b);
        doc.text(line.accountName, 24, sy);
        doc.setTextColor(MUTED.r, MUTED.g, MUTED.b);
        doc.setFontSize(6.5);
        if (line.accountNumber) doc.text(`(${line.accountNumber})`, 24 + doc.getTextWidth(line.accountName) + 2, sy);
        doc.setFont('courier');
        doc.setFontSize(7.5);
        doc.setTextColor(DARK.r, DARK.g, DARK.b);
        doc.text(this.formatCurrency(line.amount), pageWidth - 24, sy, { align: 'right' });
        doc.setFont('helvetica');
        sy += 6;

        doc.setDrawColor(226, 232, 240);
        doc.setLineWidth(0.15);
        doc.line(24, sy - 3, pageWidth - 24, sy - 3);
      }

      doc.setFillColor(accent.r, accent.g, accent.b);
      doc.setGState(new (doc as any).GState({ opacity: 0.12 }));
      doc.roundedRect(20, sy - 1, pageWidth - 40, 8, 1, 1, 'F');
      doc.setGState(new (doc as any).GState({ opacity: 1 }));
      doc.setFont('helvetica', 'bold');
      doc.setFontSize(8);
      doc.setTextColor(accent.r, accent.g, accent.b);
      doc.text(`Total ${title}`, 24, sy + 4.5);
      doc.setFont('courier');
      doc.setFontSize(9);
      doc.text(this.formatCurrency(total), pageWidth - 24, sy + 4.5, { align: 'right' });

      return sy + 14;
    };

    y = drawPnlSection('Income', data.income?.lines || [], data.income?.total || 0, SUCCESS, y);
    y = drawPnlSection('Expenses', data.expenses?.lines || [], data.expenses?.total || 0, DANGER, y);

    y += 4;
    if (y > pageHeight - 30) {
      doc.addPage();
      y = 30;
    }

    const profitColor = data.netProfit >= 0 ? SUCCESS : DANGER;
    doc.setFillColor(profitColor.r, profitColor.g, profitColor.b);
    doc.roundedRect(20, y, pageWidth - 40, 12, 2, 2, 'F');
    doc.setFont('helvetica', 'bold');
    doc.setFontSize(11);
    doc.setTextColor(WHITE.r, WHITE.g, WHITE.b);
    doc.text('NET PROFIT', 26, y + 8);
    doc.setFont('courier');
    doc.setFontSize(13);
    doc.text(this.formatCurrency(data.netProfit), pageWidth - 26, y + 8, { align: 'right' });

    this.printFooter(doc, 1, doc.getNumberOfPages(), pageWidth, pageHeight);

    const filename = `profit-loss-${new Date().toISOString().split('T')[0]}.pdf`;
    doc.save(filename);
  }
}
