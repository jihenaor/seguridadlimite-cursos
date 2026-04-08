import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllpreguntasComponent } from './all-preguntas/all-preguntas.component';

const routes: Routes = [
  {
    path: 'all-preguntas/nivel/:idnivel/:type/:nombrenivel',
    component: AllpreguntasComponent
  },
  {
    path: 'all-preguntas/grupo',
    component: AllpreguntasComponent
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PreguntasRoutingModule {}
