import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { FraudFlagResponse, FraudReviewRequest } from '../models/fraud.models';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FraudService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private apiUrl = environment.apiUrl + 'fraud/';

  getFraudAlerts(): Observable<FraudFlagResponse[]> {
    return this.http.get<FraudFlagResponse[]>(this.apiUrl);
  }

  getFraudAlertById(id: number): Observable<FraudFlagResponse | undefined> {
    return this.http.get<FraudFlagResponse[]>(this.apiUrl).pipe(
      map(list => list.find(f => f.id === id))
    );
  }

  reviewFraudAlert(id: number, request: FraudReviewRequest): Observable<FraudFlagResponse> {
    const user = this.authService.currentUserValue;
    const payload = {
      status: request.status,
      reviewedBy: user?.email || 'Unknown',
      reviewNotes: request.notes + (request.actionTaken ? ` | Action: ${request.actionTaken}` : '')
    };
    return this.http.put<FraudFlagResponse>(`${this.apiUrl}${id}/review`, payload);
  }
}
