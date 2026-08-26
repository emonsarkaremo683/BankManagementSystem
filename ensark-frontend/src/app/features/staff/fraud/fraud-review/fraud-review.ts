import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FraudService } from '../../../../core/services/fraud.service';
import { FraudFlagResponse } from '../../../../core/models/fraud.models';
import { FraudFlagStatus } from '../../../../core/models/enums';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideShieldAlert,
  LucideArrowLeft,
  LucideTriangleAlert,
  LucideMapPin,
  LucideSmartphone,
  LucideGlobe,
  LucideCircleAlert,
} from '../../../../shared/icons';

@Component({
  selector: 'app-fraud-review',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    Badge,
    LucideShieldAlert,
    LucideArrowLeft,
    LucideTriangleAlert,
    LucideMapPin,
    LucideSmartphone,
    LucideGlobe,
    LucideCircleAlert,
  ],
  templateUrl: './fraud-review.html',
  styleUrl: './fraud-review.css'
})
export class FraudReviewComponent implements OnInit {
  private fraudService = inject(FraudService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  alert?: FraudFlagResponse;
  isLoading = true;
  notFound = false;
  selectedStatus: FraudFlagStatus = FraudFlagStatus.CONFIRMED_FRAUD;
  selectedAction = 'FREEZE_ACCOUNT';
  notes = '';
  isSubmitting = false;

  ngOnInit() {
    const id = +this.route.snapshot.params['id'];
    this.fraudService.getFraudAlertById(id).subscribe({
      next: (data) => {
        this.isLoading = false;
        if (!data) {
          this.notFound = true;
          return;
        }
        this.alert = data;
        this.selectedStatus = data.status;
        this.notes = data.reviewNotes || '';
      },
      error: () => {
        this.isLoading = false;
        this.notFound = true;
      }
    });
  }

  onSaveReview() {
    if (!this.alert) return;
    this.isSubmitting = true;
    this.fraudService.reviewFraudAlert(this.alert.id, {
      status: this.selectedStatus,
      notes: this.notes,
      actionTaken: this.selectedAction as any
    }).subscribe({
      next: () => {
        this.router.navigate(['/staff/fraud']);
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }

  riskColor(risk: string): BadgeColor {
    switch (risk) {
      case 'CRITICAL':
      case 'HIGH':
        return 'danger';
      case 'MEDIUM':
        return 'warning';
      default:
        return 'neutral';
    }
  }

  statusColor(status: string): BadgeColor {
    switch (status) {
      case 'PENDING':
        return 'warning';
      case 'CONFIRMED_FRAUD':
        return 'danger';
      case 'FALSE_POSITIVE':
      case 'RESOLVED':
        return 'success';
      case 'REVIEWED':
        return 'info';
      default:
        return 'neutral';
    }
  }
}
