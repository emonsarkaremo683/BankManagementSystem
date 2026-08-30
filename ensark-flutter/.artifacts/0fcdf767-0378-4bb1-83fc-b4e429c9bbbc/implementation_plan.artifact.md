# Align Enums with Backend Source of Truth

This plan synchronizes all Flutter enums with their corresponding Java definitions in the backend (`com.elitetech_inc.ensarkbank.common.enums`) to ensure data consistency and prevent serialization errors.

## User Review Required

> [!IMPORTANT]
> **Breaking Changes**: Some enum values have been renamed to match backend naming (e.g., `WITHDRAWAL` -> `WITHDRAW`, `OVERDUE` -> `LATE`). This will affect some conditional logic in the UI.
> **BeneficiaryType**: The entire enum has changed from `INTERNAL/EXTERNAL` to provider-based types (`BKASH`, `NAGAD`, etc.).

## Proposed Changes

### Core Models

#### [MODIFY] [enums.dart](file:///D:/BankManagementSystem/ensark-flutter/lib/models/enums.dart)
Update all core enums to match backend Java files:
- `AccountType`: Added `JOINT_ACCOUNT`, `STUDENT`, `BUSINESS`, and various `VAULT` types.
- `AccountStatus`: Added `BLOCKED`, `FREEZE`.
- `HolderType`: Updated to `PRIMARY`, `SECONDARY`, `OPTIONAL`, `INTER_BANK_SETTLEMENT`, `INTER_BRANCH_SETTLEMENT`.
- `NomineeRelation`: Aligned with backend list.
- `TransactionType`: Expanded to 21 types (e.g., `LOAN_DISBURSEMENT`, `ATM_WITHDRAW`).
- `TransactionChannel`: Expanded to 15 channels (e.g., `POS`, `QR_PAYMENT`, `SWIFT`).
- `TransactionStatus`: Renamed `COMPLETED` to `SUCCESS`, added `REVERSED`.
- `CardStatus`: Added `DISABLED`, `CLOSED`.
- `ChequeLeafStatus`: Updated to `UNUSED`, `ISSUED`, `PRESENTED`, `CLEARED`, `BOUNCED`, `STOP_PAYMENT`, `CANCELLED`, `EXPIRED`.
- `RepaymentStatus`: Updated to `PENDING`, `PAID`, `LATE`, `MISSED`.
- `StandingOrderFrequency`: Added `BI_WEEKLY`.
- `StandingOrderStatus`: Added `FAILED`.
- `BeneficiaryType`: Replaced `INTERNAL/EXTERNAL` with `BKASH`, `NAGAD`, `BANK`, `CARD`, `INTER_BANK`.
- `NotificationType`: Expanded to full list of 25 types.
- `BranchType`: Updated to `HEAD_OFFICE`, `BRANCH`, `AGENT_BANK`.
- `BranchStatus`: Updated to `ACTIVE`, `CLOSED`.

#### [MODIFY] [customer_models.dart](file:///D:/BankManagementSystem/ensark-flutter/lib/models/customer/customer_models.dart)
Update enums defined within this file:
- `Role`: Expanded to include `SUPER_ADMIN`, `BRANCH_MANAGER`, `ACCOUNTANT`, `CASHIER`, `LOAN_OFFICER`, etc.
- `CustomerOccupation`: Pruned from 30+ items to the 7 items defined in backend: `STUDENT`, `SERVICE_HOLDER`, `BUSINESSMAN`, `HOUSEWIFE`, `UNEMPLOYED`, `RETIRED`, `OTHER`.

### UI Components

#### [MODIFY] [notification_list_screen.dart](file:///D:/BankManagementSystem/ensark-flutter/lib/features/notifications/screens/notification_list_screen.dart)
Update icon mapping to handle new notification types and name changes.

#### [MODIFY] [loan_list_screen.dart](file:///D:/BankManagementSystem/ensark-flutter/lib/features/loans/screens/loan_list_screen.dart)
Update status color mapping for `RepaymentStatus` and `LoanStatus` if necessary.

---

## Verification Plan

### Automated Tests
- Run `dart run build_runner build --delete-conflicting-outputs` to regenerate JSON serialization maps.
- Run `flutter analyze` to ensure all UI code is updated to the new enum names.

### Manual Verification
- Verify that notifications display correctly with the new expanded types.
- Check account and transaction lists for correct status displays.
