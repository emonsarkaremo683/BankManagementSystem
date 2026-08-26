import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-mfa-verify',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './mfa-verify.html',
  styleUrl: './mfa-verify.css'
})
export class MfaVerifyComponent {
  mfaForm: FormGroup;
  isLoading = false;
  errorMessage = '';
  email = '';

  private authService = inject(AuthService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);

  constructor() {
    this.mfaForm = this.fb.group({
      code: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]]
    });
    this.email = this.route.snapshot.queryParams['email'] || '';
  }

  onSubmit() {
    if (this.mfaForm.invalid) {
      this.mfaForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.verifyMfa({ email: this.email, totpCode: this.mfaForm.value.code }).subscribe({
      next: (res) => {
        this.isLoading = false;
        if (res.user.role === 'CUSTOMER') {
          this.router.navigate(['/customer/dashboard']);
        } else {
          this.router.navigate(['/staff/dashboard']);
        }
      },
      error: (err: any) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Invalid code. Please try again.';
      }
    });
  }
}
