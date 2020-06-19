import { Component, OnInit } from '@angular/core';
import {ProductsService} from '../../../services/products.service';
import {Product } from 'src/app/interfaces/product';
import {FormsModule, NgForm} from '@angular/forms';
import { Router } from '@angular/router';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.scss']
})
export class AgregarComponent implements OnInit {
  product: Product = {idProd: 0, name: '', condition: '', description: '',  price: 0, url: ''};
  constructor(private productsService: ProductsService, private router: Router, @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit(): void {

    console.log(this.product);

  }
  addProduct(){
    this.productsService.addProduct(this.product).subscribe(data => {
      if (this.locale === 'en') {
        alert('Product added');
      } else {
        alert('Producto agregado');
      }
      this.router.navigateByUrl('/home');
    },
    error => {
      console.log(error);
    });

  }
}
