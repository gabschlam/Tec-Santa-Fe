import { Component, OnInit } from '@angular/core';
import {User} from '../../../interfaces/user';
import {ProductsService} from '../../../services/products.service';
import {Product } from 'src/app/interfaces/product';
import { Router } from '@angular/router';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.scss']
})
export class EditarComponent implements OnInit {
  user: User;
  product: Product;
  name: string;
  description: string;
  condition: string;
  price: number;
  url: string;

  constructor(private productsService: ProductsService, private router: Router, @Inject(LOCALE_ID) public locale: string) { }


  ngOnInit(): void {
    this.product = this.productsService.product;

  }

  updateProduct(){
    const newProduct = {
      name: this.name,
      condition: this.condition,
      description: this.description,
      price: this.price,
      url: this.product.url};

    this.productsService.updateProduct(this.product.idProd, this.product).subscribe(data => {
      if (this.locale === 'en') {
        alert('Product updated');
      } else {
        alert('Producto editado');
      }
      this.router.navigateByUrl('/perfil');
    },
    error => {
      console.log(error);
    });

  }

}
