import { Grupopregunta } from './grupopregunta.model';
import { Enfasis } from './enfasis.model';
import { Nivel } from './nivel.model';
import { Respuesta } from './respuesta.model';
import { TipoevaluacionEstadistica } from './tipoevaluacionestadistica.model';

export class Pregunta {
  id: number;
  pregunta: string;
  idgrupo: number;
  grupo: Grupopregunta;
  numerorespuestacorrecta: number;
  respuestacorrecta: string;
  orden: number;
  idenfasis: number;
  enfasis: Enfasis;
  idnivel: number;
  nivel: Nivel;
  type: number;
  estado: string;
  agrupador1: string;
  agrupador2: string;
  agrupador3: string;
  respuestas: Respuesta[];
  opcionesrespuesta: string;
  nombreopcion: string;
  opcionesrespuestaArray: string[];
  tipoevaluacionEstadisticas: TipoevaluacionEstadistica[]
  numerorespuesta: number;
  textorespuesta: string;
  diflectura: string;
  base64: string;
}
