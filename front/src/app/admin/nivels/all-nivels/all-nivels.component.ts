import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Nivel } from './../../../core/models/nivel.model';

import { MatMenuTrigger, MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { NgFor } from '@angular/common';
import { NivelsService } from '../../../core/service/nivels.service';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';


@Component({
    selector: 'app-all-nivels',
    templateUrl: './all-nivels.component.html',
    imports: [PagetitleComponent, NgFor, MatMenuModule, MatIconModule, MatDividerModule]
})
export class AllNivelsComponent implements OnInit {
  dataSource: Nivel[] = [];

  @ViewChild(MatMenuTrigger)
  contextMenu: MatMenuTrigger;

  contextMenuPosition = { x: '0px', y: '0px' };

  constructor(
    public studentsService: NivelsService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.loadData();
  }

  public loadData() {
    this.studentsService.getNivelActivos().subscribe(
      (data: Nivel[]) => {
        this.dataSource = data;
      },
      (error) => {
        console.error('Error al obtener los datos:', error);
      }
    );
  }


  loadPreguntas(idnivel: number, type: number, nombrenivel: string) {
    this.router.navigate(['/admin/preguntas/all-preguntas/nivel/' + idnivel + '/' + type + '/' + nombrenivel]);
  }

  loadDisenocurricular(idnivel: number) {
    this.router.navigate(['/admin/nivels/disenocurricular/' + idnivel]);
  }

  onContextMenu(event: MouseEvent, item: Nivel) {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { 'item': item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }
}
