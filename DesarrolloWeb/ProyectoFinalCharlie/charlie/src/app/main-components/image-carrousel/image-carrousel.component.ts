import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-image-carrousel',
  templateUrl: './image-carrousel.component.html',
  styleUrls: ['./image-carrousel.component.scss']
})
export class ImageCarrouselComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  isHomeView() {
    // return true if the current page is home
    if (this.router.url.match('^/home$')) {
      return this.router.url.match('^/home$');
    }

    else if (this.router.url.match('^/$')) {
      return this.router.url.match('^/$');
    }
  }

  isContactView() {
    // return true if the current page is contactos
    return this.router.url.match('^/contacto$');
  }

}
