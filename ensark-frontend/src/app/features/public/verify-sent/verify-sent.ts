import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-verify-sent',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="min-h-screen bg-slate-950 flex items-center justify-center px-4">
      <div class="max-w-md w-full text-center space-y-8 animate-fade-in-up">
        <div class="w-20 h-20 mx-auto bg-emerald-500/10 border border-emerald-500/30 rounded-3xl flex items-center justify-center">
          <svg class="w-10 h-10 text-emerald-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
          </svg>
        </div>

        <div>
          <h1 class="text-2xl font-bold text-white">Check Your Email</h1>
          <p class="mt-3 text-slate-400 text-sm leading-relaxed">
            We've sent a verification link to your email address. Please check your inbox and click the link to activate your account.
          </p>
        </div>

        <div class="bg-slate-900/60 border border-slate-800 rounded-2xl p-5 text-sm text-slate-400 space-y-2">
          <p>If you don't see the email, check your <strong class="text-slate-300">spam/junk folder</strong>.</p>
          <p>The verification link will expire in <strong class="text-amber-400">1 hour</strong>.</p>
        </div>

        <div class="space-y-3">
          <a routerLink="/login" 
             class="block w-full py-3 bg-emerald-500 hover:bg-emerald-400 text-slate-950 font-bold text-sm rounded-xl transition-all shadow-lg shadow-emerald-500/20">
            Go to Login
          </a>
          <p class="text-xs text-slate-500">
            Didn't receive it? 
            <a routerLink="/login" class="text-emerald-400 hover:text-emerald-300 font-medium">Try again</a>
          </p>
        </div>
      </div>
    </div>
  `
})
export class VerifySentComponent {}
