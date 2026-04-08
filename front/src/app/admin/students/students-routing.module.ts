import { AboutStudentempresaComponent } from './about-student-empresa/about-student-empresa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AboutStudentComponent } from './about-student/about-student.component';

import { EvaluacionteoricaComponent } from './evaluacionteorica/evaluacionteorica.component';
import { EvaluacionpracticaComponent } from './evaluacionpractica/evaluacionpractica.component';
import { AprendicesempresaComponent } from './aprendicesempresa/aprendicesempresa.component';
import { EvaluacionperfilComponent } from './evalacion-perfil/evaluacion-perfil.component';
import { PreinscripcionComponent } from './preinscripcion/preinscripcion.component';
import { ListadoaprendicesComponent } from './listado-aprendices/listadoaprendices.component';
import { PreinscripcionlectorComponent } from './preinscripcionlector/preinscripcionlector.component';

const routes: Routes = [
  {
    path: 'aprendicesempresa',
    component: AprendicesempresaComponent
  },
  {
    path: 'evaluacionteorica/:idgrupo/:idaprendiz/:numeroevaluacion/:idnivel/:nombretrabajador',
    component: EvaluacionteoricaComponent
  },
  {
    path: 'evaluacionpractica/:idaprendiz',
    component: EvaluacionpracticaComponent
  },
  {
    path: 'evaluacionpractica/:idgrupo/:idaprendiz',
    component: EvaluacionpracticaComponent
  },
  {
    path: 'about-student/:idtrabajador',
    component: AboutStudentComponent
  },
  {
    path: 'about-student/:idtrabajador/:idgrupo',
    component: AboutStudentComponent
  },
  {
    path: 'about-aprendiz/:idaprendiz',
    component: AboutStudentComponent
  },
  {
    path: 'about-aprendiz',
    component: AboutStudentComponent
  },
  {
    path: 'about-student-empresa/:idaprendiz',
    component: AboutStudentempresaComponent
  },
  {
    path: 'wizard',
    component: PreinscripcionComponent
  },
  {
    path: 'wizard_basico/:basico/:idprograma/:idgrupo/:idnivel',
    component: PreinscripcionComponent
  },
  {
    path: 'wizard/:idgrupo',
    component: PreinscripcionComponent
  },
  {
    path: 'wizard/:idprograma/:idgrupo/:idnivel',
    component: PreinscripcionComponent
  },
  {
    path: 'evaluacion-perfil/:idaprendiz',
    component: EvaluacionperfilComponent
  },
  {
    path: 'listadoaprendices',
    component: ListadoaprendicesComponent
  },
  {
    path: 'preinscripcionlector',
    component: PreinscripcionlectorComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentsRoutingModule {}
