import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllprofessorsComponent } from './all-professors/all-professors.component';
import { AboutProfessorComponent } from './about-professor/about-professor.component';
const routes: Routes = [
  {
    path: 'all-professors',
    component: AllprofessorsComponent
  },
  {
    path: 'about-professor',
    component: AboutProfessorComponent
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfessorsRoutingModule {}
