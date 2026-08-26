import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AtmService } from '../../../../core/services/atm.service';
import { ATMResponse } from '../../../../core/models/atm.models';
import { LucideCoins, LucideCircleDollarSign } from '../../../../shared/icons';

@Component({
  selector: 'app-atm-refill',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, LucideCoins, LucideCircleDollarSign],
  templateUrl: './atm-refill.html',
  styleUrl: './atm-refill.css'
})
export class AtmRefillComponent implements OnInit {
  private atmService = inject(AtmService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  atm?: ATMResponse;
  amount = 100000;
  isSubmitting = false;
  errorMessage = '';

  ngOnInit() {
    const id = +this.route.snapshot.params['id'];
    this.atmService.getById(id).subscribe(data => this.atm = data);
  }

  onRefill() {
    if (!this.atm || !this.amount || this.amount <= 0) return;
    this.isSubmitting = true;
    this.errorMessage = '';
    this.atmService.refill(this.atm.atmId, this.amount).subscribe({
      next: () => {
        this.router.navigate(['/staff/atms', this.atm?.atmId]);
      },
      error: () => {
        this.isSubmitting = false;
        this.errorMessage = 'Refill failed. Please try again.';
      }
    });
  }
}
