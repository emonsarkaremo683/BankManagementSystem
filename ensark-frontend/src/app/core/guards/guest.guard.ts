import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Role } from '../models/enums';

export const guestGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if (!authService.isTokenExpired() && authService.getToken() && authService.currentUserValue) {
    const user = authService.currentUserValue;
    if (user.role === Role.CUSTOMER) {
      router.navigate(['/customer/dashboard']);
    } else {
      router.navigate(['/staff/dashboard']);
    }
    return false;
  }

  return true;
};
