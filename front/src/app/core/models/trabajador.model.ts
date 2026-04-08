import { formatDate } from '@angular/common';
import { Grupo } from './grupo.model';
export class Trabajador {
  id: number;
  img: string;

  tipodocumento: string;
  numerodocumento: string;
  primernombre: string;
  segundonombre: string;
  primerapellido: string;
  segundoapellido: string;
  nombrecompleto: string;
  areatrabajo: string;
  genero: string;
  fechanacimiento: string;
  nacionalidad: string;
  tiposangre: string;
  ocupacion: string;
  departamentodomicilio: string;
  ciudaddomicilio: string;
  direcciondomicilio: string;
  celular: string;
  correoelectronico: string;

  adjuntodocumento: string;
  ext: string;
  foto: string;
  valido: string;
  base64: string;
  base64a: string;
  base64b: string;
  idaprendiz: number;
  idenfasis: number;
  nombreenfasis: string;
  otroenfasis: string;
  idnivel: number;
  nombrenivel: string
  grupo: Grupo;
  idgrupo: number;
  idtrabajador: number;

  inscripcionconscaner: string;
  exception: string;
  embarazo: string;
  mesesgestacion: string;

  tieneexperienciaaltura: string;
  labordesarrolla: string;

  nombrecontacto: string;
  telefonocontacto: string;
  parentescocontacto: string;

  documentoidentidad: string;

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
  fechaemision: string;
  fechareentrenamiento: string;
  ciudadreentrenamiento: string
  ciudadexpedicion: string;
  codigoverificacion: string;
  empresa: string;
  nit: string;
  representantelegal: string;
  eingreso: number;
  fechainscripcion: string;
  fechalimiteinscripcion: string;
  fechaencuesta: string;
  asistenciacompleta: boolean;
  existeinscripcionabierta: boolean;
  aprendizContinuaAprendizaje: boolean;
  fechaUltimaAsistencia: string;
  tipovinculacionlaboral: string;
  regimenafiliacionseguridadsocial: string;
  evaluacionAbierta?: boolean;

  constructor(trabajador) {
    {
      this.id = trabajador.id;
      this.img = trabajador.avatar || 'assets/images/user/user1.jpg';
      this.numerodocumento = trabajador.numerodocumento || '';
      this.nombrecompleto = trabajador.nombrecompleto || '';
      this.correoelectronico = trabajador.correoelectronico || '';
      this.genero = trabajador.genero || '';
      this.celular = trabajador.celular || '';
      this.adjuntodocumento = trabajador.adjuntodocumento;
      this.ext = trabajador.ext;
      this.foto = trabajador.foto;
    }
  }
}
