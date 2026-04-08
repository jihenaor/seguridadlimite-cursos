import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { ComponentsModule } from './../../shared/components/components.module';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';

import { MatSidenavModule } from '@angular/material/sidenav';

import { MatSortModule } from '@angular/material/sort';

import { MatToolbarModule } from '@angular/material/toolbar';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';

import { StudentsRoutingModule } from './students-routing.module';

import { AboutStudentComponent } from './about-student/about-student.component';
import { AboutStudentempresaComponent } from './about-student-empresa/about-student-empresa.component';

import { EvaluacionteoricaComponent } from './evaluacionteorica/evaluacionteorica.component';
import { EvaluacionpracticaComponent } from './evaluacionpractica/evaluacionpractica.component';
import { AprendicesempresaComponent } from './aprendicesempresa/aprendicesempresa.component';

import { MatNativeDateModule } from '@angular/material/core';
import { OverlayModule } from '@angular/cdk/overlay';
import { EvaluacionperfilComponent } from './evalacion-perfil/evaluacion-perfil.component';
import { PhotoComponent } from './components/photo/photo.component';
import { FirmaComponent } from './components/firma/firma.component';

import { WebcamModule } from 'ngx-webcam';

import { FormEditTrabajadorComponent } from './components/form-edit-trabajador/form-edit-trabajador.component';
import { DocumentosTrabajadorComponent } from './components/documentos-trabajador/documentos-trabajador.component';
import { FotoTrabajadorComponent } from './components/foto-trabajador/foto-trabajador.component';
import { StudentGridComponent } from './components/student-grid/student-grid.component';
import { FormatoInscripcionComponent } from './components/formato-inscripcion/formato-inscripcion.component';
import { InformacionAcademicaComponent } from './components/informacion-academica/informacion-academica.component';

import { CertificadoComponent } from './components/certificado/certificado.component';
import { FormatoEvaluacionComponent } from './components/formato-evaluacion/formato-evaluacion.component';
import { DocumentosAprendizComponent } from './components/documentos-aprendiz/documentos-aprendiz.component';
import { InformeFormatoInscripcionComponent } from './components/informe-formato-inscripcion/informe-formato-inscripcion.component';
import { InformePerfilIngresoComponent } from './components/informe-perfil-ingreso/informe-perfil-ingreso.component';
import { InformacionEvaluacionComponent } from './components/informacion-evaluacion/informacion-evaluacion.component';
import { ConsultaAsistenciaComponent } from './components/consulta-asistencia/consulta-asistencia.component';
import { EncuestaSatisfaccionComponent } from './components/encuesta-satisfaccion/encuesta-satisfaccion.component';
import { SelectFormComponent } from './components/select-form/select-form.component';
import { ConocimientosPreviosComponent } from './components/conocimientos-previos/conocimientos-previos.component';
import { EvaluacionTeoricaComponent } from './components/evaluacion-teorica/evaluacion-teorica.component';
import { EvaluacionPracticaComponent } from './components/evaluacion-practica/evaluacion-practica.component';
import { EvaluacionConocimientosPreviosComponent } from './components/evaluacion-conocimientos-previos/evaluacion-conocimientos-previos.component';
import { PreinscripcionComponent } from './preinscripcion/preinscripcion.component';

import { ListadoAprendicesTableComponent } from './listado-aprendices/listado-aprendices-table/listado-aprendices-table.component';
import { ListadoaprendicesComponent } from './listado-aprendices/listadoaprendices.component';
import { SharedModule } from '../../shared/shared.module';


@NgModule({
    imports: [
        CommonModule,
        ComponentsModule,
        FormsModule,
        MatButtonToggleModule,
        MatDatepickerModule,
        MatDividerModule,
        MatExpansionModule,
        ReactiveFormsModule,
        MatGridListModule,
        MatIconModule,
        MatSidenavModule,
        MatSortModule,
        MatToolbarModule,
        MatDatepickerModule,
        MatToolbarModule,
        NgxMaskDirective,
        NgxMaskPipe,
        StudentsRoutingModule,
        MatNativeDateModule,
        OverlayModule,
        WebcamModule,
        SharedModule,
        AboutStudentComponent,
        ListadoaprendicesComponent,
        ListadoAprendicesTableComponent,

        AboutStudentempresaComponent,
        ConsultaAsistenciaComponent,
        PreinscripcionComponent,
        PhotoComponent,
        FirmaComponent,
        EvaluacionteoricaComponent,
        EvaluacionpracticaComponent,
        AprendicesempresaComponent,
        EvaluacionperfilComponent,
        FormEditTrabajadorComponent,
        DocumentosTrabajadorComponent,
        FotoTrabajadorComponent,
        StudentGridComponent,
        FormatoInscripcionComponent,
        InformacionAcademicaComponent,
        CertificadoComponent,
        FormatoEvaluacionComponent,
        DocumentosAprendizComponent,
        InformeFormatoInscripcionComponent,
        InformePerfilIngresoComponent,
        InformacionEvaluacionComponent,
        EncuestaSatisfaccionComponent,
        SelectFormComponent,
        ConocimientosPreviosComponent,
        EvaluacionTeoricaComponent,
        EvaluacionPracticaComponent,
        EvaluacionConocimientosPreviosComponent,
    ],
    providers: [provideNgxMask()],
})
export class StudentsModule { }
