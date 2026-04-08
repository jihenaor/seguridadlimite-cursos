import { Injectable } from '@angular/core';

@Injectable()
export class DeviceService {
  private deviceId: string;

  constructor() {
    this.deviceId = this.generateDeviceId();
  }

  getDeviceId(): string {
    return this.deviceId;
  }

  private generateDeviceId(): string {
    const userAgent = navigator.userAgent;
    // Puedes utilizar algún algoritmo de hash para generar un código único
    // Aquí se utiliza un ejemplo simple concatenando el userAgent con la fecha actual
    const uniqueCode = `${userAgent}_${Date.now()}`;

    // Puedes almacenar el código en sessionStorage o sessionStorage para persistencia
    // En este ejemplo, simplemente se devuelve el código generado
    return uniqueCode;
  }
}
