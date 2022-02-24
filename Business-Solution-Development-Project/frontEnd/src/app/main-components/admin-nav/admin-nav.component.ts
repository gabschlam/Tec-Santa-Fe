import { LOCALE_ID } from '@angular/core';
import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-admin-nav',
  templateUrl: './admin-nav.component.html',
  styleUrls: ['./admin-nav.component.scss']
})
export class AdminNavComponent implements OnInit {
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
