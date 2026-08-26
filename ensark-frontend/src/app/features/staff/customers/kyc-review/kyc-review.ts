import { Component, OnInit, OnDestroy, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { CustomerService } from '../../../../core/services/customer.service';
import { KycService, KycDocumentInfo } from '../../../../core/services/kyc.service';
import { CustomerResponse } from '../../../../core/models/customer.models';
import { KYCStatus } from '../../../../core/models/enums';
import { StatCard } from '../../../../shared/components/stat-card/stat-card';
import { Badge, BadgeColor } from '../../../../shared/components/badge/badge';
import {
  LucideShieldCheck,
  LucideUsers,
  LucideClock,
  LucideCircleCheck,
  LucideCircleX,
  LucideSearch,
  LucideRefreshCw,
  LucideFileText,
  LucideEye,
  LucideDownload,
  LucideX,
} from '../../../../shared/icons';

type DisplayDoc = KycDocumentInfo & { documentUrl?: string; mimeType?: string; loadFailed?: boolean };

@Component({
  selector: 'app-kyc-review',
  standalone: true,
  imports: [CommonModule, FormsModule, StatCard, Badge,
    LucideShieldCheck, LucideUsers, LucideClock, LucideCircleCheck, LucideCircleX,
    LucideSearch, LucideRefreshCw, LucideFileText, LucideEye, LucideDownload, LucideX],
  templateUrl: './kyc-review.html',
  styleUrl: './kyc-review.css'
})
export class KycReviewComponent implements OnInit, OnDestroy {
  allCustomers: (CustomerResponse & { profileUrl?: string })[] = [];
  filteredCustomers: (CustomerResponse & { profileUrl?: string })[] = [];
  isLoading = true;
  isLoadingDocs = false;
  searchTerm = '';
  activeTab: 'PENDING' | 'VERIFIED' | 'REJECTED' | 'ALL' = 'PENDING';

  selectedCustomer: (CustomerResponse & { profileUrl?: string }) | null = null;
  kycDocuments: DisplayDoc[] = [];

  // Rejection Modal state
  showRejectModal = false;
  rejectionReason = '';
  isSubmittingStatus = false;

  // Document Viewer Modal state
  previewDocument: (DisplayDoc & { safeDocumentUrl?: any }) | null = null;

  // Stats
  totalCount = 0;
  pendingCount = 0;
  verifiedCount = 0;
  rejectedCount = 0;

  toastMessage: { type: 'success' | 'error'; text: string } | null = null;

  private objectUrls: string[] = [];

  private customerService = inject(CustomerService);
  private kycService = inject(KycService);
  private cdr = inject(ChangeDetectorRef);
  private sanitizer = inject(DomSanitizer);

  ngOnInit(): void {
    this.loadAllCustomers();
  }

  ngOnDestroy(): void {
    this.revokeObjectUrls();
  }

  private revokeObjectUrls(): void {
    this.objectUrls.forEach(url => URL.revokeObjectURL(url));
    this.objectUrls = [];
  }

  loadAllCustomers(): void {
    this.isLoading = true;
    this.customerService.getAll().subscribe({
      next: (data) => {
        this.allCustomers = (data || []).map(c => ({
          ...c,
          profileUrl: this.kycService.getProfileUrl(c.profile)
        }));
        this.calculateStats();
        this.applyFilters();

        // Auto-select first customer in current filter list if none selected
        if (!this.selectedCustomer && this.filteredCustomers.length > 0) {
          this.selectCustomer(this.filteredCustomers[0]);
        }

        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching customers for KYC review', err);
        this.showToast('error', 'Failed to load customer list. Please refresh.');
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  calculateStats(): void {
    this.totalCount = this.allCustomers.length;
    this.pendingCount = this.allCustomers.filter(c => c.kycStatus === 'PENDING' || c.kycStatus === 'UNDER_REVIEW').length;
    this.verifiedCount = this.allCustomers.filter(c => c.kycStatus === 'VERIFIED').length;
    this.rejectedCount = this.allCustomers.filter(c => c.kycStatus === 'REJECTED').length;
  }

  setFilterTab(tab: 'PENDING' | 'VERIFIED' | 'REJECTED' | 'ALL'): void {
    this.activeTab = tab;
    this.applyFilters();
    if (this.filteredCustomers.length > 0) {
      this.selectCustomer(this.filteredCustomers[0]);
    } else {
      this.selectedCustomer = null;
      this.kycDocuments = [];
    }
  }

  onSearch(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    let result = [...this.allCustomers];

    if (this.activeTab === 'PENDING') {
      result = result.filter(c => c.kycStatus === 'PENDING' || c.kycStatus === 'UNDER_REVIEW');
    } else if (this.activeTab === 'VERIFIED') {
      result = result.filter(c => c.kycStatus === 'VERIFIED');
    } else if (this.activeTab === 'REJECTED') {
      result = result.filter(c => c.kycStatus === 'REJECTED');
    }

    const term = this.searchTerm.toLowerCase().trim();
    if (term) {
      result = result.filter(c =>
        c.name?.toLowerCase().includes(term) ||
        c.email?.toLowerCase().includes(term) ||
        c.phone?.toLowerCase().includes(term)
      );
    }

    this.filteredCustomers = result;
  }

  selectCustomer(customer: CustomerResponse & { profileUrl?: string }): void {
    this.selectedCustomer = {
      ...customer,
      profileUrl: this.kycService.getProfileUrl(customer.profile)
    };
    this.isLoadingDocs = true;
    this.revokeObjectUrls();
    this.kycDocuments = [];

    // GET /api/kyc/customer/{customerId} returns the full Kyc entity (id, status,
    // documents[]) for staff roles — no need to hit /documents/{id}/info per document.
    this.kycService.getByCustomerId(customer.id).subscribe({
      next: (kycData: any) => {
        const rawDocs: KycDocumentInfo[] = kycData?.documents || [];
        this.kycDocuments = rawDocs.map(d => ({ ...d }));
        this.isLoadingDocs = false;
        this.cdr.markForCheck();
        this.loadDocumentThumbnails();
      },
      error: (err) => {
        console.error('Error loading KYC docs for customer', customer.id, err);
        this.kycDocuments = [];
        this.isLoadingDocs = false;
        this.cdr.markForCheck();
      }
    });
  }

  /**
   * The document bytes live behind GET /api/kyc/documents/{id}, which requires a Bearer
   * Authorization header (JwtAuthFilter no longer accepts a ?token= query param — that
   * fallback was removed as a token-leak risk). A plain <img src="..."> can't attach that
   * header, so each document is fetched via HttpClient and turned into a blob: object URL.
   */
  private loadDocumentThumbnails(): void {
    this.kycDocuments.forEach(doc => {
      this.kycService.getDocumentBlob(doc.id).subscribe({
        next: (blob) => {
          const url = URL.createObjectURL(blob);
          this.objectUrls.push(url);
          doc.documentUrl = url;
          doc.mimeType = blob.type;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Failed to load document', doc.id, err);
          doc.loadFailed = true;
          this.cdr.markForCheck();
        }
      });
    });
  }

  approveKyc(): void {
    if (!this.selectedCustomer || this.isSubmittingStatus) return;
    this.updateKycStatus(this.selectedCustomer.id, KYCStatus.VERIFIED);
  }

  openRejectModal(): void {
    this.rejectionReason = '';
    this.showRejectModal = true;
  }

  closeRejectModal(): void {
    this.showRejectModal = false;
    this.rejectionReason = '';
  }

  confirmRejectKyc(): void {
    if (!this.selectedCustomer || this.isSubmittingStatus) return;
    this.updateKycStatus(this.selectedCustomer.id, KYCStatus.REJECTED);
  }

  private updateKycStatus(customerId: number, status: KYCStatus): void {
    this.isSubmittingStatus = true;
    this.kycService.updateStatus(customerId, status).subscribe({
      next: () => {
        this.isSubmittingStatus = false;
        this.closeRejectModal();
        this.showToast('success', `KYC status for customer #${customerId} updated to ${status}.`);

        if (this.selectedCustomer && this.selectedCustomer.id === customerId) {
          this.selectedCustomer.kycStatus = status;
        }

        this.loadAllCustomers();
      },
      error: (err) => {
        this.isSubmittingStatus = false;
        console.error('Error updating KYC status', err);
        this.showToast('error', 'Failed to update KYC status. Please try again.');
        this.cdr.markForCheck();
      }
    });
  }

  openDocumentPreview(doc: DisplayDoc): void {
    this.previewDocument = doc;
    if (this.isPdfDocument(doc) && doc.documentUrl) {
      this.previewDocument = {
        ...doc,
        safeDocumentUrl: this.sanitizer.bypassSecurityTrustResourceUrl(doc.documentUrl)
      };
    }
  }

  closeDocumentPreview(): void {
    this.previewDocument = null;
  }

  kycBadgeColor(status: string): BadgeColor {
    switch (status) {
      case 'VERIFIED': return 'success';
      case 'PENDING':
      case 'UNDER_REVIEW': return 'warning';
      case 'REJECTED': return 'danger';
      default: return 'neutral';
    }
  }

  getFileExtension(path?: string): string {
    if (!path) return '';
    const parts = path.split('.');
    if (parts.length > 1) {
      return parts.pop()!.toLowerCase();
    }
    return '';
  }

  isPdfDocument(doc: DisplayDoc): boolean {
    if (doc.mimeType) return doc.mimeType === 'application/pdf';
    return this.getFileExtension(doc.path) === 'pdf';
  }

  isImageDocument(doc: DisplayDoc): boolean {
    if (doc.mimeType) return doc.mimeType.startsWith('image/');
    const ext = this.getFileExtension(doc.path);
    const imageExtensions = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp', 'svg', 'tiff', 'tif'];
    return imageExtensions.includes(ext);
  }

  getDocumentTypeLabel(doc: DisplayDoc): string {
    if (this.isPdfDocument(doc)) return 'PDF Document';
    if (this.isImageDocument(doc)) return 'Image';
    return 'Document';
  }

  showToast(type: 'success' | 'error', text: string): void {
    this.toastMessage = { type, text };
    setTimeout(() => {
      this.toastMessage = null;
      this.cdr.markForCheck();
    }, 4000);
  }
}
