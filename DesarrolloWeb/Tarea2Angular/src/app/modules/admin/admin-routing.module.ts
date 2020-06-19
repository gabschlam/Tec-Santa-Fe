import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainAdminComponent } from './main-admin/main-admin.component';
import { ProductoStatusUpdateComponent } from './producto-status-update/producto-status-update.component';
import { StatusDeEnvioComponent } from './status-de-envio/status-de-envio.component';


const routes: Routes = [
  {path: 'main-admin', component: MainAdminComponent},
  {path: 'product-status-update', component: ProductoStatusUpdateComponent},
  {path: 'status-de-envio', component: StatusDeEnvioComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
