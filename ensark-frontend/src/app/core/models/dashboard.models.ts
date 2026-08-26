export interface JournalEntry {
  id: number;
  transactionEntityId: number;
  date: string;
  transactionId: string;
  particulars: string;
  accountNumber: string;
  counterpartyAccountNumber: string;
  counterpartyName: string;
  entryType: string;
  amount: number;
  transactionType: string;
  channel: string;
  status: string;
  remarks: string;
}

export interface CustomerDashboardResponse {
  balance: number;
  totalCredit: number;
  totalDebit: number;
  totalLoan: number;
  totalCard: number;
  totalTransaction: number;
  totalBeneficiary: number;
  totalAccount: number;
  cards: {
    cardId: number;
    cardNumber: string;
    cardHolderName: string;
    cardNetwork: string;
    cardType: string;
    status: string;
    expiryDate: string;
    dailyLimit: number;
    monthlyLimit: number;
    accountNumber: string;
    isInternationalEnabled: boolean;
    isOnlineTransactionEnabled: boolean;
    createdAt: string;
  }[];
  accounts: {
    id: number;
    accountNumber: string;
    accountType: string;
    accountStatus: string;
    availableBalance: number;
    currentBalance: number;
    holdBalance: number;
    branchName: string;
    branchRoutingNumber: string;
  }[];
  last30DaysTransactions: JournalEntry[];
  recentTransactions: JournalEntry[];
}

export interface TrendData {
  percentageChange: number;
  currentCount: number;
  previousCount: number;
  up: boolean;
}

export interface TimeSeriesPoint {
  date: string;
  count: number;
  totalAmount: number;
  inflow: number;
  outflow: number;
}

export interface LabelValue {
  label: string;
  value: number;
  totalAmount: number;
}

export interface BranchSummary {
  branchId: number;
  branchName: string;
  accountCount: number;
  customerCount: number;
  transactionCount: number;
  totalDeposit: number;
  totalWithdraw: number;
  totalBalance: number;
  totalLoan: number;
  loanCount: number;
  totalRevenue: number;
  totalExpense: number;
  totalTransfer: number;
  totalAssets: number;
  totalLiabilities: number;
  cashInflow: number;
  cashOutflow: number;
  employeeCount: number;
}

export interface ApprovalQueueItem {
  id: number;
  type: string;
  customerName: string;
  amount: number;
  status: string;
  link: string;
  branchName: string;
  createdAt: string;
}

export interface DashboardResponse {
  totalAccounts: number;
  totalCustomers: number;
  totalTransactions: number;
  totalLoans: number;
  totalBalance: number;
  totalActiveCards: number;
  totalEmployees: number;

  totalRevenue: number;
  totalExpense: number;
  totalDeposit: number;
  totalWithdraw: number;
  totalTransfer: number;
  totalAssets: number;
  totalLiabilities: number;
  cashInflow: number;
  cashOutflow: number;

  transactionTrends: TimeSeriesPoint[];
  accountTypeDistribution: LabelValue[];
  loanStatusDistribution: LabelValue[];
  transactionTypeDistribution: LabelValue[];
  transactionStatusDistribution: LabelValue[];
  branchWiseSummary: BranchSummary[];

  customersTrend: TrendData;
  accountsTrend: TrendData;
  balanceTrend: TrendData;
  transactionsTrend: TrendData;
  loansTrend: TrendData;
  atmsTrend: TrendData;
  pendingApprovals: ApprovalQueueItem[];
}
