import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Grupoevaluacion } from '../../../core/models/grupoevaluacion.model';

@Injectable()
export class EvaluacionpracticaService {
  dataChange: BehaviorSubject<Grupoevaluacion[]> = new BehaviorSubject<Grupoevaluacion[]>([]);
  // Temporarily stores data from dialogs
  dialogData: any;
  filterData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Grupoevaluacion[] {
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

  getEvaluacionGrupo(filter: any): void {
    let nameService: string;

    nameService = '/evaluacion/T/' + filter.idaprendiz;

    this.httpClient.get<Grupoevaluacion[]>(`${environment.apiUrl}` + nameService).subscribe(
      data => {
        this.dataChange.next(data);
      },
      (error: HttpErrorResponse) => {
        console.log(error.name + ' ' + error.message);
      }
    );
  }
  // DEMO ONLY, you can find working methods below
  addStudents(students: Grupoevaluacion): void {
    this.dialogData = students;
  }
  async filterCurso(curso: Grupoevaluacion) {
    this.filterData = curso;
  }
  updateStudents(students: Grupoevaluacion): void {
    this.dialogData = students;
  }
}
