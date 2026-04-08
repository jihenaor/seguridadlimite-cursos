import { Pregunta } from './pregunta.model';

export interface RespuestaEncuestaSatisfaccion {
  comentariosencuesta: string;
  activacomentarios: boolean;
  preguntas: Pregunta[];
}
