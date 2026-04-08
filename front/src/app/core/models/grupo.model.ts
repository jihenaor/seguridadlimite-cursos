import { Nivel } from './nivel.model';
import { Professors } from './professors.model';
import { Programa } from './programa.model';
import { Aprendiz } from './aprendiz.model';
import { Empresa } from './empresa.model';
export class Grupo {
  id: number;
  identrenadorinduccion: number;
  identrenador: number;
  idsupervisor: number;
  idpersonaapoyo: number;

  entrenadorinduccion: Professors;
  entrenador: Professors;
  supervisor: Professors;
  personaapoyo: Professors;

  fechainicio: Date;
  fechafinal: string;
  fechaexpedicion: string;

  aucodestad: string;

  idnivel: number;
  nivel: Nivel;
  cupoinicial: number;
  cupofinal: number;
  codigoministerio: string;
  fechas: Date[];
  aprendizs: Aprendiz[];
  idempresa: number;
  selected: boolean;
  contadorAprendices: number;
  empresa: Empresa;

  constructor(curso) {
    {
      this.id = curso.id;
      this.identrenadorinduccion = curso.identrenadorinduccion;
      this.identrenador = curso.identrenador;
      this.idsupervisor = curso.idsupervisor;

      this.entrenadorinduccion = curso.entrenadorinduccion;
      this.entrenador = curso.entrenador;
      this.supervisor = curso.supervisor;

      this.fechainicio = curso.fechainicio;
      this.fechafinal = curso.fechafinal;

      this.fechaexpedicion = curso.fechaexpedicion;
      this.aucodestad = curso.aucodestad;

      this.cupoinicial = curso.cupoinicial;
      this.cupofinal = curso.cupofinal;
      this.idnivel = curso.idnivel;
      this.codigoministerio = curso.codigoministerio;

      this.fechas = curso.fechas;
      this.idempresa = curso.idempresa;
      this.selected = false;
    }
  }
}
