import { Role } from './enums';

export interface LoginResponse<T = User> {
  token: string;
  refreshToken: string;
  tokenType: string;
  name: string;
  user: T;
  mfaRequired: boolean;
  mfaSecret?: string;
  mfaQrCode?: string;
}

export interface MfaVerifyRequest {
  email: string;
  totpCode: string;
}

export interface User {
  id: number;
  email: string;
  role: Role;
  name: string;
  profile: string;
}
