import { Currency } from './enums';

// Matches CurrencyResponse.java exactly — the backend does not return a
// baseCurrency or updatedAt on each rate row (the base is implied by the
// `?base=` query param used to fetch the list).
export interface CurrencyResponse {
  currency: Currency;
  rate: number;
}

// Matches the raw ExchangeRate JPA entity returned by GET /api/currency/rate
// (+ its inherited BaseEntity fields). The backend field names are
// baseCurrency/targetCurrency, not fromCurrency/toCurrency.
export interface ExchangeRate {
  id?: number;
  baseCurrency: Currency;
  targetCurrency: Currency;
  rate: number;
  fetchedAt?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface CurrencyConversionRequest {
  fromCurrency: Currency;
  toCurrency: Currency;
  amount: number;
}

// Matches the Map.of("from","to","amount","result") body built by
// CurrencyController.convert() exactly.
export interface CurrencyConversionResponse {
  from: Currency;
  to: Currency;
  amount: number;
  result: number;
}
