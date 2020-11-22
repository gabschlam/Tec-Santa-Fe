import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/User';
import { Router } from '@angular/router';

const endpoint = 'http://localhost:3000/api/';

@Injectable()
export class LoginService {
 
    usr: any;

    httpOptions = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*'
      })
    };

    constructor(private router: Router, private http: HttpClient) { }

    login(user: User) {  
      if (localStorage.getItem('idUser') === null || localStorage.getItem('idUser') === undefined){

        this.http.post(endpoint + 'login', user).subscribe(result => {
          console.log(result);
          if(result['status'] === 'success') {
            this.usr = result['data'];
            this.usr = this.usr[0].username;
            localStorage.setItem('idUser', this.usr); // Obtener ID usuario
            console.log(this.usr);
            this.router.navigate(['/home']);
          } else {
            alert('Usuario o password incorrecto');
          }
        },
        error => {
          console.log('error is ', error);
        });
      }
      else{
        this.usr = localStorage.getItem('idUser');
        console.log(this.usr);
        this.router.navigate(['/home']);
  
      }
    }

    logout() {
      this.usr = '';
      localStorage.removeItem('idUser');
    }

    getUser() {
      this.usr = localStorage.getItem('idUser');
      console.log('this.usr: ' + this.usr);
      return this.usr;
    }
  
 
}