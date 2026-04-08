import { Component, AfterViewInit, ElementRef, OnDestroy, ViewChild, Input } from '@angular/core';
import { ServicesService } from '../../../../core/service/services.service';
import { MediaService } from '../../../../core/service/media.service';
import { Firmaaprendiz } from '../../../../core/models/firmaaprendiz.model';
import { DomSanitizer } from '@angular/platform-browser';
import * as Topaz from '@webappaloosa/topaz-sig-web';
import { UntypedFormBuilder, UntypedFormControl, UntypedFormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-firma',
    templateUrl: './firma.component.html',
    styleUrls: ['./firma.component.scss'],
    imports: [
        NgIf,
        MatButtonModule,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
    ]
})
export class FirmaComponent implements AfterViewInit, OnDestroy{
  @Input() idaprendiz: string;
  @Input() idtrabajador: string;

  public loadFinish = false;
  public base64: string;
  private _destroy$: Subject<boolean> = new Subject<boolean>();

  @ViewChild('sigImg', {static: false})
  cnv: ElementRef;
  cnvContext: CanvasRenderingContext2D;

  tmr;

  formGroup: UntypedFormGroup;

  constructor(private fb: UntypedFormBuilder,
    private service: ServicesService,
    public mediaService: MediaService,
    private sanitizer: DomSanitizer) {

      this.formGroup = this.fb.group({
          bioSigData: new UntypedFormControl(null),
          sigImgData: new UntypedFormControl(null),
          sigStringData: new UntypedFormControl(null),
          sigImageData: new UntypedFormControl(null),
      });
  }

  async ngAfterViewInit() {
      this.cnvContext =  ( <HTMLCanvasElement> this.cnv.nativeElement).getContext('2d')!;

    this.getFirma();
  }

  ngOnDestroy(): void {
      this.closingSigWeb();
      this._destroy$.next(true);
  }

  get bioSigDataCtrl(): UntypedFormControl {
      return <UntypedFormControl>this.formGroup.get('bioSigData');
  }

  get sigImgDataCtrl(): UntypedFormControl {
      return <UntypedFormControl>this.formGroup.get('sigImgData');
  }

  get sigStringDataCtrl(): UntypedFormControl {
      return <UntypedFormControl>this.formGroup.get('sigStringData');
  }

  get sigImageDataCtrl(): UntypedFormControl {
      return <UntypedFormControl>this.formGroup.get('sigStringData');
  }

  closingSigWeb() {
      Topaz.ClearTablet();
      Topaz.SetTabletState(0, this.tmr);
  }

  onSign() {
    try {
      // let ctx = document.getElementById('cnv').getContext('2d');

      Topaz.SetDisplayXSize( 500 );
      Topaz. SetDisplayYSize( 100 );
      Topaz.SetTabletState(0, this.tmr);
      Topaz.SetJustifyMode(0);
      Topaz.ClearTablet();

      if ( this.tmr == null) {
          this.tmr = Topaz.SetTabletState(1, this.cnvContext, 50);
      }
      else {
          Topaz.SetTabletState(0, this.tmr);
          this.tmr = null;
          this.tmr = Topaz.SetTabletState(1, this.cnvContext, 50);
      }
    } catch (error) {
      alert('Asegurese de tener conectado el dispositivo Topaz. ' + error)
    }

  }

  onClear() {
      Topaz.ClearTablet();
  }

  onDone() {
    if (Topaz.NumberOfTabletPoints() === 0) {
      alert('Please sign before continuing');
    } else {
      Topaz.SetTabletState(0, this.tmr);

      // RETURN TOPAZ-FORMAT SIGSTRING
      Topaz.SetSigCompressionMode(1);
      this.bioSigDataCtrl.patchValue(Topaz.GetSigString());
      this.sigImgDataCtrl.patchValue(Topaz.GetSigString());

      // this returns the signature in Topaz's own format, with biometric information


      // RETURN BMP BYTE ARRAY CONVERTED TO BASE64 STRING
      Topaz.SetImageXSize(500);
      Topaz.SetImageYSize(100);
      Topaz.SetImagePenWidth(5);
      Topaz.GetSigImageB64((base64: string) => {
          this.sigImageDataCtrl.patchValue(base64);
          this.updateFirma(base64);
      });
    }
  }

  async updateFirma(base64: string) {
    this.loadFinish = !this.loadFinish;
    try {
      if (base64) {
        let firmaaprendiz = new Firmaaprendiz();
        firmaaprendiz.id = Number(this.idaprendiz);
        firmaaprendiz.base64 = base64;
        const r = await this.service.post('/aprendiz/updateSignature', firmaaprendiz);
        alert('Actualización exitosa');
        
        // Refresh the signature display after successful save
        this.getFirma();
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

  getFirma() {
    if (this.idaprendiz) {
      this.mediaService.searchFirma(Number(this.idaprendiz));
    } else {
      console.log('No idaprendiz provided');
    }
  }

  transformFirma() {
    const firmaBase64 = this.mediaService.firmaObtenida();
    if (firmaBase64) {
      return this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,' + firmaBase64);
    }
    return null;
  }
}
