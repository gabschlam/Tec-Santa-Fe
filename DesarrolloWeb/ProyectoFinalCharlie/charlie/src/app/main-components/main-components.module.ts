import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ChatbotComponent } from './chatbot/chatbot.component';
import { FooterComponent } from './footer/footer.component';
import { FormProductoComponent } from './form-producto/form-producto.component';
import { ImageCarrouselComponent } from './image-carrousel/image-carrousel.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    FooterComponent,
    FormProductoComponent,
    ImageCarrouselComponent,
    NavBarComponent,
    ChatbotComponent,
  ],
  imports: [CommonModule, RouterModule, MDBBootstrapModule.forRoot(), NgbModule],
  exports: [
    FooterComponent,
    FormProductoComponent,
    ImageCarrouselComponent,
    NavBarComponent,
    BrowserModule,
    HttpClientModule,
    ChatbotComponent
  ],
})
export class MainComponentsModule {}
