import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { debounceTime} from 'rxjs/operators';
import { Router, RouterLink } from "@angular/router";

import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AprendizInscritoFindService } from '../../../core/service/aprendizInscritoFind.service';
import { BlockHeaderComponent } from '../../../shared/components/block-header/block-header.component';
import { AprendicesInstructorTableComponent } from './components/aprendices-table/aprendices-instructor-table.component';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';

@Component({
    selector: 'app-consult-aprendiz',
    templateUrl: './consult-aprendiz.component.html',
    imports: [
        MatFormFieldModule, MatInputModule,
        FormsModule, ReactiveFormsModule,
        MatButtonModule, MatTooltipModule, MatIconModule, NgIf,
        BlockHeaderComponent,
        AprendicesInstructorTableComponent]
})
export class ConsultAprendizComponent implements OnInit {
  searchField = new FormControl('');
  public aprendizInscritoFindService = inject(AprendizInscritoFindService);

  constructor(
    private notificacionService: ShowNotificacionService) {
  }

  ngOnInit() {
    this.searchField.valueChanges
    .pipe(debounceTime(1000))
    .subscribe(async value => {
      await this.search();
    });
  }

  search() {
    const filtro = this.searchField.value;

    if (!filtro || filtro.length < 6) {
      this.notificacionService.displayWarning('El filtro es demasiado corto');

      return;
    }

    this.aprendizInscritoFindService.searchAprendizConFiltro(filtro);
  }


  searchInscritoshoy() {
    this.aprendizInscritoFindService.searchAprendizInscritos();
}


}
