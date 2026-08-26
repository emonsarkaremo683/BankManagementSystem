import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
// Note: Assuming a toast/notification service exists or will be created
// import { NotificationService } from '../services/notification.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMsg = '';
      if (error.error instanceof ErrorEvent) {
        errorMsg = `Error: ${error.error.message}`;
      } else {
        errorMsg = error.error?.message || `Error Code: ${error.status}\nMessage: ${error.message}`;
        
        switch (error.status) {
          case 401:
            break;
          case 403:
            break;
          case 429:
            break;
          case 404:
            break;
          case 500:
            break;
          default:
            break;
        }
      }
      console.error(errorMsg);
      return throwError(() => error);
    })
  );
};
