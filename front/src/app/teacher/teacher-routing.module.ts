import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeTeacherComponent } from './pages/home-teacher/home-teacher.component';
import { EvaluarComponent } from './pages/evaluar/evaluar.component';
import { ConsultAprendizComponent } from './pages/consult-aprendiz/consult-aprendiz.component';
import { PermisoTrabajoComponent } from './pages/permiso-trabajo/permiso-trabajo.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'permiso-trabajo',
    pathMatch: 'full'
  },
  {
    path: 'inicio',
    component: HomeTeacherComponent
  },
  {
    path: 'evaluar',
    component: EvaluarComponent
  },
  {
    path: 'consultar',
    component: ConsultAprendizComponent
  },
  {
    path: 'permiso-trabajo',
    component: PermisoTrabajoComponent
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TeacherRoutingModule {}
