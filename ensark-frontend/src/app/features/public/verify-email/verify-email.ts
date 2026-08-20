import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-verify-email',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="min-h-screen bg-slate-950 flex items-center justify-center px-4">
      <div class="max-w-md w-full text-center space-y-8 animate-fade-in-up">
        
        <!-- Loading -->
        <div *ngIf="isLoading" class="space-y-4">
          <div class="w-20 h-20 mx-auto bg-slate-800 border border-slate-700 rounded-3xl flex items-center justify-center">
            <div class="w-8 h-8 border-2 border-emerald-400 border-t-transparent rounded-full animate-spin"></div>
          </div>
          <h1 class="text-xl font-bold text-white">Verifying your email...</h1>
          <p class="text-slate-400 text-sm">Please wait while we verify your account.</p>
        </div>

        <!-- Success -->
        <div *ngIf="!isLoading && success" class="space-y-4">
          <div class="w-20 h-20 mx-auto bg-emerald-500/10 border border-emerald-500/30 rounded-3xl flex items-center justify-center">
            <svg class="w-10 h-10 text-emerald-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
          </div>
          <h1 class="text-2xl font-bold text-white">Email Verified!</h1>
          <p class="text-slate-400 text-sm">Your account has been activated. You can now log in to access your banking portal.</p>
          <a routerLink="/login" 
             class="inline-block px-8 py-3 bg-emerald-500 hover:bg-emerald-400 text-slate-950 font-bold text-sm rounded-xl transition-all shadow-lg shadow-emerald-500/20">
            Go to Login
          </a>
        </div>

        <!-- Error -->
        <div *ngIf="!isLoading && !success" class="space-y-4">
          <div class="w-20 h-20 mx-auto bg-red-500/10 border border-red-500/30 rounded-3xl flex items-center justify-center">
            <svg class="w-10 h-10 text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </div>
          <h1 class="text-2xl font-bold text-white">Verification Failed</h1>
          <p class="text-slate-400 text-sm">{{ errorMessage }}</p>
          <a routerLink="/login" 
             class="inline-block px-8 py-3 bg-slate-800 hover:bg-slate-700 text-white font-bold text-sm rounded-xl transition-all border border-slate-700">
            Go to Login
          </a>
        </div>

      </div>
    </div>
  `
})
export class VerifyEmailComponent implements OnInit {
  isLoading = true;
  success = false;
  errorMessage = '';

  private route = inject(ActivatedRoute);
  private http = inject(HttpClient);
  private router = inject(Router);

  ngOnInit(): void {
    const token = this.route.snapshot.queryParamMap.get('token');
    if (!token) {
      this.isLoading = false;
      this.success = false;
      this.errorMessage = 'No verification token found. Please request a new verification email.';
      return;
    }

    this.http.get(`${environment.apiUrl}auth/verify-email?token=${token}`, { responseType: 'text' }).subscribe({
      next: () => {
        this.isLoading = false;
        this.success = true;
      },
      error: (err) => {
        this.isLoading = false;
        this.success = false;
        this.errorMessage = err.error?.message || err.error || 'Verification link is invalid or has expired. Please request a new one.';
      }
    });
  }
}
