import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = async (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  const token = authService.getToken();
  if (!token) {
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  if (authService.isTokenExpired()) {
    const refreshToken = authService.getRefreshToken();
    if (refreshToken) {
      try {
        await authService.refreshToken().toPromise();
        return true;
      } catch {
        authService.clearTokensAndRedirect();
        router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return false;
      }
    }
    authService.clearTokensAndRedirect();
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  const user = authService.currentUserValue;
  if (user) {
    return true;
  }

  router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
  return false;
};
