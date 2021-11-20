import { Injectable } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { AuthService } from './auth.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { LOCALE_ID, Inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {


  constructor(private router: Router, private authService: AuthService, @Inject(LOCALE_ID) public locale: string) { }

  intercept(req, next) {
    const tokenizeReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${this.authService.getToken()}`
      }
    });
    return next.handle(tokenizeReq).pipe(
      catchError(
        (err, caught) => {
          if (err.status === 500){
            this.handleAuthError();
            return of(err);
          }
          throw err;
        }
      )
    );
  }
  private handleAuthError() {
    if (this.locale === 'en') {
      alert('Session expired');
    } else {
      alert('Sesión expirada');
    }
    this.authService.logout();
    this.router.navigateByUrl('/log-in');
  }
  }

