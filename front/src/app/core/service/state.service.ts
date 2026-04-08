import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class StateService {
  idTrabajador: number | null = null;
  idAprendiz: number | null = null;
}
