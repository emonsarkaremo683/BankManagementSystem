import { Injectable, inject, NgZone } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { Client, IMessage, StompSubscription } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { AuthService } from './auth.service';
import { NotificationResponse } from '../models/notification.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private client: Client | null = null;
  private notificationSubject = new Subject<NotificationResponse>();
  private connectionSubject = new Subject<boolean>();
  private subscription: StompSubscription | null = null;
  private isConnected = false;
  private reconnectTimer: any = null;

  private authService = inject(AuthService);
  private ngZone = inject(NgZone);

  get notifications$(): Observable<NotificationResponse> {
    return this.notificationSubject.asObservable();
  }

  get connection$(): Observable<boolean> {
    return this.connectionSubject.asObservable();
  }

  connect(): void {
    if (this.client?.active) return;

    const token = this.authService.getToken();
    if (!token) return;

    const wsUrl = environment.apiUrl.replace('/api/', '');

    this.ngZone.runOutsideAngular(() => {
      this.client = new Client({
        // JwtHandshakeInterceptor authenticates the SockJS HTTP handshake
        // itself (before any STOMP frame exists), reading either the
        // "Authorization" HTTP header or a "token" request parameter off the
        // raw ServerHttpRequest. SockJS/browsers can't attach a custom
        // Authorization header to that handshake request, so connectHeaders
        // below (a STOMP CONNECT frame header, sent only after the socket is
        // already open) never reaches the interceptor — the token must ride
        // as a "?token=" query param on the handshake URL instead.
        webSocketFactory: () => new SockJS(`${wsUrl}/ws?token=${encodeURIComponent(token)}`),
        connectHeaders: { Authorization: `Bearer ${token}` },
        heartbeatIncoming: 10000,
        heartbeatOutgoing: 10000,
        reconnectDelay: 5000,
        debug: () => {},
        onConnect: () => {
          this.isConnected = true;
          this.ngZone.run(() => {
            this.connectionSubject.next(true);
          });
          this.subscribeToNotifications();
        },
        onDisconnect: () => {
          this.isConnected = false;
          this.ngZone.run(() => {
            this.connectionSubject.next(false);
          });
        },
        onStompError: (frame) => {
          console.error('STOMP error:', frame.headers['message']);
          this.isConnected = false;
        }
      });

      this.client.activate();
    });
  }

  disconnect(): void {
    if (this.reconnectTimer) {
      clearTimeout(this.reconnectTimer);
      this.reconnectTimer = null;
    }
    this.subscription?.unsubscribe();
    this.subscription = null;
    this.client?.deactivate();
    this.client = null;
    this.isConnected = false;
  }

  private subscribeToNotifications(): void {
    if (!this.client?.active) return;

    this.subscription?.unsubscribe();

    this.subscription = this.client.subscribe(
      '/user/queue/notifications',
      (message: IMessage) => {
        try {
          const notification: NotificationResponse = JSON.parse(message.body);
          this.ngZone.run(() => {
            this.notificationSubject.next(notification);
          });
        } catch (e) {
          console.error('Failed to parse notification', e);
        }
      }
    );
  }

  reconnect(): void {
    this.disconnect();
    this.reconnectTimer = setTimeout(() => this.connect(), 2000);
  }
}
