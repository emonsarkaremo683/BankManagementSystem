import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-unauthorized',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './unauthorized.html',
  styleUrl: './unauthorized.css'
})
export class UnauthorizedComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  goHome() {
    const user = this.authService.currentUserValue;
    if (user) {
      if (user.role === 'CUSTOMER') {
        this.router.navigate(['/customer/dashboard']);
      } else {
        this.router.navigate(['/staff/dashboard']);
      }
    } else {
      this.router.navigate(['/login']);
    }
  }
}
