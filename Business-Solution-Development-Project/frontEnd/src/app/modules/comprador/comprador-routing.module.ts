import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {CarritoComponent} from './carrito/carrito.component'


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'carrito', component: CarritoComponent}


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompradorRoutingModule { }
