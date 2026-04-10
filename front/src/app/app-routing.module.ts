import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Page404Component } from './authentication/page404/page404.component';
import { AuthLayoutComponent } from './layout/app-layout/auth-layout/auth-layout.component';
import { MainLayoutComponent } from './layout/app-layout/main-layout/main-layout.component';
import { AuthGuard } from './core/guard/auth.guard';
import { Role } from './core/models/role';
import { HomeQuizComponent } from './quiz/pages/home/home-quiz.component';
import { HomeTeacherComponent } from './teacher/pages/home-teacher/home-teacher.component';

/**
 * MAPA DE RUTAS — Seguridad Al Límite
 * ────────────────────────────────────────────────────────────────────
 *
 *  ÁREA PROTEGIDA (requiere JWT válido + rol)
 *  ├── /admin     → AdminModule    (Admin, Teacher, Company)
 *  └── /teacher   → TeacherModule  (Teacher, Admin)
 *
 *  ÁREA PÚBLICA (sin autenticación — módulo Portal Trabajador)
 *  ├── /student            → InscriptionModule  (inscripción de trabajadores)
 *  ├── /quiz               → QuizModule         (evaluación)
 *  └── /conocimientosprevios → QuizModule       (evaluación de ingreso)
 *
 *  AUTENTICACIÓN
 *  └── /authentication     → AuthenticationModule (login, 404, 500)
 *
 * NOTA ARQUITECTÓNICA:
 *  Las rutas /student, /quiz y /conocimientosprevios son INTENCIONALMENTE
 *  públicas: los trabajadores acceden directamente sin cuenta del sistema.
 *  En la arquitectura microfrontend objetivo, estas rutas vivirán en un
 *  proyecto Angular separado (portal-trabajador).
 * ────────────────────────────────────────────────────────────────────
 */
const routes: Routes = [

  // ══════════════════════════════════════════════════════════
  //  ÁREA PROTEGIDA — requiere JWT + roles
  // ══════════════════════════════════════════════════════════
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: '/authentication/signin', pathMatch: 'full' },

      {
        path: 'admin',
        canActivate: [AuthGuard],
        data: { role: [Role.Administrador, Role.Coordinador, Role.Company] },
        loadChildren: () =>
          import('./admin/admin.module').then((m) => m.AdminModule),
      },

      {
        path: 'teacher',
        canActivate: [AuthGuard],
        data: { role: [Role.Instructor, Role.Administrador, Role.Coordinador] },
        loadChildren: () =>
          import('./teacher/teacher.module').then((m) => m.TeacherModule),
      },
    ],
  },

  // ══════════════════════════════════════════════════════════
  //  AUTENTICACIÓN
  // ══════════════════════════════════════════════════════════
  {
    path: 'authentication',
    component: AuthLayoutComponent,
    loadChildren: () =>
      import('./authentication/authentication.module').then(
        (m) => m.AuthenticationModule
      ),
  },

  // ══════════════════════════════════════════════════════════
  //  ÁREA PÚBLICA — Portal del Trabajador
  //  Acceso sin autenticación (intencional).
  //  Destino de migración: proyecto angular portal-trabajador.
  // ══════════════════════════════════════════════════════════
  {
    // Inscripción de trabajadores / pre-inscripción
    path: 'student',
    component: AuthLayoutComponent,
    loadChildren: () =>
      import('./student/inscription.module').then((m) => m.InscriptionModule),
  },
  {
    // Evaluaciones teóricas y prácticas
    path: 'quiz',
    component: HomeQuizComponent,
    loadChildren: () =>
      import('./quiz/quiz.module').then((m) => m.QuizModule),
  },
  {
    // Evaluación de conocimientos previos al ingreso
    path: 'conocimientosprevios',
    component: HomeQuizComponent,
    loadChildren: () =>
      import('./quiz/quiz.module').then((m) => m.QuizModule),
  },

  // ══════════════════════════════════════════════════════════
  //  FALLBACK
  // ══════════════════════════════════════════════════════════
  { path: '**', component: Page404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {})],
  exports: [RouterModule],
})
export class AppRoutingModule {}
