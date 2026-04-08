import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { ServicesService } from '../../core/service/services.service';
import { MatCardModule } from '@angular/material/card';
import { MatDialogContent } from '@angular/material/dialog';
import { NgIf, DatePipe } from '@angular/common';

@Component({
    selector: 'app-checkqr',
    templateUrl: './checkqr.component.html',
    styleUrls: ['./checkqr.component.scss'],
    imports: [
        NgIf,
        MatDialogContent,
        MatCardModule,
        DatePipe,
    ]
})
export class CheckqrComponent implements OnInit {
  submitted = false;
  error = '';
  hide = true;
  idaprendiz: string;
  aprendiz: Aprendiz;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private services: ServicesService,
  ) {
    if (this.route.snapshot.paramMap.get('idaprendiz') !== undefined) {
      this.idaprendiz = this.route.snapshot.paramMap.get('idaprendiz');
    }
  }

  async ngOnInit() {
    await this.loadAprendiz();
  }

  async loadAprendiz() {
    try {
      this.aprendiz = await this.services.executeFetch("/aprendiz/" + this.idaprendiz);
    } catch (error) {
      console.log(error);
    }
  }

}
