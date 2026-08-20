import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { NotificationResponse } from '../models/notification.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private http = inject(HttpClient);
  // No trailing slash: NotificationController is @RequestMapping("/api/notifications")
  // with a bare @GetMapping on getNotifications() — Spring's strict (non
  // trailing-slash-matching) routing means "…/notifications/" 404s while
  // "…/notifications" matches. Sub-paths are appended with an explicit "/".
  private apiUrl = environment.apiUrl + 'notifications';

  getNotifications(): Observable<NotificationResponse[]> {
    return this.http.get<NotificationResponse[]>(this.apiUrl);
  }

  getUnreadCount(): Observable<{ unreadCount: number }> {
    return this.http.get<{ count: number }>(`${this.apiUrl}/unread-count`).pipe(
      map(res => ({ unreadCount: res.count }))
    );
  }

  markAsRead(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/read`, {});
  }

  markAllAsRead(): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/read-all`, {});
  }
}
