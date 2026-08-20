import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { LoginResponse, MfaVerifyRequest, User } from '../models/auth.models';
import { Role } from '../models/enums';
import { environment } from '../../../environments/environment';
import { CustomerResponse } from '../models/customer.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  private apiUrl = environment.apiUrl + 'auth/';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  private tokenExpirationTimer: any;

  constructor() {
    this.loadUserFromToken();
  }

  public get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      if (!payload.exp || payload.exp * 1000 < Date.now()) {
        return true;
      }
      return false;
    } catch {
      return true;
    }
  }

  login(credentials: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}login`, credentials).pipe(
      tap(response => {
        if (!response.mfaRequired) {
          this.setTokens(response);
        }
      })
    );
  }

  verifyMfa(data: MfaVerifyRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}verify-mfa`, data).pipe(
      tap(response => this.setTokens(response))
    );
  }

  logout(): void {
    const token = this.getToken();
    if (token) {
      // authInterceptor skips attaching the Authorization header for every
      // /api/auth/ URL (by design, so reset/verify tokens never ride as a
      // Bearer credential). /api/auth/logout is the one auth endpoint that
      // actually needs the header — AuthController reads it to blacklist the
      // token — so it must be attached here explicitly.
      const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
      this.http.post(`${this.apiUrl}logout`, {}, { headers }).subscribe({
        next: () => {},
        error: () => {}
      });
    }
    this.clearTokensAndRedirect();
  }

  clearTokensAndRedirect(): void {
    this.clearTokens();
    this.router.navigate(['/login']);
  }

  refreshToken(): Observable<LoginResponse> {
    const refresh = this.getRefreshToken();
    return this.http.post<LoginResponse>(`${this.apiUrl}refresh`, { refreshToken: refresh }).pipe(
      tap(response => this.setTokens(response))
    );
  }

  register(data: any, files: { [key: string]: File }): Observable<CustomerResponse> {
    const formData = new FormData();
    formData.append('data', JSON.stringify(data));
    
    Object.keys(files).forEach(key => {
      if (files[key]) {
        formData.append(key, files[key]);
      }
    });

    return this.http.post<CustomerResponse>(`${this.apiUrl}register`, formData);
  }

  registerCustomer(data: any, profilePicture?: File | null): Observable<CustomerResponse> {
    const formData = new FormData();
    formData.append('data', JSON.stringify(data));

    if (profilePicture) {
      formData.append('profile', profilePicture);
    }

    return this.http.post<CustomerResponse>(`${this.apiUrl}register`, formData);
  }

  setupMfa(email: string): Observable<any> {
    return this.http.post(`${this.apiUrl}setup-mfa`, { email });
  }

  confirmMfa(email: string, totpCode: string): Observable<any> {
    return this.http.post(`${this.apiUrl}confirm-mfa`, { email, totpCode });
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${this.apiUrl}forgot-password`, { email });
  }

  resetPassword(data: { token: string; newPassword: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}reset-password`, data);
  }

  hasRole(allowedRoles: Role[]): boolean {
    const user = this.currentUserValue;
    if (!user) return false;
    return allowedRoles.includes(user.role);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRefreshToken(): string | null {
    return localStorage.getItem('refreshToken');
  }

  private setTokens(response: LoginResponse): void {
    localStorage.setItem('token', response.token);
    localStorage.setItem('refreshToken', response.refreshToken);
    localStorage.setItem('user', JSON.stringify(response.user));
    this.currentUserSubject.next(response.user);
    this.scheduleAutoLogout(response.token);
  }

  private clearTokens(): void {
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
      this.tokenExpirationTimer = null;
    }
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');
    this.currentUserSubject.next(null);
  }

  private scheduleAutoLogout(token: string): void {
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      if (payload.exp) {
        const expiresInMs = payload.exp * 1000 - Date.now();
        if (expiresInMs > 0) {
          this.tokenExpirationTimer = setTimeout(() => {
            alert('Session expired. You have been logged out.');
            this.clearTokensAndRedirect();
          }, expiresInMs);
        } else {
          this.clearTokensAndRedirect();
        }
      }
    } catch {
      // Ignore invalid token format
    }
  }

  private loadUserFromToken(): void {
    const token = this.getToken();
    if (!token) return;

    let payload: any;
    try {
      payload = JSON.parse(atob(token.split('.')[1]));
      if (payload.exp && payload.exp * 1000 < Date.now()) {
        this.clearTokens();
        return;
      }
    } catch {
      this.clearTokens();
      return;
    }

    // Restore the full user (including `name`) persisted at login time.
    // The login response already carries the real name (mapped from the
    // customer/employee table on the backend), so no extra API call is needed.
    // The JWT itself only carries id/email/role, so without this the display
    // name would be lost on page refresh.
    const stored = localStorage.getItem('user');
    if (stored) {
      try {
        this.currentUserSubject.next(JSON.parse(stored) as User);
        this.scheduleAutoLogout(token);
        return;
      } catch {
        // fall through to JWT-based fallback
      }
    }

    this.currentUserSubject.next({
      id: payload.userId,
      email: payload.sub,
      role: payload.role as Role,
      name: '',
      profile: ''
    });
    this.scheduleAutoLogout(token);
  }
}
