import { Component, OnInit, inject } from '@angular/core';
import { FormControl, UntypedFormBuilder, UntypedFormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { debounceTime} from 'rxjs/operators';
import { Router, RouterLink } from "@angular/router";

import { ParametrosFindService } from '../../../core/service/parametrosFind.service';
import { AprendizFindService } from '../../../core/service/aprendizfind.service';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AprendizInscritoFindService } from '../../../core/service/aprendizInscritoFind.service';
import { BlockHeaderComponent } from '../../../shared/components/block-header/block-header.component';
import { AprendicesTableComponent } from '../components/aprendices-table/aprendices-table.component';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatButtonModule, MatTooltipModule, MatIconModule, NgIf, BlockHeaderComponent, AprendicesTableComponent]
})
export class MainComponent implements OnInit {
  searchField = new FormControl('');
  public aprendizInscritoFindService = inject(AprendizInscritoFindService);

  constructor(
    private snackBar: MatSnackBar,
    private router: Router) {
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
      this.showNotification(
        'snackbar-danger',
        'El filtro es demasiado corto',
        'bottom',
        'center'
      );
      return;
    }

    this.aprendizInscritoFindService.searchAprendizConFiltro(filtro);
  }


  searchInscritos() {
    this.aprendizInscritoFindService.searchAprendizInscritos();
}

  irTrabajador(idtrabajador: number, idaprendiz: number) {
    try {
      sessionStorage.setItem('idtrabajador', idtrabajador + '');
      sessionStorage.setItem('idaprendiz', idaprendiz + '');

      this.router.navigate(['/admin/students/about-aprendiz', idaprendiz]);
    } catch(error) {
      alert('Error al abrir la página para tomar la foto' + error);
    }
  }


  showNotification(colorName, text, placementFrom, placementAlign) {
    this.snackBar.open(text, '', {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }
}
