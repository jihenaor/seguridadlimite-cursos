import { CommonModule } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  Optional,
  Self,
  ViewChild,
  computed,
  input,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { ControlValueAccessor, NgControl } from '@angular/forms';
import { MatDatepicker, MatDatepickerModule } from '@angular/material/datepicker';
import { DateAdapter, MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { SvgIconComponent } from '../svg-icon/svg-icon.component';

@Component({
  selector: 'app-ui-birth-date-field',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    SvgIconComponent,
  ],
  template: `
    <div
      class="ui-outlined-field ui-birth-date-field"
      [class.ui-outlined-field--disabled]="disabled"
      [class.ui-outlined-field--invalid]="showError"
    >
      <label class="ui-outlined-field__label" [attr.for]="resolvedId">
        {{ label() }}
        @if (requiredMark()) {
          <span class="ui-birth-date-field__req" aria-hidden="true">*</span>
        }
      </label>
      <div class="ui-outlined-field__control">
        <input
          class="ui-outlined-field__input ui-birth-date-field__input"
          [id]="resolvedId"
          matInput
          [matDatepicker]="picker"
          [formControl]="innerCtrl"
          [min]="minDate() ?? null"
          [max]="maxDate() ?? null"
          [placeholder]="placeholder()"
          readonly
          autocomplete="bday"
          (dateChange)="markTouched()"
          (blur)="markTouched()"
        />
        <button
          type="button"
          class="ui-outlined-field__suffix"
          tabindex="-1"
          (click)="openPicker()"
          [disabled]="disabled"
          aria-label="Abrir calendario para elegir fecha de nacimiento"
        >
          <app-svg-icon name="calendar" [size]="20" />
        </button>
        <mat-datepicker
          #picker
          startView="multi-year"
          panelClass="ui-birth-date-datepicker-panel"
        ></mat-datepicker>
      </div>
    </div>
  `,
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'es-CO' }],
  changeDetection: ChangeDetectionStrategy.Default,
  styleUrl: './ui-birth-date-field.component.scss',
})
export class UiBirthDateFieldComponent implements ControlValueAccessor {
  readonly label = input.required<string>();
  readonly placeholder = input('día / mes / año');
  readonly fieldId = input<string | undefined>(undefined);
  /** Muestra asterisco en la etiqueta (el control sigue validando con Validators del padre). */
  readonly requiredMark = input(true);
  /** Límite inferior inclusive, formato `yyyy-MM-dd`. */
  readonly minIso = input<string | undefined>(undefined);
  /** Límite superior inclusive, formato `yyyy-MM-dd`. */
  readonly maxIso = input<string | undefined>(undefined);

  private readonly autoId = `ui-birth-${Math.random().toString(36).slice(2, 11)}`;

  @ViewChild('picker') picker!: MatDatepicker<Date>;

  protected readonly innerCtrl = new FormControl<Date | null>(null);

  readonly minDate = computed(() => UiBirthDateFieldComponent.parseIsoToLocalDate(this.minIso()));
  readonly maxDate = computed(() => UiBirthDateFieldComponent.parseIsoToLocalDate(this.maxIso()));

  protected disabled = false;
  private onChange: (v: string) => void = () => {};
  private onTouchedFn: () => void = () => {};

  constructor(
    @Optional() @Self() public ngControl: NgControl | null,
    dateAdapter: DateAdapter<Date>,
  ) {
    if (this.ngControl) {
      this.ngControl.valueAccessor = this;
    }
    dateAdapter.setLocale('es-CO');

    this.innerCtrl.valueChanges
      .pipe(takeUntilDestroyed())
      .subscribe((d) => {
        this.onChange(d ? UiBirthDateFieldComponent.toIsoLocalDate(d) : '');
      });
  }

  protected get resolvedId(): string {
    return this.fieldId() ?? this.autoId;
  }

  protected get showError(): boolean {
    const c = this.ngControl?.control;
    return !!(c && c.invalid && (c.touched || c.dirty));
  }

  static parseIsoToLocalDate(iso: string | null | undefined): Date | undefined {
    if (iso == null || iso === '') {
      return undefined;
    }
    const m = String(iso).trim().match(/^(\d{4})-(\d{2})-(\d{2})$/);
    if (!m) {
      return undefined;
    }
    const y = +m[1];
    const mo = +m[2] - 1;
    const d = +m[3];
    const dt = new Date(y, mo, d);
    if (dt.getFullYear() !== y || dt.getMonth() !== mo || dt.getDate() !== d) {
      return undefined;
    }
    return dt;
  }

  static toIsoLocalDate(d: Date): string {
    const y = d.getFullYear();
    const mo = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${y}-${mo}-${day}`;
  }

  writeValue(v: string | null | undefined): void {
    const d = v ? UiBirthDateFieldComponent.parseIsoToLocalDate(String(v)) : null;
    this.innerCtrl.setValue(d, { emitEvent: false });
  }

  registerOnChange(fn: (v: string) => void): void {
    this.onChange = fn;
    this.innerCtrl.valueChanges.subscribe((date) => {
      fn(date ? UiBirthDateFieldComponent.toIsoLocalDate(date) : '');
    });
  }

  registerOnTouched(fn: () => void): void {
    this.onTouchedFn = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
    if (isDisabled) {
      this.innerCtrl.disable({ emitEvent: false });
    } else {
      this.innerCtrl.enable({ emitEvent: false });
    }
  }

  protected markTouched(): void {
    this.onTouchedFn();
  }

  protected openPicker(): void {
    if (this.disabled) {
      return;
    }
    this.picker?.open();
  }
}
