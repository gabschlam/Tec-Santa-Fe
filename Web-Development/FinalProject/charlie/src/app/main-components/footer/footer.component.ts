import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  constructor(public authService: AuthService, private router: Router, @Inject(LOCALE_ID) public locale: string) { }

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
