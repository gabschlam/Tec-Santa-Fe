import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavBarComponent } from './main-components/nav-bar/nav-bar.component';
import { FooterComponent } from './main-components/footer/footer.component';
import { HomeComponent } from './home/home.component';
import { ImageCarrouselComponent } from './main-components/image-carrousel/image-carrousel.component';
import { NewsFeedComponent } from './news-feed/news-feed.component';
import { ContactoComponent } from './contacto/contacto.component';
import { PerfilComponent } from './perfil/perfil.component';
import { AgregarComponent } from './agregar/agregar.component';
import { EditarComponent } from './editar/editar.component';
import { FormProductoComponent } from './form-producto/form-producto.component';
import { ComprarComponent } from './comprar/comprar.component';
import { LogInComponent } from './log-in/log-in.component';
import { CrearCuentaComponent } from './crear-cuenta/crear-cuenta.component';

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
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
