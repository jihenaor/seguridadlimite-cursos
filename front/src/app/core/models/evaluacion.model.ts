import { Pregunta } from './pregunta.model';

export class Evaluacion {
  id: number;
  numerorespuesta: number;
  textorespuesta: string;
  respuestacorrecta: string;
  idaprendiz: number;
  pregunta: Pregunta;

  constructor(pregunta) {
    {
      this.id = pregunta.id;
      this.numerorespuesta = pregunta.numerorespuesta;
      this.textorespuesta = pregunta.textorespuesta;
      this.respuestacorrecta = pregunta.respuestacorrecta;
      this.idaprendiz = pregunta.idaprendiz;
      this.pregunta = pregunta.pregunta;
    }
  }
}
