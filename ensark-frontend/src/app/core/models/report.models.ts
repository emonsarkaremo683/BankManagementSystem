// Matches LedgerReportLineResponse.java exactly. The backend line already
// carries a real transactionId + particulars — there's no need to fabricate
// a "JRNL-xxx" reference or a generic "posting entry" description.
export interface LedgerEntry {
  journalId?: number;
  date?: string;
  transactionId?: string;
  particulars?: string;
  accountNumber?: string;
  accountName?: string;
  debit: number;
  credit: number;
  balance: number;
}

// Matches LedgerReportResponse.java exactly.
export interface LedgerAccountReport {
  branchId?: number;
  branchName?: string;
  accountNumber: string;
  openingBalance: number;
  closingBalance: number;
  entries: LedgerEntry[];
}

// Matches TrialBalanceReportLineResponse.java exactly.
export interface TrialBalanceLine {
  glCode: string;
  accountName: string;
  accountNumber: string;
  debit: number;
  credit: number;
}

// Matches TrialBalanceReportResponse.java exactly — note there is no
// asOfDate field on the backend response.
export interface TrialBalanceResponse {
  branchId?: number;
  branchName?: string;
  lines: TrialBalanceLine[];
  totalDebit: number;
  totalCredit: number;
}

// Matches BalanceSheetReportSectionLine.java exactly.
export interface BalanceSheetLine {
  glCode: string;
  accountName: string;
  amount: number;
}

// Matches BalanceSheetReportSection.java exactly.
export interface BalanceSheetSection {
  title: string;
  lines: BalanceSheetLine[];
  total: number;
}

// Matches BalanceSheetReportResponse.java exactly — again, no asOfDate field.
export interface BalanceSheetResponse {
  branchId?: number;
  branchName?: string;
  assets: BalanceSheetSection;
  liabilities: BalanceSheetSection;
  equity: BalanceSheetSection;
  totalAssets: number;
  totalLiabilitiesAndEquity: number;
}

// Matches ProfitAndLossLine.java
export interface ProfitLossLine {
  accountNumber: string;
  accountName: string;
  amount: number;
}

// Matches ProfitAndLossSection.java
export interface ProfitLossSection {
  title: string;
  lines: ProfitLossLine[];
  total: number;
}

// Matches ProfitAndLossReportResponse.java
export interface ProfitLossResponse {
  income: ProfitLossSection;
  expenses: ProfitLossSection;
  netProfit: number;
}

export interface ReportSpanRequest {
  from: string;
  to: string;
}
