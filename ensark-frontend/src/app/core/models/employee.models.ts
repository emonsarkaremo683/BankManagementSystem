import { Role, Gender, Designation, EmployeeStatus } from './enums';

export interface EmployeeAddressResponse {
  id?: number;
  holdingNo: string;
  area: string;
  postalCode: string;
  addressType: string;
  policeStation?: {
    id: number;
    name?: string;
  };
}

export interface EmployeeRequest {
  email: string;
  password?: string;
  role: Role;
  branchId: number;
  name: string;
  gender: Gender;
  phone: string;
  designation: Designation;
  dob: string | Date;
  profile?: string;
  addresses: any[];
}

export interface EmployeeResponse {
  user_id?: number;
  id: number;
  email: string;
  role: Role;
  isEmailVerified?: boolean;
  active?: boolean;
  branchId: number;
  branchName?: string;
  name: string;
  gender: Gender;
  phone: string;
  designation: Designation;
  dob: string | Date;
  profile: string;
  status: EmployeeStatus;
  addresses: EmployeeAddressResponse[];
  imageUrl?: string;
  createdAt?: string;
}

/**
 * Human-readable label for each Designation, mirrors Designation#getDisplayName()
 * in com.elitetech_inc.ensarkbank.common.enums.Designation.
 */
export const DESIGNATION_LABELS: Record<Designation, string> = {
  [Designation.CHIEF_EXECUTIVE_OFFICER]: 'Chief Executive Officer',
  [Designation.MANAGING_DIRECTOR]: 'Managing Director',
  [Designation.DEPUTY_MANAGING_DIRECTOR]: 'Deputy Managing Director',
  [Designation.GENERAL_MANAGER]: 'General Manager',
  [Designation.DEPUTY_GENERAL_MANAGER]: 'Deputy General Manager',
  [Designation.ASSISTANT_GENERAL_MANAGER]: 'Assistant General Manager',
  [Designation.BRANCH_MANAGER]: 'Branch Manager',
  [Designation.ASSISTANT_BRANCH_MANAGER]: 'Assistant Branch Manager',
  [Designation.OPERATIONS_MANAGER]: 'Operations Manager',
  [Designation.TELLER]: 'Teller',
  [Designation.CASH_OFFICER]: 'Cash Officer',
  [Designation.CUSTOMER_SERVICE_OFFICER]: 'Customer Service Officer',
  [Designation.RELATIONSHIP_MANAGER]: 'Relationship Manager',
  [Designation.LOAN_OFFICER]: 'Loan Officer',
  [Designation.ACCOUNTS_OFFICER]: 'Accounts Officer',
  [Designation.COMPLIANCE_OFFICER]: 'Compliance Officer',
  [Designation.AUDIT_OFFICER]: 'Audit Officer',
  [Designation.SYSTEM_ADMINISTRATOR]: 'System Administrator',
  [Designation.SOFTWARE_ENGINEER]: 'Software Engineer',
  [Designation.NETWORK_ENGINEER]: 'Network Engineer',
  [Designation.DATABASE_ADMINISTRATOR]: 'Database Administrator',
  [Designation.HR_OFFICER]: 'HR Officer',
  [Designation.ADMIN_OFFICER]: 'Admin Officer',
  [Designation.FINANCE_OFFICER]: 'Finance Officer',
  [Designation.TREASURY_OFFICER]: 'Treasury Officer',
  [Designation.SECURITY_OFFICER]: 'Security Officer',
  [Designation.OFFICE_ASSISTANT]: 'Office Assistant',
  [Designation.INTERN]: 'Intern',
};

/**
 * Default Role for each Designation, mirrors Designation#getDefaultRole() on the backend.
 * Purely a UX convenience (auto-fill the Role select when a Designation is chosen) — the
 * backend does not derive role from designation itself, both are sent independently in
 * EmployeeRequest, so this mapping is not authoritative and the user can still override it.
 */
export const DESIGNATION_DEFAULT_ROLE: Record<Designation, Role> = {
  [Designation.CHIEF_EXECUTIVE_OFFICER]: Role.SUPER_ADMIN,
  [Designation.MANAGING_DIRECTOR]: Role.SUPER_ADMIN,
  [Designation.DEPUTY_MANAGING_DIRECTOR]: Role.ADMIN,
  [Designation.GENERAL_MANAGER]: Role.ADMIN,
  [Designation.DEPUTY_GENERAL_MANAGER]: Role.ADMIN,
  [Designation.ASSISTANT_GENERAL_MANAGER]: Role.ADMIN,
  [Designation.BRANCH_MANAGER]: Role.BRANCH_MANAGER,
  [Designation.ASSISTANT_BRANCH_MANAGER]: Role.BRANCH_MANAGER,
  [Designation.OPERATIONS_MANAGER]: Role.BRANCH_MANAGER,
  [Designation.TELLER]: Role.CASHIER,
  [Designation.CASH_OFFICER]: Role.CASHIER,
  [Designation.CUSTOMER_SERVICE_OFFICER]: Role.CUSTOMER_SERVICE,
  [Designation.RELATIONSHIP_MANAGER]: Role.CUSTOMER_SERVICE,
  [Designation.LOAN_OFFICER]: Role.LOAN_OFFICER,
  [Designation.ACCOUNTS_OFFICER]: Role.ACCOUNTANT,
  [Designation.COMPLIANCE_OFFICER]: Role.AUDITOR,
  [Designation.AUDIT_OFFICER]: Role.AUDITOR,
  [Designation.SYSTEM_ADMINISTRATOR]: Role.ADMIN,
  [Designation.SOFTWARE_ENGINEER]: Role.ADMIN,
  [Designation.NETWORK_ENGINEER]: Role.ADMIN,
  [Designation.DATABASE_ADMINISTRATOR]: Role.ADMIN,
  [Designation.HR_OFFICER]: Role.ADMIN,
  [Designation.ADMIN_OFFICER]: Role.ADMIN,
  [Designation.FINANCE_OFFICER]: Role.ACCOUNTANT,
  [Designation.TREASURY_OFFICER]: Role.ACCOUNTANT,
  [Designation.SECURITY_OFFICER]: Role.ADMIN,
  [Designation.OFFICE_ASSISTANT]: Role.CUSTOMER_SERVICE,
  [Designation.INTERN]: Role.CUSTOMER_SERVICE,
};
