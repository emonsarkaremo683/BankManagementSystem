import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError, switchMap, filter, take } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

let isRefreshing = false;
let refreshTokenSubject = new BehaviorSubject<any>(null);

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (req.url.includes('/api/public/') || req.url.includes('/api/auth/')) {
    return next(req);
  }

  const token = authService.getToken();

  if (token) {
    req = addTokenHeader(req, token);
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !req.url.includes('auth/login') && !req.url.includes('auth/refresh')) {
        return handle401Error(req, next, authService, router);
      }
      return throwError(() => error);
    })
  );
};

function addTokenHeader(request: HttpRequest<any>, token: string) {
  return request.clone({
    headers: request.headers.set('Authorization', `Bearer ${token}`)
  });
}

function handle401Error(request: HttpRequest<any>, next: HttpHandlerFn, authService: AuthService, router: Router) {
  if (!isRefreshing) {
    isRefreshing = true;
    refreshTokenSubject.next(null);

    const refreshToken = authService.getRefreshToken();
    if (refreshToken) {
      return authService.refreshToken().pipe(
        switchMap((response: any) => {
          isRefreshing = false;
          refreshTokenSubject.next(response.token);
          return next(addTokenHeader(request, response.token));
        }),
        catchError((err) => {
          isRefreshing = false;
          refreshTokenSubject.error(new Error('Token refresh failed'));
          refreshTokenSubject = new BehaviorSubject<any>(null);
          authService.logout();
          router.navigate(['/login']);
          return throwError(() => err);
        })
      );
    } else {
      isRefreshing = false;
      refreshTokenSubject.error(new Error('No refresh token'));
      refreshTokenSubject = new BehaviorSubject<any>(null);
      authService.logout();
      router.navigate(['/login']);
      return throwError(() => new Error('No refresh token'));
    }
  }

  return refreshTokenSubject.pipe(
    filter(token => token !== null),
    take(1),
    switchMap(token => next(addTokenHeader(request, token)))
  );
}
