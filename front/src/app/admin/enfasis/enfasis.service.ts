import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Enfasis } from '../../core/models/enfasis.model';

@Injectable({
  providedIn: 'root',
})
export class EnfasisService {
  dataChange: BehaviorSubject<Enfasis[]> = new BehaviorSubject<Enfasis[]>(
    []
  );

  private _enfasis: Enfasis[] = [];
  private initialized = false;

  dialogData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Enfasis[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }

  getAllEnfasis(tipo?: string): void {
    // Determine which endpoint to use based on the optional parameter
    const endpoint = tipo === 'Inscripcion' ? 'enfasis-inscripcion' : 'enfasis';
    const cacheKey = `_enfasis_${endpoint}`;
    
    // Check if we have cached data for this specific endpoint
    if (this.initialized && this._enfasis.length > 0 && !tipo) {
      console.log('Using cached enfasis data:', this._enfasis);
      this.dataChange.next(this._enfasis);
      return;
    }

    const url = `${environment.apiUrl}/${endpoint}`;
    console.log(`Fetching enfasis from (${tipo || 'default'}):`, url);
    this.httpClient.get<Enfasis[]>(url).subscribe({
      next: (data) => {
        console.log('Enfasis data received:', data);
        if (Array.isArray(data)) {
          this._enfasis = [...data];
          this.initialized = true;
          this.dataChange.next(this._enfasis);
        } else {
          console.error('Received non-array data:', data);
          this._enfasis = [];
          this.dataChange.next([]);
        }
      },
      error: (error) => {
        console.error('Error loading enfasis:', error);
        this._enfasis = [];
        this.dataChange.next([]);
      }
    });
  }

  get enfasis(): Enfasis[] {
    return this._enfasis;
  }
}
