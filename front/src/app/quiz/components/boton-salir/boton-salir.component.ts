import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-boton-salir',
    templateUrl: './boton-salir.component.html',
    styleUrls: ['./boton-salir.component.sass'],
    imports: [MatButtonModule]
})
export class BotonSalirComponent {
  @Input()
  tipoEvaluacion: string;

  constructor(
    private router: Router,
  ) { }

  irPrincipal(): void {
    if (this.tipoEvaluacion !== 'INGRESO') {
      sessionStorage.removeItem('numerodocumento')
    }
    window.location.href = 'https://seguridadallimite.com';
  }

  irInscripcion(): void {
    this.router.navigate(['/student/inscription']);
  }

}
