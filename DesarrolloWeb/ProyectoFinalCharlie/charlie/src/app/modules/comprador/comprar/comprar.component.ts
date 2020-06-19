import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LOCALE_ID, Inject } from '@angular/core';
import {ProductsService} from '../../../services/products.service';
import {Address } from 'src/app/interfaces/address';

@Component({
  selector: 'app-comprar',
  templateUrl: './comprar.component.html',
  styleUrls: ['./comprar.component.scss']
})
export class ComprarComponent implements OnInit {
  carrito: any;
  total = 0;

  address: Address = {street: '', country: '', state: '', zip: 12345};
  constructor(private productsService: ProductsService, @Inject(LOCALE_ID) public locale: string, private router: Router) { }

  ngOnInit(): void {
   this.productsService.getCarritoUser().subscribe(carrito => {
      this.carrito = carrito[0];
      this.carrito.products.forEach(element => {
       this.total += element.price;
      });
    });
  }

  buyItems(){
    this.productsService.buyProduct(this.address).subscribe(data => {
      if (this.locale === 'en') {
        alert('Products bought');
      } else {
        alert('Productos comprados');
      }
      // this.router.navigateByUrl('/validar-compra');
      this.router.navigateByUrl('/home');
    },
    error => {
      console.log(error);
    });
  }

}
