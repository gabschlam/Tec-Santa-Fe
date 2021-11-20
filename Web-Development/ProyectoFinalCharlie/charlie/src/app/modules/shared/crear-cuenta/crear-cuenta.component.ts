import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {User } from 'src/app/interfaces/user';
import { AuthService } from 'src/app/services/auth.service';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-crear-cuenta',
  templateUrl: './crear-cuenta.component.html',
  styleUrls: ['./crear-cuenta.component.scss']
})
export class CrearCuentaComponent implements OnInit {
  user: User = {profile_pic: '', name: '', lname: '', dBirth: '',  country: '', email: '', password: ''};
  constructor(private router: Router, private authService: AuthService, @Inject(LOCALE_ID) public locale: string) { }

  password: string;
  reingresaPassword: string;

  ngOnInit(): void {
    console.log(this.user);
  }

  addUser(){
    if (this.password === this.reingresaPassword) {
      this.user.password = this.password;
      this.authService.addUser(this.user).subscribe(data => {
        if (this.locale === 'en') {
          alert('User created');
        } else {
          alert('Usuario creado');
        }
        this.router.navigateByUrl('/log-in');
      },
      error => {
        console.log(error);
        if (error.error.error === 'Email already exists') {
          alert(error.error.error);

        }
      });
    }
    else {
      if (this.locale === 'en') {
        alert('Passwords do not match');
      } else {
        alert('Contraseñas no son iguales');
      }
    }

  }

  onSubmit() {
    this.router.navigateByUrl('/log-in');
  }

}
