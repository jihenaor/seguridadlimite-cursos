import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from 'src/app/core/service/auth.service';
import { ServicesService } from './../../core/service/services.service';
import { ShowNotificacionService } from '../../core/service/show-notificacion.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
// import { CustomButtonComponent } from '../../shared/components/custom-button/custom-button.component';

@Component({
    selector: 'app-signin',
    templateUrl: './signin.component.html',
    imports: [
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule//,
       // CustomButtonComponent
    ]
})
export class SigninComponent implements OnInit {
  authForm: UntypedFormGroup;
  authFormTrabajador: UntypedFormGroup;
  submitted = false;
  error = '';
  hide = true;
  perfil: string;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private services: ServicesService,
    private showNotificacionService: ShowNotificacionService
  ) {
    if (this.route.snapshot.paramMap.get('perfil') !== undefined) {
      this.perfil = this.route.snapshot.paramMap.get('perfil');
    }

    if (this.perfil === undefined || this.perfil === null || this.perfil === '') {
      this.perfil = 'A';
    }
  }
  async ngOnInit() {
    this.authForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      perfil: [this.perfil],
    });

    this.authFormTrabajador = this.formBuilder.group({
      username: ['', Validators.required],
      perfil: [this.perfil],
    });
  }

  get f() {
    return this.authForm.controls;
  }

  async test() {
    try {
      await this.services.executeFetch("test");
    } catch (error) {
      console.log(error);
    }

    try {
      await this.services.executeFetchHttps("httpstest");
    } catch (error) {
      console.log(error);
    }
  }

  async loginAdmin() {
    this.submitted = true;
    this.error = '';

    if (this.authForm.invalid) {
      this.error = 'Username and Password not valid !';
      return;
    } else {
      try {
        const res = await this.authService.post('/authenticate', this.authForm.getRawValue(), true);

        if (res && res.token !== undefined) {
          this.authService.saveToken(res);
          sessionStorage.setItem("loginid", res.id);

          const role = (res.role || '').toUpperCase();
          if (role === 'ADMINISTRADOR' || role === 'COORDINADOR') {
            this.router.navigate(['/admin/dashboard/main']);
          } else if (role === 'INSTRUCTOR') {
            this.router.navigate(['/teacher/permiso-trabajo']);
          } else if (role === 'C') {
            this.router.navigate(['/company/dashboard']);
          } else {
            // Rol no reconocido (ej. 'x', null) — no navegar, mostrar error
            this.showNotificacionService.displayError(
              'El usuario no tiene un rol asignado. Contacte al administrador.'
            );
          }
        } else {
          alert('Fallo en la autenticación');
        }
      } catch (err) {
        this.showNotificacionService.displayError(
          'Se ha presentado un error o el usuario o la clave son incorrectos'
        );
      }
    }
  }

  async loginTrabajador() {

    this.submitted = true;
    this.error = '';

    if (this.authFormTrabajador.invalid) {
      this.error = 'Debe digitar el numero de documento !';
      return;
    } else {
      try {
        const res = await this.authService.post('/authenticatetrabajador', this.authFormTrabajador.getRawValue(), true);

        if (res && res.token !== undefined) {
          this.authService.saveToken(res);

          this.router.navigate(['/student/dashboard']);
        } else {
          alert('Fallo en la autenticación');
        }
      } catch (e) {
        alert(e);
      }
    }
  }

  async onSubmit() {
    switch (this.perfil) {
      case 'A':
        await this.loginAdmin();
        break;
      case 'E':
        await this.loginAdmin();
        break;
      case 'T':
        await this.loginTrabajador();
        break;
      default:
        alert('El perfil ' + this.perfil + ' no tiene permiso para ingresar');
    }
  }
}



