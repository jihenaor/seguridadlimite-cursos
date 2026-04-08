import { Programa } from "./programa.model";
import { Grupo } from './grupo.model';

// Interfaz para los días de diseño
export interface DiaDto {
  dia: number;
  fecha: string;
  seleccionado: boolean;
  contexto: string;
  unidad: string;
}

export class Nivel {
  id: number;
  nombre: string;
  programa: Programa;
  duraciontotal: number;
  requierereentrenamiento: string;
  anos: number;
  grupos: Grupo[];

  capacidadanalitica: string;
  actitudautoaprendizaje: string;
  relacionesinterpersonales: string;
  trabajaequipo: string;
  formacionbasica1409: string;
  dominioinformacion: string;
  liderazgo: string;
  seguirinstrucciones: string;
  tieneevaluacionconocimientos: string
  fechadesde?: string;
  fechahasta?: string;
  seleccionado?: boolean;
  cupoInicial?: number;
  numerogrupos?: number;
  dias?: number;
  diasdiseno?: DiaDto[];

  constructor(nivel) {
    {
      this.id = nivel.id;
      this.nombre = nivel.nombre || '';
      this.programa = nivel.programa || {};
      this.duraciontotal = nivel.duraciontotal;
      this.requierereentrenamiento = nivel.requierereentrenamiento;
      this.anos = nivel.anos;
      this.grupos = nivel.grupos || [];
      this.actitudautoaprendizaje = nivel.actitudautoaprendizaje;
      this.relacionesinterpersonales = nivel.relacionesinterpersonales;
      this.trabajaequipo = nivel.trabajaequipo;
      this.formacionbasica1409 = nivel.formacionbasica1409;
      this.dominioinformacion = nivel.dominioinformacion;
      this.liderazgo = nivel.liderazgo;
      this.seguirinstrucciones = nivel.seguirinstrucciones;
      this.seleccionado = false;
    }
  }

}
