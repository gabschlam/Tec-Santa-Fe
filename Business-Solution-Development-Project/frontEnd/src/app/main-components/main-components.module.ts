import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FooterComponent } from './footer/footer.component';
import { FormProductoComponent } from './form-producto/form-producto.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminNavComponent } from './admin-nav/admin-nav.component';


@NgModule({
  declarations: [
    FooterComponent,
    FormProductoComponent,
    NavBarComponent,
    AdminNavComponent
  ],
  imports: [CommonModule, RouterModule, MDBBootstrapModule.forRoot(), NgbModule],
  exports: [
    FooterComponent,
    FormProductoComponent,
    NavBarComponent,
    AdminNavComponent,
    BrowserModule,
    HttpClientModule,
  ],
})
export class MainComponentsModule {}
