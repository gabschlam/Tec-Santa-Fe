import { Injectable } from '@angular/core';
import { LOCALE_ID, Inject } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard implements CanActivate {
  constructor(private authService: AuthService,  private router: Router, @Inject(LOCALE_ID) public locale: string) { }

  canActivate(): boolean{
    if (this.authService.isAdmin() && this.authService.loggedIn()) {
      console.log(this.authService.isAdmin())
      console.log(this.authService.loggedIn())
      console.log(localStorage.getItem('role'))
      return true;
    }

    if (this.locale === 'en') {
      alert('Unauthorized');
    } else {
      alert('No authorizado');
    }
    this.router.navigateByUrl('/home');
  }

}
