import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CardService } from '../../../../core/services/card.service';
import { CardResponse } from '../../../../core/models/card.models';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucidePlus, LucideSearch, LucideCreditCard, LucideCircleCheck, LucideBan, LucideClock } from '../../../../shared/icons';

@Component({
  selector: 'app-card-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, StatCard, Badge, LucidePlus, LucideSearch, LucideCreditCard, LucideCircleCheck, LucideBan, LucideClock],
  templateUrl: './card-list.html',
  styleUrl: './card-list.css'
})
export class CardListComponent implements OnInit {
  cards: CardResponse[] = [];
  filteredCards: CardResponse[] = [];
  isLoading = true;
  searchTerm = '';

  private cardService = inject(CardService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadCards();
  }

  loadCards(): void {
    this.isLoading = true;
    this.cardService.getAll().subscribe({
      next: (data) => {
        this.cards = data;
        this.filteredCards = data;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching cards', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredCards = this.cards;
      return;
    }
    this.filteredCards = this.cards.filter(c =>
      c.cardNumber?.toLowerCase().includes(term) ||
      c.cardHolderName?.toLowerCase().includes(term) ||
      c.accountNumber?.toLowerCase().includes(term)
    );
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

  get activeCount(): number {
    return this.cards.filter(c => c.status === 'ACTIVE').length;
  }

  get inactiveCount(): number {
    return this.cards.filter(c => c.status === 'BLOCKED' || c.status === 'DISABLED' || c.status === 'EXPIRED').length;
  }

  get pendingCount(): number {
    return this.cards.filter(c => c.status === 'PENDING').length;
  }
}
