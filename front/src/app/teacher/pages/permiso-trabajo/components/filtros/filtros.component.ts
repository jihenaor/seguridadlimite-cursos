import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SvgIconComponent } from '../../../../../shared/components/svg-icon/svg-icon.component';

export interface FiltroPermiso {
  fechaDesde: Date | null;
  fechaHasta: Date | null;
  codigoministerio: string | null;
}

@Component({
    selector: 'app-permiso-trabajo-filtros',
    templateUrl: './filtros.component.html',
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatTooltipModule,
        SvgIconComponent
    ]
})
export class PermisoTrabajoFiltrosComponent implements OnInit {
  @Output() filtroAplicado = new EventEmitter<FiltroPermiso>();

  fechaDesde: Date | null = null;
  fechaHasta: Date | null = null;
  codigoministerio: string | null = null;

  constructor() { }

  ngOnInit(): void {
  }

  aplicarFiltro(): void {
    const filtro: FiltroPermiso = {
      fechaDesde: this.fechaDesde,
      fechaHasta: this.fechaHasta,
      codigoministerio: this.codigoministerio
    };

    console.log('Aplicando filtro:', filtro);
    this.filtroAplicado.emit(filtro);
  }

  limpiarFiltros(): void {
    this.fechaDesde = null;
    this.fechaHasta = null;
    this.codigoministerio = null;

    this.aplicarFiltro();
  }
}
