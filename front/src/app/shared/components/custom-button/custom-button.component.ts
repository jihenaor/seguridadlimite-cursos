import { Component, Input, Output, EventEmitter } from '@angular/core';
import { NgClass, NgIf } from '@angular/common';

@Component({
  selector: 'app-custom-button',
  standalone: true,
  imports: [NgClass],
  template: `
    <button
      [ngClass]="getButtonClasses()"
      (click)="onClick.emit()"
      [disabled]="disabled"
      type="button">
      @if (icon) {
        <i [class]="icon" class="mr-2"></i>
      }
      <ng-content />
    </button>
  `,
  styles: [`
    :host {
      display: inline-block;
    }
  `]
})
export class CustomButtonComponent {
  @Input() variant: 'primary' | 'secondary' | 'warning' | 'danger' = 'primary';
  @Input() icon?: string;
  @Input() disabled = false;
  @Output() onClick = new EventEmitter<void>();

  getButtonClasses(): string {
    const baseClasses = 'px-4 py-2 rounded-md flex items-center gap-2 transition-all duration-200';

    const variantClasses = {
      primary: 'bg-blue-600 hover:bg-blue-700 text-white disabled:opacity-50 disabled:cursor-not-allowed',
      secondary: 'bg-gray-600 hover:bg-gray-700 text-white disabled:opacity-50 disabled:cursor-not-allowed',
      warning: 'bg-yellow-500 hover:bg-yellow-600 text-white disabled:opacity-50 disabled:cursor-not-allowed',
      danger: 'bg-red-600 hover:bg-red-700 text-white disabled:opacity-50 disabled:cursor-not-allowed'
    };

    return `${baseClasses} ${variantClasses[this.variant]}`;
  }
}