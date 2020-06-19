import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartsModule } from 'ng2-charts';

import { AdminRoutingModule } from './admin-routing.module';
import { MainComponentsModule } from '../../main-components/main-components.module';

import { MainAdminComponent } from './main-admin/main-admin.component';
import { ProductoStatusUpdateComponent } from './producto-status-update/producto-status-update.component';
import { StatusDeEnvioComponent } from './status-de-envio/status-de-envio.component';
import { GraficasComponent } from './graficas/graficas.component';
import { AdminNavComponent } from './admin-nav/admin-nav.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    MainAdminComponent,
    ProductoStatusUpdateComponent,
    StatusDeEnvioComponent,
    GraficasComponent,
    AdminNavComponent,
  ],
  imports: [CommonModule, AdminRoutingModule, MainComponentsModule, ChartsModule, NgbModule],
})
export class AdminModule {}
