import { Injectable } from '@angular/core';
import { CanActivate, Router} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { LOCALE_ID, Inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService,  private router: Router, @Inject(LOCALE_ID) public locale: string) { }

  canActivate(): boolean{
    if (this.authService.loggedIn()) {
      return true;
    }

    if (this.locale === 'en') {
      alert('Session not started');
    } else {
      alert('No has iniciado sesion');
    }
    this.router.navigateByUrl('/log-in');
  }

}
