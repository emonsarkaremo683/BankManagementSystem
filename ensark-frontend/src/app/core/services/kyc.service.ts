import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { KYCStatus } from '../models/enums';
import { environment } from '../../../environments/environment';

export interface KycDocumentInfo {
  id: number;
  doc_type: string;
  /** Present when the doc list comes from the full Kyc entity (customer/{id}, pending);
   *  ABSENT from GET /kyc/my-status, which only ever returns {id, doc_type}. Don't rely
   *  on this for MIME/type detection when the source might be my-status — prefer the
   *  Blob's own `.type` from getDocumentBlob() instead. */
  path?: string;
}

export interface KycResponse {
  id: number;
  status: string;
  documents: KycDocumentInfo[];
}

export interface KycMyStatus {
  status: string;
  documents: KycDocumentInfo[];
}

@Injectable({
  providedIn: 'root'
})
export class KycService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'kyc/';

  getByCustomerId(customerId: number): Observable<KycResponse> {
    return this.http.get<KycResponse>(`${this.apiUrl}customer/${customerId}`);
  }

  getByAccountId(accountId: number): Observable<KycResponse> {
    return this.http.get<KycResponse>(`${this.apiUrl}account/${accountId}`);
  }

  save(customerId: number, files: { [key: string]: File }): Observable<any> {
    const formData = new FormData();
    Object.entries(files).forEach(([key, file]) => {
      if (file) formData.append(key, file);
    });
    return this.http.post(`${this.apiUrl}?customerId=${customerId}`, formData);
  }

  update(customerId: number, files: { [key: string]: File }): Observable<any> {
    const formData = new FormData();
    Object.entries(files).forEach(([key, file]) => {
      if (file) formData.append(key, file);
    });
    return this.http.put(`${this.apiUrl}${customerId}`, formData);
  }

  updateStatus(id: number, status: KYCStatus): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}customer/${id}/status`, JSON.stringify(status), {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  /**
   * GET /api/kyc/documents/{id} requires a Bearer Authorization header (staff can fetch
   * any document, a CUSTOMER only their own — see KycController#assertStaffOrOwner).
   * JwtAuthFilter only accepts the token via the Authorization header — the `?token=`
   * query-parameter fallback that a plain `<img src>`/`<a href>` URL would have needed
   * was intentionally removed (it leaked tokens into access logs/Referer headers), so a
   * bare URL string no longer works here. Fetch the bytes through HttpClient (which the
   * authInterceptor attaches the header to) and hand back an object URL instead.
   */
  getDocumentBlob(documentId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}documents/${documentId}`, { responseType: 'blob' });
  }

  /** GET /api/kyc/documents/{id}/info — metadata only, same staff-or-owner check as above. */
  getDocumentInfo(documentId: number): Observable<KycDocumentInfo> {
    return this.http.get<KycDocumentInfo>(`${this.apiUrl}documents/${documentId}/info`);
  }

  getProfileUrl(path?: string | null): string {
    if (!path) return '';
    if (path.startsWith('http://') || path.startsWith('https://') || path.startsWith('data:')) {
      return path;
    }
    const cleanPath = path.startsWith('customer/') ? path.substring(9) : path;
    return `${environment.uploadsUrl}customer/${cleanPath}`;
  }

  passportExists(accountNumber: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}passport-exists/${accountNumber}`);
  }

  uploadMyDocuments(files: { [key: string]: File }): Observable<void> {
    const formData = new FormData();
    Object.entries(files).forEach(([key, file]) => {
      if (file) formData.append(key, file);
    });
    return this.http.post<void>(`${this.apiUrl}my-documents`, formData);
  }

  getMyKycStatus(): Observable<KycMyStatus> {
    return this.http.get<KycMyStatus>(`${this.apiUrl}my-status`);
  }
}
