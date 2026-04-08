import { Component, OnInit, inject, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { NumericOnlyDirective } from '../../../directives/numeric-only.directive';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { debounceTime } from 'rxjs';
import { TrabajadorFindService } from '../../../core/service/trabajadorfind.service';
import { MatRadioModule } from '@angular/material/radio'; // Import MatRadioModule
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EmpresaFindCertificateService } from '../../../core/service/empresafindcertificate.service';

@Component({
    selector: 'certificate-form-search',
    templateUrl: './certificate-form-search.component.html',
    styleUrls: ['./certificate-form-search.component.css'],
    imports: [
        MatCardModule,
        FormsModule,
        ReactiveFormsModule,
        NgIf,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatRadioModule,
        MatDatepickerModule,
        MatNativeDateModule
    ]
})
export class CertificateFormSearchComponent implements OnInit {
  @Output() tipoUsuarioChange = new EventEmitter<'aprendiz' | 'empresa' | null>();
  searchField = new FormControl('');
  empresaNit = new FormControl('');
  fechaInicial = new FormControl('');
  fechaFinal = new FormControl('');
  tipoUsuario: 'aprendiz' | 'empresa' | null = null;
  error: string = '';

  public trabajadorfindService = inject(TrabajadorFindService);
  public empresaFindCertificateService = inject(EmpresaFindCertificateService);

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.searchField.valueChanges
      .pipe(debounceTime(1000))
      .subscribe(async value => {
        if (this.tipoUsuario === 'aprendiz' && value && value.length >= 6) {
          await this.search();
        }
      });
  }

  onUserTypeChange() {
    this.tipoUsuarioChange.emit(this.tipoUsuario);
    // Reset form fields when user type changes
    this.searchField.reset();
    this.empresaNit.reset();
    this.fechaInicial.reset();
    this.fechaFinal.reset();
  }


  onSubmit() {
    if (this.isFormValid()) {
      if (this.tipoUsuario === 'aprendiz') {
        console.log('Descargando certificado para aprendiz con documento:', this.searchField.value);
        this.search(); // Trigger search for apprentice
      } else if (this.tipoUsuario === 'empresa') {
        console.log('Descargando certificados para empresa con NIT:', this.empresaNit.value,
          'desde:', this.fechaInicial.value, 'hasta:', this.fechaFinal.value);
        this.downloadEnterpriseCertificates();
      }
    }
  }

  isFormValid(): boolean {
    if (this.tipoUsuario === 'aprendiz') {
      return !!this.searchField.value && this.searchField.value?.length >= 6;
    } else if (this.tipoUsuario === 'empresa') {
      return !!this.empresaNit.value && !!this.fechaInicial.value && !!this.fechaFinal.value;
    }
    return false;
  }


  search() {
    const filtro = this.searchField.value;

    if (!filtro || filtro.length < 6) {
      return;
    }

    this.trabajadorfindService.searchTrabajadorCertificado(filtro);
  }

  downloadEnterpriseCertificates() {
    const formatDate = (date: any): string => {
      if (!date) return '';
      const d = new Date(date);
      return d.toISOString().split('T')[0]; // Esto dará el formato YYYY-MM-DD
    };

    this.empresaFindCertificateService.searchTrabajadorCertificado(
      this.empresaNit.value ?? '',
      formatDate(this.fechaInicial.value),
      formatDate(this.fechaFinal.value)
    );
  }
}
