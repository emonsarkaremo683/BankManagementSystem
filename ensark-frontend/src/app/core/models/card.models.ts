import { CardType, CardNetwork, CardStatus } from './enums';

export interface CardRequest {
  accountId: number;
  cardNetwork: CardNetwork;
  cardType: CardType;
  pin: string;
  isInternationalEnabled: boolean;
  isOnlineTransactionEnabled: boolean;
}

export interface CardResponse {
  cardId: number;
  cardNumber: string;
  cardHolderName: string;
  cardNetwork: CardNetwork;
  cardType: CardType;
  status: CardStatus;
  expiryDate: string;
  dailyLimit: number;
  monthlyLimit: number;
  accountNumber: string;
  isInternationalEnabled: boolean;
  isOnlineTransactionEnabled: boolean;
  createdAt: string;
}

export interface PinChangeRequest {
  oldPin: string;
  newPin: string;
}

export interface CardUsageResponse {
  cardId: number;
  cardNumber: string;
  dailyLimit: number;
  monthlyLimit: number;
  currentDailyUsage: number;
  currentMonthlyUsage: number;
  dailyRemaining: number;
  monthlyRemaining: number;
}

export interface CardPurchaseAuthorizationResponse {
  holdId: number;
  authorizationReference: string;
  amount: number;
  expiresAt: string;
}
