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
  dialogData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Professors[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }
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

  private authHeaders(): Record<string, string> {
    const token = sessionStorage.getItem('token');
    return token ? { Authorization: `Bearer ${token}` } : {};
  }

  async addProfessors(professors: Record<string, unknown>): Promise<Professors | void> {
    this.dialogData = professors;

    const url = `${environment.apiUrl}/savePersonal`;
    const datos = JSON.stringify(professors);
    try {
      const response = await fetch(url,
        {
          method: 'POST',
          body: datos,
          headers: {
            'Content-Type': 'application/json',
            ...this.authHeaders(),
          }
        });
      if (!response.ok) {
        const msg = await response.text().catch(() => response.statusText);
        alert('Se generó un error al guardar: ' + response.status + ' ' + msg);
        return;
      }
      const data = await response.json() as Professors;
      return data;
    } catch (error) {
      alert(String(error));
    }
  }

  async updateProfessors(professors: Record<string, unknown>): Promise<Professors | void> {
    this.dialogData = professors;
    const id = professors['id'];
    if (id == null) {
      alert('Id inválido');
      return;
    }
    const url = `${environment.apiUrl}/updatePersonal/${id}`;
    try {
      const response = await fetch(url, {
        method: 'PUT',
        body: JSON.stringify(professors),
        headers: {
          'Content-Type': 'application/json',
          ...this.authHeaders(),
        },
      });
      if (!response.ok) {
        const msg = await response.text().catch(() => response.statusText);
        alert('Se generó un error al actualizar: ' + response.status + ' ' + msg);
        return;
      }
      return await response.json() as Professors;
    } catch (error) {
      alert(String(error));
    }
  }

  deleteProfessors(id: number): void {
    console.log(id);
  }
}
