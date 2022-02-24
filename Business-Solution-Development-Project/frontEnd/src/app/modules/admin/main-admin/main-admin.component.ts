import { Component, OnInit, Input } from '@angular/core';
import {ProductsService} from '../../../services/products.service';
import { LOCALE_ID, Inject } from '@angular/core';
import { Product } from 'src/app/interfaces/product';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-main-admin',
  templateUrl: './main-admin.component.html',
  styleUrls: ['./main-admin.component.scss']
})
export class MainAdminComponent implements OnInit {
  products: any;
  allProducts: any;
  currentPage: number;
  pages: number;
  product: Product;
  user: any;
  cantidadTotal: number;

  constructor(private authService: AuthService, private productsService: ProductsService, @Inject(LOCALE_ID) public locale: string) {

  }

  ngOnInit(): void {
    this.productsService.getUser();
    this.productsService.getProducts(1).subscribe(productos => {
      this.cantidadTotal = 0;
      this.products = productos;
      this.currentPage = this.products.currentPage;
      this.pages = this.products.pages;
      this.allProducts = [];
      this.products.products.forEach(element => {
        element.quantity.forEach(data => {
          this.cantidadTotal += data.quantity;
        });
        element["totalQuantity"] = this.cantidadTotal;
        this.cantidadTotal = 0;
        this.allProducts.push(element);
    });
    });

    this.authService.getUserInfo().subscribe(result => {
      this.user = result;
    });
  }

  getProductsPage(i) {
    this.productsService.getProducts(i).subscribe(productos => {
      this.cantidadTotal = 0;
      this.products = productos;
      this.currentPage = this.products.currentPage;
      this.pages = this.products.pages;
      this.allProducts = [];
      this.products.products.forEach(element => {
        element.quantity.forEach(data => {
          this.cantidadTotal += data.quantity;
        });
        element["totalQuantity"] = this.cantidadTotal;
        this.cantidadTotal = 0;
        this.allProducts.push(element);
    });
    });
  }

  findElement(index){
    this.product = this.allProducts[index];
    console.log(this.product)
    this.productsService.removeProduct(this.product._id).subscribe(data => {
      if (this.locale === 'en') {
        alert('Product removed');
      } else {
        alert('Producto eliminado');
      }
      window.location.reload();
    },
    error => {
      console.log(error);
    });
  }

  findProduct(index){
    this.product = this.allProducts[index];
    this.productsService.setProduct(this.product);
  }
}
