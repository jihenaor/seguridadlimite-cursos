import {
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  inject,
  input,
  NgZone,
  OnDestroy,
  output,
  signal,
  viewChild,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { SvgIconComponent } from '../svg-icon/svg-icon.component';

/**
 * Captura de firma con puntero/táctil sobre lienzo (secundaria frente a tablet Topaz).
 * Emite PNG en base64 sin prefijo data URL, compatible con {@link Firmaaprendiz}.
 */
@Component({
  selector: 'app-firma-canvas-capture',
  standalone: true,
  imports: [CommonModule, SvgIconComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="flex flex-col gap-4">
      <p class="text-sm text-gray-600 dark:text-gray-400 m-0">
        Dibuje la firma con el mouse, el dedo o un lápiz óptico. Use el mismo botón Guardar que
        confirma el trazo y lo envía al servidor.
      </p>
      <canvas
        #canvas
        class="max-w-full rounded-2xl border border-gray-200 bg-white touch-none cursor-crosshair outline-none dark:border-gray-700"
        [style.width.px]="ancho()"
        [style.height.px]="alto()"
        (pointerdown)="onPointerDown($event)"
        (pointermove)="onPointerMove($event)"
        (pointerup)="onPointerUp($event)"
        (pointercancel)="onPointerUp($event)"
      ></canvas>
      <div class="flex flex-wrap items-center justify-end gap-3">
        <button
          type="button"
          class="inline-flex items-center gap-2 rounded-2xl border border-gray-200 bg-white px-4 py-3 text-sm font-medium text-gray-700 shadow-sm transition-colors hover:bg-gray-50 dark:border-gray-600 dark:bg-gray-800 dark:text-gray-200 dark:hover:bg-gray-700"
          (click)="clear()"
        >
          <app-svg-icon name="trash" [size]="18" />
          <span>Limpiar</span>
        </button>
        <button
          type="button"
          class="sl-firma-canvas-btn-guardar inline-flex items-center gap-2 rounded-2xl px-4 py-3 text-sm font-semibold shadow-lg shadow-blue-500/30 transition-colors disabled:pointer-events-none"
          [disabled]="!hasInk()"
          (click)="guardar()"
        >
          <app-svg-icon name="save" [size]="18" />
          <span>Guardar firma</span>
        </button>
      </div>
    </div>
  `,
})
export class FirmaCanvasCaptureComponent implements AfterViewInit, OnDestroy {
  private readonly ngZone = inject(NgZone);

  readonly canvas = viewChild<ElementRef<HTMLCanvasElement>>('canvas');

  /** Ancho lógico (px); alinear con salida Topaz si aplica. */
  readonly ancho = input(500);
  /** Alto lógico (px). */
  readonly alto = input(100);

  /** Base64 PNG sin prefijo `data:image/png;base64,`. */
  readonly firmaBase64 = output<string>();

  readonly hasInk = signal(false);

  private ctx: CanvasRenderingContext2D | null = null;
  private drawing = false;
  private lastX = 0;
  private lastY = 0;
  private activePointerId: number | null = null;

  ngAfterViewInit(): void {
    this.applyCanvasSize();
  }

  ngOnDestroy(): void {
    this.ctx = null;
  }

  private applyCanvasSize(): void {
    const el = this.canvas()?.nativeElement;
    if (!el) return;
    const w = this.ancho();
    const h = this.alto();
    const dpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1;
    el.width = Math.max(1, Math.floor(w * dpr));
    el.height = Math.max(1, Math.floor(h * dpr));
    el.style.width = `${w}px`;
    el.style.height = `${h}px`;
    const ctx = el.getContext('2d');
    this.ctx = ctx;
    if (ctx) {
      ctx.setTransform(1, 0, 0, 1, 0, 0);
      ctx.scale(dpr, dpr);
      ctx.strokeStyle = '#000';
      ctx.lineWidth = 2;
      ctx.lineCap = 'round';
      ctx.lineJoin = 'round';
    }
    this.hasInk.set(false);
  }

  private coords(e: PointerEvent): { x: number; y: number } {
    const el = this.canvas()?.nativeElement;
    if (!el) return { x: 0, y: 0 };
    const rect = el.getBoundingClientRect();
    const lw = this.ancho();
    const lh = this.alto();
    const rw = rect.width || 1;
    const rh = rect.height || 1;
    const nx = ((e.clientX - rect.left) / rw) * lw;
    const ny = ((e.clientY - rect.top) / rh) * lh;
    return {
      x: Math.max(0, Math.min(lw, nx)),
      y: Math.max(0, Math.min(lh, ny)),
    };
  }

  onPointerDown(e: PointerEvent): void {
    e.preventDefault();
    e.stopPropagation();
    if (e.pointerType === 'mouse' && e.button !== 0) return;
    const el = this.canvas()?.nativeElement;
    if (!el) return;
    try {
      el.setPointerCapture(e.pointerId);
    } catch {
      /* ignore */
    }
    this.activePointerId = e.pointerId;
    this.drawing = true;
    const { x, y } = this.coords(e);
    this.lastX = x;
    this.lastY = y;
  }

  onPointerMove(e: PointerEvent): void {
    e.preventDefault();
    e.stopPropagation();
    if (!this.drawing || !this.ctx) return;
    if (this.activePointerId !== null && e.pointerId !== this.activePointerId) return;

    const { x, y } = this.coords(e);
    this.ctx.beginPath();
    this.ctx.moveTo(this.lastX, this.lastY);
    this.ctx.lineTo(x, y);
    this.ctx.stroke();
    this.lastX = x;
    this.lastY = y;
    this.ngZone.run(() => this.hasInk.set(true));
  }

  onPointerUp(e: PointerEvent): void {
    e.preventDefault();
    e.stopPropagation();
    if (this.activePointerId !== null && e.pointerId !== this.activePointerId) return;
    const el = this.canvas()?.nativeElement;
    if (el && this.activePointerId === e.pointerId) {
      try {
        el.releasePointerCapture(e.pointerId);
      } catch {
        /* ignore */
      }
    }
    this.activePointerId = null;
    this.drawing = false;
  }

  clear(): void {
    const el = this.canvas()?.nativeElement;
    if (!el || !this.ctx) return;
    this.ctx.clearRect(0, 0, this.ancho(), this.alto());
    this.hasInk.set(false);
  }

  guardar(): void {
    const el = this.canvas()?.nativeElement;
    if (!el || !this.hasInk()) return;
    const dataUrl = el.toDataURL('image/png');
    const base64 = dataUrl.replace(/^data:image\/png;base64,/, '');
    this.firmaBase64.emit(base64);
  }
}
