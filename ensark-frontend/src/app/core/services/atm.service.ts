import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ATMRequest, ATMResponse, ATMTransactionRequest, ATMTransactionResponse } from '../models/atm.models';
import { ATMStatus } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AtmService {
  private http = inject(HttpClient);
  // No trailing slash on the base URLs: ATMController is @RequestMapping("/api/atm")
  // and ATMTransactionController is @RequestMapping("/api/atm-transactions") — neither
  // has a trailing slash, and Spring 6+/Boot 3+ path matching is strict by default
  // (no trailing-slash-match), so a bare "…/atm/" or "…/atm-transactions/" 404s against
  // their root-level GET/POST mappings. Sub-paths are built with an explicit "/" below.
  private apiUrl = environment.apiUrl + 'atm';
  private txnUrl = environment.apiUrl + 'atm-transactions';

  getAll(): Observable<ATMResponse[]> {
    return this.http.get<ATMResponse[]>(`${this.apiUrl}/all`);
  }

  getById(id: number): Observable<ATMResponse> {
    return this.http.get<ATMResponse>(`${this.apiUrl}/${id}`);
  }

  getByBranchId(branchId: number): Observable<ATMResponse[]> {
    return this.http.get<ATMResponse[]>(`${this.apiUrl}/branch/${branchId}`);
  }

  create(data: ATMRequest): Observable<ATMResponse> {
    return this.http.post<ATMResponse>(this.apiUrl, data);
  }

  update(id: number, data: ATMRequest): Observable<ATMResponse> {
    return this.http.put<ATMResponse>(`${this.apiUrl}/update/${id}`, data);
  }

  updateStatus(id: number, status: ATMStatus): Observable<ATMResponse> {
    return this.http.patch<ATMResponse>(`${this.apiUrl}/${id}/status?status=${status}`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  findByStatus(status: ATMStatus): Observable<ATMResponse[]> {
    return this.http.get<ATMResponse[]>(`${this.apiUrl}/status/${status}`);
  }

  refill(atmId: number, amount: number): Observable<ATMTransactionResponse> {
    return this.http.post<ATMTransactionResponse>(`${this.txnUrl}/${atmId}/refill?amount=${amount}`, {});
  }

  getTransactions(atmId?: number): Observable<ATMTransactionResponse[]> {
    const url = atmId ? `${this.txnUrl}/atm/${atmId}` : this.txnUrl;
    return this.http.get<ATMTransactionResponse[]>(url);
  }

  createTransaction(req: ATMTransactionRequest): Observable<ATMTransactionResponse> {
    return this.http.post<ATMTransactionResponse>(this.txnUrl, req);
  }
}
