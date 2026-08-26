import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Role } from '../models/enums';

export const roleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  
  const expectedRoles: Role[] | undefined = route.data?.['roles'] as Role[] | undefined;
  const user = authService.currentUserValue;
  
  if (!user || !expectedRoles || expectedRoles.length === 0) {
    router.navigate(['/login']);
    return false;
  }
  
  const hasRole = expectedRoles.includes(user.role);
  if (!hasRole) {
    router.navigate(['/unauthorized']);
    return false;
  }
  
  return true;
};
