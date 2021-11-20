import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavBarComponent } from './main-components/nav-bar/nav-bar.component';
import { FooterComponent } from './main-components/footer/footer.component';
import { HomeComponent } from './modules/comprador/home/home.component';
import { ImageCarrouselComponent } from './main-components/image-carrousel/image-carrousel.component';
import { NewsFeedComponent } from './modules/info/news-feed/news-feed.component';
import { ContactoComponent } from './modules/info/contacto/contacto.component';
import { PerfilComponent } from './modules/shared/perfil/perfil.component';
import { AgregarComponent } from './modules/vendedor/agregar/agregar.component';
import { EditarComponent } from './modules/vendedor/editar/editar.component';
import { FormProductoComponent } from './main-components/form-producto/form-producto.component';
import { ComprarComponent } from './modules/comprador/comprar/comprar.component';
import { LogInComponent } from './modules/shared/log-in/log-in.component';
import { CrearCuentaComponent } from './modules/shared/crear-cuenta/crear-cuenta.component';
import { AdminModule } from './modules/admin/admin.module';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'news-feed', component: NewsFeedComponent },
  { path: 'contacto', component: ContactoComponent },
  { path: 'perfil', component: PerfilComponent },
  { path: 'crear-cuenta', component: CrearCuentaComponent },
  { path: 'log-in', component: LogInComponent },
  { path: 'editar', component: EditarComponent },
  { path: 'comprar', component: ComprarComponent },
  { path: 'agregar', component: AgregarComponent },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
