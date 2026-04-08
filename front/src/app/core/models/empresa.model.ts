import { Enfasis } from './enfasis.model';
export class Empresa {
  id: number;
  tipodocumento: string;
  numerodocumento: string;
  nombre: string;
  direccion: string;
  telefono: string;
  nombrecontacto: string;
  telefonocontacto: string;
  certificadosinpago: string;
  nombrerepresentantelegal: string;
  idenfasis: number;
  enfasis: Enfasis
  seleccion: string;

  constructor(empresa) {
    {
      this.id = empresa.id;
      this.tipodocumento = empresa.tipodocumento;
      this.numerodocumento = empresa.numerodocumento;
      this.nombre = empresa.nombre;
      this.direccion = empresa.direccion;
      this.telefono = empresa.telefono;
      this.nombrecontacto = empresa.nombrecontacto;
      this.telefonocontacto = empresa.telefonocontacto;
      this.certificadosinpago = empresa.certificadosinpago;
      this.nombrerepresentantelegal = empresa.nombrerepresentantelegal;
    }
  }
}
