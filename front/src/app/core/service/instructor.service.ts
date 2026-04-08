import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Professors } from '../models/professors.model';

@Injectable({
  providedIn: 'root'
})

export class InstructorService {
  private _instructores: Professors[] | null;
  private _supervisores: Professors[] | null;
  private _personaapoyos: Professors[] | null;

  constructor(private http: HttpClient) {}

  public find(): void {
    this.http.get<Professors[]>(`${environment.apiUrl}/personals`)
    .subscribe(resp => {
      this._supervisores = resp.filter((instructor) => instructor.supervisor === 'S');
      this._instructores = resp.filter((instructor) => instructor.entrenador === 'S');
      this._personaapoyos = resp.filter((instructor) => instructor.personaapoyo === 'S');
    });
  }

  get instructores() {
    return this._instructores;
  }

  get supervisores() {
    return this._supervisores;
  }

  get personaapoyos() {
    return this._personaapoyos;
  }
}
