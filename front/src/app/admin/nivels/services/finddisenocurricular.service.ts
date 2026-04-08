import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject } from 'rxjs';

import { environment } from 'src/environments/environment';

import { Disenocurricular } from '../../../core/models/disenocurricular.model';

@Injectable({
  providedIn: 'root',
})
export class DisenocurricularfindService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _disenocurriculars: Disenocurricular[] | null;

  constructor(private http: HttpClient) {}

  public search(idnivel: number): void {
    this.http
    .get<Disenocurricular[]>(`${environment.apiUrl}/disenocurricular/${idnivel}`)
    .subscribe(resp => {
      this._disenocurriculars = resp
      this.dataReadySubject.next(true);
    });
  }

  get disenocurriculars() {
    return this._disenocurriculars;
  }

}
