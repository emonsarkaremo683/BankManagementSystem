import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeRequest, EmployeeResponse } from '../models/employee.models';
import { Designation, EmployeeStatus, Role } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'employee/';

  getAll(): Observable<EmployeeResponse[]> {
    return this.http.get<EmployeeResponse[]>(this.apiUrl);
  }

  getById(id: number): Observable<EmployeeResponse> {
    return this.http.get<EmployeeResponse>(`${this.apiUrl}${id}`);
  }

  getByBranchId(branchId: number): Observable<EmployeeResponse[]> {
    return this.http.get<EmployeeResponse[]>(`${this.apiUrl}branch/${branchId}`);
  }

  existsByEmail(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}exists?email=${email}`);
  }

  findByEmail(email: string): Observable<EmployeeResponse> {
    return this.http.get<EmployeeResponse>(`${this.apiUrl}email/${email}`);
  }

  save(data: EmployeeRequest, profilePicture?: File): Observable<EmployeeResponse> {
    const formData = new FormData();
    formData.append('data', JSON.stringify(data));
    if (profilePicture) {
      formData.append('profile', profilePicture);
    }
    return this.http.post<EmployeeResponse>(this.apiUrl, formData);
  }

  update(id: number, data: EmployeeRequest, profilePicture?: File): Observable<EmployeeResponse> {
    const formData = new FormData();
    formData.append('data', JSON.stringify(data));
    if (profilePicture) {
      formData.append('profile', profilePicture);
    }
    return this.http.put<EmployeeResponse>(`${this.apiUrl}${id}`, formData);
  }

  updateProfilePicture(id: number, profilePicture: File): Observable<EmployeeResponse> {
    const formData = new FormData();
    formData.append('profile', profilePicture);
    return this.http.put<EmployeeResponse>(`${this.apiUrl}${id}/profile`, formData);
  }

  updateStatus(id: number, status: EmployeeStatus): Observable<EmployeeResponse> {
    return this.http.patch<EmployeeResponse>(`${this.apiUrl}${id}/status/${status}`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}${id}`);
  }

  search(query: string): Observable<EmployeeResponse[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<EmployeeResponse[]>(`${this.apiUrl}search`, { params });
  }

  resetPassword(id: number, newPassword: string): Observable<void> {
    const params = new HttpParams().set('newPassword', newPassword);
    return this.http.post<void>(`${this.apiUrl}${id}/reset-password`, null, { params });
  }

  updateDesignation(id: number, designation: Designation, role: Role): Observable<EmployeeResponse> {
    const params = new HttpParams().set('designation', designation).set('role', role);
    return this.http.put<EmployeeResponse>(`${this.apiUrl}${id}/designation`, null, { params });
  }
}
