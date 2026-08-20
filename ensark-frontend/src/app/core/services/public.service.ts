import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HeaderRatesResponse, ExchangeRateResponse } from '../models/public.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PublicService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'public/';

  getHeaderRates(): Observable<HeaderRatesResponse> {
    return this.http.get<HeaderRatesResponse>(`${this.apiUrl}header-rates`);
  }

  getAllExchangeRates(): Observable<ExchangeRateResponse> {
    return this.http.get<ExchangeRateResponse>(`${this.apiUrl}exchange-rates`);
  }
}
