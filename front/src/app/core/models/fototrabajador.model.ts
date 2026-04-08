export class Fototrabajador {
  id: number;
  base64: string;
  ext: string;
  constructor(documento) {
    {
      this.id = documento.id;
      this.base64 = documento.base64;
      this.ext = documento.ext;
    }
  }
}
