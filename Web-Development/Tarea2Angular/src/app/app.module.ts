import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
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

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    FooterComponent,
    HomeComponent,
    ImageCarrouselComponent,
    NewsFeedComponent,
    ContactoComponent,
    PerfilComponent,
    AgregarComponent,
    EditarComponent,
    FormProductoComponent,
    ComprarComponent,
    LogInComponent,
    CrearCuentaComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AdminModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
