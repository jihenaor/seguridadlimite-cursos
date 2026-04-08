export class Fotografiaaprendiz {
  id: number;
  ext: string;
  base64: string;
  constructor(trabajador) {
    {
      this.id = trabajador.id;
      this.ext = trabajador.ext;
      this.base64 = trabajador.base64;
    }
  }
}
