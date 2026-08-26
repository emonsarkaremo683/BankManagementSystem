import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  LedgerAccountReport,
  TrialBalanceResponse,
  BalanceSheetResponse,
  ProfitLossResponse,
  ReportSpanRequest
} from '../models/report.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'reports/';

  // LedgerController/TrialBalanceController/BalanceSheetController are all
  // mapped WITHOUT a trailing slash on their root GET (e.g. LedgerController
  // is @RequestMapping({"/api/reports/ledger","/api/ledger"}) with a bare
  // @GetMapping on getAll()). Spring's path matching is strict by default
  // (no trailing-slash-match), so calling ".../trial-balance/" 404s — only
  // ".../trial-balance" (no trailing slash) matches. Sub-paths like
  // "branch/{id}/span" are unaffected since they aren't the bare root.

  getTrialBalance(branchId?: number): Observable<TrialBalanceResponse> {
    const url = branchId ? `${this.apiUrl}trial-balance/branch/${branchId}` : `${this.apiUrl}trial-balance`;
    return this.http.get<TrialBalanceResponse>(url);
  }

  getTrialBalanceSpan(request: ReportSpanRequest, branchId?: number): Observable<TrialBalanceResponse> {
    const url = branchId ? `${this.apiUrl}trial-balance/branch/${branchId}/span` : `${this.apiUrl}trial-balance/span`;
    return this.http.post<TrialBalanceResponse>(url, request);
  }

  getBalanceSheet(): Observable<BalanceSheetResponse> {
    return this.http.get<BalanceSheetResponse>(`${this.apiUrl}balance-sheet`);
  }

  getBalanceSheetSpan(request: ReportSpanRequest): Observable<BalanceSheetResponse> {
    return this.http.post<BalanceSheetResponse>(`${this.apiUrl}balance-sheet/span`, request);
  }

  getProfitLoss(): Observable<ProfitLossResponse> {
    return this.http.get<ProfitLossResponse>(`${this.apiUrl}profit-loss`);
  }

  getLedger(branchId?: number): Observable<LedgerAccountReport[]> {
    const url = branchId ? `${this.apiUrl}ledger/branch/${branchId}` : `${this.apiUrl}ledger`;
    return this.http.get<LedgerAccountReport[]>(url);
  }

  getLedgerSpan(request: ReportSpanRequest, branchId?: number): Observable<LedgerAccountReport[]> {
    const url = branchId ? `${this.apiUrl}ledger/branch/${branchId}/span` : `${this.apiUrl}ledger/span`;
    return this.http.post<LedgerAccountReport[]>(url, request);
  }
}
