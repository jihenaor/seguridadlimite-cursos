import { Injectable } from '@angular/core';

import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../../environments/environment';
import { Bloque } from '../models/bloque.model';


@Injectable({
  providedIn: 'root'
})
export class BloqueService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _bloques: Bloque[] | [];

  constructor(private http: HttpClient) {}

  public getBloques(): void {
    this.http
    .get<Bloque[]>(`${environment.apiUrl}/bloque`)
    .subscribe(resp => {
      this._bloques = resp
      this.dataReadySubject.next(true);
    });
  }

  get bloques() {
    return this._bloques;
  }
}
