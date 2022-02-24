import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { HomeComponent } from './modules/comprador/home/home.component';
import { PerfilComponent } from './modules/shared/perfil/perfil.component';
import { LogInComponent } from './modules/shared/log-in/log-in.component';
import { CrearCuentaComponent } from './modules/shared/crear-cuenta/crear-cuenta.component';
import { CarritoComponent } from './modules/comprador/carrito/carrito.component';

import { AuthGuard} from './guards/auth.guard';
import { AgregarComponent } from './modules/admin/agregar/agregar.component';
import { EditarComponent } from './modules/admin/editar/editar.component';
import { MainAdminComponent } from './modules/admin/main-admin/main-admin.component';
import { AdminAuthGuard } from './guards/admin-auth.guard';
import { AgregarDireccionComponent } from './modules/shared/agregar-direccion/agregar-direccion.component';


const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]  },
  { path: 'perfil', component: PerfilComponent, canActivate: [AuthGuard] },
  { path: 'crear-cuenta', component: CrearCuentaComponent,  },
  { path: 'agregar-direccion', component: AgregarDireccionComponent, canActivate: [AuthGuard] },
  { path: 'log-in', component: LogInComponent,  },
  { path: 'carrito', component: CarritoComponent, canActivate: [AuthGuard]},
  { path: 'admin', component: MainAdminComponent, canActivate: [AdminAuthGuard]},
  { path: 'admin/agregar', component: AgregarComponent, canActivate: [AdminAuthGuard]  },
  { path: 'admin/editar', component: EditarComponent, canActivate: [AdminAuthGuard]  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
