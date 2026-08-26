import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet, NavigationEnd } from '@angular/router';
import { NavbarComponent } from './shared/components/navbar/navbar';
import { FooterComponent } from './shared/components/footer/footer';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent, FooterComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'ensark-frontend';
  isDashboard = false;

  private router = inject(Router);

  constructor() {
    this.checkDashboard(this.router.url);
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.checkDashboard(event.urlAfterRedirects || event.url);
    });
  }

  private checkDashboard(url: string) {
    this.isDashboard = !!url && (url.startsWith('/staff') || url.startsWith('/customer'));
  }
}
