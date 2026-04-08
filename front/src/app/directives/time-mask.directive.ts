import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[timeMask]',
  standalone: true
})
export class TimeMaskDirective {
  @Input() maxLength: number = 5;

  constructor(private el: ElementRef) {}

  @HostListener('input', ['$event'])
  onInput(event: InputEvent) {
    let value = this.el.nativeElement.value;

    // Eliminar cualquier carácter que no sea número o :
    value = value.replace(/[^\d:]/g, '');

    // Si el primer número es mayor a 2, agregar un 0 al inicio
    if (value.length === 1 && parseInt(value) > 2) {
      value = '0' + value;
    }

    // Si hay dos números y el primer número es 2, validar que el segundo sea 0-3
    if (value.length === 2) {
      const firstDigit = parseInt(value[0]);
      const secondDigit = parseInt(value[1]);
      if (firstDigit === 2 && secondDigit > 3) {
        value = '23';
      }
    }

    // Agregar : después de los primeros dos números si no existe
    if (value.length === 2 && !value.includes(':')) {
      value += ':';
    }

    // Validar minutos (00-59)
    if (value.length > 3) {
      const minutes = parseInt(value.split(':')[1]);
      if (minutes > 59) {
        value = value.substring(0, 3) + '59';
      }
    }

    // Limitar la longitud total
    if (value.length > this.maxLength) {
      value = value.substring(0, this.maxLength);
    }

    this.el.nativeElement.value = value;
  }
}