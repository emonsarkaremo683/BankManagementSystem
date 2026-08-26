import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountRequest, AccountResponse } from '../models/account.models';
import { AccountStatus } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'account/';

  getAll(): Observable<AccountResponse[]> {
    return this.http.get<AccountResponse[]>(`${this.apiUrl}all/`);
  }

  getById(id: number): Observable<AccountResponse> {
    return this.http.get<AccountResponse>(`${this.apiUrl}${id}`);
  }

  getByAccountNumber(accountNumber: string): Observable<AccountResponse> {
    return this.http.get<AccountResponse>(`${this.apiUrl}number/${accountNumber}`);
  }

  getByCustomerEmail(email: string): Observable<AccountResponse[]> {
    return this.http.get<AccountResponse[]>(`${this.apiUrl}email/${email}`);
  }

  getByBranchId(branchId: number): Observable<AccountResponse[]> {
    return this.http.get<AccountResponse[]>(`${this.apiUrl}branch/${branchId}`);
  }

  search(query: string): Observable<AccountResponse[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<AccountResponse[]>(`${this.apiUrl}search`, { params });
  }

  accountNumberExists(accountNumber: string): Observable<boolean> {
    const params = new HttpParams().set('accountNumber', accountNumber);
    return this.http.get<boolean>(`${this.apiUrl}exists`, { params });
  }

  getBalance(accountNumber: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}${accountNumber}/balance`);
  }

  save(data: AccountRequest, signatures?: File[], photo?: File, nidFront?: File, nidBack?: File): Observable<AccountResponse> {
    const formData = new FormData();
    formData.append('data', JSON.stringify(data));
    if (signatures) {
      signatures.forEach(f => formData.append('signatures', f));
    }
    if (photo) {
      formData.append('photo', photo);
    }
    if (nidFront) {
      formData.append('nid_front', nidFront);
    }
    if (nidBack) {
      formData.append('nid_back', nidBack);
    }
    return this.http.post<AccountResponse>(`${this.apiUrl}create`, formData);
  }

  update(id: number, data: AccountRequest): Observable<AccountResponse> {
    return this.http.put<AccountResponse>(`${this.apiUrl}${id}`, data);
  }

  updateStatus(id: number, status: AccountStatus): Observable<AccountResponse> {
    return this.http.patch<AccountResponse>(`${this.apiUrl}${id}/status/${status}`, {});
  }
}
