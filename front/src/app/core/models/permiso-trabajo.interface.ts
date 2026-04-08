interface GrupoChequeo {
  idGrupo: number;
  descripcion: string;
  estado: string;
  detalles: DetalleChequeo[];
}

interface DetalleChequeo {
  idDetalle: number;
  idGrupo: number;
  descripcion: string;
  estado: string;
  respuesta: string;
}


export interface PermisoTipoTrabajo {
    id: number;
    idPermiso: number;
    tipoTrabajo: string;
}

export interface PermisoDetalleChequeo {
    id: number;
    idPermiso: number;
    pregunta: string;
    respuesta: boolean;
}

export interface PermisoDetalleActividad {
    idPermisoActividad: number;
    idPermiso: number;
    actividadrealizar: string;
    peligros: string;
    controlesrequeridos: string;
}

export interface PermisoFechas {
  id?: number;
  idPermiso: number;
  fecha: string; // Format: YYYY-MM-DD
  dia: number;
}

export interface PermisoTrabajoAlturas {
    idPermiso: number;
    codigoministerio: number;
    idPersonal: number;
    fechaInicio: string;
    cupoinicial: number;
    cupofinal: number;
    validodesde: string;
    validohasta: string;
    horaInicio: string;
    horaFinal: string;
    proyectoAreaSeccion: string;
    ubicacionEspecifica: string;
    descripcionTarea: string;
    herramientasUtilizar: string;
    alturaTrabajo: string;
    idNivel: number;
    nombrenivel: string;
    verificacionSeguridadSocial: boolean;
    certificadoAptitudMedica: boolean;
    certificadoCompetencia: boolean;
    condicionSaludTrabajador: boolean;

    numerogrupos: number;
    idpersonaautoriza1: number;
    idpersonaautoriza2: number;
    nombrepersonaautoriza1: string;
    nombrepersonaautoriza2: string;
    idresponsableemeergencias: number;

    gruposChequeo: GrupoChequeo[];
    tiposTrabajo: PermisoTipoTrabajo[];
    permisoDetalleChequeos: PermisoDetalleChequeo[];
    permisoDetalleActividades: PermisoDetalleActividad[];
    permisoFechas: PermisoFechas[];
    seleccionado: boolean;
    dias: number;
}
