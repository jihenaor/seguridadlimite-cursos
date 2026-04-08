import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Fototrabajador } from '../../../core/models/fototrabajador.model';
import { ServicesService } from 'src/app/core/service/services.service';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { Header2Component } from '../../components/header2/header2.component';
import { ShowNotificacionService } from 'src/app/core/service/show-notificacion.service';


@Component({
    templateUrl: './tomar-foto.component.html',
    styleUrls: ['./tomar-foto.component.scss'],
    imports: [Header2Component, MatButtonModule, RouterLink, MatIconModule, MatProgressBarModule]
})
export class TomarFotoComponent  implements OnInit, AfterViewInit {

  public fototrabajador: Fototrabajador;
  public idtrabajador: number;
  public idaprendiz: number;
  public loadFinish = false;

  constructor(private service: ServicesService,
    private notificacionService: ShowNotificacionService,
  ) {
    this.idtrabajador = Number(sessionStorage.getItem('idtrabajador'));
    this.idaprendiz = Number(sessionStorage.getItem('idaprendiz'));
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
  error: string = '';
  isCaptured: boolean = false;

  async ngAfterViewInit() {
    await this.setupDevices();
  }

  async setupDevices() {
    // Check browser support first
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
      this.error = 'Su navegador no soporta el acceso a la cámara. Por favor, utilice un navegador moderno como Chrome, Firefox o Edge.';
      return;
    }

    try {
      const stream = await navigator.mediaDevices.getUserMedia({
        video: true
      });

      if (stream) {
        this.video.nativeElement.srcObject = stream;
        this.video.nativeElement.play();
        this.error = ''; // Clear any previous errors
      } else {
        this.error = 'No se pudo acceder a la cámara. Verifique que su dispositivo tenga una cámara conectada.';
      }
    } catch (e: any) {
      console.error('Camera error:', e);
      
      // Handle specific camera errors
      if (e.name === 'NotAllowedError' || e.name === 'PermissionDeniedError') {
        this.error = 'Acceso a la cámara denegado. Por favor, habilite los permisos de cámara para esta página y actualice.';
      } else if (e.name === 'NotFoundError' || e.name === 'DevicesNotFoundError') {
        this.error = 'No se encontró ninguna cámara en este dispositivo. Verifique que tenga una cámara conectada.';
      } else if (e.name === 'NotReadableError' || e.name === 'TrackStartError') {
        this.error = 'La cámara está siendo utilizada por otra aplicación. Cierre otras aplicaciones que puedan estar usando la cámara.';
      } else {
        this.error = `Error al acceder a la cámara: ${e.message || 'Error desconocido'}. Intente actualizar la página.`;
      }
    }
  }

  capture() {
    this.drawImageToCanvas(this.video.nativeElement);
    this.fototrabajador.base64 = this.canvas.nativeElement.toDataURL("image/png");
    this.fototrabajador.ext = "png";
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

  async retryCamera() {
    this.error = '';
    await this.setupDevices();
  }

  async updateFotoTrabajador() {
    try {
      if (!this.fototrabajador?.base64) {
        alert('No se ha seleccionado ninguna foto para actualizar');
        return;
      }

      this.loadFinish = true; // Activamos el loading
      const response = await this.service.post('/updateFoto', this.fototrabajador);

      if (response) {
        this.notificacionService.displaySuccess('La foto del trabajador se actualizó exitosamente');
      }
    } catch (error) {
      console.error('Error al actualizar la foto:', error);
      let mensajeError = 'Error al actualizar la foto del trabajador';

      if (typeof error === 'object' && error?.message) {
        mensajeError = error.message;
      }

      alert(mensajeError);
    } finally {
      this.loadFinish = false; // Desactivamos el loading sin importar el resultado
    }
  }

}
