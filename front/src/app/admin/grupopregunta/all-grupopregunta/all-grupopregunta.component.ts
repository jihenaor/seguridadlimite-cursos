import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { GrupopreguntaService } from '../services/grupopregunta.service';

import { Grupopregunta } from '../../../core/models/grupopregunta.model';
import { MatMenuTrigger, MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';

@Component({
    standalone: true,
    templateUrl: './all-grupopregunta.component.html',
    styleUrls: ['./all-grupopregunta.component.scss'],
    imports: [
        PagetitleComponent,
        MatMenuModule,
        MatIconModule,
        MatButtonModule,
        MatTooltipModule,
    ],
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
    this.contextMenu.menuData = { item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }

  onRowMenuButton(event: MouseEvent, item: Grupopregunta) {
    event.preventDefault();
    event.stopPropagation();
    const el = event.currentTarget as HTMLElement;
    const r = el.getBoundingClientRect();
    this.contextMenuPosition.x = `${Math.max(0, r.right - 8)}px`;
    this.contextMenuPosition.y = `${Math.max(0, r.bottom + 4)}px`;
    this.contextMenu.menuData = { item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }
}
