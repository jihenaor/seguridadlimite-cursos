import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Aprendiz } from '../../../core/models/aprendiz.model';

@Injectable()
export class AprendicesempresaService {
  private readonly API_URL = 'assets/data/students.json';
  dataChange: BehaviorSubject<Aprendiz[]> = new BehaviorSubject<Aprendiz[]>([]);
  // Temporarily stores data from dialogs
  dialogData: any;
  filterData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Aprendiz[] {
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
  getAllAprendices(filter: any): void {
    let nameService: string;

    if (filter.idempresa !== undefined) {
      nameService = 'aprendizempresa/' + filter.idempresa;
    }

    this.httpClient.get<Aprendiz[]>(`${environment.apiUrl}/` + nameService).subscribe(
      data => {
        this.dataChange.next(data);
      },
      (error: HttpErrorResponse) => {
        alert(error.name + ' ' + error.message);
      }
    );
  }
  // DEMO ONLY, you can find working methods below
  addStudents(students: Aprendiz): void {
    this.dialogData = students;
  }
  async filterCurso(curso: Aprendiz) {
    this.filterData = curso;
  }
  updateStudents(students: Aprendiz): void {
    this.dialogData = students;
  }
}
