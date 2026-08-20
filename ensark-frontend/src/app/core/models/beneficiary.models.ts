import { BeneficiaryType } from './enums';

/**
 * Mirrors customer_management.beneficiary.dto.request.BeneficiaryRequest /
 * dto.response.BeneficiaryResponse exactly. The previous version of this file used
 * accountNumber/bankName/holderType (HolderType, an unrelated AccountHolder enum) — none
 * of those fields exist on the backend DTOs, so every beneficiary create/update/list call
 * was silently sending/reading the wrong JSON shape.
 */
export interface BeneficiaryRequest {
  accNumber: string;
  name: string;
  provider: string;
  routingNumber?: string;
  beneficiaryType: BeneficiaryType;
  /** Ignored by BeneficiaryController#add (overwritten from the authenticated customer);
   *  not used by #update either, but harmless to include. */
  customerId?: number;
}

export interface BeneficiaryResponse {
  id: number;
  accNumber: string;
  name: string;
  provider: string;
  routingNumber?: string;
  beneficiaryType: BeneficiaryType;
  customerId: number;
  customerName: string;
  /** Backend BeneficiaryResponse declares `boolean isVerified`/`isBlocked`, but Lombok
   *  generates getters/setters as isVerified()/setVerified() and isBlocked()/setBlocked()
   *  (it strips a leading "is" from an already-"is"-prefixed boolean field), so Jackson
   *  serializes these as "verified"/"blocked", not "isVerified"/"isBlocked". Confirmed via
   *  BeneficiaryMapper#toResponse: `br.setVerified(b.isVerified()); br.setBlocked(b.isBlocked());`. */
  verified: boolean;
  blocked: boolean;
  blockReason?: string;
}
