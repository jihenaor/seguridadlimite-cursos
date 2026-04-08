import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DisenocurricularfindService } from '../services/finddisenocurricular.service';
import { UpdateDisenocurricularService } from './../services/updateDisenocurricular.service';

import { Disenocurricular } from 'src/app/core/models/disenocurricular.model';
import { tap } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgFor, NgClass, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    templateUrl: './disenocurricular.component.html',
    styleUrls: ['./disenocurricular.component.scss'],
    imports: [MatButtonModule, MatIconModule, NgFor, NgClass, NgIf, MatFormFieldModule, MatInputModule, FormsModule]
})
export class DisenocurricularComponent implements OnInit {
  private idnivel: number;
  isEditing: boolean[] = []; // Un array para rastrear

  constructor(
    private route: ActivatedRoute,
    public service: DisenocurricularfindService,
    private saveService: UpdateDisenocurricularService) {
    this.route.params.subscribe(params => {
      this.idnivel = params['idnivel'];
   });

  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.service.search(this.idnivel);
  }

  addNew() {
    this.service.disenocurriculars.push(new Disenocurricular(this.idnivel));
    this.isEditing[this.service.disenocurriculars.length - 1] = true
  }


  toggleEdit(index: number) {
    this.isEditing[index] = !this.isEditing[index];
  }

  cancelChanges(index: number) {
    this.isEditing[index] = false;
  }

  saveChanges(index: number) {
    this.isEditing[index] = false;

    this.saveService.update(this.service.disenocurriculars[index])
    .pipe(
      tap(response => {
        alert('Actualización exitosa');

      })
    )
    .subscribe();
  }

  sumarHoras() {
    const horasPorContexto = new Map();

    this.service.disenocurriculars?.forEach((item) => {
      const contexto = item.contexto;
      const horas = Number(item.horas);

      if (!horasPorContexto.has(contexto)) {
        horasPorContexto.set(contexto, 0);
      }

      const horasActuales = horasPorContexto.get(contexto);
      horasPorContexto.set(contexto, horasActuales + horas);
    });

    return horasPorContexto;
  }
}
