export interface RespuestaEvaluacion {
  id:                      number;
  pregunta:                string;
  numerorespuestacorrecta: number;
  orden:                   number;
  grupo:                   Grupo;
  enfasis:                 Bloque;
  bloque:                  Bloque;
  type:                    number;
  estado:                  Estado;
  respuesta1:              string;
  respuesta2:              string;
  respuesta3:              string;
  respuesta4:              string;
  respuestacorrecta:       string;
  idevaluacion:            number;
  numerorespuesta:         number;
  nivel:                   Nivel;
  respuestas:              Respuesta[];
  respondida:              boolean;
  textorespuesta:          string;
  base64?:                 string;
}

export interface Bloque {
  id:     number;
  nombre: BloqueNombre;
}

export enum BloqueNombre {
  Definiciones = "DEFINICIONES",
  Generalidades = "GENERALIDADES",
  MedidasDeProteccion = "Medidas de proteccion",
  MedidasPreventivas = "Medidas preventivas",
  NoDefinido = "NO DEFINIDO",
  Obligaciones = "Obligaciones",
}

export enum Estado {
  A = "A",
}

export interface Grupo {
  id:             number;
  nombre:         GrupoNombre;
  tipoevaluacion: Tipoevaluacion;
  orden:          number;
  idprograma:     number;
  preguntas:      null;
}

export enum GrupoNombre {
  Conocimiento = "CONOCIMIENTO",
}

export enum Tipoevaluacion {
  T = "T",
}

export interface Nivel {
  id:                        number;
  nombre:                    NivelNombre;
  duraciontotal:             number;
  requierereentranamiento:   Requierereentranamiento;
  anos:                      number;
  idprograma:                number;
  capacidadanalitica:        Actitudautoaprendizaje;
  actitudautoaprendizaje:    Actitudautoaprendizaje;
  relacionesinterpersonales: Actitudautoaprendizaje;
  trabajaequipo:             Actitudautoaprendizaje;
  formacionbasica1409:       Actitudautoaprendizaje;
  dominioinformacion:        Actitudautoaprendizaje;
  liderazgo:                 Actitudautoaprendizaje;
  seguirinstrucciones:       Actitudautoaprendizaje;
  estado:                    Estado;
  orden:                     null;
}

export enum Actitudautoaprendizaje {
  N = "N",
}

export enum NivelNombre {
  TrabajadorAutorizado = "TRABAJADOR AUTORIZADO",
}

export enum Requierereentranamiento {
  S = "S",
}

export interface Respuesta {
  id:         number;
  numero:     number;
  respuesta:  string;
  idpregunta: number;
  respondida: Boolean;
}
