import { Component, OnInit, inject, computed } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { debounceTime} from 'rxjs/operators';

import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AprendizInscritoFindService } from '../../../core/service/aprendizInscritoFind.service';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';
import { AprendicesTableComponent } from '../components/aprendices-table/aprendices-table.component';
import { ResumenInscritosModalComponent } from '../components/resumen-inscritos-modal/resumen-inscritos-modal.component';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    imports: [
      FormsModule,
      ReactiveFormsModule,
      MatButtonModule,
      MatTooltipModule,
      MatIconModule,
      NgIf,
      MatProgressSpinnerModule,
      PagetitleComponent,
      AprendicesTableComponent,
    ]
})
export class MainComponent implements OnInit {
  readonly pageTitle = 'Consulta de aprendices';
  searchField = new FormControl('');
  public aprendizInscritoFindService = inject(AprendizInscritoFindService);
  private readonly dialog = inject(MatDialog);

  readonly hasAprendices = computed(() => {
    const list = this.aprendizInscritoFindService.aprendizs();
    return Array.isArray(list) && list.length > 0;
  });

  constructor(private snackBar: MatSnackBar) {}

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

  mostrarResumen(): void {
    const aprendices = this.aprendizInscritoFindService.aprendizs();
    if (!aprendices?.length) {
      return;
    }
    this.dialog.open(ResumenInscritosModalComponent, {
      width: '600px',
      data: { aprendices },
    });
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
