import { Evaluacion } from './evaluacion.model';
export class Grupoevaluacion {
  id: number;
  nombre: string;
  tipoevaluacion: string;
  orden: number;
  evaluacions: Evaluacion[];
  porcentaje: number;

  constructor(g) {
    {
      this.id = g.id;
      this.nombre = g.nombre;
      this.tipoevaluacion = g.tipotipoevaluacion;
      this.orden = g.orden;
      this.evaluacions = g.evaluacions;
    }
  }
}
