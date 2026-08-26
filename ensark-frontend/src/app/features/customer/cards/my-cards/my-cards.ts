import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CardService } from '../../../../core/services/card.service';
import { AuthService } from '../../../../core/services/auth.service';
import { CardResponse } from '../../../../core/models/card.models';
import { CardNetwork } from '../../../../core/models/enums';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import { LucidePlus, LucideCreditCard, LucideCircleCheck, LucideBan, LucideClock, LucideEye, LucideEyeOff } from '../../../../shared/icons';

@Component({
  selector: 'app-my-cards',
  standalone: true,
  imports: [CommonModule, RouterLink, StatCard, Badge, LucidePlus, LucideCreditCard, LucideCircleCheck, LucideBan, LucideClock, LucideEye, LucideEyeOff],
  templateUrl: './my-cards.html',
  styleUrl: './my-cards.css'
})
export class MyCardsComponent implements OnInit {
  cards: CardResponse[] = [];
  isLoading = true;
  visibleCards = new Set<number>();

  private cardService = inject(CardService);
  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadCards();
  }

  loadCards(): void {
    this.isLoading = true;
    const user = this.authService.currentUserValue;
    if (user?.email) {
      this.cardService.getByCustomerEmail(user.email).subscribe({
        next: (data) => {
          this.cards = data;
          this.isLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error fetching cards', err);
          this.isLoading = false;
          this.cdr.markForCheck();
        }
      });
    } else {
      this.isLoading = false;
    }
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

  maskedCardNumber(cardNumber: string): string {
    if (!cardNumber) return '•••• •••• •••• ••••';
    const last4 = cardNumber.slice(-4);
    return `•••• •••• •••• ${last4}`;
  }

  formatCardNumber(cardNumber: string): string {
    if (!cardNumber) return '';
    return cardNumber.replace(/(.{4})/g, '$1 ').trim();
  }

  isCardVisible(cardId: number): boolean {
    return this.visibleCards.has(cardId);
  }

  toggleCardVisibility(cardId: number): void {
    if (this.visibleCards.has(cardId)) {
      this.visibleCards.delete(cardId);
    } else {
      this.visibleCards.add(cardId);
    }
  }

  cardBgClass(card: CardResponse): string {
    if (card.cardNetwork === CardNetwork.MASTERCARD) return 'card-mc';
    return 'card-visa';
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
