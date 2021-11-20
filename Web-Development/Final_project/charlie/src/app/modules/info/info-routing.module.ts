import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactoComponent } from './contacto/contacto.component';
import { NewsFeedComponent } from './news-feed/news-feed.component';


const routes: Routes = [
  {path: 'contacto', component: ContactoComponent},
  {path: 'news-feed', component: NewsFeedComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InfoRoutingModule { }
