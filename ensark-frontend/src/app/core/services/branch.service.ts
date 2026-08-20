import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BranchRequest, BranchResponse } from '../models/branch.models';
import { BranchType, BranchStatus } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BranchService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'branch/';

  getAll(): Observable<BranchResponse[]> {
    return this.http.get<BranchResponse[]>(this.apiUrl);
  }

  getById(id: number): Observable<BranchResponse> {
    return this.http.get<BranchResponse>(`${this.apiUrl}${id}`);
  }

  getByType(type: BranchType): Observable<BranchResponse[]> {
    return this.http.get<BranchResponse[]>(`${this.apiUrl}type/${type}`);
  }

  getByStatus(status: BranchStatus): Observable<BranchResponse[]> {
    return this.http.get<BranchResponse[]>(`${this.apiUrl}status/${status}`);
  }

  search(query: string): Observable<BranchResponse[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<BranchResponse[]>(`${this.apiUrl}search`, { params });
  }

  getByPoliceStationId(policeStationId: number): Observable<BranchResponse[]> {
    return this.http.get<BranchResponse[]>(`${this.apiUrl}police-station/${policeStationId}`);
  }

  save(data: BranchRequest): Observable<BranchResponse> {
    return this.http.post<BranchResponse>(this.apiUrl, data);
  }

  update(id: number, data: BranchRequest): Observable<BranchResponse> {
    return this.http.put<BranchResponse>(`${this.apiUrl}${id}`, data);
  }

  branchCodeExists(code: string): Observable<boolean> {
    const params = new HttpParams().set('code', code);
    return this.http.get<boolean>(`${this.apiUrl}exists`, { params });
  }

  deactivate(id: number): Observable<BranchResponse> {
    return this.http.post<BranchResponse>(`${this.apiUrl}${id}/deactivate`, {});
  }
}
