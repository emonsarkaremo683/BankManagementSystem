import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountTransactionRequest, AccountTransactionResponse, OtpInitiateResponse, OtpVerifyRequest } from '../models/transaction.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'account-transaction/';

  getAll(): Observable<AccountTransactionResponse[]> {
    return this.http.get<AccountTransactionResponse[]>(`${this.apiUrl}all`);
  }

  getById(id: number): Observable<AccountTransactionResponse> {
    return this.http.get<AccountTransactionResponse>(`${this.apiUrl}${id}`);
  }

  getByReferenceNo(referenceNo: string): Observable<AccountTransactionResponse> {
    return this.http.get<AccountTransactionResponse>(`${this.apiUrl}reference/${referenceNo}`);
  }

  getByAccountNumber(accountNumber: string): Observable<AccountTransactionResponse[]> {
    return this.http.get<AccountTransactionResponse[]>(`${this.apiUrl}accountNumber/${accountNumber}`);
  }

  getByAccountId(accountId: number): Observable<AccountTransactionResponse[]> {
    return this.http.get<AccountTransactionResponse[]>(`${this.apiUrl}account/${accountId}`);
  }

  create(data: AccountTransactionRequest): Observable<AccountTransactionResponse> {
    return this.http.post<AccountTransactionResponse>(this.apiUrl, data);
  }

  initiateOnline(data: AccountTransactionRequest): Observable<OtpInitiateResponse> {
    return this.http.post<OtpInitiateResponse>(`${this.apiUrl}online/initiate`, data);
  }

  verifyOnline(data: OtpVerifyRequest): Observable<AccountTransactionResponse> {
    return this.http.post<AccountTransactionResponse>(`${this.apiUrl}online/verify`, data);
  }

  reverse(id: number): Observable<AccountTransactionResponse> {
    return this.http.post<AccountTransactionResponse>(`${this.apiUrl}transaction/${id}/reverse`, {});
  }

  reverseByReferenceNo(referenceNo: string): Observable<AccountTransactionResponse> {
    return this.http.post<AccountTransactionResponse>(`${this.apiUrl}reverse/${referenceNo}`, {});
  }
}
