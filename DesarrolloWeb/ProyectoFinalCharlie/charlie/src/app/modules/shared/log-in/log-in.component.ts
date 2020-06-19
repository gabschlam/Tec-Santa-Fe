import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.scss']
})
export class LogInComponent implements OnInit {
  email: string;
  password: string;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
    const user = {email: this.email, password: this.password};
    this.authService.login(user).subscribe(res => {
      console.log(res);
      // localStorage.setItem('role', res.role);
      // console.log(res.role);
      localStorage.setItem('token', res.token);
      localStorage.setItem('idUser', res._id);
      this.router.navigateByUrl('/home');
    },
    error => {
      console.log(error);
      alert(error.error.message);
    });
  }

}
