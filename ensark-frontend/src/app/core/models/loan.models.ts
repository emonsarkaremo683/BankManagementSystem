import { LoanStatus, RepaymentStatus } from './enums';

export interface GuarantorRequest {
  name: string;
  phone: string;
  address: string;
  nidNumber: string;
  relation: string;
}

export interface LoanApplicationRequest {
  accountId: number;
  principalAmount: number;
  annualInterestRate: number;
  tenureMonths: number;
  guarantor: GuarantorRequest;
}

export interface GuarantorResponse {
  id: number;
  name: string;
  phone: string;
  address: string;
  nidNumber: string;
  relation: string;
  photoPath: string;
}

export interface DocumentResponse {
  id: number;
  fileName: string;
  originalFileName: string;
  contentType: string;
  fileSize: number;
}

export interface LoanApplicationResponse {
  loanId: number;
  accountId: number;
  accountNumber: string;
  principalAmount: number;
  annualInterestRate: number;
  tenureMonths: number;
  emiAmount: number;
  totalPayable: number;
  outstandingBalance: number;
  disbursementCharge: number;
  status: LoanStatus;
  applicationDate: string;
  approvalDate: string;
  disbursementDate: string;
  nextDueDate: string;
  rejectionReason: string;
  disbursementTransactionRef: string;
  guarantors: GuarantorResponse[];
  documents: DocumentResponse[];
}

export interface LoanRepaymentResponse {
  id: number;
  loanId: number;
  installmentNumber: number;
  dueDate: string;
  principalComponent: number;
  interestComponent: number;
  emiAmount: number;
  remainingBalanceAfter: number;
  status: RepaymentStatus;
  paidDate: string;
  transactionRef: string;
}

export interface LoanScheduleResponse {
  repaymentId: number;
  installmentNumber: number;
  dueDate: string;
  principalComponent: number;
  interestComponent: number;
  emiAmount: number;
  remainingBalanceAfter: number;
  status: RepaymentStatus;
  paidDate: string;
  transactionRef: string;
}
