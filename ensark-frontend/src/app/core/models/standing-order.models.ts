import { StandingOrderFrequency, StandingOrderStatus } from './enums';

export interface StandingOrderRequest {
  sourceAccountId: number;
  targetAccountNumber: string;
  targetAccountName: string;
  amount: number;
  frequency: StandingOrderFrequency;
  startDate: string;
  endDate: string;
  maxExecutions: number;
  description: string;
}

export interface StandingOrderResponse {
  id: number;
  sourceAccountNumber: string;
  targetAccountNumber: string;
  targetAccountName: string;
  amount: number;
  frequency: StandingOrderFrequency;
  status: StandingOrderStatus;
  startDate: string;
  endDate: string;
  nextExecutionDate: string;
  lastExecutionDate: string;
  executionCount: number;
  maxExecutions: number;
  description: string;
}
