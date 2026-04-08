
import { enableProdMode, importProvidersFrom } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';


import { environment } from './environments/environment';
import { AppComponent } from './app/app.component';
import { SharedModule } from './app/shared/shared.module';
import { CoreModule } from './app/core/core.module';
import { AppRoutingModule } from './app/app-routing.module';
import { provideAnimations } from '@angular/platform-browser/animations';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { InscripcionService } from './app/student/services/inscripcion.service';
import { ServicesService } from './app/core/service/services.service';
import { fakeBackendProvider } from './app/core/interceptor/fake-backend';
import { HttpInterceptorService } from './app/core/interceptor/http.interceptor';
import { ErrorInterceptor } from './app/core/interceptor/error.interceptor';
import { JwtInterceptor } from './app/core/interceptor/jwt.interceptor';
import { HTTP_INTERCEPTORS, withInterceptorsFromDi, provideHttpClient } from '@angular/common/http';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
    providers: [
        importProvidersFrom(BrowserModule, AppRoutingModule, CoreModule, SharedModule),
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true },
        fakeBackendProvider,
        ServicesService,
        InscripcionService,
        provideAnimations(),
        provideHttpClient(withInterceptorsFromDi()),
    ]
})
  .catch(err => console.error(err));
