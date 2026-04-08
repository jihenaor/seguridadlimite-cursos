import { Component, OnInit, ViewChild } from '@angular/core';

import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { NgFor } from '@angular/common';

import { PagetitleComponent } from '../../shared/components/page-title/pagetitle.component';
import { EnfasisService } from './enfasis.service';

@Component({
    selector: 'app-all-enfasis',
    templateUrl: './all-enfasis.component.html',
    standalone: true,
    imports: [PagetitleComponent, NgFor, MatMenuModule, MatIconModule, MatDividerModule]
})
export class AllEnfasisComponent implements OnInit {
  constructor(
    public enfasisService: EnfasisService
  ) { }

  ngOnInit() {
    this.loadData();
  }

  public loadData() {
    this.enfasisService.getAllEnfasis();
  }
}
