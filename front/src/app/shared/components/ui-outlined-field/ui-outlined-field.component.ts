import { CommonModule } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  forwardRef,
  input,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { SvgIconComponent } from '../svg-icon/svg-icon.component';

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
        } @else {
          <input
            [id]="resolvedId"
            [type]="fieldType()"
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
  /* Default: NgModel + diálogo Material + CVA; OnPush puede dejar el valor del input desincronizado. */
  changeDetection: ChangeDetectionStrategy.Default,
  styleUrl: './ui-outlined-field.component.scss',
})
export class UiOutlinedFieldComponent implements ControlValueAccessor {
  readonly label = input.required<string>();
  readonly fieldType = input<'text' | 'number' | 'date'>('text');
  readonly readOnly = input(false);
  readonly placeholder = input('');
  readonly min = input<string | undefined>(undefined);
  readonly max = input<string | undefined>(undefined);
  readonly step = input<string | undefined>(undefined);
  /** Si se omite, se genera un id único por instancia. */
  readonly fieldId = input<string | undefined>(undefined);

  private readonly autoId = `ui-field-${Math.random().toString(36).slice(2, 11)}`;

  protected get resolvedId(): string {
    return this.fieldId() ?? this.autoId;
  }

  protected value = '';
  protected disabled = false;

  private onChange: (v: string) => void = () => {};
  private onTouchedFn: () => void = () => {};

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

  protected onBlur(): void {
    this.onTouchedFn();
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
