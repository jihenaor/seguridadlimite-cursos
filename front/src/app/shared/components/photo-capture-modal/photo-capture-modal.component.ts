import { CommonModule } from '@angular/common';
import {
  afterNextRender,
  Component,
  ElementRef,
  EventEmitter,
  inject,
  Injector,
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
  private readonly injector = inject(Injector);

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

  /**
   * Se incrementa en cada apertura / reintento / «Tomar otra» para destruir y recrear el {@code <video>}
   * y volver a ejecutar {@code getUserMedia} en un elemento nuevo.
   *
   * **Limitación del navegador:** si el permiso del sitio ya está en «Permitir», no se vuelve a mostrar el
   * diálogo del sistema; solo se obtiene un nuevo {@link MediaStream}. Para que vuelva a preguntar hay que
   * revocar el permiso en ajustes del sitio o borrar datos de este origen.
   */
  cameraSessionKey = 0;

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
      this.refreshCameraSession();
      this.scheduleCameraSetup();
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
      // Petición explícita en cada entrada (sin reusar MediaStreamTrack de sesiones anteriores).
      const stream = await navigator.mediaDevices.getUserMedia({
        video: {
          facingMode: { ideal: 'user' },
          width: { ideal: 1280 },
          height: { ideal: 720 },
        },
        audio: false,
      });
      this.stream = stream;
      el.srcObject = stream;
      await el.play();
    } catch (e: unknown) {
      const err = e as { name?: string; message?: string };
      if (err?.name === 'NotAllowedError' || err?.name === 'PermissionDeniedError') {
        this.error = this.construirMensajePermisoCamaraDenegado();
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
    this.refreshCameraSession();
    this.scheduleCameraSetup();
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
    this.refreshCameraSession();
    this.scheduleCameraSetup();
  }

  /** Nuevo ciclo de montaje → Angular recrea el {@code video} y se vuelve a llamar a {@code getUserMedia}. */
  private refreshCameraSession(): void {
    this.cameraSessionKey++;
  }

  /**
   * Tras crear el nodo {@code video}, solicita cámara. {@code afterNextRender} evita perder el nodo frente a
   * {@code setTimeout(0)} y mantiene la petición lo más cerca posible del gesto de apertura/reintento.
   */
  private scheduleCameraSetup(): void {
    afterNextRender(
      () => {
        if (this.isOpen && this.step === 'live') {
          void this.setupDevices();
        }
      },
      { injector: this.injector },
    );
  }

  /**
   * Texto multilínea con pasos según el navegador (Chrome, Edge, Firefox, Safari, etc.).
   * Se muestra con {@code white-space: pre-line} en el template del modal.
   */
  private construirMensajePermisoCamaraDenegado(): string {
    const ua = navigator.userAgent || '';
    const intro =
      'El navegador bloqueó el acceso a la cámara. Siga los pasos que correspondan a su navegador y luego pulse «Reintentar cámara» (o cierre y vuelva a abrir esta ventana).\n\n';

    const isEdge = /\bEdg\//i.test(ua);
    const isFirefox = /\bFirefox\//i.test(ua) || /\bFxiOS\//i.test(ua);
    const isOpera = /\bOPR\//i.test(ua) || /\bOpera\b/i.test(ua);
    const isCriOS = /\bCriOS\//i.test(ua);
    const isChromeFamily = /\bChrome\//i.test(ua) || /\bChromium\//i.test(ua);
    const isSafari = /\bSafari\//i.test(ua) && !isChromeFamily && !isCriOS;
    const isBrave = /\bBrave\b/i.test(ua) || /\bBrave\/\d/i.test(ua);
    const isIOS = /iPhone|iPad|iPod/i.test(ua);

    if (isEdge) {
      return (
        intro +
        'Microsoft Edge: icono del candado o «i» a la izquierda de la dirección → Permisos para este sitio → Cámara → Permitir.\n' +
        'Alternativa: escriba edge://settings/content/camera en la barra de direcciones y revise sitios bloqueados o el interruptor general de cámara.'
      );
    }

    if (isFirefox) {
      return (
        intro +
        'Mozilla Firefox: icono del candado a la izquierda de la URL → «Más información» o conexión segura → pestaña Permisos → usar la cámara → Permitir.\n' +
        'Alternativa: menú (≡) → Ajustes → Privacidad y seguridad → Permisos → Cámara → Configuración y quite el bloqueo de este sitio. En móvil: Ajustes del sistema → aplicaciones → Firefox → Permisos → Cámara.'
      );
    }

    if (isCriOS) {
      return (
        intro +
        'Chrome en iPhone/iPad: iOS gestiona la cámara desde Ajustes → Chrome → active «Cámara», o Ajustes → Privacidad y seguridad → Cámara → permita Chrome.\n' +
        'Luego cierre por completo Chrome (deslice desde el multitarea) y vuelva a abrir la página.'
      );
    }

    if (isSafari) {
      if (isIOS) {
        return (
          intro +
          'Safari en iPhone/iPad: toque «AA» a la izquierda de la barra de direcciones (o el icono de tamaño) → «Solicitudes de sitio web» → Cámara → Permitir.\n' +
          'Si ya denegó antes: Ajustes → Safari → Cámara / o borrar datos del sitio. También Ajustes → Privacidad y seguridad → Cámara y compruebe que Safari esté permitido.'
        );
      }
      return (
        intro +
        'Safari en Mac: menú Safari → «Configuración para [este sitio web]…» → Cámara → Permitir o Solicitar.\n' +
        'Si no aparece el menú: Safari → Ajustes… → Sitios web → Cámara y permita este dominio.'
      );
    }

    if (isOpera) {
      return (
        intro +
        'Opera: igual que en Chrome — candado junto a la URL → Cámara → Permitir. O escriba opera://settings/content/camera en la barra de direcciones.'
      );
    }

    if (isBrave) {
      return (
        intro +
        'Brave: candado en la barra de direcciones → permisos → Cámara → Permitir. Si el «escudo» de Brave bloquea scripts o dispositivos, baje la protección para este sitio.\n' +
        'También brave://settings/content/camera en escritorio.'
      );
    }

    if (isChromeFamily && !isEdge) {
      return (
        intro +
        'Google Chrome: icono del candado o «i» a la izquierda de la dirección → Cámara → «Permitir» (si dice Bloqueada, cámbielo).\n' +
        'Alternativa: copie chrome://settings/content/camera, péguelo en la barra de direcciones y pulse Intro. Revise sitios bloqueados y el acceso general a la cámara.\n' +
        'Otros navegadores basados en Chrome (Arc, Vivaldi, etc.) suelen usar la misma ruta con su prefijo (p. ej. arc://settings/content/camera).'
      );
    }

    return (
      intro +
      'Busque junto a la URL el icono de candado o de información, abra los permisos del sitio y permita la cámara. En la configuración de privacidad del navegador, revise la sección de cámara o multimedia para este dominio.'
    );
  }
}
