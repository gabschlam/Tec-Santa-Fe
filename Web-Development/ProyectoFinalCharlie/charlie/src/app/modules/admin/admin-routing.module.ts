import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainAdminComponent } from './main-admin/main-admin.component';
import { ProductoStatusUpdateComponent } from './producto-status-update/producto-status-update.component';
import { StatusDeEnvioComponent } from './status-de-envio/status-de-envio.component';
import { GraficasComponent } from './graficas/graficas.component';
import { AdminNavComponent } from './admin-nav/admin-nav.component';


const routes: Routes = [
  {path: 'main-admin', component: MainAdminComponent},
  {path: 'product-status-update/:idProd', component: ProductoStatusUpdateComponent},
  {path: 'status-de-envio', component: StatusDeEnvioComponent},
  {path: 'graficas', component: GraficasComponent},
  {path: 'admin-nav', component: AdminNavComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
