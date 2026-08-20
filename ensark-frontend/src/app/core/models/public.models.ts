import { Currency } from './enums';

export interface HeaderRatesResponse {
  baseCurrency: Currency;
  lastUpdated: string;
  rates: CurrencyRate[];
}

export interface CurrencyRate {
  currency: Currency;
  rate: number;
}

export interface ExchangeRateResponse {
  baseCurrency: Currency;
  lastUpdated: string;
  rates: PublicExchangeRate[];
}

export interface PublicExchangeRate {
  currency: Currency;
  buyRate: number;
  sellRate: number;
}
