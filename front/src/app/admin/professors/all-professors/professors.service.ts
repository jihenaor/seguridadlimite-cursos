import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Professors } from './../../../core/models/professors.model';
import { environment } from 'src/environments/environment';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class ProfessorsService {
  dataChange: BehaviorSubject<Professors[]> = new BehaviorSubject<Professors[]>(
    []
  );
  // Temporarily stores data from dialogs
  dialogData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Professors[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }
  /** CRUD METHODS */
  getAllProfessorss(): void {
    this.httpClient.get<Professors[]>(`${environment.apiUrl}/personals`).subscribe(
      data => {
        this.dataChange.next(data);
      },
      (error: HttpErrorResponse) => {
        console.log(error.name + ' ' + error.message);
      }
    );
  }
  // DEMO ONLY, you can find working methods below
  async addProfessors(professors: Professors) {
    this.dialogData = professors;

    const url = `${environment.apiUrl}/savePersonal`;

    const datos = JSON.stringify(professors);
//            'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
// 'Authorization': 'my-new-auth-token'
    try {
      const response = await fetch(url,
        {
          method: 'POST',
          body: datos,
          headers: {
            'Content-Type': 'application/json'
          }
        });
      if (!response.ok) {
        alert('Se genero un error ' + response.statusText);
      } else {
        const data = await response.json();
        return data;
      }
    } catch (error) {
      alert(error);
    }
  }

  updateProfessors(professors: Professors): void {
    this.dialogData = professors;
  }

  deleteProfessors(id: number): void {
    console.log(id);
  }
}
