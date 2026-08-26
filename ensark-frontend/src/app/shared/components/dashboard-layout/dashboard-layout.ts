import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar';
import { AuthService } from '../../../core/services/auth.service';
import { Role } from '../../../core/models/enums';
import { LucideMenu, LucideChevronDown, LucideLogOut } from '../../icons';

import { NotificationBellComponent } from '../notification-bell/notification-bell';

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, SidebarComponent, NotificationBellComponent, LucideMenu, LucideChevronDown, LucideLogOut],
  templateUrl: './dashboard-layout.html',
  styleUrl: './dashboard-layout.css'
})
export class DashboardLayoutComponent {
  authService = inject(AuthService);
  sidebarOpen = false;
  isCollapsed = false;
  profileMenuOpen = false;

  get isStaff(): boolean {
    const user = this.authService.currentUserValue;
    return !!user && user.role !== Role.CUSTOMER;
  }

  get currentUser() {
    return this.authService.currentUserValue;
  }

  get userInitials(): string {
    const user = this.currentUser;
    if (!user?.email) return 'U';
    return user.email.charAt(0).toUpperCase();
  }

  toggleSidebar(): void {
    if (window.innerWidth >= 1024) {
      this.isCollapsed = !this.isCollapsed;
    } else {
      this.sidebarOpen = !this.sidebarOpen;
    }
  }

  onCollapseToggled(collapsed: boolean): void {
    this.isCollapsed = collapsed;
  }

  toggleProfileMenu(event: Event): void {
    event.stopPropagation();
    this.profileMenuOpen = !this.profileMenuOpen;
  }

  closeProfileMenu(): void {
    this.profileMenuOpen = false;
  }

  logout(): void {
    this.profileMenuOpen = false;
    this.authService.logout();
  }

  closeSidebar(): void {
    this.sidebarOpen = false;
  }
}
