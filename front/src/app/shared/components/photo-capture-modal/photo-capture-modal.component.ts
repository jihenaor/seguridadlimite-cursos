import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { Fototrabajador } from '../../../core/models/fototrabajador.model';
import { ServicesService } from '../../../core/service/services.service';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';
import { UiModalComponent } from '../ui-modal/ui-modal.component';

type PhotoStep = 'live' | 'preview';

@Component({
  selector: 'app-photo-capture-modal',
  standalone: true,
  imports: [CommonModule, UiModalComponent],
  templateUrl: './photo-capture-modal.component.html',
  styleUrl: './photo-capture-modal.component.scss',
})
export class PhotoCaptureModalComponent implements OnChanges {
  @Input({ required: true }) isOpen = false;
  @Output() isOpenChange = new EventEmitter<boolean>();
  /** Nombre del aprendiz (título de la modal). */
  @Input({ required: true }) nombreAprendiz = '';
  @Input({ required: true }) idTrabajador = 0;
  @Output() fotoGuardada = new EventEmitter<void>();

  @ViewChild('video') video?: ElementRef<HTMLVideoElement>;
  @ViewChild('canvas') canvas?: ElementRef<HTMLCanvasElement>;

  /** Resolución máxima de captura (lado mayor); reduce peso y mantiene nitidez suficiente. */
  readonly maxCaptureEdge = 1280;

  fototrabajador: Fototrabajador;
  error = '';
  step: PhotoStep = 'live';
  previewDataUrl: string | null = null;
  saving = false;
  canvasW = 640;
  canvasH = 480;

  private stream: MediaStream | null = null;
  private cameraSetupRetries = 0;

  constructor(
    private service: ServicesService,
    private notificacionService: ShowNotificacionService,
  ) {
    this.fototrabajador = new Fototrabajador({ id: 0 });
  }

  get tituloModal(): string {
    const n = (this.nombreAprendiz || '').trim();
    return n ? n : 'Registro fotográfico';
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!changes['isOpen']) {
      return;
    }
    if (this.isOpen) {
      this.error = '';
      this.step = 'live';
      this.previewDataUrl = null;
      this.saving = false;
      this.cameraSetupRetries = 0;
      this.fototrabajador = new Fototrabajador({ id: this.idTrabajador });
      setTimeout(() => {
        requestAnimationFrame(() => void this.setupDevices());
      }, 0);
    } else {
      this.stopCamera();
    }
  }

  onModalOpenChange(open: boolean): void {
    this.isOpenChange.emit(open);
    if (!open) {
      this.stopCamera();
    }
  }

  async setupDevices(): Promise<void> {
    this.stopCamera();
    this.error = '';

    if (!navigator.mediaDevices?.getUserMedia) {
      this.error =
        'Su navegador no permite usar la cámara desde aquí. Use Chrome, Firefox o Edge actualizado.';
      return;
    }

    const el = this.video?.nativeElement;
    if (!el) {
      if (this.isOpen && this.step === 'live' && !this.error && this.cameraSetupRetries < 10) {
        this.cameraSetupRetries++;
        setTimeout(() => void this.setupDevices(), 100);
      } else if (this.isOpen && this.step === 'live' && !this.error) {
        this.error = 'No se pudo mostrar la vista de la cámara. Cierre la ventana y vuelva a intentar.';
      }
      return;
    }
    this.cameraSetupRetries = 0;

    try {
      const stream = await navigator.mediaDevices.getUserMedia({
        video: { facingMode: { ideal: 'user' }, width: { ideal: 1280 }, height: { ideal: 720 } },
        audio: false,
      });
      this.stream = stream;
      el.srcObject = stream;
      await el.play();
    } catch (e: unknown) {
      const err = e as { name?: string; message?: string };
      if (err?.name === 'NotAllowedError' || err?.name === 'PermissionDeniedError') {
        this.error = 'Permiso de cámara denegado. Habilítelo en el navegador y vuelva a abrir la ventana.';
      } else if (err?.name === 'NotFoundError') {
        this.error = 'No se detectó cámara en este dispositivo.';
      } else {
        this.error = err?.message ? String(err.message) : 'No se pudo iniciar la cámara.';
      }
    }
  }

  stopCamera(): void {
    const el = this.video?.nativeElement;
    if (el?.srcObject) {
      const ms = el.srcObject as MediaStream;
      ms.getTracks().forEach((t) => t.stop());
      el.srcObject = null;
    }
    if (this.stream) {
      this.stream.getTracks().forEach((t) => t.stop());
      this.stream = null;
    }
  }

  capture(): void {
    const v = this.video?.nativeElement;
    const c = this.canvas?.nativeElement;
    if (!v || !c) {
      return;
    }
    const ctx = c.getContext('2d');
    if (!ctx) {
      return;
    }

    const vw = v.videoWidth || 640;
    const vh = v.videoHeight || 480;
    let tw = vw;
    let th = vh;
    const maxEdge = Math.max(vw, vh);
    if (maxEdge > this.maxCaptureEdge) {
      const s = this.maxCaptureEdge / maxEdge;
      tw = Math.round(vw * s);
      th = Math.round(vh * s);
    }

    this.canvasW = tw;
    this.canvasH = th;
    c.width = tw;
    c.height = th;
    ctx.drawImage(v, 0, 0, tw, th);

    const dataUrl = c.toDataURL('image/png');
    this.previewDataUrl = dataUrl;
    this.fototrabajador.base64 = dataUrl;
    this.fototrabajador.ext = 'png';
    this.step = 'preview';
    this.stopCamera();
  }

  rechazarPreview(): void {
    this.previewDataUrl = null;
    this.fototrabajador.base64 = '';
    this.step = 'live';
    this.cameraSetupRetries = 0;
    setTimeout(() => {
      requestAnimationFrame(() => void this.setupDevices());
    }, 0);
  }

  async guardar(): Promise<void> {
    if (!this.idTrabajador) {
      alert('No hay trabajador válido para asociar la foto.');
      return;
    }
    if (!this.fototrabajador?.base64) {
      alert('Capture primero la imagen.');
      return;
    }

    this.saving = true;
    try {
      await this.service.post('/updateFoto', this.fototrabajador);
      this.notificacionService.displaySuccess('Foto actualizada correctamente');
      this.stopCamera();
      this.isOpenChange.emit(false);
      this.fotoGuardada.emit();
    } catch (error: unknown) {
      const err = error as { status?: number; message?: string };
      let msg = 'Error al guardar la foto';
      if (err?.status === 403) {
        msg = 'Sin permiso para guardar la foto (403).';
      } else if (err?.message) {
        msg = err.message;
      }
      alert(msg);
    } finally {
      this.saving = false;
    }
  }

  async reintentarCamara(): Promise<void> {
    this.error = '';
    this.step = 'live';
    this.previewDataUrl = null;
    this.cameraSetupRetries = 0;
    setTimeout(() => {
      requestAnimationFrame(() => void this.setupDevices());
    }, 0);
  }
}
