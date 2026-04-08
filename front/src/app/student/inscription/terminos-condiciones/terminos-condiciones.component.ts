import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'inscripcion-terminos-condiciones',
    templateUrl: './terminos-condiciones.component.html',
    imports: [FormsModule]
})
export class TerminosCondicionesComponent {
  aceptarterminos: boolean = false;
  @Output() checkboxChange: EventEmitter<boolean> = new EventEmitter<boolean>();


  @Input() esEmpresa;

  onCheckboxChange(): void {
    this.checkboxChange.emit(this.aceptarterminos);
  }}
