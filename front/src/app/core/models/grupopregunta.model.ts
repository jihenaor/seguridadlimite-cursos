export class Grupopregunta {
  id: number;
  nombre: string;
  tipoevaluacion: string;
  orden: number;
  idprograma: number;
  constructor(g) {
    {
      this.id = g.id;
      this.nombre = g.nombre;
      this.tipoevaluacion = g.tipotipoevaluacion;
      this.orden = g.orden;
      this.idprograma = g.idprograma;
    }
  }
}
