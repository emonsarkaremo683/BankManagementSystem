import { Role, Gender, CustomerOccupation, CustomerStatus, KYCStatus, AddressType, DocumentType } from './enums';

export interface AddressResponse {
  id?: number;
  holdingNo: string;
  area: string;
  postalCode: string;
  addressType: AddressType;
  policeStationId?: number;
  policeStationName: string;
  districtId?: number;
  districtName?: string;
  divisionId?: number;
  divisionName?: string;
}

export interface AddressRequest {
  holdingNo: string;
  area: string;
  postalCode: string;
  addressType: AddressType;
  policeStation: { id: number };
}

export interface KycRequest {
  id?: number;
  path: string;
  doc_type: DocumentType;
}

export interface CustomerRequest {
  email: string;
  password?: string;
  name: string;
  gender: Gender;
  phone: string;
  occupation: CustomerOccupation;
  dob: Date | string;
  profile?: string;
  addresses: AddressRequest[];
  kycRequests?: KycRequest[];
}

export interface CustomerResponse {
  id: number;
  email: string;
  role: Role;
  isEmailVerified: boolean;
  active: boolean;
  name: string;
  gender: Gender;
  phone: string;
  occupation: CustomerOccupation;
  dob: Date | string;
  profile: string;
  profileUrl?: string;
  addresses: AddressResponse[];
  documents: KycRequest[];
  kycStatus: KYCStatus;
  status: CustomerStatus;
}
