import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LogInComponent } from './log-in/log-in.component';
import { CrearCuentaComponent } from './crear-cuenta/crear-cuenta.component';
import { PerfilComponent } from './perfil/perfil.component';


const routes: Routes = [
  {path: 'log-in', component: LogInComponent},
  {path: 'crear-cuenta', component: CrearCuentaComponent},
  {path: 'perfil', component: PerfilComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SharedRoutingModule { }
