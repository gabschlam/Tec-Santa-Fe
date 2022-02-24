import { Component, OnInit } from '@angular/core';
import { LOCALE_ID, Inject } from '@angular/core';
import { Address } from 'src/app/interfaces/address';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {
  user: any;
  addresses: any;
  newAddresses: any;

  constructor(private authService: AuthService, @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit(): void {
    this.authService.getUserInfo().subscribe(result => {
      this.user = result;
      this.addresses = this.user.addresses;
    });
  }

  cambiarRecurrente(indx){
    this.newAddresses = this.addresses 
    this.newAddresses.forEach(element => {
      if (element.recurrent == true) {
        element.recurrent = false;
      }
    });
    this.newAddresses[indx].recurrent = true;
    this.authService.changeRecurrent(this.newAddresses).subscribe(data => {
      if (this.locale === 'en') {
        alert('Recurrence updated');
        window.location.reload();

      } else {
        alert('Recurrencia actualizada');
        window.location.reload();

      }
    },
    error => {
      console.log(error);
    });
  }

}
