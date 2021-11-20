import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './modules/comprador/home/home.component';
import { NewsFeedComponent } from './modules/info/news-feed/news-feed.component';
import { ContactoComponent } from './modules/info/contacto/contacto.component';
import { PerfilComponent } from './modules/shared/perfil/perfil.component';
import { AgregarComponent } from './modules/vendedor/agregar/agregar.component';
import { EditarComponent } from './modules/vendedor/editar/editar.component';
import { ComprarComponent } from './modules/comprador/comprar/comprar.component';
import { LogInComponent } from './modules/shared/log-in/log-in.component';
import { CrearCuentaComponent } from './modules/shared/crear-cuenta/crear-cuenta.component';
import { AdminModule } from './modules/admin/admin.module';
import { CarritoComponent} from './modules/comprador/carrito/carrito.component';
import { ValidarCompraComponent} from './modules/comprador/validar-compra/validar-compra.component';
import { MainComponentsModule } from './main-components/main-components.module';
import { registerLocaleData } from '@angular/common';
import localUS from '@angular/common/locales/en';
import { StandoutDirective } from './directives/standout.directive';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { AuthGuard} from './guards/auth.guard';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';

registerLocaleData(localUS, 'en');

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NewsFeedComponent,
    ContactoComponent,
    PerfilComponent,
    AgregarComponent,
    EditarComponent,
    ComprarComponent,
    LogInComponent,
    CrearCuentaComponent,
    CarritoComponent,
    StandoutDirective,
    ValidarCompraComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AdminModule,
    MainComponentsModule,
    BrowserModule,
    FormsModule,
    NgbModule,
    MDBBootstrapModule.forRoot(),
    HttpClientModule,
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
