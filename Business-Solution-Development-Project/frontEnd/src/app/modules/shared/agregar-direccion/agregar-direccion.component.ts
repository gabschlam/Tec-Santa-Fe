import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Address } from 'src/app/interfaces/address';
import { User } from 'src/app/interfaces/user';
import { AuthService } from 'src/app/services/auth.service';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-agregar-direccion',
  templateUrl: './agregar-direccion.component.html',
  styleUrls: ['./agregar-direccion.component.scss']
})
export class AgregarDireccionComponent implements OnInit {

  address: Address = {street: '', country: '', state: '', recurrent: false};

  constructor(private router: Router, private authService: AuthService, @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit(): void {
    this.authService.getUser();
  }

  addAddress(){
    
      this.authService.addAddress(this.address).subscribe(data => {
        if (this.locale === 'en') {
          alert('Address added');
        } else {
          alert('Dirección agregada');
        }
        this.router.navigateByUrl('/perfil');
      },
      error => {
        console.log(error);
      });
    
    
    }

}
