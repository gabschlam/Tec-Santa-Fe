import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ComprarComponent } from './comprar/comprar.component';
import {CarritoComponent} from './carrito/carrito.component'


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'comprar', component: ComprarComponent},
  {path: 'carrito', component: CarritoComponent}


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompradorRoutingModule { }
