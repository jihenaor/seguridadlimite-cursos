
import { Component, OnInit, Inject } from '@angular/core';

import { FormBuilder, FormGroup, Validators, FormArray, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

import { PreguntasService } from '../../preguntas.service';
import { Pregunta } from './../../../../../core/models/pregunta.model';
import { ServicesService } from 'src/app/core/service/services.service';
import { Respuesta } from 'src/app/core/models/respuesta.model';
import { GrupopreguntaService } from './../../../../grupopregunta/services/grupopregunta.service';
import { BloqueService } from './../../../../../core/service/bloque.service';

import { NivelsService } from './../../../../../core/service/nivels.service';
import { EnfasisService } from '../../../../enfasis/enfasis.service';

import { Nivel } from 'src/app/core/models/nivel.model';
import { Enfasis } from 'src/app/core/models/enfasis.model';
import { ShowNotificacionService } from '../../../../../core/service/show-notificacion.service';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogContent } from '@angular/material/dialog';
import { PreguntasRegistrarService } from '../../preguntaregistrar.service';
import { tap } from 'rxjs';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { NgFor, NgIf } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-form-dialog',
    templateUrl: './form-dialog.component.html',
    styleUrls: ['./form-dialog.component.scss'],
    imports: [MatButtonModule, MatIconModule, MatDialogContent, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatSelectModule, NgFor, MatOptionModule, MatInputModule, NgIf]
})
export class FormDialogComponent implements OnInit {
  action: string;
  dialogTitle: string;
  proForm: FormGroup;
  pregunta: Pregunta;
  type: number;
  idgrupo: number;
  x: Respuesta
  public nivels: Nivel[] = [];
  public enfasiss: Enfasis[] = [];

  constructor(
    public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public preguntaService: PreguntasService,
    private formBuilder: FormBuilder,
    public service: ServicesService,
    public grupopreguntaService: GrupopreguntaService,
    public bloqueService: BloqueService,
    private sanitizer: DomSanitizer,
    private nivelsService: NivelsService,
    public enfasisService: EnfasisService,
    private notificacionService: ShowNotificacionService,
    private preguntasRegistrarService: PreguntasRegistrarService
  ) {

    this.action = data.action;
    if (this.action === 'edit') {
      this.dialogTitle = '(' + data.pregunta.id + ')' + data.pregunta.pregunta;
      this.pregunta = data.pregunta;
      this.proForm = this.createContactForm();
      this.type = data.pregunta.type;
    } else {
      this.dialogTitle = 'Nueva pregunta';
      this.pregunta = new Pregunta();
      this.idgrupo= data.idgrupo;
      this.proForm = this.createNewContactForm();
    }

    this.proForm.get('type').valueChanges.subscribe(x => {
      this.type = x;
    });

    this.proForm.get('idgrupo').valueChanges.subscribe(x => {
      this.idgrupo = x;
    });
  }

  ngOnInit() {
    this.grupopreguntaService.getGrupopregunta();
    this.bloqueService.getBloques();
    this.loadNiveles()
    this.loadEnfasis();
  }

  private loadNiveles() {
    this.nivelsService.getNivelActivos().subscribe(
      (data: Nivel[]) => {
        this.nivels = data;
      }
    );
  }

  private loadEnfasis() {
    this.enfasisService.getAllEnfasis("Inscripcion");
  }

  createContactForm(): FormGroup {
    return this.formBuilder.group({
      id: [this.pregunta.id],
      pregunta: [this.pregunta.pregunta, [Validators.required]],
      idgrupo: [this.pregunta.grupo.id, [Validators.required]],
      numerorespuestacorrecta: [this.pregunta.numerorespuestacorrecta, [Validators.required]],
      orden: [this.pregunta.orden],
      idenfasis: [this.pregunta.enfasis.id, [Validators.required]],
      idnivel: [this.pregunta.nivel?.id],
      type: [this.pregunta.type, [Validators.required]],
      estado: [this.pregunta.estado, [Validators.required]],
      agrupador1: [this.pregunta.agrupador1],
      agrupador2: [this.pregunta.agrupador2],
      agrupador3: [this.pregunta.agrupador3],
      opcionesrespuesta: [this.pregunta.opcionesrespuesta],
      nombreopcion: [this.pregunta.nombreopcion],
      diflectura: [this.pregunta.diflectura],
      respuestas: this.formBuilder.array(
        this.pregunta.respuestas.map(respuesta => this.buildRespuestaForm(respuesta))
      )
    });
  }

  buildRespuestaForm(respuesta: Respuesta): FormGroup {
    return this.formBuilder.group({
      id: [respuesta.id],
      idpregunta: [respuesta.idpregunta],
      numero: [respuesta.numero],
      respuesta: [respuesta.respuesta, [Validators.required]],
      tieneimagen: [respuesta.tieneimagen],
      nombreimagen: [respuesta.nombreimagen],
    });
  }

  createNewContactForm(): FormGroup {
    return this.formBuilder.group({
      id: [],
      pregunta: ['', [Validators.required]],
      idgrupo: [Number(this.idgrupo), [Validators.required]],
      numerorespuestacorrecta: [, [Validators.required]],
      orden: [],
      idenfasis: [, [Validators.required]],
      idnivel: [],
      type: [, [Validators.required]],
      agrupador1: [],
      agrupador2: [],
      agrupador3: [],
      opcionesrespuesta: [],
      nombreopcion: [],
      estado: [, [Validators.required]],
      diflectura: [, [Validators.required]],
      respuestas: this.formBuilder.array([])
    });
  }

  get respuestas(): FormArray {
    return this.proForm.get('respuestas') as FormArray;
  }

  agregarRespuesta() {
    const nuevaRespuesta = this.formBuilder.group({
      idpregunta: [this.pregunta.id],
      numero: [this.respuestas.length + 1],
      respuesta: ['', Validators.required],
      tieneimagen: ['N'],
      nombreimagen: ['']
    });

    // Agrega el nuevo FormGroup al formulario de respuestas
    this.respuestas.push(nuevaRespuesta);
  }

  eliminarRespuesta(index: number) {
    this.respuestas.removeAt(index);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  confirmAdd(copia) {
    if (copia) {
      this.proForm.controls['id'].setValue(null);
    }

    this.preguntasRegistrarService.savePregunta(this.proForm.getRawValue())
    .pipe(
      tap((resp : Pregunta) => {
          this.notificacionService.displaySuccess('La actualizacion fué exitosa');
      })
    )
    .subscribe();
  }

  transformImagen(respuestaForm: FormGroup): SafeResourceUrl {
    const tieneImagen = respuestaForm.get('tieneimagen').value;
    if (tieneImagen && tieneImagen === 'S') {
      const id = respuestaForm.get('id')?.value;
      const nombreImagen = respuestaForm.get('nombreimagen').value;

      const resp = this.pregunta.respuestas.find((respuesta) => respuesta.id === id)

      if (tieneImagen === 'S' && nombreImagen) {
        const safeUrl = 'data:image/png;base64,' + resp.base64;
        return this.sanitizer.bypassSecurityTrustResourceUrl(safeUrl);
      }
    }
    return '';
  }
}
