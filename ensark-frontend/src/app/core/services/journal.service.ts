import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JournalEntry } from '../models/dashboard.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JournalService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  getJournalByCustomerEmail(email: string): Observable<JournalEntry[]> {
    return this.http.get<JournalEntry[]>(`${this.apiUrl}journal/customer/${encodeURIComponent(email)}`);
  }
}
