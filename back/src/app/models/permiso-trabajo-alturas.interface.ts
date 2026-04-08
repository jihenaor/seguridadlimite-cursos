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
    id: number;
    idPermiso: number;
    actividad: string;
    estado: string;
}

export interface PermisoTrabajoAlturas {
    idPermiso: number;
    fechaInicio: string;
    validoDesde: string;
    validoHasta: string;
    horaInicio: string;
    horaFinal: string;
    proyectoAreaSeccion: string;
    ubicacionEspecifica: string;
    descripcionTarea: string;
    herramientasUtilizar: string;
    alturaTrabajo: string;
    idNivel: number;
    verificacionSeguridadSocial: boolean;
    certificadoAptitudMedica: boolean;
    certificadoCompetencia: boolean;
    condicionSaludTrabajador: boolean;
    idpersonaautoriza: number;
    idresponsableemeergencias: number;
    idcoordinadoralturas: number;
    codigoministerio: string;
    cupoinicial: number;
    cupofinal: number;
    tiposTrabajo: PermisoTipoTrabajo[];
    permisoDetalleChequeos: PermisoDetalleChequeo[];
    permisoDetalleActividades: PermisoDetalleActividad[];
} 