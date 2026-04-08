import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllEpsComponent } from './all-eps/all-eps.component';

const routes: Routes = [
  {
    path: 'all-eps',
    component: AllEpsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EpsRoutingModule {}
