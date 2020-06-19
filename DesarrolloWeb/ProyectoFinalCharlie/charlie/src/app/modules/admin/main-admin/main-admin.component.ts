import { Component, OnInit, Input } from '@angular/core';
import {ProductsService} from '../../../services/products.service';
import { Router } from '@angular/router';
import { LOCALE_ID, Inject } from '@angular/core';

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

  constructor(private productsService: ProductsService, private router: Router, @Inject(LOCALE_ID) public locale: string) {

  }

  ngOnInit(): void {
    this.productsService.getCompras(1).subscribe(productos => {
      this.products = productos;
      console.log('this.products' + this.products);
      this.currentPage = this.products.currentPage;
      this.pages = this.products.pages;
      this.allProducts = [];
      this.products.forEach(prd => {
          this.allProducts.push(prd._id);
        });
      });
  }

  goToUpdate(products){
    this.router.navigate(['product-status-update', products]);
    // this.router.router
  }
}
