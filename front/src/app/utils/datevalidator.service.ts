import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DatevalidatorService {
  // constructor() {}
  formatDate(dateString) {

    if (dateString && dateString.length == 8) {
      const day = dateString.substr(0, 2);
      const month = dateString.substr(2, 2);
      const year = dateString.substr(4, 4);

      return `${day}/${month}/${year}`;
    }

    if (dateString && dateString.length == 10) {
      return dateString.replace('-', '/');
    }

    return dateString;
  }

  isFechaMayorFechaActual(fecha: string): boolean {
    const fechaActual = new Date();
    const fechaFinalFormatted = new Date(fecha + "T00:00:00");

    fechaActual.setHours(0, 0, 0, 0);

    return fechaFinalFormatted >= fechaActual;
  }
}
