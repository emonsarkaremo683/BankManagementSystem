import { AccountType, AccountStatus, AccountCategory, NomineeRelation, HolderType } from './enums';

export interface AccountHolderRequest {
  holderType: HolderType;
  canWithdraw: boolean;
  canDeposit: boolean;
  canApproveTransaction: boolean;
  signature?: string;
  customerId: number;
}

export interface AccountRequest {
  accountType: AccountType;
  availableBalance: number;
  branchId: number;
  n_name: string;
  n_email: string;
  n_phone: string;
  relation: NomineeRelation;
  n_photo?: string;
  n_nid_front?: string;
  n_nid_back?: string;
  accountHolders: AccountHolderRequest[];
}

export interface AccountHolderResponse {
  id: number;
  accountHolderName: string;
  holderType: HolderType;
  canWithdraw: boolean;
  canDeposit: boolean;
  canApproveTransaction: boolean;
  signature?: string;
}

export interface AccountResponse {
  id: number;
  accountNumber: string;
  accountType: AccountType;
  accountStatus: AccountStatus;
  availableBalance: number;
  currentBalance: number;
  holdBalance: number;
  branchName: string;
  branchRoutingNumber: string;
  n_name?: string;
  n_email?: string;
  relation?: NomineeRelation;
  n_phone?: string;
  n_photo?: string;
  n_nid_front?: string;
  n_nid_back?: string;
  holderResponses: AccountHolderResponse[];
}
