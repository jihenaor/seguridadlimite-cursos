import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DoQuizComponent } from './pages/do-quiz/do-quiz.component';
import { FinishQuizComponent } from './pages/finish-quiz/finish-quiz.component';
import { InicioQuizComponent } from './pages/inicio-quiz/inicio-quiz.component';
import { SatisfaccionclienteComponent } from './pages/satisfaccioncliente/satisfaccioncliente.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'inicio',
    pathMatch: 'full'
  },
  {
    path: 'inicio-teorico',
    component: InicioQuizComponent
  },
  {
    path: 'inicio-conocimientos-previos',
    component: InicioQuizComponent
  },
  {
    path: 'inicio-satisfaccion-cliente',
    component: InicioQuizComponent
  },
  {
    path: 'quiz',
    component: DoQuizComponent
  },
  {
    path: 'conocimientosprevios',
    component: DoQuizComponent
  },
  {
    path: 'satisfaccioncliente',
    component: SatisfaccionclienteComponent
  },
  {
    path: 'finish/:tipoevaluacion',
    component: FinishQuizComponent
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuizRoutingModule {}
