import { BranchType, BranchStatus } from './enums';

/**
 * BranchController takes/returns the raw JPA `Branch` entity (no dedicated
 * DTO package on the backend), so this shape must mirror
 * branch_management.branch.entity.Branch field-for-field:
 *  - branchCode / routingNumber are generated server-side on create — never send them.
 *  - parentBranch is only honoured when type === AGENT_BANK (BranchServiceImpl
 *    .resolveParentBranch), and must be sent nested as { id } (Jackson deserializes
 *    into the entity's `parentBranch` field, not a flat `parentBranchId`).
 *  - parentBranch is annotated @JsonProperty(Access.WRITE_ONLY) on the entity, so it is
 *    ACCEPTED on write but never comes back in a GET/response — a loaded BranchResponse's
 *    parentBranch will always be undefined, even for an existing agent-bank branch.
 *  - policeStation must also be sent nested as { id }.
 */
export interface BranchRequest {
  name: string;
  address: string;
  email: string;
  phoneNumber: string;
  type: BranchType;
  status?: BranchStatus;
  parentBranch?: { id: number } | null;
  policeStation?: { id: number } | null;
}

export interface BranchResponse {
  id: number;
  name: string;
  address: string;
  routingNumber: string;
  branchCode: string;
  email: string;
  phoneNumber: string;
  type: BranchType;
  status: BranchStatus;
  /** Always undefined on read — see note above (WRITE_ONLY on the backend entity). */
  parentBranch?: { id: number; name?: string } | null;
  policeStation?: {
    id: number;
    name?: string;
  } | null;
  createdAt: string;
  updatedAt: string;
}
