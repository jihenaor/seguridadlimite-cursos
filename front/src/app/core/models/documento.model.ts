export class Documento {
  id: number;
  nombre: string;
  nombrefile: string;
  base64: string;
  ext: string;
  idaprendiz: number;
  iddocumentoaprendiz: number;
  savedmsg: string;
  constructor(documento) {
    {
      this.id = documento.id;
      this.nombre = documento.nombre || '';
      this.idaprendiz = documento.idaprendiz;
      this.iddocumentoaprendiz = documento.iddocumentoaprendiz;
    }
  }
}
