import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Booking } from '../models/Booking';
import { LoginService } from './login.service';

const endpoint = 'http://localhost:3000/api/';

@Injectable()
export class ReservasService {
  idUser: any;
  reserva: Booking;

  httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*'
    })
  };

  constructor(private router: Router, private loginService: LoginService, private http: HttpClient) { }

  getReservas(idUser) {
    return this.http.get(endpoint + 'reservas?idUser=' + idUser);
  }

  getReserva(bookingId) {
    return this.http.get(endpoint + 'reserva?bookingId=' + bookingId);
  }

  getSalones() {
    return this.http.get(endpoint + 'salones');
  }
  
  addReserva(booking: Booking) {
    booking.userId = this.loginService.getUser();
    console.log(booking);
    return this.http.post(endpoint + 'reservar', booking);
  }

  deleteReserva(idReserva) {
    return this.http.request('delete', endpoint + 'reservas/' , { body: {bookingId : idReserva} });
  }

}
