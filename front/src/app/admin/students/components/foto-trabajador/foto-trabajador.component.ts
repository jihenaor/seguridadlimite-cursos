import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

import { NgIf } from '@angular/common';

@Component({
    selector: 'app-foto-trabajador',
    templateUrl: './foto-trabajador.component.html',
    imports: [NgIf]
})
export class FotoTrabajadorComponent {
  @Input()
  base64: string;

  constructor(private sanitizer: DomSanitizer,) { }

  transformFoto() {
    if (this.base64) {
      return this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,' + this.base64);
    }
  }

}
