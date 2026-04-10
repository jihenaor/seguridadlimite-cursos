import { CommonModule } from '@angular/common';
import { Component, HostListener, Input, Output, EventEmitter, ViewEncapsulation } from '@angular/core';

let uiModalTitleSeq = 0;

@Component({
  selector: 'app-ui-modal',
  standalone: true,
  imports: [CommonModule],
  encapsulation: ViewEncapsulation.None,
  template: `
    @if (open) {
      <div class="ui-modal-root" role="presentation">
        <div class="ui-modal-backdrop" (click)="onBackdropClick()" aria-hidden="true"></div>
        <div
          class="ui-modal-panel"
          [class.ui-modal-panel--no-header]="hideHeader"
          role="dialog"
          aria-modal="true"
          [attr.aria-labelledby]="hideHeader ? null : titleId"
          [attr.aria-label]="hideHeader ? ('Foto: ' + title) : null"
          (click)="$event.stopPropagation()">
          @if (!hideHeader) {
            <header class="ui-modal-header" [class.ui-modal-header--compact]="compactHeader">
              <h2 class="ui-modal-title" [class.ui-modal-title--compact]="compactHeader" [id]="titleId">{{ title }}</h2>
              <button
                type="button"
                class="ui-modal-close"
                [class.ui-modal-close--compact]="compactHeader"
                (click)="close()"
                aria-label="Cerrar">
                ×
              </button>
            </header>
          } @else {
            <button type="button" class="ui-modal-close ui-modal-close--floating" (click)="close()" aria-label="Cerrar">
              ×
            </button>
          }
          <div
            class="ui-modal-body"
            [class.ui-modal-body--compact]="compactHeader && !hideHeader"
            [class.ui-modal-body--no-header]="hideHeader">
            <ng-content />
          </div>
        </div>
      </div>
    }
  `,
  styleUrl: './ui-modal.component.scss',
})
export class UiModalComponent {
  @Input({ required: true }) open = false;
  @Input({ required: true }) title = '';
  @Input() closeOnBackdrop = true;
  /** Encabezado y cuerpo más bajos (p. ej. captura de foto). */
  @Input() compactHeader = false;
  /** Sin barra superior: solo cuerpo + botón cerrar flotante (p. ej. modal de cámara). */
  @Input() hideHeader = false;
  @Output() openChange = new EventEmitter<boolean>();

  readonly titleId = `ui-modal-title-${++uiModalTitleSeq}`;

  @HostListener('document:keydown.escape')
  onEscape(): void {
    if (this.open) {
      this.close();
    }
  }

  close(): void {
    this.openChange.emit(false);
  }

  onBackdropClick(): void {
    if (this.closeOnBackdrop) {
      this.close();
    }
  }
}
