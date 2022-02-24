import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, tap, timeout} from 'rxjs/operators';
import { Observable } from 'rxjs';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
//const endpoint = 'http://localhost:8080/api/';
const endpoint = 'http://54.172.116.225:8080/api/';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  usr: any;
  current: any;

  httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
    }),
  };
  constructor(private router: Router, private http: HttpClient) {}

  addUser(user: any) {
    return this.http.post<any>(endpoint + 'users', user).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  addAddress(address: any) {
    return this.http.post<any>(endpoint + 'address/' + this.usr, {address: address}).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  changeRecurrent(address: any) {
    return this.http.put<any>(endpoint + 'address/' + this.usr, {address: address}).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  login(user) {
    return this.http.post<any>(endpoint + 'login', user).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  loggedIn() {
    if (localStorage.getItem('token')) {
      return true;
    }
  }

  isAdmin() {
    if (localStorage.getItem('role')) {
      return true;
    }
  }

  logout() {
    this.usr = '';
    localStorage.removeItem('token');
    localStorage.removeItem('idUser');
    localStorage.removeItem('role');
    return this.http.get(endpoint + 'logout');
  }

  getToken() {
    return localStorage.getItem('token');
  }

  getUser() {
    this.usr = localStorage.getItem('idUser');
    console.log('this.usr: ' + this.usr);
    return this.usr;
  }

  getUserInfo() {
    this.usr = localStorage.getItem('idUser');
    return this.http.get(endpoint + 'users/' + this.usr).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }
}
