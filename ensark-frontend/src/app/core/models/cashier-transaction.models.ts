import { TransactionType } from './enums';

export interface CashierTransactionRequest {
  checkNo?: string;
  branchId: number;
  accountNumber: string;
  accountName: string;
  type: TransactionType;
  bankName: string;
  employeeId: number;
  routingNumber: string;
  transactionRequest: {
    amount: number;
    remarks: string;
  };
}

export interface CashierTransactionResponse {
  id: number;
  transactionEntityId: number;
  checkNo: string;
  cashierName: string;
  branchName: string;
  transaction: {
    transactionId: string;
    referenceNo: string;
    transactionType: TransactionType;
    channel: string;
    status: string;
    amount: number;
    chargeAmount: number;
    vatAmount: number;
    remarks: string;
    createdAt: string;
  };
  journals: any[];
}
