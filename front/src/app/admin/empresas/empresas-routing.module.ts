import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllEmpresasComponent } from './all-empresas/all-empresas.component';
import { PagospendientesComponent } from './pagospendientes/pagospendientes.component';

const routes: Routes = [
  {
    path: 'all-empresas',
    component: AllEmpresasComponent
  },
  {
    path: 'pagospendientes',
    component: PagospendientesComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpresasRoutingModule {}
