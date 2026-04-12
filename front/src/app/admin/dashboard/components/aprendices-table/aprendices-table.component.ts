import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { Aprendiz } from '@model/aprendiz.model';

@Component({
  selector: 'app-aprendices-table',
  imports: [MatIconModule, MatTooltipModule, MatButtonModule],
  templateUrl: './aprendices-table.component.html',
  styleUrls: ['./aprendices-table.component.scss'],
})
export class AprendicesTableComponent {
  @Input() aprendices: Aprendiz[] = [];

  constructor(private router: Router) {}

  irTrabajador(idtrabajador: number, idaprendiz: number) {
    try {
      sessionStorage.setItem('idtrabajador', idtrabajador + '');
      sessionStorage.setItem('idaprendiz', idaprendiz + '');

      this.router.navigate(['/admin/students/about-aprendiz', idaprendiz]);
    } catch (error) {
      alert('Error al abrir la página para tomar la foto' + error);
    }
  }
}
