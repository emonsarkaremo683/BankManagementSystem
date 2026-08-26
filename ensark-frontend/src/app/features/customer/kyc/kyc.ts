import { Component, OnInit, OnDestroy, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KycService, KycMyStatus, KycDocumentInfo } from '../../../core/services/kyc.service';
import { Badge, BadgeColor } from '../../../shared/components/badge/badge';
import {
  LucideShieldCheck,
  LucideCircleCheck,
  LucideClock,
  LucideCircleX,
  LucideUpload,
  LucideEye,
  LucideFileText,
  LucideLock,
} from '../../../shared/icons';

export interface DocUpload {
  type: string;
  label: string;
  description: string;
  file: File | null;
  preview: string | null;
  existingDoc: (KycDocumentInfo & { documentUrl?: string; mimeType?: string }) | null;
}

@Component({
  selector: 'app-customer-kyc',
  standalone: true,
  imports: [CommonModule, Badge, LucideShieldCheck, LucideCircleCheck, LucideClock, LucideCircleX, LucideUpload, LucideEye, LucideFileText, LucideLock],
  templateUrl: './kyc.html',
  styleUrl: './kyc.css'
})
export class CustomerKycComponent implements OnInit, OnDestroy {
  isLoading = true;
  isUploading = false;
  kycStatus: string = 'NOT_SUBMITTED';
  documents: (KycDocumentInfo & { documentUrl?: string; mimeType?: string })[] = [];
  uploadDocs: DocUpload[] = [];

  toastMessage: { type: 'success' | 'error'; text: string } | null = null;

  private objectUrls: string[] = [];

  private kycService = inject(KycService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.initUploadDocs();
    this.loadKycStatus();
  }

  ngOnDestroy(): void {
    this.objectUrls.forEach(url => URL.revokeObjectURL(url));
  }

  initUploadDocs(): void {
    this.uploadDocs = [
      {
        type: 'NID',
        label: 'National Identity Card (NID)',
        description: 'Upload front side of your official government NID card.',
        file: null,
        preview: null,
        existingDoc: null
      },
      {
        type: 'PASSPORT',
        label: 'International Passport',
        description: 'Upload clear photo page of your valid international passport.',
        file: null,
        preview: null,
        existingDoc: null
      },
      {
        type: 'DRIVING_LICENSE',
        label: 'Driving License',
        description: 'Upload front copy of your driver license.',
        file: null,
        preview: null,
        existingDoc: null
      },
      {
        type: 'BIRTH_CERTIFICATE',
        label: 'Birth Certificate',
        description: 'Upload official birth registration document.',
        file: null,
        preview: null,
        existingDoc: null
      },
    ];
  }

  loadKycStatus(): void {
    this.isLoading = true;
    // GET /api/kyc/my-status is scoped to the authenticated customer's own KYC record
    // (KycController#getMyKycStatus resolves customerId from the JWT via
    // CustomerSecurity#getAuthenticatedCustomerId — it never takes a caller-supplied id),
    // so every document id handled below is guaranteed to belong to this customer.
    this.kycService.getMyKycStatus().subscribe({
      next: (data: KycMyStatus) => {
        this.kycStatus = data?.status || 'NOT_SUBMITTED';
        const rawDocs = data?.documents || [];
        this.documents = rawDocs.map(d => ({ ...d }));
        this.mergeExistingDocs();
        this.isLoading = false;
        this.cdr.markForCheck();
        this.loadDocumentThumbnails();
      },
      error: (err) => {
        console.error('Error loading customer KYC status', err);
        this.kycStatus = 'NOT_SUBMITTED';
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  /**
   * GET /api/kyc/my-status only returns {id, doc_type} per document (no `path`), and the
   * file bytes at GET /api/kyc/documents/{id} require a Bearer Authorization header that a
   * plain <img src> can't attach (JwtAuthFilter dropped its old ?token= query-param
   * fallback as a token-leak risk). So thumbnails are fetched as blobs via HttpClient
   * (which the authInterceptor attaches the header to) and the blob's own MIME type is
   * used to tell images/PDFs apart, since there's no file extension to go on here.
   */
  private loadDocumentThumbnails(): void {
    this.documents.forEach(doc => {
      this.kycService.getDocumentBlob(doc.id).subscribe({
        next: (blob) => {
          const url = URL.createObjectURL(blob);
          this.objectUrls.push(url);
          doc.documentUrl = url;
          doc.mimeType = blob.type;
          this.mergeExistingDocs();
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Failed to load document thumbnail', doc.id, err)
      });
    });
  }

  mergeExistingDocs(): void {
    this.uploadDocs.forEach(ud => {
      ud.existingDoc = this.documents.find(d => d.doc_type === ud.type) || null;
    });
  }

  canUpload(): boolean {
    if (this.kycStatus === 'REJECTED') return true;
    if (this.kycStatus === 'NOT_SUBMITTED') return true;
    if (this.kycStatus === 'PENDING' && this.documents.length === 0) return true;
    return false;
  }

  onFileSelected(event: Event, docUpload: DocUpload): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.length) {
      const selectedFile = input.files[0];
      docUpload.file = selectedFile;

      const reader = new FileReader();
      reader.onload = () => {
        docUpload.preview = reader.result as string;
        this.cdr.markForCheck();
      };
      reader.readAsDataURL(selectedFile);
    }
  }

  removeFile(docUpload: DocUpload): void {
    docUpload.file = null;
    docUpload.preview = null;
    this.cdr.markForCheck();
  }

  hasAnyFiles(): boolean {
    return this.uploadDocs.some(ud => ud.file !== null);
  }

  onSubmit(): void {
    if (!this.hasAnyFiles() || this.isUploading) return;

    this.isUploading = true;
    const files: { [key: string]: File } = {};
    this.uploadDocs.forEach(ud => {
      if (ud.file) {
        files[ud.type] = ud.file;
      }
    });

    this.kycService.uploadMyDocuments(files).subscribe({
      next: () => {
        this.isUploading = false;
        this.showToast('success', 'KYC documents submitted successfully! Your account is now under review.');
        this.initUploadDocs();
        this.loadKycStatus();
      },
      error: (err) => {
        this.isUploading = false;
        console.error('Error submitting KYC documents', err);
        this.showToast('error', 'Failed to upload documents. Please try again.');
        this.cdr.markForCheck();
      }
    });
  }

  getStatusLabel(): string {
    switch (this.kycStatus) {
      case 'NOT_SUBMITTED': return 'Verification Required';
      case 'PENDING': return 'Submitted & Pending Review';
      case 'UNDER_REVIEW': return 'Under Compliance Review';
      case 'VERIFIED': return 'Verified Account';
      case 'REJECTED': return 'Submission Rejected';
      case 'EXPIRED': return 'Verification Expired';
      default: return this.kycStatus;
    }
  }

  statusBadgeColor(): BadgeColor {
    switch (this.kycStatus) {
      case 'VERIFIED': return 'success';
      case 'PENDING':
      case 'UNDER_REVIEW': return 'warning';
      case 'REJECTED': return 'danger';
      default: return 'neutral';
    }
  }

  showToast(type: 'success' | 'error', text: string): void {
    this.toastMessage = { type, text };
    setTimeout(() => {
      this.toastMessage = null;
      this.cdr.markForCheck();
    }, 4000);
  }
}
