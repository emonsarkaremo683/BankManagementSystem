import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  LoanApplicationRequest,
  LoanApplicationResponse,
  LoanRepaymentResponse,
  LoanScheduleResponse
} from '../models/loan.models';
import { LoanStatus } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'loan/';

  apply(data: LoanApplicationRequest, documents?: File[], guarantorPhoto?: File): Observable<LoanApplicationResponse> {
    const formData = new FormData();
    formData.append('data', JSON.stringify(data));
    if (documents) {
      documents.forEach(file => formData.append('documents', file));
    }
    if (guarantorPhoto) {
      formData.append('guarantorPhoto', guarantorPhoto);
    }
    return this.http.post<LoanApplicationResponse>(`${this.apiUrl}apply`, formData);
  }

  getAll(): Observable<LoanApplicationResponse[]> {
    return this.http.get<LoanApplicationResponse[]>(this.apiUrl);
  }

  getById(id: number): Observable<LoanApplicationResponse> {
    return this.http.get<LoanApplicationResponse>(`${this.apiUrl}${id}`);
  }

  getByCustomerEmail(email: string): Observable<LoanApplicationResponse[]> {
    return this.http.get<LoanApplicationResponse[]>(`${this.apiUrl}customer/${email}`);
  }

  getByAccountNumber(accountNumber: string): Observable<LoanApplicationResponse[]> {
    return this.http.get<LoanApplicationResponse[]>(`${this.apiUrl}account/${accountNumber}`);
  }

  search(query: string): Observable<LoanApplicationResponse[]> {
    return this.http.get<LoanApplicationResponse[]>(`${this.apiUrl}search`, { params: { query } });
  }

  updateStatus(id: number, status: LoanStatus, reason?: string): Observable<LoanApplicationResponse> {
    let params: any = { status };
    if (reason) params.reason = reason;
    return this.http.patch<LoanApplicationResponse>(`${this.apiUrl}${id}/status`, null, { params });
  }

  getRepayments(loanId: number): Observable<LoanRepaymentResponse[]> {
    return this.http.get<LoanRepaymentResponse[]>(`${this.apiUrl}${loanId}/repayments`);
  }

  getSchedule(loanId: number): Observable<LoanScheduleResponse[]> {
    return this.http.get<LoanScheduleResponse[]>(`${this.apiUrl}${loanId}/schedule`);
  }

  payRepayment(repaymentId: number): Observable<LoanRepaymentResponse> {
    return this.http.post<LoanRepaymentResponse>(`${this.apiUrl}repayments/${repaymentId}/pay`, {});
  }

  findByStatus(status: LoanStatus): Observable<LoanApplicationResponse[]> {
    return this.http.get<LoanApplicationResponse[]>(`${this.apiUrl}status/${status}`);
  }

  foreclose(id: number, sweepFromAccountId: number): Observable<LoanApplicationResponse> {
    return this.http.post<LoanApplicationResponse>(`${this.apiUrl}${id}/foreclose`, null, {
      params: { sweepFromAccountId }
    });
  }

  recalculateEmiSchedule(id: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}${id}/recalculate`, null);
  }

  getLoanSummary(id: number): Observable<Record<string, any>> {
    return this.http.get<Record<string, any>>(`${this.apiUrl}${id}/summary`);
  }

  payInstallmentByAccount(repaymentId: number, accountId: number): Observable<LoanRepaymentResponse> {
    return this.http.post<LoanRepaymentResponse>(`${this.apiUrl}repayments/${repaymentId}/pay-account`, null, {
      params: { accountId }
    });
  }

  payInstallmentByCashier(repaymentId: number, cashierId: number, branchId: number): Observable<LoanRepaymentResponse> {
    return this.http.post<LoanRepaymentResponse>(`${this.apiUrl}repayments/${repaymentId}/pay-cashier`, null, {
      params: { cashierId, branchId }
    });
  }
}
