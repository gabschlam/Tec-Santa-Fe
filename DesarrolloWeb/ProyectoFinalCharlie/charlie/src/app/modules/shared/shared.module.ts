import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { MainComponentsModule } from '../../main-components/main-components.module';

import { LogInComponent } from './log-in/log-in.component';
import { CrearCuentaComponent } from './crear-cuenta/crear-cuenta.component';
import { PerfilComponent } from './perfil/perfil.component';
import { HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [LogInComponent, CrearCuentaComponent, PerfilComponent],
  imports: [
    CommonModule,
    SharedRoutingModule,
    MainComponentsModule,
    HttpClientModule
  ]
})
export class SharedModule { }
