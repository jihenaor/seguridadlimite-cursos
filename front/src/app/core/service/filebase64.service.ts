import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Filebase64Service {


  constructor() {

  }

  separateBase64Data(base64Text: string): { mimeType: string, extension: string, content: string } {
    const base64Parts = base64Text.split(';base64,');

    if (base64Parts.length !== 2) {
      throw new Error('Texto de base64 no válido. Debe estar en el formato "data:[tipo MIME];base64,[contenido]"');
    }

    const mimeTypeAndExtension = base64Parts[0].split(':');
    if (mimeTypeAndExtension.length !== 2) {
      throw new Error('Texto de base64 no válido. El tipo MIME no está presente.');
    }

    const mimeType = mimeTypeAndExtension[1].trim();
    const contentPart = base64Parts[1];

    const extension = mimeType.split('/')[1];

    return {
      mimeType: mimeType,
      extension: extension,
      content: contentPart
    };
  }
}
