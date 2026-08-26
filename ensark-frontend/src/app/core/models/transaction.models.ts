import { TransactionType, TransactionChannel, TransactionStatus } from './enums';

export interface TransactionRequest {
  amount: number;
  remarks: string;
}

export interface TransactionResponse {
  transactionId: string;
  referenceNo: string;
  transactionType: TransactionType;
  channel: TransactionChannel;
  status: TransactionStatus;
  amount: number;
  chargeAmount: number;
  vatAmount: number;
  remarks: string;
  createdAt: string;
}

export interface AccountTransactionRequest {
  senderAccountId: number;
  receiverAccountId?: number;
  receiverAccountNumber?: string;
  receiverName?: string;
  bankName?: string;
  routingNumber?: string;
  beneficiaryId?: number;
  request: TransactionRequest;
}

export interface AccountTransactionResponse {
  id: number;
  transactionId: string;
  senderAccountNumber: string;
  senderName: string;
  receiverAccountNumber: string;
  receiverName: string;
  bankName: string;
  direction: string;
  response: TransactionResponse;
}

export interface OtpInitiateResponse {
  otpReferenceId: number;
  maskedEmail: string;
  expiresAt: string;
}

export interface OtpVerifyRequest {
  otpReferenceId: number;
  otpCode: string;
}
