import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {Product} from '../interfaces/product';
import { HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { map, catchError, tap, timeout} from 'rxjs/operators';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
// const endpoint = 'http://localhost:8080/api/';
const endpoint = 'http://35.223.24.82:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  result: any;
  usr: any;
  product: Product;
  products: any;
  httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*'
    })
  };
  constructor(private authService: AuthService, private router: Router, private http: HttpClient) {
    this.usr = this.authService.getUser();
  }

  getProducts(i){
    return this.http.get(endpoint + 'allProducts/' + i).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  getProductsUser() {
    return this.http.get(endpoint + 'productsUsers/' + this.usr).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  getCarritoUser() {
    console.log('this.usr: ' + this.usr);
    return this.http.get(endpoint + 'carrito/' + this.usr).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  getCompraUser() {
    console.log('this.usr: ' + this.usr);
    return this.http.get(endpoint + 'compra/' + this.usr).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  validarCompra(validacion: any, comentario: any){
    console.log('this.usr: ' + this.usr);
    return this.http.put(endpoint + 'validarCompra/' + this.usr, {validation: validacion, comment: comentario});
  }

  getProduct(id) {
    return this.http.get(endpoint + 'products/' + id).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  getCompras(id) {
    return this.http.get(endpoint + 'compras');
  }

  addProduct(datos: any) {
    delete datos.idProd;
    console.log('this.usr: ' + this.usr);
    const prod = Object.assign({idUser: this.usr}, datos);
    return this.http.post(endpoint + 'productsUsers', prod).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  addProductToCarrito(id) {
    console.log(id);
    return this.http.post(endpoint + 'carrito/' + this.usr, {idProd: id}).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  buyProduct(datos: any) {
    return this.http.post(endpoint + 'compra/' + this.usr, {address: datos}).pipe(timeout(5000), catchError((error) => {
      return ErrorObservable.create('error');
    }));
  }

  removeProductFromCarrito(id) {
    console.log(this.usr);
    console.log(id);
    console.log('this.usr: ' + this.usr);
    return this.http.request('delete', endpoint + 'carrito/' + this.usr, { body: {idProd: id } });
  }

  removeProduct(id){
    return this.http.delete(endpoint + 'products/' + id);
  }

  setProduct(product){
    this.product = product;
  }

  setCompra(product){
    this.product = product;
  }

  getUser() {
    this.usr = localStorage.getItem('idUser');
    console.log('this.usr: ' + this.usr);
    return this.usr;
  }

  setUser(user) {
    this.usr = user;
  }

  updateProduct(id, product){
    return this.http.put(endpoint + 'products/' + id, product);
  }

  getStatus(id){
    return this.http.get(endpoint + 'compras/' + id);
  }

  updateStatus(id, sts){
    return this.http.put(endpoint + 'compras/' + id, {status: sts});
  }

}
