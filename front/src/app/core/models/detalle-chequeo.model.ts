/**
 * Interfaz que representa un detalle de chequeo
 */
export interface DetalleChequeo {
  idDetalle: number;
  idGrupo: number;
  codigo: string;
  descripcion: string;
  estado: string;
}
