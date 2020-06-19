import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompradorRoutingModule } from './vendedor-routing.module';
import { MainComponentsModule } from '../../main-components/main-components.module';

import { AgregarComponent } from './agregar/agregar.component';
import { EditarComponent } from './editar/editar.component';


@NgModule({
  declarations: [AgregarComponent, EditarComponent],
  imports: [
    CommonModule,
    CompradorRoutingModule,
    MainComponentsModule
  ]

})
export class VendedorModule { }
