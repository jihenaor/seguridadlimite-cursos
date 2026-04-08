import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Evaluacion } from '../../../core/models/evaluacion.model';

@Injectable()
export class EvaluacionteoricaService {
  dataChange: BehaviorSubject<Evaluacion[]> = new BehaviorSubject<Evaluacion[]>([]);
  // Temporarily stores data from dialogs
  dialogData: any;
  filterData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Evaluacion[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }

  getFilterData() {
    return this.filterData;
  }

  setFilterData(f: any) {
    this.filterData = f;
  }
  /** CRUD METHODS */
  getEvaluacionGrupo(filter: any): void {
    let nameService: string;

    nameService = '/evaluacion/T/' + filter.idaprendiz + '/' + filter.numeroevaluacion + '/' + filter.idnivel;

    this.httpClient.get<Evaluacion[]>(`${environment.apiUrl}` + nameService).subscribe(
      data => {
        this.dataChange.next(data);
      },
      (error: HttpErrorResponse) => {
        console.log(error.name + ' ' + error.message);
      }
    );
  }
  // DEMO ONLY, you can find working methods below
  addStudents(students: Evaluacion): void {
    this.dialogData = students;
  }
  async filterCurso(curso: Evaluacion) {
    this.filterData = curso;
  }
  updateStudents(students: Evaluacion): void {
    this.dialogData = students;
  }
}
