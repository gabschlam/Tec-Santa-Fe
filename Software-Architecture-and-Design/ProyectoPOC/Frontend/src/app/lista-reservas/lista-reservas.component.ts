import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { ReservasService } from '../services/reservas.service';

@Component({
  selector: 'app-lista-reservas',
  templateUrl: './lista-reservas.component.html',
  styleUrls: ['./lista-reservas.component.css'],
  providers: [ReservasService, LoginService]
})
export class ListaReservasComponent implements OnInit {

  allReservas: any;
  reserva: any;
  idUser: any;

  constructor(private reservasService: ReservasService, private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
    if(this.loginService.getUser() === null) {
      this.router.navigate(['/']);

    }
    else {
      this.idUser = this.loginService.getUser();
      console.log(this.idUser);
      this.reservasService.getReservas(this.idUser).subscribe(reservas => {
        console.log(reservas);
        this.allReservas = reservas;

      })
    }
  }

  deleteReserva(idReserva) {
    console.log("Llegue delete");

    this.reservasService.deleteReserva(idReserva).subscribe(result => {
      alert('Reserva eliminada');
      window.location.reload();
    },
    error => {
      console.log(error);
    });
  }

}
