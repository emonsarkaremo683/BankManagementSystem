import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerRequest, CustomerResponse } from '../models/customer.models';
import { CustomerStatus } from '../models/enums';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl + 'customer/';

  getAll(): Observable<CustomerResponse[]> {
    return this.http.get<CustomerResponse[]>(`${this.apiUrl}`);
  }

  getById(id: number): Observable<CustomerResponse> {
    return this.http.get<CustomerResponse>(`${this.apiUrl}${id}`);
  }

  getByEmail(email: string): Observable<CustomerResponse> {
    return this.http.get<CustomerResponse>(`${this.apiUrl}email/${email}`);
  }

  search(query: string): Observable<CustomerResponse[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<CustomerResponse[]>(`${this.apiUrl}search`, { params });
  }

  emailExists(email: string): Observable<boolean> {
    const params = new HttpParams().set('email', email);
    return this.http.get<boolean>(`${this.apiUrl}exists`, { params });
  }

  update(id: number, data: CustomerRequest, profile?: File): Observable<CustomerResponse> {
    const formData = new FormData();
    formData.append('data', JSON.stringify(data));
    if (profile) {
      // CustomerController.updateByEmployee expects @RequestPart("profile") — the previous
      // 'profilePicture' key silently dropped every profile-photo update since the part was
      // optional server-side and therefore never rejected, just ignored.
      formData.append('profile', profile);
    }
    return this.http.put<CustomerResponse>(`${this.apiUrl}${id}`, formData);
  }

  updateProfilePicture(id: number, profile: File): Observable<CustomerResponse> {
    const formData = new FormData();
    formData.append('profile', profile);
    return this.http.put<CustomerResponse>(`${this.apiUrl}${id}/profile`, formData);
  }

  updatePassword(id: number, oldPass: string, newPass: string): Observable<void> {
    const params = new HttpParams()
      .set('oldPass', oldPass)
      .set('newPass', newPass);
    return this.http.patch<void>(`${this.apiUrl}${id}/password`, null, { params });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}${id}`);
  }

  deactivate(id: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}${id}/deactivate`, {});
  }

  findByPhone(phone: string): Observable<CustomerResponse> {
    return this.http.get<CustomerResponse>(`${this.apiUrl}phone/${phone}`);
  }

  phoneExists(phone: string): Observable<boolean> {
    const params = new HttpParams().set('phone', phone);
    return this.http.get<boolean>(`${this.apiUrl}phone-exists`, { params });
  }

  findByStatus(status: CustomerStatus): Observable<CustomerResponse[]> {
    return this.http.get<CustomerResponse[]>(`${this.apiUrl}status/${status}`);
  }
}
