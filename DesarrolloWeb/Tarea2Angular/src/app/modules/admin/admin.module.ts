import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { MainAdminComponent } from './main-admin/main-admin.component';
import { ProductoStatusUpdateComponent } from './producto-status-update/producto-status-update.component';
import { StatusDeEnvioComponent } from './status-de-envio/status-de-envio.component';


@NgModule({
  declarations: [MainAdminComponent, ProductoStatusUpdateComponent, StatusDeEnvioComponent],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
