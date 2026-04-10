import { HttpErrorResponse } from '@angular/common/http';
import { Component, EffectRef, OnDestroy, effect, inject, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';

import { DiaDto, Nivel } from '../../../core/models/nivel.model';
import {
  IniciarPermisoTrabajoAlturasService,
  PermisoSolapamientoConflicto,
  PermisoSolapamientoErrorBody
} from '../../../core/service/iniciarPermisoTrabajoAlturas.service';
import { ActiveWorkPermitsComponent } from '../active-work-permits/active-work-permits.component';
import { MatTabChangeEvent, MatTabsModule } from '@angular/material/tabs';

@Component({
  selector: 'app-update-inscription-date',
  standalone: true,
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'es-CO' }],
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatDialogModule,
    MatIconModule,
    MatTabsModule,
    ActiveWorkPermitsComponent
  ],
  styles: [`
    .addContainer {
      max-width: 100%;
      width: 100%;
    }
    .modalHeader {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px 20px;
      background: #f5f5f5;
      border-bottom: 1px solid #ddd;
    }
    .modal-about {
      font-size: 1.1rem;
      font-weight: 500;
    }
    .mat-dialog-content {
      padding: 20px;
      margin: 0;
    }
    .row {
      margin-right: -12px;
      margin-left: -12px;
    }
    .col-xl-12, .col-lg-12, .col-md-12, .col-sm-12 {
      padding-right: 12px;
      padding-left: 12px;
    }
    .mt-3 {
      margin-top: 1rem;
    }
    .mt-4 {
      margin-top: 1.5rem;
    }
    .mb-2 {
      margin-bottom: 0.5rem;
    }
    .me-2 {
      margin-right: 0.5rem;
    }
    :host ::ng-deep .inscripcion-date-field {
      width: 100%;
    }
    /* Ocultar hint/error sin colapsar el cálculo de altura del outline (evita etiqueta pegada al borde). */
    :host ::ng-deep .inscripcion-date-field .mat-mdc-form-field-subscript-wrapper,
    :host ::ng-deep .inscripcion-dia-date .mat-mdc-form-field-subscript-wrapper {
      display: none;
    }
    :host ::ng-deep .inscripcion-date-field.mat-mdc-form-field,
    :host ::ng-deep .inscripcion-dia-date.mat-mdc-form-field {
      --mat-form-field-subscript-text-line-height: 0px;
      --mat-form-field-subscript-text-tracking: 0;
    }
    :host ::ng-deep .inscripcion-dia-date {
      flex: 1;
      width: 100%;
      font-size: 13px;
    }
    /*
     * Artefacto MDC: el notch lleva border-left transparente; si un tema pinta border-color
     * en el notch, aparece una línea vertical en medio del texto (también fuera de .dark).
     */
    :host ::ng-deep .inscripcion-date-field .mdc-notched-outline__notch,
    :host ::ng-deep .inscripcion-dia-date .mdc-notched-outline__notch {
      border-left-color: transparent !important;
    }
    :host ::ng-deep [dir='rtl'] .inscripcion-date-field .mdc-notched-outline__notch,
    :host ::ng-deep [dir='rtl'] .inscripcion-dia-date .mdc-notched-outline__notch {
      border-left: none !important;
      border-right-color: transparent !important;
    }
    /* Línea entre zona de texto y botón del calendario (sufijo). */
    :host ::ng-deep .inscripcion-date-field .mat-mdc-form-field-icon-suffix,
    :host ::ng-deep .inscripcion-dia-date .mat-mdc-form-field-icon-suffix {
      border: none !important;
      border-inline-start: none !important;
    }
    :host ::ng-deep .inscripcion-date-field .mat-mdc-form-field-infix,
    :host ::ng-deep .inscripcion-dia-date .mat-mdc-form-field-infix {
      border-inline-end: none !important;
    }
    .dia-diseno-label {
      min-width: 0;
      flex: 0 1 148px;
      display: flex;
      flex-direction: column;
      gap: 6px;
    }
    .dia-diseno-label__row {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      gap: 8px;
    }
    .dia-diseno-label__num {
      font-size: 13px;
      font-weight: 600;
      color: #37474f;
    }
    .dia-contexto-chip {
      display: inline-flex;
      align-items: center;
      gap: 4px;
      padding: 2px 8px 2px 6px;
      border-radius: 999px;
      font-size: 11px;
      font-weight: 600;
      letter-spacing: 0.02em;
      line-height: 1.25;
      border: 1px solid transparent;
    }
    .dia-contexto-chip mat-icon {
      font-size: 14px;
      width: 14px;
      height: 14px;
    }
    .dia-contexto-chip--teorico {
      background: #e3f2fd;
      color: #1565c0;
      border-color: rgba(21, 101, 192, 0.22);
    }
    .dia-contexto-chip--practico {
      background: #e8f5e9;
      color: #2e7d32;
      border-color: rgba(46, 125, 50, 0.25);
    }
    .dia-contexto-chip--otro {
      background: #eceff1;
      color: #546e7a;
      border-color: rgba(84, 110, 122, 0.2);
    }
    .dia-diseno-label__unidad {
      font-size: 11px;
      color: #78909c;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      max-width: 100%;
    }
  `],

  templateUrl: './update-inscription-date.component.html'
})
export class UpdateInscriptionDateComponent implements OnDestroy {
  niveles: Nivel[] = [];
  /** Modelo Date para mat-datepicker (API sigue usando yyyy-MM-dd en Nivel) */
  nivelPickerDates: Record<number, { desde: Date | null; hasta: Date | null }> = {};
  diaPickerDates: Record<string, Date | null> = {};
  /** 0 = Cursos abiertos en la fecha, 1 = Apertura de Inscripción */
  selectedTabIndex = 0;
  updateSuccess = signal(false);
  private effectRef: EffectRef | undefined;

  dialogRef = inject(MatDialogRef<UpdateInscriptionDateComponent>);
  private iniciarPermisoTrabajoAlturasService = inject(IniciarPermisoTrabajoAlturasService);
  private data = inject(MAT_DIALOG_DATA) as { niveles: Nivel[] };

  constructor() {
    if (this.data && this.data.niveles) {
      this.niveles = this.data.niveles.map(nivel => ({
        ...nivel,
        seleccionado: false
      }));
      this.initPickerModels();
    }
    this.setupEffect();
  }

  private initPickerModels(): void {
    for (const n of this.niveles) {
      this.nivelPickerDates[n.id] = {
        desde: this.parseYmd(n.fechadesde),
        hasta: this.parseYmd(n.fechahasta),
      };
      n.diasdiseno?.forEach((d) => {
        this.diaPickerDates[this.diaPickerKey(n.id, d.dia)] = this.parseYmd(d.fecha);
      });
      if (n.diasdiseno?.length) {
        this.syncNivelRangoDesdeDias(n);
      }
    }
  }

  /** Con días de diseño curricular, el rango se deriva del mínimo y máximo de esas fechas. */
  nivelUsaRangoDesdeDias(nivel: Nivel): boolean {
    return !!nivel.diasdiseno?.length;
  }

  private syncNivelRangoDesdeDias(nivel: Nivel): void {
    const dias = nivel.diasdiseno;
    const row = this.nivelPickerDates[nivel.id];
    if (!dias?.length || !row) {
      return;
    }
    const fechas: Date[] = [];
    for (const d of dias) {
      const dt = this.parseYmd(d.fecha);
      if (!dt) {
        row.desde = null;
        row.hasta = null;
        delete nivel.fechadesde;
        delete nivel.fechahasta;
        return;
      }
      fechas.push(dt);
    }
    const times = fechas.map((d) => d.getTime());
    const minT = Math.min(...times);
    const maxT = Math.max(...times);
    row.desde = new Date(minT);
    row.hasta = new Date(maxT);
    nivel.fechadesde = this.formatYmd(row.desde);
    nivel.fechahasta = this.formatYmd(row.hasta);
  }

  diaPickerKey(nivelId: number, diaNum: number): string {
    return `${nivelId}-${diaNum}`;
  }

  esContextoTeorico(dia: DiaDto): boolean {
    return (dia.contexto ?? '').trim().toUpperCase() === 'T';
  }

  esContextoPractico(dia: DiaDto): boolean {
    return (dia.contexto ?? '').trim().toUpperCase() === 'P';
  }

  etiquetaContextoDia(dia: DiaDto): string {
    if (this.esContextoTeorico(dia)) {
      return 'Teórico';
    }
    if (this.esContextoPractico(dia)) {
      return 'Práctico';
    }
    const c = (dia.contexto ?? '').trim();
    return c.length > 0 ? c : '—';
  }

  parseYmd(s?: string | null): Date | null {
    if (s == null || typeof s !== 'string') {
      return null;
    }
    const t = s.trim();
    if (!/^\d{4}-\d{2}-\d{2}$/.test(t)) {
      return null;
    }
    const [y, m, d] = t.split('-').map(Number);
    const dt = new Date(y, m - 1, d);
    if (dt.getFullYear() !== y || dt.getMonth() !== m - 1 || dt.getDate() !== d) {
      return null;
    }
    return dt;
  }

  formatYmd(d: Date | null): string | undefined {
    if (!d) {
      return undefined;
    }
    const y = d.getFullYear();
    const m = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${y}-${m}-${day}`;
  }

  onNivelFechaChange(nivel: Nivel, field: 'fechadesde' | 'fechahasta', value: Date | null): void {
    const row = this.nivelPickerDates[nivel.id];
    if (!row) {
      return;
    }
    if (field === 'fechadesde') {
      row.desde = value;
    } else {
      row.hasta = value;
    }
    const ymd = this.formatYmd(value);
    if (ymd) {
      nivel[field] = ymd;
    } else {
      delete nivel[field];
    }
  }

  onDiaFechaChange(nivel: Nivel, dia: DiaDto, value: Date | null): void {
    this.diaPickerDates[this.diaPickerKey(nivel.id, dia.dia)] = value;
    const ymd = this.formatYmd(value);
    dia.fecha = ymd ?? '';
    if (nivel.diasdiseno?.length) {
      this.syncNivelRangoDesdeDias(nivel);
    }
  }

  onSelectionChange(nivel: Nivel) {
  }

  onTabChange(ev: MatTabChangeEvent): void {
    this.selectedTabIndex = ev.index;
  }

  /**
   * Al menos un nivel seleccionado con fechas válidas y, si hay fechas de diseño, día informado.
   */
  canSaveInscripcion(): boolean {
    const seleccionados = this.niveles.filter((n) => n.seleccionado);
    if (seleccionados.length === 0) {
      return false;
    }
    for (const n of seleccionados) {
      if (!this.validateDate(n.fechadesde) || !this.validateDate(n.fechahasta)) {
        return false;
      }
      const dias = n.diasdiseno;
      if (dias?.length) {
        for (const d of dias) {
          if (d.dia === null || d.dia === undefined) {
            return false;
          }
          if (!this.validateDate(d.fecha)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private setupEffect() {
    this.effectRef = effect(() => {
      if (this.updateSuccess()) {
        alert('Fechas actualizadas correctamente');
        this.dialogRef.close(true);
      }
    });
  }

  validateDate(date: string | undefined): boolean {
    if (!date) return false;
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    return dateRegex.test(date);
  }

  startWorkPermissions() {
    const nivelesSeleccionados = this.niveles.filter(nivel => nivel.seleccionado);

    if (nivelesSeleccionados.length === 0) {
      alert('Seleccione al menos un nivel y complete las fechas en la pestaña «Apertura de Inscripción».');
      return;
    }

    const nivelesInvalidos = nivelesSeleccionados.filter(nivel => {
      return !this.validateDate(nivel.fechadesde) ||
             !this.validateDate(nivel.fechahasta);
    });

    if (nivelesInvalidos.length > 0) {
      alert('Todos los campos de fecha deben estar en formato AAAA-MM-DD para los niveles seleccionados');
      return;
    }

    if (!this.canSaveInscripcion()) {
      alert('Complete la fecha desde, la fecha hasta y todas las fechas del diseño curricular (calendario) para cada nivel seleccionado.');
      return;
    }

    this.enviarInscripcionPermisos(nivelesSeleccionados, false);
  }

  private enviarInscripcionPermisos(nivelesSeleccionados: Nivel[], forzarSolapamiento: boolean): void {
    this.iniciarPermisoTrabajoAlturasService.startWorkPermissions(nivelesSeleccionados, forzarSolapamiento).subscribe({
      next: () => {
        this.updateSuccess.set(true);
      },
      error: (err: HttpErrorResponse) => {
        const body = err.error as PermisoSolapamientoErrorBody | undefined;
        if (err.status === 409 && body?.conflictos?.length && !forzarSolapamiento) {
          const texto = this.mensajeSolapamiento(body.conflictos, nivelesSeleccionados);
          const confirmar = globalThis.confirm(
            'Advertencia — solapamiento de fechas\n\n' +
              texto +
              '\n\n¿Desea registrar de todas formas un permiso adicional para ese nivel?'
          );
          if (confirmar) {
            this.enviarInscripcionPermisos(nivelesSeleccionados, true);
          }
          return;
        }
        console.error('Error al actualizar las fechas:', err);
        const msg = typeof body?.message === 'string' ? body.message : 'Ocurrió un error al actualizar las fechas';
        alert(msg);
      },
    });
  }

  private mensajeSolapamiento(conflictos: PermisoSolapamientoConflicto[], niveles: Nivel[]): string {
    const nombrePorId = new Map(niveles.map((n) => [n.id, n.nombre]));
    const lineas = conflictos.map((c) => {
      const nombre = nombrePorId.get(c.idNivel) ?? `Nivel ${c.idNivel}`;
      return (
        `• ${nombre}: ya existe el permiso #${c.idPermisoExistente} ` +
        `(${c.permisoExistenteValidoDesde} → ${c.permisoExistenteValidoHasta ?? '—'}). ` +
        `La solicitud usa ${c.solicitudValidoDesde} → ${c.solicitudValidoHasta}.`
      );
    });
    return (
      'La ventana de fechas que está guardando se cruza con al menos un permiso que ya existe ' +
        'para el mismo nivel. Detalle:\n\n' +
      lineas.join('\n')
    );
  }

  ngOnDestroy(): void {
    if (this.effectRef) {
      this.effectRef.destroy();
    }
  }
}
