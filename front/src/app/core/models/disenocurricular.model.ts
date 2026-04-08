export class Disenocurricular {
  id: number;
  modulo: number;
  contexto: string;
  unidad: string;
  dia: number;
  horas: number;
  idnivel: number;


  constructor(idnivel: number) {
    this.idnivel = idnivel;
  }
}
