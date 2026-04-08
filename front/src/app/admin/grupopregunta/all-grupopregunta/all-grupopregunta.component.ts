import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { GrupopreguntaService } from '../services/grupopregunta.service';

import { Grupopregunta } from '../../../core/models/grupopregunta.model';
import { MatMenuTrigger, MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { NgFor } from '@angular/common';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';

@Component({
    templateUrl: './all-grupopregunta.component.html',
    imports: [PagetitleComponent, NgFor, MatMenuModule, MatIconModule]
})
export class AllGrupopreguntaComponent implements OnInit {
  dataSource: Grupopregunta[] = [];

  @ViewChild(MatMenuTrigger)
  contextMenu: MatMenuTrigger;

  contextMenuPosition = { x: '0px', y: '0px' };

  constructor(
    public httpClient: HttpClient,
    public grupopreguntaService: GrupopreguntaService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.loadData();
  }

  public loadData() {
    this.grupopreguntaService.getGrupopregunta();
  }

  loadPreguntas(grupopregunta: Grupopregunta) {
    sessionStorage.setItem("idgrupopregunta", grupopregunta.id + '');
    sessionStorage.setItem("nombregrupopregunta", grupopregunta.nombre);
    this.router.navigate(['/admin/preguntas/all-preguntas/grupo']);
  }

  onContextMenu(event: MouseEvent, item: Grupopregunta) {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { 'item': item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }
}
