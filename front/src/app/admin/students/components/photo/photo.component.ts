import { Component, OnInit, Input, input } from '@angular/core';
import { ServicesService } from '../../../../core/service/services.service';
import { Fototrabajador } from '../../../../core/models/fototrabajador.model';

import { AfterViewInit, ElementRef, ViewChild } from "@angular/core";
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { NgIf } from '@angular/common';
import { FotoTrabajadorComponent } from '../foto-trabajador/foto-trabajador.component';

@Component({
    selector: 'app-photo',
    templateUrl: './photo.component.html',
    styleUrls: ['./photo.component.scss'],
    imports: [
        FotoTrabajadorComponent,
        NgIf,
        MatProgressBarModule,
    ]
})
export class PhotoComponent implements OnInit, AfterViewInit {
  @Input()
  base64: string;
  @Input()
  idtrabajador: number;

  public fototrabajador: Fototrabajador;
  public loadFinish = false;

  constructor(private service: ServicesService) {

  }

  ngOnInit() {
    this.fototrabajador = new Fototrabajador({
      id: this.idtrabajador,
    });
  }

  WIDTH = 640;
  HEIGHT = 480;

  @ViewChild("video")
  public video: ElementRef;

  @ViewChild("canvas")
  public canvas: ElementRef;

  captures: string[] = [];
  error: any;
  isCaptured: boolean;

  async ngAfterViewInit() {
    await this.setupDevices();
  }

  async setupDevices() {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: true
        });

        if (stream) {
          this.video.nativeElement.srcObject = stream;
          this.video.nativeElement.play();
          this.error = null;
        } else {
          this.error = "You have no output video device";
          alert(this.error);
        }
      } catch (e) {
        this.error = e;
        alert('Se ha presentado un error ' + e);
      }
    } else {
      alert('Sorry, your browser does not support getUserMedia');
    }
  }

  capture() {
    this.drawImageToCanvas(this.video.nativeElement);
    this.fototrabajador.base64 = this.canvas.nativeElement.toDataURL("image/png");

    this.isCaptured = true;
  }

  removeCurrent() {
    this.isCaptured = false;
  }

  setPhoto(idx: number) {
    this.isCaptured = true;
    var image = new Image();
    image.src = this.captures[idx];
    this.drawImageToCanvas(image);
  }

  drawImageToCanvas(image: any) {
    this.canvas.nativeElement
      .getContext("2d")
      .drawImage(image, 0, 0, this.WIDTH, this.HEIGHT);
  }

  async updateFotoTrabajador() {
    this.loadFinish = !this.loadFinish;
    try {
//      this.fototrabajador.base64 = this.croppedImage;
      if (this.fototrabajador.base64 !== undefined) {
        const r = await this.service.post('/updateFoto', this.fototrabajador);
        alert('Actualización exitosa');
      } else {
        alert('No hay foto');
      }
    } catch (error) {
      if (error.status) {
        alert(error.status + ' json:' + error.json?.status + ' Text: ' + error.statusText);
      } else {
        alert(error);
      }
    }
    this.loadFinish = !this.loadFinish;
  }
}
