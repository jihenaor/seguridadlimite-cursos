import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AllGrupopreguntaComponent } from './all-grupopregunta/all-grupopregunta.component';

const routes: Routes = [
  {
    path: 'all-grupopregunta',
    component: AllGrupopreguntaComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FormsRoutingModule {}
