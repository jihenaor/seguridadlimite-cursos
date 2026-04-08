import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllprogramsComponent } from './all-programs/all-programs.component';

const routes: Routes = [
  {
    path: 'all-programs',
    component: AllprogramsComponent
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProgramsRoutingModule {}
