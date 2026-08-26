import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CurrencyService } from '../../../core/services/currency.service';
import { Currency } from '../../../core/models/enums';
import { CurrencyResponse, CurrencyConversionResponse } from '../../../core/models/currency.models';
import { LucideArrowLeftRight, LucideCircleDollarSign } from '../../../shared/icons';

@Component({
  selector: 'app-currency-converter',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideArrowLeftRight, LucideCircleDollarSign],
  templateUrl: './currency-converter.html',
  styleUrl: './currency-converter.css'
})
export class CurrencyConverterComponent implements OnInit {
  private currencyService = inject(CurrencyService);

  currencies = Object.values(Currency);
  fromCurrency = Currency.USD;
  toCurrency = Currency.BDT;
  amount = 100;

  conversionResult?: CurrencyConversionResponse;
  popularRates: CurrencyResponse[] = [];

  ngOnInit() {
    this.currencyService.getRates(Currency.BDT).subscribe({
      next: (rates) => this.popularRates = rates || [],
      error: () => this.popularRates = []
    });
    this.onConvert();
  }

  onConvert() {
    if (!this.amount || this.amount <= 0) return;
    this.currencyService.convert({
      fromCurrency: this.fromCurrency,
      toCurrency: this.toCurrency,
      amount: this.amount
    }).subscribe({
      next: (res) => this.conversionResult = res,
      error: () => {}
    });
  }

  swapCurrencies() {
    const temp = this.fromCurrency;
    this.fromCurrency = this.toCurrency;
    this.toCurrency = temp;
    this.onConvert();
  }
}
