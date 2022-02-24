import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { MainComponentsModule } from '../../main-components/main-components.module';
import { MainAdminComponent } from './main-admin/main-admin.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EditarComponent } from './editar/editar.component';
import { AgregarComponent } from './agregar/agregar.component';

@NgModule({
  declarations: [
    MainAdminComponent,
  ],
  imports: [CommonModule, AdminRoutingModule, MainComponentsModule, NgbModule],
})
export class AdminModule {}
