export interface InformeMinisterio {
  id?: number;
  // Entrenador fields
  entrenadorId?: number;
  entrenadorNombrecompleto: string;
  // Supervisor fields
  supervisorId?: number;
  supervisorNombrecompleto: string;
  // Group fields
  fechainicio: string;
  fechafinal: string;
  cupoinicial: number;
  cupofinal: number;
  codigoministerio: string;
  // Aprendiz fields
  aprendizId?: number;
  aprendizCodigoverificacion: string;
  // Trabajador fields
  trabajadorId?: number;
  trabajadorNumerodocumento: string;
  trabajadorNombrecompleto: string;
  trabajadorAreatrabajo: string;
  trabajadorNiveleducativo: string;
  trabajadorCargoactual: string;
  trabajadorGenero: string;
  trabajadorNacionalidad: string;
}
