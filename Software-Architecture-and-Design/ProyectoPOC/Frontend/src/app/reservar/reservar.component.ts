import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Booking } from '../models/Booking';
import { LoginService } from '../services/login.service';
import { ReservasService } from '../services/reservas.service';

@Component({
  selector: 'app-reservar',
  templateUrl: './reservar.component.html',
  styleUrls: ['./reservar.component.css'],
  providers: [ReservasService, LoginService]

})
export class ReservarComponent implements OnInit {

  allSalones: any;
  public booking : Booking;

  constructor(private router: Router, private reservasService: ReservasService, private loginService: LoginService) { 
    this.booking = new Booking();

  }

  ngOnInit(): void {
    if(this.loginService.getUser() === null) {
      this.router.navigate(['/']);
    }
    else {
      this.reservasService.getSalones().subscribe(salones => {
        console.log(salones);
        this.allSalones = salones;
  
      })
    }
  }

  agregarReserva(){
    if(this.booking.roomId && this.booking.date && this.booking.timeStart && this.booking.timeEnd && this.booking.concept) {
      this.reservasService.addReserva(this.booking).subscribe(result => {
        alert('Reserva creada');
        window.location.reload();

      })
    } else {
        alert('Ingresa datos');
    }
  }


}
