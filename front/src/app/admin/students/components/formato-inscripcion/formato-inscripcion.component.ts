import { Component, Input, OnInit } from '@angular/core';
import { ServicesService } from 'src/app/core/service/services.service';

@Component({
    selector: 'student-formato-inscripcion',
    templateUrl: './formato-inscripcion.component.html',
    standalone: true
})
export class FormatoInscripcionComponent implements OnInit {
  @Input()
  idaprendiz: number;
  public loadFinish = false;

  constructor(
    public service: ServicesService,) { }

  ngOnInit(): void {
  }



}
