import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, Subject } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { LoadingService } from '../service/loading.service';
import { ShowNotificacionService } from '../service/show-notificacion.service';

@Injectable({
  providedIn: 'root',
})
export class HttpInterceptorService implements HttpInterceptor {
  private errorSubject = new Subject<string | null>();

  constructor(private loadingService: LoadingService,
    private notificacionService: ShowNotificacionService) {}

  get error$(): Observable<string | null> {
    return this.errorSubject.asObservable();
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.loadingService.show();

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        const esSolapamientoInscripcion =
          error.status === 409 && error.error?.code === 'PERMISO_SOLAPAMIENTO';

        if (!esSolapamientoInscripcion) {
          if (error.error instanceof ErrorEvent) {
            this.notificacionService.displayError(error.error.message);
          } else {
            switch (error?.status) {
              case 504:
                this.notificacionService.displayError('Error de comunicacion con el servidor');
                break;

              default:
                this.notificacionService.displayError(error.error?.message ?? error.message);
            }
          }

          this.errorSubject.next('Error en la solicitud');
        }

        return throwError(() => error);
      }),
      finalize(() => {
        this.loadingService.hide();
      })
    );
  }
}

