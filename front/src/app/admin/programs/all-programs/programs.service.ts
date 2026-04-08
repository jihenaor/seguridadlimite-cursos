import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Programa } from '../../../core/models/programa.model';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
@Injectable()
export class ProgramsService {
  dataChange: BehaviorSubject<Programa[]> = new BehaviorSubject<Programa[]>(
    []
  );

  private _programas: Programa[] | null;

  dialogData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Programa[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }

  getAllPrograms(): void {
    this.httpClient.get<Programa[]>(`${environment.apiUrl}` + '/programas').subscribe(
      data => {
        this.dataChange.next(data);
        this._programas = data;
      },
      (error: HttpErrorResponse) => {
        console.log(error.name + ' ' + error.message);
      }
    );
  }
  // DEMO ONLY, you can find working methods below
  async addPrograma(professors: Programa) {
    this.dialogData = professors;

    const url = `${environment.apiUrl}` + '/savePrograma';

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


  get programas() {
    return this._programas;
  }

}
