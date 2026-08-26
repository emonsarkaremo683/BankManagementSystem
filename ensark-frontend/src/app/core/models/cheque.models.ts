import { ChequeBookStatus, ChequeLeafStatus } from './enums';

export interface ChequeBookRequest {
  accountId: number;
  numberOfLeaves: number;
}

export interface ChequeBookResponse {
  chequeBookId: number;
  bookSerialNumber: string;
  numberOfLeaves: number;
  startLeafNumber: number;
  endLeafNumber: number;
  status: ChequeBookStatus;
  accountId: number;
  accountNumber: string;
  applicationDate: string;
  approvalDate: string;
  deliveryDate: string;
  activationDate: string;
  expiryDate: string;
  rejectionReason: string;
  leaves: ChequeLeafResponse[];
}

export interface ChequeLeafResponse {
  leafId: number;
  leafNumber: number;
  chequeNumber: string;
  amount: number;
  payeeName: string;
  remarks: string;
  status: ChequeLeafStatus;
  issueDate: string;
  clearanceDate: string;
  expiryDate: string;
  bounceReason: string;
  transactionReference: string;
  chequeBookId: number;
  bookSerialNumber: string;
}

/**
 * Raw JPA entity returned as-is by GET leaves/{leafId}/status-history
 * (backend has no DTO for this endpoint). Fields mirror
 * ChequeLeafStatusHistory.java + its BaseEntity superclass.
 */
export interface ChequeLeafStatusHistory {
  id: number;
  version: number;
  createdAt: string;
  updatedAt: string;
  fromStatus: ChequeLeafStatus;
  toStatus: ChequeLeafStatus;
  reason: string;
  performedBy: string;
  chequeLeaf?: any;
}
