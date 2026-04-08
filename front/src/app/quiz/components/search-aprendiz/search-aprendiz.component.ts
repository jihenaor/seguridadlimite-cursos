import { AfterViewInit, Component, ElementRef, ViewChild, Input } from '@angular/core';
import { fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { AprendizEvaluacionService } from '../../services/aprendizevaluacion.service';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
    selector: 'quiz-search-aprendiz',
    templateUrl: './search-aprendiz.component.html',
    imports: [MatFormFieldModule, MatInputModule]
})
export class SearchAprendizComponent implements AfterViewInit {
  @ViewChild("txtTagInput")
  public tagInput!: ElementRef<HTMLInputElement>;

  @Input()
  opciones: Record<string, boolean> = {};

  constructor(private aprendizService: AprendizEvaluacionService) {

  }

  ngAfterViewInit(): void {
    const numerodocumento = sessionStorage.getItem('numerodocumento') || ''

    this.tagInput.nativeElement.value = numerodocumento;

    if (numerodocumento && numerodocumento.length > 3) {
      this.search();
    }

    fromEvent(this.tagInput.nativeElement, 'input')
    .pipe(
      debounceTime(1000),
      distinctUntilChanged()
    )
    .subscribe(() => {

    });
  }

  search() {
    if (!this.tagInput.nativeElement.value
      || this.tagInput.nativeElement.value.length < 4) {
        alert('El número de documento no es válido')
      return;
    }

    if (this.opciones.evaluacionTeorico) {
      this.aprendizService.searchAprendizevaluacion(this.tagInput.nativeElement.value, 'teorico');
    } else if (this.opciones.conocimientosPrevios) {
      this.aprendizService.searchAprendizevaluacion(this.tagInput.nativeElement.value, 'conocimientosprevios');
    } else if (this.opciones.satisfaccionCliente) {
        this.aprendizService.searchAprendizevaluacion(this.tagInput.nativeElement.value, 'satisfaccioncliente');
    }
  }

  goBack(): void {
    window.history.back();
  }

}
