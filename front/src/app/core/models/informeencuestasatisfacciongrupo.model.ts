import { Grupo } from './grupo.model';
import { Pregunta } from './pregunta.model';

export class InformeEncuestaSatisfaccionGrupo {
  numeroAprendices: number;
  grupo: Grupo
  preguntas: Pregunta[] ;
}
