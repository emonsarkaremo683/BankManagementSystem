import { Component, inject, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { Role } from '../../../core/models/enums';
import {
  LucideLayoutDashboard,
  LucideBuilding2,
  LucideUserCog,
  LucideWallet,
  LucideArrowLeftRight,
  LucideUsers,
  LucideBadgeCheck,
  LucideCircleUser,
  LucideHandshake,
  LucideCreditCard,
  LucideHandCoins,
  LucideFileText,
  LucideRepeat,
  LucideShieldAlert,
  LucideChartColumn,
  LucideCoins,
  LucideLandmark,
  LucideLogOut,
  LucideMapPin,
  LucidePanelLeftClose,
  LucidePanelLeftOpen,
} from '../../icons';

export interface NavItem {
  label: string;
  route: string;
  icon: string;
  roles?: Role[];
}

export interface NavGroup {
  title: string;
  items: NavItem[];
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    LucideLayoutDashboard,
    LucideBuilding2,
    LucideUserCog,
    LucideWallet,
    LucideArrowLeftRight,
    LucideUsers,
    LucideBadgeCheck,
    LucideCircleUser,
    LucideHandshake,
    LucideCreditCard,
    LucideHandCoins,
    LucideFileText,
    LucideRepeat,
    LucideShieldAlert,
    LucideChartColumn,
    LucideCoins,
    LucideLandmark,
    LucideLogOut,
    LucidePanelLeftClose,
    LucidePanelLeftOpen,
    LucideMapPin,
  ],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css'
})
export class SidebarComponent implements OnInit {
  @Input() isCollapsed = false;
  @Output() navClicked = new EventEmitter<void>();
  @Output() collapseToggled = new EventEmitter<boolean>();

  authService = inject(AuthService);
  private router = inject(Router);
  today = new Date();

  staffNavGroups: NavGroup[] = [
    {
      title: 'Core Banking',
      items: [
        { label: 'Dashboard', route: '/staff/dashboard', icon: 'dashboard' },
        { label: 'Customers', route: '/staff/customers', icon: 'customer', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.BRANCH_MANAGER, Role.CUSTOMER_SERVICE] },
        { label: 'KYC Review', route: '/staff/customers/kyc', icon: 'kyc', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.BRANCH_MANAGER, Role.CUSTOMER_SERVICE, Role.CASHIER] },
        { label: 'Accounts', route: '/staff/accounts', icon: 'account', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.CASHIER, Role.BRANCH_MANAGER] },
        { label: 'Transactions', route: '/staff/transactions', icon: 'transaction', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.CASHIER] },
        { label: 'Cashier Transaction', route: '/staff/transactions/cashier-new', icon: 'transaction', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.CASHIER, Role.BRANCH_MANAGER] }
      ]
    },
    {
      title: 'Services & Products',
      items: [
        { label: 'Cards', route: '/staff/cards', icon: 'card', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.CASHIER, Role.BRANCH_MANAGER, Role.CUSTOMER_SERVICE] },
        { label: 'Loans', route: '/staff/loans', icon: 'loan', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.LOAN_OFFICER, Role.BRANCH_MANAGER] },
        { label: 'Cheques', route: '/staff/cheques', icon: 'cheque', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.CASHIER, Role.BRANCH_MANAGER] },
        { label: 'Standing Orders', route: '/staff/standing-orders', icon: 'standing-order', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.CASHIER] }
      ]
    },
    {
      title: 'Management & Fleet',
      items: [
        { label: 'Branches', route: '/staff/branches', icon: 'branch', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.BRANCH_MANAGER] },
        { label: 'Employees', route: '/staff/employees', icon: 'employee', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.BRANCH_MANAGER] },
        { label: 'ATM Fleet', route: '/staff/atms', icon: 'atm', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.ATM_MANAGER, Role.BRANCH_MANAGER] }
      ]
    },
    {
      title: 'Compliance & Risk',
      items: [
        { label: 'Fraud Alerts', route: '/staff/fraud', icon: 'fraud', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.BRANCH_MANAGER, Role.AUDITOR] }
      ]
    },
    {
      title: 'Ledgers & Reports',
      items: [
        { label: 'Trial Balance', route: '/staff/reports/trial-balance', icon: 'report', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.ACCOUNTANT, Role.AUDITOR, Role.BRANCH_MANAGER] },
        { label: 'Balance Sheet', route: '/staff/reports/balance-sheet', icon: 'report', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.ACCOUNTANT, Role.AUDITOR, Role.BRANCH_MANAGER] },
        { label: 'General Ledger', route: '/staff/reports/ledger', icon: 'report', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.ACCOUNTANT, Role.AUDITOR, Role.BRANCH_MANAGER] },
        { label: 'Profit & Loss', route: '/staff/reports/profit-loss', icon: 'report', roles: [Role.SUPER_ADMIN, Role.ADMIN, Role.ACCOUNTANT, Role.AUDITOR, Role.BRANCH_MANAGER] }
      ]
    },
    {
      title: 'Settings & Address',
      items: [
        { label: 'Divisions', route: '/staff/divisions', icon: 'map-pin', roles: [Role.SUPER_ADMIN, Role.ADMIN] },
        { label: 'Districts', route: '/staff/districts', icon: 'map-pin', roles: [Role.SUPER_ADMIN, Role.ADMIN] },
        { label: 'Police Stations', route: '/staff/police-stations', icon: 'map-pin', roles: [Role.SUPER_ADMIN, Role.ADMIN] }
      ]
    }
  ];

  customerNavGroups: NavGroup[] = [
    {
      title: 'Overview & Profile',
      items: [
        { label: 'Dashboard', route: '/customer/dashboard', icon: 'dashboard' },
        { label: 'My Profile', route: '/customer/profile', icon: 'profile' }
      ]
    },
    {
      title: 'Accounts & Cards',
      items: [
        { label: 'My Accounts', route: '/customer/accounts', icon: 'account' },
        { label: 'My Cards', route: '/customer/cards', icon: 'card' }
      ]
    },
    {
      title: 'Payments & Transfers',
      items: [
        { label: 'Transfer Money', route: '/customer/transfer', icon: 'transaction' },
        { label: 'Transactions', route: '/customer/transactions', icon: 'transaction' },
        { label: 'Beneficiaries', route: '/customer/beneficiaries', icon: 'beneficiary' },
        { label: 'Standing Orders', route: '/customer/standing-orders', icon: 'standing-order' }
      ]
    },
    {
      title: 'Requests & Services',
      items: [
        { label: 'My Loans', route: '/customer/loans', icon: 'loan' },
        { label: 'My Cheques', route: '/customer/cheques', icon: 'cheque' },
        { label: 'Currency Converter', route: '/customer/currency-converter', icon: 'currency' },
        { label: 'KYC Verification', route: '/customer/kyc', icon: 'kyc' }
      ]
    }
  ];

  visibleNavGroups: NavGroup[] = [];

  ngOnInit(): void {
    this.computeVisibleNavGroups();
  }

  private computeVisibleNavGroups(): void {
    const user = this.authService.currentUserValue;
    if (!user) {
      this.visibleNavGroups = [];
      return;
    }

    const rawGroups = user.role === Role.CUSTOMER ? this.customerNavGroups : this.staffNavGroups;

    this.visibleNavGroups = rawGroups.map(group => {
      const filteredItems = group.items.filter(item => {
        if (!item.roles || item.roles.length === 0) return true;
        return item.roles.includes(user.role);
      });
      return { title: group.title, items: filteredItems };
    }).filter(group => group.items.length > 0);
  }

  get userRole(): string {
    const user = this.authService.currentUserValue;
    return user ? user.role.replace(/_/g, ' ') : '';
  }

  get userName(): string {
    const user = this.authService.currentUserValue;
    return user?.name || user?.email || '';
  }

  get isCustomer(): boolean {
    const user = this.authService.currentUserValue;
    return user?.role === Role.CUSTOMER;
  }

  onNavClick(): void {
    this.navClicked.emit();
  }

  toggleCollapse(): void {
    this.isCollapsed = !this.isCollapsed;
    this.collapseToggled.emit(this.isCollapsed);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
    this.navClicked.emit();
  }
}
