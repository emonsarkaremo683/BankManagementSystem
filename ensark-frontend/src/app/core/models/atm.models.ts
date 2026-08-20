import { ATMStatus, AccountStatus, AccountType, ATMTransactionType } from './enums';
import { TransactionRequest, TransactionResponse } from './transaction.models';

// Matches ATMRequest.java exactly (status, balance, limit, address, branchId).
// NOTE: the backend does NOT accept a policeStationId on create/update — the
// police-station shown on ATMResponse is derived from the ATM's branch
// (ATMMapper reads atm.getBranch().getPoliceStation()), it isn't set directly.
export interface ATMRequest {
  branchId: number;
  address: string;
  limit: number;
  balance: number;
  status: ATMStatus;
}

// Matches ATMResponse.java exactly. The backend response has no numeric
// branchId/policeStationId — only the resolved names — so a form can't use
// this to pre-select a branch dropdown for editing.
export interface ATMResponse {
  atmId: number;
  status: ATMStatus;
  limit: number;
  address: string;
  routingNumber: string;
  accNumber: string;
  type: AccountType;
  accountStatus: AccountStatus;
  availableBalance: number;
  branchName: string;
  policeStationName?: string;
}

// Matches ATMTransactionRequest.java exactly.
export interface ATMTransactionRequest {
  atmId: number;
  cardNumber: string;
  pin: string;
  transactionType: ATMTransactionType;
  transactionRequest: TransactionRequest;
}

// Matches ATMTransactionResponse.java exactly, including the unusual
// "ATMTransactionId" field name: the entity field is declared as
// `private Long ATMTransactionId;`, and because Lombok/Jackson bean
// introspection leaves a name alone when its first two characters are both
// uppercase, the getter is getATMTransactionId() and the JSON key really is
// "ATMTransactionId" (not "atmTransactionId" / "id").
export interface ATMTransactionResponse {
  ATMTransactionId: number;
  transactionType: ATMTransactionType;
  cardNumber: string;
  address: string;
  transactionResponse: TransactionResponse;
}
