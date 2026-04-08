import { Component, OnInit, inject, signal } from '@angular/core';

import { Trabajador } from 'src/app/core/models/trabajador.model';
import { MatButtonModule } from '@angular/material/button';
import { NgFor, NgIf } from '@angular/common';

import { Header2Component } from '../components/header2/header2.component';

import { TrabajadorFindService } from 'src/app/core/service/trabajadorfind.service';
import { CertificateFormSearchComponent } from '../components/certificate-form-search/certificate-form-search.component';

import { MatIconModule } from '@angular/material/icon';
import { CertificateTableComponent } from '../components/certificate-table/certificate-table.component';
import { CertificateTableEmpresaComponent } from '../components/certificate-table-empresa/certificate-table-empresa.component';

@Component({
    selector: 'app-certificate',
    templateUrl: './certificate.component.html',
    styleUrls: ['./certificate.component.scss'],
    imports: [Header2Component,
        CertificateFormSearchComponent,
        CertificateTableComponent,
        CertificateTableEmpresaComponent,
        MatButtonModule,
        MatIconModule]
})
export class CertificateComponent implements OnInit {

  public loading: boolean = false;
  tipoUsuario: 'aprendiz' | 'empresa' | null = null;

  constructor(

  ) {}

  ngOnInit() {}

  async validarTrabajador(trabajador: Trabajador) {
  }

  onTipoUsuarioChange(tipo: 'aprendiz' | 'empresa' | null) {
    this.tipoUsuario = tipo;
  }

}
