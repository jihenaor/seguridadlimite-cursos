import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
    selector: '[nombreValidator]',
    standalone: true
})
export class NombreValidatorDirective {
  
  constructor(private el: ElementRef) { }

  @HostListener('input', ['$event']) onInputChange(event) {
    const initialValue = event.target.value;
    event.target.value = initialValue.replace(/[^a-zA-ZñÑáéíóúÁÉÍÓÚ\s]/g, '').toUpperCase();
    if (initialValue !== event.target.value) {
      event.stopPropagation();
    }
  }

}