import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CurrencyResponse, ExchangeRate, CurrencyConversionRequest, CurrencyConversionResponse } from '../models/currency.models';
import { Currency } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'currency/';

  getRates(baseCurrency: Currency = Currency.BDT): Observable<CurrencyResponse[]> {
    const params = new HttpParams().set('base', baseCurrency);
    return this.http.get<CurrencyResponse[]>(this.apiUrl, { params });
  }

  getRate(from: Currency, to: Currency): Observable<ExchangeRate> {
    const params = new HttpParams().set('from', from).set('to', to);
    return this.http.get<ExchangeRate>(`${this.apiUrl}rate`, { params });
  }

  convert(req: CurrencyConversionRequest): Observable<CurrencyConversionResponse> {
    const params = new HttpParams()
      .set('from', req.fromCurrency)
      .set('to', req.toCurrency)
      .set('amount', req.amount.toString());

    return this.http.get<CurrencyConversionResponse>(`${this.apiUrl}convert`, { params });
  }
}
