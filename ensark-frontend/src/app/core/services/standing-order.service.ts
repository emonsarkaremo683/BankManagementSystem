import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StandingOrderRequest, StandingOrderResponse } from '../models/standing-order.models';
import { TransactionResponse } from '../models/transaction.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StandingOrderService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'standing-orders/';

  create(data: StandingOrderRequest): Observable<StandingOrderResponse> {
    return this.http.post<StandingOrderResponse>(this.apiUrl, data);
  }

  update(id: number, data: StandingOrderRequest): Observable<StandingOrderResponse> {
    return this.http.put<StandingOrderResponse>(`${this.apiUrl}${id}`, data);
  }

  getExecutionHistory(id: number): Observable<TransactionResponse[]> {
    return this.http.get<TransactionResponse[]>(`${this.apiUrl}${id}/history`);
  }

  getById(id: number): Observable<StandingOrderResponse> {
    return this.http.get<StandingOrderResponse>(`${this.apiUrl}${id}`);
  }

  getByAccountId(accountId: number): Observable<StandingOrderResponse[]> {
    return this.http.get<StandingOrderResponse[]>(`${this.apiUrl}account/${accountId}`);
  }

  getActive(): Observable<StandingOrderResponse[]> {
    return this.http.get<StandingOrderResponse[]>(`${this.apiUrl}active`);
  }

  cancel(id: number): Observable<StandingOrderResponse> {
    return this.http.put<StandingOrderResponse>(`${this.apiUrl}${id}/cancel`, {});
  }

  pause(id: number): Observable<StandingOrderResponse> {
    return this.http.put<StandingOrderResponse>(`${this.apiUrl}${id}/pause`, {});
  }

  resume(id: number): Observable<StandingOrderResponse> {
    return this.http.put<StandingOrderResponse>(`${this.apiUrl}${id}/resume`, {});
  }
}
