import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-nav',
  templateUrl: './admin-nav.component.html',
  styleUrls: ['./admin-nav.component.scss']
})
export class AdminNavComponent implements OnInit {
  isCollapsed: boolean;
  constructor(private router: Router) {
    this.isCollapsed = true;
   }

  ngOnInit(): void {
  }

  isHomeView() {
    // return true if the current page is home
    if (this.router.url.match('^/$')) {
      return this.router.url.match('^/$');
    }
    else if (this.router.url.match('^/main-admin$')) {
      return this.router.url.match('^/main-admin$');
    }
  }

}
