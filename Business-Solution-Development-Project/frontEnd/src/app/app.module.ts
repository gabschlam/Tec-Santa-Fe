import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './modules/comprador/home/home.component';
import { PerfilComponent } from './modules/shared/perfil/perfil.component';
import { LogInComponent } from './modules/shared/log-in/log-in.component';
import { CrearCuentaComponent } from './modules/shared/crear-cuenta/crear-cuenta.component';
import { AdminModule } from './modules/admin/admin.module';
import { CarritoComponent} from './modules/comprador/carrito/carrito.component';
import { MainComponentsModule } from './main-components/main-components.module';
import { registerLocaleData } from '@angular/common';
import localUS from '@angular/common/locales/en';
import { StandoutDirective } from './directives/standout.directive';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { AuthGuard} from './guards/auth.guard';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { EditarComponent } from './modules/admin/editar/editar.component';
import { AgregarComponent } from './modules/admin/agregar/agregar.component';
import { AgregarDireccionComponent } from './modules/shared/agregar-direccion/agregar-direccion.component';

registerLocaleData(localUS, 'en');

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PerfilComponent,
    EditarComponent,
    AgregarComponent,
    LogInComponent,
    CrearCuentaComponent,
    CarritoComponent,
    StandoutDirective,
    AgregarDireccionComponent,
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
