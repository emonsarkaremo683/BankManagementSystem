import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BeneficiaryRequest, BeneficiaryResponse } from '../models/beneficiary.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BeneficiaryService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'beneficiary/';

  getByCustomerId(customerId: number): Observable<BeneficiaryResponse[]> {
    return this.http.get<BeneficiaryResponse[]>(`${this.apiUrl}customer/${customerId}`);
  }

  getByCustomerEmail(email: string): Observable<BeneficiaryResponse[]> {
    return this.http.get<BeneficiaryResponse[]>(`${this.apiUrl}customer/email/${email}`);
  }

  getById(id: number): Observable<BeneficiaryResponse> {
    return this.http.get<BeneficiaryResponse>(`${this.apiUrl}${id}`);
  }

  findByAccountId(accountId: number): Observable<BeneficiaryResponse[]> {
    return this.http.get<BeneficiaryResponse[]>(`${this.apiUrl}account/${accountId}`);
  }

  create(data: BeneficiaryRequest): Observable<BeneficiaryResponse> {
    return this.http.post<BeneficiaryResponse>(`${this.apiUrl}`, data);
  }

  update(id: number, data: BeneficiaryRequest): Observable<BeneficiaryResponse> {
    return this.http.put<BeneficiaryResponse>(`${this.apiUrl}${id}`, data);
  }

  /**
   * BeneficiaryController#delete returns `ResponseEntity<String>` (a plain confirmation
   * message body, e.g. "Beneficiary deleted successfully"), not JSON — Angular's default
   * responseType is 'json', so parsing that body as JSON throws and every delete used to
   * surface as a client-side failure even though the record was actually deleted server-side.
   */
  delete(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}${id}`, { responseType: 'text' });
  }

  /** Same plain-text response shape as delete() — see comment above. Backend does not
   *  return a masked email/OTP reference, only this fixed confirmation string. */
  initiateVerification(id: number): Observable<string> {
    return this.http.post(`${this.apiUrl}${id}/initiate-verify`, {}, { responseType: 'text' });
  }

  /** Same plain-text response shape as delete() — backend returns a message string, not
   *  the updated BeneficiaryResponse, so callers must re-fetch to see isVerified flip. */
  verify(id: number, otpCode: string): Observable<string> {
    const params = new HttpParams().set('otpCode', otpCode);
    return this.http.post(`${this.apiUrl}${id}/verify`, {}, { params, responseType: 'text' });
  }

  blockBeneficiary(id: number, reason: string): Observable<BeneficiaryResponse> {
    const params = new HttpParams().set('reason', reason);
    return this.http.put<BeneficiaryResponse>(`${this.apiUrl}${id}/block`, {}, { params });
  }

  unblockBeneficiary(id: number): Observable<BeneficiaryResponse> {
    return this.http.put<BeneficiaryResponse>(`${this.apiUrl}${id}/unblock`, {});
  }
}
