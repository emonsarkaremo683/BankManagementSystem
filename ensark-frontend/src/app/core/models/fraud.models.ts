import { FraudFlagStatus, FraudRiskLevel } from './enums';

// FraudController returns the raw FraudFlag JPA entity (no response DTO), so
// this mirrors FraudFlag.java + its inherited BaseEntity fields exactly.
// There is no customerName/accountNumber/amount on the backend — only the
// raw userId/accountId FKs and flaggedAmount — so screens must resolve
// display names themselves or just show the numeric id.
export interface FraudFlagResponse {
  id: number;
  userId: number;
  transactionId?: number;
  accountId?: number;
  riskLevel: FraudRiskLevel;
  status: FraudFlagStatus;
  reason: string;
  details?: string;
  ipAddress?: string;
  deviceInfo?: string;
  location?: string;
  flaggedAmount?: number;
  reviewedBy?: string;
  reviewNotes?: string;
  createdAt: string;
  updatedAt?: string;
}

// Outgoing shape for PUT /api/fraud/{id}/review — FraudController reads this
// as a raw Map<String,String> with keys "status", "reviewedBy", "reviewNotes".
// actionTaken has no backend column; it's folded into reviewNotes text by
// fraud.service.ts before the request is sent.
export interface FraudReviewRequest {
  status: FraudFlagStatus;
  notes: string;
  actionTaken?: 'FREEZE_ACCOUNT' | 'BLOCK_CARD' | 'DISMISS_ALERT' | 'ESCALATE';
}
