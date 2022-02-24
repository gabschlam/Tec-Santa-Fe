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
  role: any;
  constructor(private router: Router, public authService: AuthService, @Inject(LOCALE_ID) public locale: string) {
    this.isCollapsed = true;
   }

  ngOnInit(): void {

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

}
