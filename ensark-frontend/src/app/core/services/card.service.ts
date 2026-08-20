import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CardRequest, CardResponse, PinChangeRequest, CardUsageResponse, CardPurchaseAuthorizationResponse } from '../models/card.models';
import { CardStatus, CardNetwork, CardType } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CardService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'card/';

  apply(data: CardRequest): Observable<CardResponse> {
    return this.http.post<CardResponse>(`${this.apiUrl}apply`, data);
  }

  getAll(): Observable<CardResponse[]> {
    return this.http.get<CardResponse[]>(this.apiUrl);
  }

  getById(id: number): Observable<CardResponse> {
    return this.http.get<CardResponse>(`${this.apiUrl}${id}`);
  }

  getByCardNumber(cardNumber: string): Observable<CardResponse> {
    return this.http.get<CardResponse>(`${this.apiUrl}number/${cardNumber}`);
  }

  cardNumberExists(cardNumber: string): Observable<boolean> {
    const params = new HttpParams().set('cardNumber', cardNumber);
    return this.http.get<boolean>(`${this.apiUrl}exists`, { params });
  }

  search(query: string): Observable<CardResponse[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<CardResponse[]>(`${this.apiUrl}search`, { params });
  }

  getByCustomerEmail(email: string): Observable<CardResponse[]> {
    return this.http.get<CardResponse[]>(`${this.apiUrl}customer/${email}`);
  }

  getByAccountNumber(accountNumber: string): Observable<CardResponse[]> {
    return this.http.get<CardResponse[]>(`${this.apiUrl}account/${accountNumber}`);
  }

  update(id: number, data: CardRequest): Observable<CardResponse> {
    return this.http.put<CardResponse>(`${this.apiUrl}${id}`, data);
  }

  updateStatus(id: number, status: CardStatus): Observable<CardResponse> {
    return this.http.patch<CardResponse>(`${this.apiUrl}${id}/status/${status}`, {});
  }

  updateNetwork(id: number, network: CardNetwork): Observable<CardResponse> {
    return this.http.patch<CardResponse>(`${this.apiUrl}${id}/network/${network}`, {});
  }

  updateType(id: number, type: CardType): Observable<CardResponse> {
    return this.http.patch<CardResponse>(`${this.apiUrl}${id}/type/${type}`, {});
  }

  enableMultiCurrency(id: number): Observable<CardResponse> {
    return this.http.post<CardResponse>(`${this.apiUrl}${id}/multi-currency`, {});
  }

  toggleMultiCurrency(id: number, active: boolean): Observable<CardResponse> {
    const params = new HttpParams().set('active', active);
    return this.http.patch<CardResponse>(`${this.apiUrl}${id}/multi-currency`, {}, { params });
  }

  changePin(id: number, data: PinChangeRequest): Observable<CardResponse> {
    return this.http.patch<CardResponse>(`${this.apiUrl}${id}/pin`, data);
  }

  setTransactionLimit(id: number, dailyLimit: number, monthlyLimit: number): Observable<CardResponse> {
    const params = new HttpParams()
      .set('dailyLimit', dailyLimit)
      .set('monthlyLimit', monthlyLimit);
    return this.http.patch<CardResponse>(`${this.apiUrl}${id}/limit`, {}, { params });
  }

  reportLostOrStolen(id: number, reason: string): Observable<CardResponse> {
    const params = new HttpParams().set('reason', reason);
    return this.http.post<CardResponse>(`${this.apiUrl}${id}/report-lost-stolen`, {}, { params });
  }

  renewCard(id: number): Observable<CardResponse> {
    return this.http.post<CardResponse>(`${this.apiUrl}${id}/renew`, {});
  }

  getUsage(id: number): Observable<CardUsageResponse> {
    return this.http.get<CardUsageResponse>(`${this.apiUrl}${id}/usage`);
  }

  authorizePurchase(id: number, amount: number, merchantInfo?: string): Observable<CardPurchaseAuthorizationResponse> {
    let params = new HttpParams().set('amount', amount);
    if (merchantInfo) {
      params = params.set('merchantInfo', merchantInfo);
    }
    return this.http.post<CardPurchaseAuthorizationResponse>(`${this.apiUrl}${id}/authorize-purchase`, {}, { params });
  }
}
