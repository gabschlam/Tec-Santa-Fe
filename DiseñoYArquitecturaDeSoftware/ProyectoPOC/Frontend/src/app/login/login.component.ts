import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { User } from '../models/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [ LoginService ]

})
export class LoginComponent implements OnInit {

  public user : User;
 
  constructor(private router: Router, private loginService: LoginService) {
    this.user = new User();
    if (this.loginService.getUser() != null) {
      this.router.navigate(['/home']);
    }
}

  ngOnInit(): void {
  }

  validateLogin() {
    if(this.user.username && this.user.password) {
        this.loginService.login(this.user);
    } else {
        alert('Ingresa usuario y password');
    }
  }

}
