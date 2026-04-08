import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
    selector: '[appValidarFecha]',
    standalone: true
})
export class ValidarFechaDirective {
  constructor(private el: ElementRef) {}

  @HostListener('input', ['$event']) onInputChange(event: Event) {
    const input = this.el.nativeElement.value;
    const onlyDigits = input.replace(/\D/g, '');
    const day = onlyDigits.substring(0, 2);
    const month = onlyDigits.substring(2, 4);
    const year = onlyDigits.substring(4, 8);

    let output = '';
    if (onlyDigits.length > 0) {
      output =  `${day}/`;
    }
    if (onlyDigits.length > 2) {
      output = `${day}/${month}/`;
    }
    if (onlyDigits.length > 4) {
      output = `${day}/${month}/${year}`;
    }
    this.el.nativeElement.value = output;
    this.el.nativeElement.setSelectionRange(output.length, output.length);
  }

  @HostListener('blur') onBlur() {
    const fecha = this.el.nativeElement.value;
    const regex = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;

    if (regex.test(fecha)) {
      const [_, dia, mes, anio] = fecha.match(regex);
      const fechaValida = new Date(+anio, +mes - 1, +dia);
      const diaValido = fechaValida.getDate() == +dia;
      const mesValido = fechaValida.getMonth() + 1 == +mes;
      const anioValido = fechaValida.getFullYear() == +anio;

      if (!(diaValido && mesValido && anioValido)) {
        alert('Fecha no válida');
        this.el.nativeElement.value = '';
        this.el.nativeElement.focus();
      } else {
        const [day, month, year] = this.el.nativeElement.value.split('/');
        const dateValue = new Date(`${year}-${month}-${day}`);

        const currentDate = new Date();
        const minDate = new Date(currentDate.getFullYear() - 100, currentDate.getMonth(), currentDate.getDate());
  
        if (dateValue > currentDate || dateValue < minDate) {
          this.el.nativeElement.value = '';
          alert('La fecha ingresada no es válida. Verifique que no supere la fecha actual o que la fecha supere los 100 años');
        }

        this.el.nativeElement.value = `${dia}/${mes}/${anio}`;
        this.el.nativeElement.value = this.el.nativeElement.value.toUpperCase();
      }
    } else {
      if (fecha.length > 0) {
        alert('Formato de fecha incorrecto');
        this.el.nativeElement.value = '';
        this.el.nativeElement.focus();
      }
    }
  }
}