import { CommonModule } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  forwardRef,
  input,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { SvgIconComponent } from '../svg-icon/svg-icon.component';

export type UiOutlinedFieldType =
  | 'text'
  | 'number'
  | 'date'
  | 'email'
  | 'password'
  | 'select';

@Component({
  selector: 'app-ui-outlined-field',
  standalone: true,
  imports: [CommonModule, SvgIconComponent],
  template: `
    <div
      class="ui-outlined-field"
      [class.ui-outlined-field--readonly]="readOnly()"
      [class.ui-outlined-field--disabled]="disabled"
    >
      <label class="ui-outlined-field__label" [attr.for]="resolvedId">{{ label() }}</label>
      <div class="ui-outlined-field__control">
        @if (fieldType() === 'date') {
          <input
            #dateInput
            [id]="resolvedId"
            type="date"
            class="ui-outlined-field__input"
            [readOnly]="readOnly()"
            [disabled]="disabled"
            [attr.placeholder]="placeholder() || null"
            [value]="value"
            (input)="onInput($event)"
            (blur)="onBlur()"
          />
          @if (!readOnly()) {
            <button
              type="button"
              class="ui-outlined-field__suffix"
              (click)="openPicker(dateInput)"
              tabindex="-1"
              aria-label="Abrir calendario"
            >
              <app-svg-icon name="calendar" [size]="20" />
            </button>
          }
        } @else if (fieldType() === 'select') {
          <select
            [id]="resolvedId"
            class="ui-outlined-field__input ui-outlined-field__select"
            [disabled]="disabled"
            [value]="value"
            (change)="onSelectChange($event)"
            (blur)="onBlur()"
          >
            @for (opt of options(); track opt.value) {
              <option [value]="opt.value">{{ opt.label }}</option>
            }
          </select>
          <span class="ui-outlined-field__suffix ui-outlined-field__suffix--static" aria-hidden="true">
            <app-svg-icon name="chevron-down" [size]="18" />
          </span>
        } @else if (fieldType() === 'password' && showPasswordToggle()) {
          <input
            [id]="resolvedId"
            [type]="passwordVisible ? 'text' : 'password'"
            class="ui-outlined-field__input"
            [readOnly]="readOnly()"
            [disabled]="disabled"
            [attr.placeholder]="placeholder() || null"
            [value]="value"
            (input)="onInput($event)"
            (blur)="onBlur()"
          />
          @if (!readOnly()) {
            <button
              type="button"
              class="ui-outlined-field__suffix"
              (click)="togglePassword()"
              tabindex="-1"
              [attr.aria-label]="passwordVisible ? 'Ocultar contraseña' : 'Mostrar contraseña'"
            >
              <app-svg-icon [name]="passwordVisible ? 'eye-off' : 'eye'" [size]="20" />
            </button>
          }
        } @else {
          <input
            [id]="resolvedId"
            [type]="nativeInputType()"
            class="ui-outlined-field__input"
            [readOnly]="readOnly()"
            [disabled]="disabled"
            [attr.placeholder]="placeholder() || null"
            [attr.min]="min() ?? null"
            [attr.max]="max() ?? null"
            [attr.step]="step() ?? null"
            [value]="value"
            (input)="onInput($event)"
            (blur)="onBlur()"
          />
        }
      </div>
    </div>
  `,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => UiOutlinedFieldComponent),
      multi: true,
    },
  ],
  changeDetection: ChangeDetectionStrategy.Default,
  styleUrl: './ui-outlined-field.component.scss',
})
export class UiOutlinedFieldComponent implements ControlValueAccessor {
  readonly label = input.required<string>();
  readonly fieldType = input<UiOutlinedFieldType>('text');
  readonly readOnly = input(false);
  readonly placeholder = input('');
  readonly min = input<string | undefined>(undefined);
  readonly max = input<string | undefined>(undefined);
  readonly step = input<string | undefined>(undefined);
  /** Opciones para {@code fieldType === 'select'} */
  readonly options = input<{ value: string; label: string }[]>([]);
  /** Solo aplica si {@code fieldType === 'password'} */
  readonly showPasswordToggle = input(false);
  readonly fieldId = input<string | undefined>(undefined);

  private readonly autoId = `ui-field-${Math.random().toString(36).slice(2, 11)}`;

  protected passwordVisible = false;

  protected get resolvedId(): string {
    return this.fieldId() ?? this.autoId;
  }

  protected value = '';
  protected disabled = false;

  private onChange: (v: string) => void = () => {};
  private onTouchedFn: () => void = () => {};

  protected nativeInputType(): string {
    const t = this.fieldType();
    if (t === 'email') {
      return 'email';
    }
    if (t === 'password') {
      return 'password';
    }
    if (t === 'number') {
      return 'number';
    }
    return 'text';
  }

  writeValue(v: string | number | null | undefined): void {
    if (v == null || v === '') {
      this.value = '';
    } else {
      this.value = String(v);
    }
  }

  registerOnChange(fn: (v: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouchedFn = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  protected onInput(ev: Event): void {
    const t = ev.target as HTMLInputElement;
    this.value = t.value;
    this.onChange(this.value);
  }

  protected onSelectChange(ev: Event): void {
    const t = ev.target as HTMLSelectElement;
    this.value = t.value;
    this.onChange(this.value);
  }

  protected onBlur(): void {
    this.onTouchedFn();
  }

  protected togglePassword(): void {
    this.passwordVisible = !this.passwordVisible;
  }

  protected openPicker(el: HTMLInputElement): void {
    if (this.disabled || this.readOnly()) {
      return;
    }
    const anyEl = el as HTMLInputElement & { showPicker?: () => void };
    if (typeof anyEl.showPicker === 'function') {
      try {
        anyEl.showPicker();
      } catch {
        el.focus();
      }
    } else {
      el.focus();
    }
  }
}
