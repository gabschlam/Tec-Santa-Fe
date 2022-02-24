import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import { catchError, timeout } from 'rxjs/operators';
//const endpoint = 'http://localhost:8080/api/';
const endpoint = 'http://54.172.116.225:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class FacilitiesService {

  httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*'
    })
  };

  constructor(private http: HttpClient) { }

  getFacilities() {
    return this.http.get(endpoint + 'facility').pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }
}
