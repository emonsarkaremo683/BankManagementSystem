import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PublicService } from '../../../core/services/public.service';
import { ExchangeRateResponse, PublicExchangeRate } from '../../../core/models/public.models';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class HomeComponent implements OnInit {
  private publicService = inject(PublicService);

  exchangeRates: PublicExchangeRate[] = [];
  lastUpdated: string = '';

  ngOnInit() {
    this.loadExchangeRates();
  }

  loadExchangeRates() {
    this.publicService.getAllExchangeRates().subscribe({
      next: (data) => {
        this.exchangeRates = data.rates;
        this.lastUpdated = data.lastUpdated;
      },
      error: (err) => { console.error('Exchange rates load failed:', err); }
    });
  }
}
