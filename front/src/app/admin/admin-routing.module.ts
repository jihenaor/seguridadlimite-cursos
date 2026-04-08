import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: 'dashboard',
    loadChildren: () =>
      import('./dashboard/dashboard.module').then((m) => m.DashboardModule),
  },
  {
    path: 'grupos',
    loadChildren: () =>
      import('./grupos/grupos.module').then((m) => m.GruposModule),
  },
  {
    path: 'professors',
    loadChildren: () =>
      import('./professors/professors.module').then((m) => m.ProfessorsModule),
  },
  {
    path: 'students',
    loadChildren: () =>
      import('./students/students.module').then((m) => m.StudentsModule),
  },
  {
    path: 'empresas',
    loadChildren: () =>
    import('./empresas/empresas.module').then((m) => m.EmpresasModule),
  },
  {
    path: 'eps',
    loadChildren: () =>
    import('./eps/eps.module').then((m) => m.EpsModule),
  },
  {
    path: 'programs',
    loadChildren: () =>
      import('./programs/programs.module').then((m) => m.ProgramsModule),
  },
  {
    path: 'nivels',
    loadChildren: () =>
      import('./nivels/nivels.module').then((m) => m.NivelsModule),
  },
  {
    path: 'enfasis',
    loadComponent: () =>
      import('./enfasis/all-enfasis.component').then((m) => m.AllEnfasisComponent),
  },
  {
    path: 'grupopregunta',
    loadChildren: () =>
      import('./grupopregunta/grupopregunta.module').then((m) => m.GrupopreguntaModule),
  },
  {
    path: 'preguntas',
    loadChildren: () =>
      import('./preguntas/preguntas.module').then((m) => m.PreguntasModule),
  },
  {
    path: 'evaluacion',
    loadChildren: () =>
      import('../teacher/teacher.module').then((m) => m.TeacherModule),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
