import { AuthService } from '../service/auth.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

/** Respuesta 409 de inscripción con solapamiento: el componente muestra confirmación; no convertir a string. */
const ES_SOLAPAMIENTO_PERMISO = (err: HttpErrorResponse): boolean =>
  err.status === 409 && err.error?.code === 'PERMISO_SOLAPAMIENTO';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          this.authenticationService.logout();
          location.reload();
        }

        if (ES_SOLAPAMIENTO_PERMISO(err)) {
          return throwError(() => err);
        }

        const error = err.error?.message || err.statusText;
        return throwError(() => error);
      })
    );
  }
}





