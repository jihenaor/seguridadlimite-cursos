import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllNivelsComponent } from './all-nivels/all-nivels.component';
import { DisenocurricularComponent } from './disenocurricular/disenocurricular.component';

const routes: Routes = [
  {
    path: 'all-nivels',
    component: AllNivelsComponent
  },
  {
    path: 'disenocurricular/:idnivel',
    component: DisenocurricularComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NivelsRoutingModule {}
