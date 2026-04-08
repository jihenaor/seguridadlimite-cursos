import { Modulo } from './modulo.model';
import { Asistencia } from './asistencia.model';
import { Nivel } from './nivel.model';
import { Enfasis } from './enfasis.model';
import { Trabajador } from './trabajador.model';
import { Grupo } from './grupo.model';
import { Documento } from './documento.model';
import { PermisoTrabajoAlturas } from './permiso-trabajo.interface';

export class Aprendiz {
  id: number;
  idtrabajador: number;
  trabajador: Trabajador;
  nombrecompleto: string;
  idgrupo: number;
  grupo: Grupo;
  idenfasis: number;
  enfasis: Enfasis;
  idnivel: number;
  nivel: Nivel;
  fechaverificacion: Date;
  codigoverificacion: string;
  pagocurso: string;
  tieneexperienciaaltura: string;
  labordesarrolla: string;
  operativa: string;
  administrativa: string;
  otralabor: string;

  nombrecontacto: string;
  telefonocontacto: string;
  parentescocontacto: string;

  embarazo: string;
  mesesgestacion: string;

  tipovinculacionlaboral: string;
  regimenafiliacionseguridadsocial: string;
  documentoidentidad: string;
  ultimopagoseguridadsocial: string;
  afiliacionseguridadsocial: string;
  certificadoaptitudmedica: string;
  certificadotrabajadorautorizado: string;
  certificadonivelcoordinador: string;

  eps: string;
  arl: string;
  sabeleerescribir: string;
  niveleducativo: string;
  cargoactual: string;

  alergias: string;
  medicamentos: string;
  enfermedades: string;
  lesiones: string;
  drogas: string;
  idempresa: number;
  evaluacionformacion: number;
  evaluacionentrenamiento: number;
  cumplehoras: string;
  abonocurso: string;
  fechareentrenamiento: string;
  documentos: Documento[];
  estadoinscripcion: string;

  eingreso: number;
  eenfasis: number;
  eteorica1: number;
  eteorica2: number;
  epractica: number;

  exteteorica1: string;
  exteteorica2: string;
  extepractica: string;

  asistencia: Asistencia;
  asistencia1: Asistencia;
  asistencia2: Asistencia;
  asistencia3: Asistencia;
  asistencia4: Asistencia;
  asistencia5: Asistencia;

  asistencias: Asistencia[];
  modulos: Modulo[];

  aprobocurso: boolean;
  base64: string;
  aceptaterminos: string;
  tienefirma: string;
  fechaemision: string;
  ciudadreentrenamiento: string
  ciudadexpedicion: string;
  empresa: string;
  nit: string;
  representantelegal: string;
  minutosEvaluacion: number;
  fechaencuesta: string;
  fechainscripcion: string;
  fechalimiteinscripcion: string;
  fechalimiteevaluacion: string;
  fechalimiteencuesta: string;
  totalhoras: number;
  duraciontotal: number;

  departamentodomicilio: string;
  ciudaddomicilio: string;
  direcciondomicilio: string;
  foto: string;
  idPermiso: number;
  permisoTrabajoAlturas: PermisoTrabajoAlturas;
  asistenciaCompleta: boolean;


  codigoministerio: string;
}
