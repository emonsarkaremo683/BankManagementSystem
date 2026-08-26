import { Routes } from '@angular/router';
import { HomeComponent } from './features/public/home/home';
import { LoginComponent } from './features/public/login/login';
import { RegisterComponent } from './features/public/register/register';
import { ForgotPasswordComponent } from './features/public/forgot-password/forgot-password';
import { ResetPasswordComponent } from './features/public/reset-password/reset-password';
import { VerifySentComponent } from './features/public/verify-sent/verify-sent';
import { VerifyEmailComponent } from './features/public/verify-email/verify-email';
import { AboutComponent } from './features/public/about/about';
import { ServicesComponent } from './features/public/services/services';
import { BranchesComponent as BranchesPublicComponent } from './features/public/branches/branches';
import { AtmsComponent } from './features/public/atms/atms';
import { MfaVerifyComponent } from './features/public/mfa-verify/mfa-verify';
import { UnauthorizedComponent } from './features/public/unauthorized/unauthorized';
import { StaffDashboardComponent } from './features/staff/dashboard/dashboard';
import { CustomerDashboardComponent } from './features/customer/dashboard/dashboard';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';
import { guestGuard } from './core/guards/guest.guard';
import { DashboardLayoutComponent } from './shared/components/dashboard-layout/dashboard-layout';

import { Role } from './core/models/enums';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent, canActivate: [guestGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [guestGuard] },
  { path: 'forgot-password', component: ForgotPasswordComponent, canActivate: [guestGuard] },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'about', component: AboutComponent },
  { path: 'services', component: ServicesComponent },
  { path: 'branches', component: BranchesPublicComponent },
  { path: 'atms', component: AtmsComponent },
  { path: 'mfa-verify', component: MfaVerifyComponent },
  { path: 'verify-email-sent', component: VerifySentComponent },
  { path: 'verify-email', component: VerifyEmailComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { 
    path: 'customer', 
    component: DashboardLayoutComponent,
    canActivate: [authGuard, roleGuard], 
    data: { roles: [Role.CUSTOMER] }, 
    children: [
      { path: 'dashboard', component: CustomerDashboardComponent },
      { 
        path: 'accounts',
        loadComponent: () => import('./features/customer/accounts/my-accounts/my-accounts').then(m => m.MyAccountsComponent)
      },
      { 
        path: 'accounts/create',
        loadComponent: () => import('./features/customer/accounts/account-create/account-create').then(m => m.CustomerAccountCreateComponent)
      },
      { 
        path: 'accounts/:id',
        loadComponent: () => import('./features/customer/accounts/account-detail/account-detail').then(m => m.CustomerAccountDetailComponent)
      },
      { 
        path: 'transactions',
        loadComponent: () => import('./features/customer/transactions/transaction-history/transaction-history').then(m => m.TransactionHistoryComponent)
      },
      { 
        path: 'transfer',
        loadComponent: () => import('./features/customer/transfer/transfer-money/transfer-money').then(m => m.TransferMoneyComponent)
      },
      { 
        path: 'profile',
        loadComponent: () => import('./features/customer/profile/my-profile/my-profile').then(m => m.MyProfileComponent)
      },
      { 
        path: 'beneficiaries/new',
        loadComponent: () => import('./features/customer/beneficiary/beneficiary-form/beneficiary-form').then(m => m.BeneficiaryFormComponent)
      },
      { 
        path: 'beneficiaries/edit/:id',
        loadComponent: () => import('./features/customer/beneficiary/beneficiary-form/beneficiary-form').then(m => m.BeneficiaryFormComponent)
      },
      { 
        path: 'beneficiaries',
        loadComponent: () => import('./features/customer/beneficiary/beneficiary-list/beneficiary-list').then(m => m.BeneficiaryListComponent)
      },
      { 
        path: 'cards/apply',
        loadComponent: () => import('./features/customer/cards/apply-card/apply-card').then(m => m.ApplyCardComponent)
      },
      { 
        path: 'cards/:id',
        loadComponent: () => import('./features/customer/cards/card-detail/card-detail').then(m => m.CustomerCardDetailComponent)
      },
      { 
        path: 'cards',
        loadComponent: () => import('./features/customer/cards/my-cards/my-cards').then(m => m.MyCardsComponent)
      },
      { 
        path: 'loans/apply',
        loadComponent: () => import('./features/customer/loans/loan-apply/loan-apply').then(m => m.LoanApplyComponent)
      },
      { 
        path: 'loans/:id',
        loadComponent: () => import('./features/customer/loans/loan-detail/loan-detail').then(m => m.CustomerLoanDetailComponent)
      },
      { 
        path: 'loans',
        loadComponent: () => import('./features/customer/loans/my-loans/my-loans').then(m => m.MyLoansComponent)
      },
      { 
        path: 'cheques/apply',
        loadComponent: () => import('./features/customer/cheques/cheque-apply/cheque-apply').then(m => m.ChequeApplyComponent)
      },
      { 
        path: 'cheques/:id',
        loadComponent: () => import('./features/customer/cheques/cheque-detail/cheque-detail').then(m => m.CustomerChequeDetailComponent)
      },
      { 
        path: 'cheques',
        loadComponent: () => import('./features/customer/cheques/my-cheques/my-cheques').then(m => m.MyChequesComponent)
      },
      { 
        path: 'standing-orders/new',
        loadComponent: () => import('./features/customer/standing-orders/standing-order-form/standing-order-form').then(m => m.CustomerStandingOrderFormComponent)
      },
      { 
        path: 'standing-orders',
        loadComponent: () => import('./features/customer/standing-orders/my-standing-orders/my-standing-orders').then(m => m.MyStandingOrdersComponent)
      },
      { 
        path: 'currency-converter',
        loadComponent: () => import('./features/customer/currency-converter/currency-converter').then(m => m.CurrencyConverterComponent)
      },
      { 
        path: 'kyc',
        loadComponent: () => import('./features/customer/kyc/kyc').then(m => m.CustomerKycComponent)
      }
    ]
  },
  { 
    path: 'staff', 
    component: DashboardLayoutComponent,
    canActivate: [authGuard, roleGuard], 
    data: { roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.BRANCH_MANAGER, Role.CASHIER, Role.ACCOUNTANT, Role.CUSTOMER_SERVICE, Role.LOAN_OFFICER, Role.ATM_MANAGER, Role.AUDITOR] }, 
    children: [
      { path: 'dashboard', component: StaffDashboardComponent },
      { 
        path: 'branches',
        loadComponent: () => import('./features/staff/branches/branch-list/branch-list').then(m => m.BranchListComponent)
      },
      { 
        path: 'branches/new',
        loadComponent: () => import('./features/staff/branches/branch-form/branch-form').then(m => m.BranchFormComponent)
      },
      { 
        path: 'branches/edit/:id',
        loadComponent: () => import('./features/staff/branches/branch-form/branch-form').then(m => m.BranchFormComponent)
      },
      { 
        path: 'employees',
        loadComponent: () => import('./features/staff/employees/employee-list/employee-list').then(m => m.EmployeeListComponent)
      },
      { 
        path: 'employees/new',
        loadComponent: () => import('./features/staff/employees/employee-form/employee-form').then(m => m.EmployeeFormComponent)
      },
      { 
        path: 'employees/edit/:id',
        loadComponent: () => import('./features/staff/employees/employee-form/employee-form').then(m => m.EmployeeFormComponent)
      },
      { 
        path: 'accounts',
        loadComponent: () => import('./features/staff/accounts/account-list/account-list').then(m => m.AccountListComponent)
      },
      { 
        path: 'accounts/create',
        loadComponent: () => import('./features/staff/accounts/account-create/account-create').then(m => m.AccountCreateComponent)
      },
      { 
        path: 'accounts/:id',
        loadComponent: () => import('./features/staff/accounts/account-detail/account-detail').then(m => m.AccountDetailComponent)
      },
      { 
        path: 'transactions',
        loadComponent: () => import('./features/staff/transactions/transaction-list/transaction-list').then(m => m.TransactionListComponent)
      },
      { 
        path: 'transactions/new',
        loadComponent: () => import('./features/staff/transactions/transaction-form/transaction-form').then(m => m.TransactionFormComponent)
      },
      { 
        path: 'transactions/cashier-new',
        loadComponent: () => import('./features/staff/transactions/cashier-transaction-form/cashier-transaction-form').then(m => m.CashierTransactionFormComponent)
      },
      { 
        path: 'transactions/:referenceNo',
        loadComponent: () => import('./features/staff/transactions/transaction-detail/transaction-detail').then(m => m.TransactionDetailComponent)
      },
      { 
        path: 'customers',
        loadComponent: () => import('./features/staff/customers/customer-list/customer-list').then(m => m.CustomerListComponent)
      },
      { 
        path: 'customers/kyc',
        loadComponent: () => import('./features/staff/customers/kyc-review/kyc-review').then(m => m.KycReviewComponent)
      },
      { 
        path: 'customers/:id',
        loadComponent: () => import('./features/staff/customers/customer-detail/customer-detail').then(m => m.CustomerDetailComponent)
      },
      { 
        path: 'cards',
        loadComponent: () => import('./features/staff/cards/card-list/card-list').then(m => m.CardListComponent)
      },
      { 
        path: 'cards/new',
        loadComponent: () => import('./features/staff/cards/card-form/card-form').then(m => m.CardFormComponent)
      },
      { 
        path: 'cards/:id',
        loadComponent: () => import('./features/staff/cards/card-detail/card-detail').then(m => m.CardDetailComponent)
      },
      { 
        path: 'loans',
        loadComponent: () => import('./features/staff/loans/loan-list/loan-list').then(m => m.LoanListComponent)
      },
      { 
        path: 'loans/:id',
        loadComponent: () => import('./features/staff/loans/loan-detail/loan-detail').then(m => m.LoanDetailComponent)
      },
      { 
        path: 'cheques',
        loadComponent: () => import('./features/staff/cheques/cheque-list/cheque-list').then(m => m.ChequeListComponent)
      },
      { 
        path: 'cheques/:id',
        loadComponent: () => import('./features/staff/cheques/cheque-detail/cheque-detail').then(m => m.ChequeDetailComponent)
      },
      { 
        path: 'standing-orders',
        loadComponent: () => import('./features/staff/standing-orders/standing-order-list/standing-order-list').then(m => m.StandingOrderListComponent)
      },
      { 
        path: 'standing-orders/new',
        loadComponent: () => import('./features/staff/standing-orders/standing-order-form/standing-order-form').then(m => m.StandingOrderFormComponent)
      },
      { 
        path: 'atms',
        loadComponent: () => import('./features/staff/atms/atm-list/atm-list').then(m => m.AtmListComponent)
      },
      { 
        path: 'atms/new',
        loadComponent: () => import('./features/staff/atms/atm-form/atm-form').then(m => m.AtmFormComponent)
      },
      { 
        path: 'atms/edit/:id',
        loadComponent: () => import('./features/staff/atms/atm-form/atm-form').then(m => m.AtmFormComponent)
      },
      { 
        path: 'atms/refill/:id',
        loadComponent: () => import('./features/staff/atms/atm-refill/atm-refill').then(m => m.AtmRefillComponent)
      },
      { 
        path: 'atms/:id',
        loadComponent: () => import('./features/staff/atms/atm-detail/atm-detail').then(m => m.AtmDetailComponent)
      },
      { 
        path: 'fraud',
        loadComponent: () => import('./features/staff/fraud/fraud-list/fraud-list').then(m => m.FraudListComponent)
      },
      { 
        path: 'fraud/review/:id',
        loadComponent: () => import('./features/staff/fraud/fraud-review/fraud-review').then(m => m.FraudReviewComponent)
      },
      { 
        path: 'reports/trial-balance',
        loadComponent: () => import('./features/staff/reports/trial-balance/trial-balance').then(m => m.TrialBalanceComponent)
      },
      { 
        path: 'reports/balance-sheet',
        loadComponent: () => import('./features/staff/reports/balance-sheet/balance-sheet').then(m => m.BalanceSheetComponent)
      },
      { 
        path: 'reports/ledger',
        loadComponent: () => import('./features/staff/reports/ledger/ledger').then(m => m.LedgerComponent)
      },
      { 
        path: 'reports/profit-loss',
        loadComponent: () => import('./features/staff/reports/profit-loss/profit-loss').then(m => m.ProfitLossComponent)
      },
      { 
        path: 'divisions',
        loadComponent: () => import('./features/staff/divisions/division-list/division-list').then(m => m.DivisionListComponent)
      },
      { 
        path: 'divisions/new',
        loadComponent: () => import('./features/staff/divisions/division-form/division-form').then(m => m.DivisionFormComponent)
      },
      { 
        path: 'divisions/edit/:id',
        loadComponent: () => import('./features/staff/divisions/division-form/division-form').then(m => m.DivisionFormComponent)
      },
      { 
        path: 'districts',
        loadComponent: () => import('./features/staff/districts/district-list/district-list').then(m => m.DistrictListComponent)
      },
      { 
        path: 'districts/new',
        loadComponent: () => import('./features/staff/districts/district-form/district-form').then(m => m.DistrictFormComponent)
      },
      { 
        path: 'districts/edit/:id',
        loadComponent: () => import('./features/staff/districts/district-form/district-form').then(m => m.DistrictFormComponent)
      },
      { 
        path: 'police-stations',
        loadComponent: () => import('./features/staff/police-stations/police-station-list/police-station-list').then(m => m.PoliceStationListComponent)
      },
      { 
        path: 'police-stations/new',
        loadComponent: () => import('./features/staff/police-stations/police-station-form/police-station-form').then(m => m.PoliceStationFormComponent)
      },
      { 
        path: 'police-stations/edit/:id',
        loadComponent: () => import('./features/staff/police-stations/police-station-form/police-station-form').then(m => m.PoliceStationFormComponent)
      }
    ]
  },
  { path: '**', redirectTo: '' }
];
