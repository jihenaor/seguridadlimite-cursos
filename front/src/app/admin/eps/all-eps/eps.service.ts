import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Eps } from '../../../core/models/eps.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable()
export class EpsService {
  dataChange: BehaviorSubject<Eps[]> = new BehaviorSubject<Eps[]>([]);
  // Temporarily stores data from dialogs
  dialogData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Eps[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }

  getAllEps(): void {
    this.httpClient.get<Eps[]>(`${environment.apiUrl}` + '/epss').subscribe(
      data => {
        this.dataChange.next(data);
      }
    );
  }

  async addEmpresa(eps: Eps) {
    const url = `${environment.apiUrl}` + '/saveEps';

    const datos = JSON.stringify(eps);

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

        this.dialogData = eps;

        return data;
      }
    } catch (error) {
      alert(error);
    }
  }
  updateStudents(curso: Eps): void {
    this.dialogData = curso;
  }
}
