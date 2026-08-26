import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CashierTransactionRequest, CashierTransactionResponse } from '../models/cashier-transaction.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CashierTransactionService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'cashier-transaction/';

  create(data: CashierTransactionRequest): Observable<CashierTransactionResponse> {
    return this.http.post<CashierTransactionResponse>(this.apiUrl, data);
  }

  reverse(id: number): Observable<CashierTransactionResponse> {
    return this.http.post<CashierTransactionResponse>(`${this.apiUrl}${id}/reverse`, {});
  }
}
