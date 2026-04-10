import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InscriptionComponent } from './inscription/inscription.component';
import { CertificadoComponent } from '../admin/students/components/certificado/certificado.component';
import { CertificateComponent } from './certificate/certificate.component';
import { InfoComponent } from './info/info.component';
const routes: Routes = [
  {
    path: 'inscription',
    component: InscriptionComponent
  },
  {
    path: 'certificate',
    component: CertificateComponent
  },
  {
    path: 'info',
    component: InfoComponent
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InscriptionRoutingModule {}
