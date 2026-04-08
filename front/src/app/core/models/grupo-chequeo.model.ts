import { DetalleChequeo } from './detalle-chequeo.model';

/**
 * Interfaz que representa un grupo de chequeo
 */
export interface GrupoChequeo {
  idGrupo: number;
  codigo: string;
  descripcion: string;
  posiblesValores?: string;
  detalles?: DetalleChequeo[];
}
