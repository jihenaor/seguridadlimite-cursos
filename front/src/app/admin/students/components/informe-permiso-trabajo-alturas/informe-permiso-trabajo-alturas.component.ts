import { Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'student-informe-permiso-trabajo-alturas',
  standalone: true,
  imports: [
    CommonModule,
  ],
  template: `<button mat-menu-item (click)="showPdfFormatoinscripcion()">
      <mat-icon>dialpad</mat-icon>
      <span>Formato de inscripción</span>
    </button>
  `,
  styleUrl: './informe-permiso-trabajo-alturas.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InformePermisoTrabajoAlturasComponent {
  @Input()
  idaprendiz: number;

}
