import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgregarComponent } from './agregar/agregar.component';
import { EditarComponent } from './editar/editar.component';
import { MainAdminComponent } from './main-admin/main-admin.component';


const routes: Routes = [
  {path: 'admin', component: MainAdminComponent},
  {path: 'admin/agregar', component: AgregarComponent},
  {path: 'admin/editar', component: EditarComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
