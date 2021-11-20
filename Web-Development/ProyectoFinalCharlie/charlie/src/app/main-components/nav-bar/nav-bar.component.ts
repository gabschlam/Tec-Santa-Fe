import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  isCollapsed: boolean;
  showChatBot: boolean;
  role: any;
  constructor(private router: Router, public authService: AuthService, @Inject(LOCALE_ID) public locale: string) {
    this.isCollapsed = true;
    this.showChatBot = false;
   }

  ngOnInit(): void {
    // this.role = localStorage.getItem('role');
    // console.log(this.role);

  }

  toggleChild(){
    this.showChatBot = !this.showChatBot;
  }

  logOut() {
    if (this.locale === 'en') {
      alert('Session finished');
    } else {
      alert('Sesion terminada');
    }
    this.authService.logout().subscribe(data => {
      console.log(data);
      this.router.navigateByUrl('/log-in');
    },
    error => {
      console.log(error);
    });
  }

  isHomeView() {
    // return true if the current page is home
    if (this.router.url.match('^/$')) {
      return this.router.url.match('^/$');
    }
    else if (this.router.url.match('^/home$')) {
      return this.router.url.match('^/home$');
    }
  }

}
