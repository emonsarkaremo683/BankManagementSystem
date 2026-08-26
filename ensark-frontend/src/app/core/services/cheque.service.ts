import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChequeBookRequest, ChequeBookResponse, ChequeLeafResponse, ChequeLeafStatusHistory } from '../models/cheque.models';
import { ChequeLeafStatus } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChequeService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'cheque/';

  apply(data: ChequeBookRequest): Observable<ChequeBookResponse> {
    return this.http.post<ChequeBookResponse>(`${this.apiUrl}apply`, data);
  }

  getAll(): Observable<ChequeBookResponse[]> {
    return this.http.get<ChequeBookResponse[]>(`${this.apiUrl}all`);
  }

  getById(id: number): Observable<ChequeBookResponse> {
    return this.http.get<ChequeBookResponse>(`${this.apiUrl}${id}`);
  }

  getByCustomerEmail(email: string): Observable<ChequeBookResponse[]> {
    return this.http.get<ChequeBookResponse[]>(`${this.apiUrl}customer/email/${email}`);
  }

  getByAccountNumber(accountNumber: string): Observable<ChequeBookResponse[]> {
    return this.http.get<ChequeBookResponse[]>(`${this.apiUrl}account/${accountNumber}`);
  }

  search(query: string): Observable<ChequeBookResponse[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<ChequeBookResponse[]>(`${this.apiUrl}search`, { params });
  }

  update(id: number, data: ChequeBookRequest): Observable<ChequeBookResponse> {
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}`, data);
  }

  approve(id: number): Observable<ChequeBookResponse> {
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}/approve`, {});
  }

  reject(id: number, reason: string): Observable<ChequeBookResponse> {
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}/reject`, null, { params: { reason } });
  }

  markPrinted(id: number): Observable<ChequeBookResponse> {
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}/print`, {});
  }

  markReadyForDelivery(id: number): Observable<ChequeBookResponse> {
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}/ready-for-delivery`, {});
  }

  markDelivered(id: number): Observable<ChequeBookResponse> {
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}/deliver`, {});
  }

  activate(id: number): Observable<ChequeBookResponse> {
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}/activate`, {});
  }

  block(id: number, reason: string): Observable<ChequeBookResponse> {
    const params = new HttpParams().set('reason', reason);
    return this.http.put<ChequeBookResponse>(`${this.apiUrl}${id}/block`, null, { params });
  }

  reissueChequeBook(oldBookId: number): Observable<ChequeBookResponse> {
    return this.http.post<ChequeBookResponse>(`${this.apiUrl}${oldBookId}/reissue`, {});
  }

  getUnusedLeafCount(chequeBookId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}${chequeBookId}/unused-count`);
  }

  getChequeBookSummary(accountId: number): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.apiUrl}account/${accountId}/summary`);
  }

  addLeaf(chequeBookId: number, amount: number, payeeName: string, remarks: string): Observable<ChequeLeafResponse> {
    const params = new HttpParams()
      .set('amount', amount.toString())
      .set('payeeName', payeeName)
      .set('remarks', remarks);
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}${chequeBookId}/leaves`, null, { params });
  }

  getLeafById(leafId: number): Observable<ChequeLeafResponse> {
    return this.http.get<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}`);
  }

  getLeafByNumber(chequeNumber: string): Observable<ChequeLeafResponse> {
    return this.http.get<ChequeLeafResponse>(`${this.apiUrl}leaves/number/${chequeNumber}`);
  }

  getPresentedLeaves(): Observable<ChequeLeafResponse[]> {
    return this.http.get<ChequeLeafResponse[]>(`${this.apiUrl}leaves/presented`);
  }

  getPresentedLeavesByBranch(branchId: number): Observable<ChequeLeafResponse[]> {
    return this.http.get<ChequeLeafResponse[]>(`${this.apiUrl}leaves/presented/branch/${branchId}`);
  }

  presentLeaf(leafId: number, remarks?: string): Observable<ChequeLeafResponse> {
    const params = remarks ? new HttpParams().set('remarks', remarks) : new HttpParams();
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}/present`, null, { params });
  }

  clearLeaf(leafId: number, transactionReference: string): Observable<ChequeLeafResponse> {
    const params = new HttpParams().set('transactionReference', transactionReference);
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}/clear`, null, { params });
  }

  bounceLeaf(leafId: number, reason: string): Observable<ChequeLeafResponse> {
    const params = new HttpParams().set('reason', reason);
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}/bounce`, null, { params });
  }

  stopPayment(leafId: number, remarks?: string): Observable<ChequeLeafResponse> {
    const params = remarks ? new HttpParams().set('remarks', remarks) : new HttpParams();
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}/stop-payment`, null, { params });
  }

  stopPaymentOnPresented(leafId: number, remarks?: string): Observable<ChequeLeafResponse> {
    const params = remarks ? new HttpParams().set('remarks', remarks) : new HttpParams();
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}/stop-payment-presented`, null, { params });
  }

  cancelLeaf(leafId: number, remarks?: string): Observable<ChequeLeafResponse> {
    const params = remarks ? new HttpParams().set('remarks', remarks) : new HttpParams();
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}/cancel`, null, { params });
  }

  revertToIssued(leafId: number, reason: string): Observable<ChequeLeafResponse> {
    const params = new HttpParams().set('reason', reason);
    return this.http.post<ChequeLeafResponse>(`${this.apiUrl}leaves/${leafId}/revert-presented`, null, { params });
  }

  getLeavesByBookId(chequeBookId: number): Observable<ChequeLeafResponse[]> {
    return this.http.get<ChequeLeafResponse[]>(`${this.apiUrl}${chequeBookId}/leaves`);
  }

  findByChequeBookIdAndLeafNumber(chequeBookId: number, leafNumber: number): Observable<ChequeLeafResponse> {
    return this.http.get<ChequeLeafResponse>(`${this.apiUrl}${chequeBookId}/leaves/${leafNumber}`);
  }

  getLeavesByCustomerId(customerId: number, status?: ChequeLeafStatus): Observable<ChequeLeafResponse[]> {
    let params = new HttpParams();
    if (status) params = params.set('status', status);
    return this.http.get<ChequeLeafResponse[]>(`${this.apiUrl}customer/${customerId}/leaves`, { params });
  }

  getLeafStatusHistory(leafId: number): Observable<ChequeLeafStatusHistory[]> {
    return this.http.get<ChequeLeafStatusHistory[]>(`${this.apiUrl}leaves/${leafId}/status-history`);
  }
}
