import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CardService } from '../../../../core/services/card.service';
import { CardResponse } from '../../../../core/models/card.models';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucideArrowLeft } from '../../../../shared/icons';

@Component({
  selector: 'app-customer-card-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, Badge, LucideArrowLeft],
  templateUrl: './card-detail.html',
  styleUrl: './card-detail.css'
})
export class CustomerCardDetailComponent implements OnInit {
  card: CardResponse | null = null;
  isLoading = true;

  private cardService = inject(CardService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadCard(id);
    }
  }

  loadCard(id: number): void {
    this.cardService.getById(id).subscribe({
      next: (data) => {
        this.card = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading card', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  statusColor(status: string | undefined): BadgeColor {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'PENDING': return 'warning';
      case 'BLOCKED': case 'DISABLED': case 'EXPIRED': return 'danger';
      case 'CLOSED': return 'neutral';
      default: return 'neutral';
    }
  }
}
