import { Component, OnInit } from '@angular/core';
import {ProductsService} from '../../../services/products.service';
import { Router } from '@angular/router';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-validar-compra',
  templateUrl: './validar-compra.component.html',
  styleUrls: ['./validar-compra.component.scss']
})
export class ValidarCompraComponent implements OnInit {
  compra: any;
  validation: boolean;
  comment: string;

  constructor(private productsService: ProductsService, private router: Router, @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit(): void {
    this.productsService.getCompraUser().subscribe(compra => {
      this.compra = compra[0];
    });

  }

  validarCompra() {
    this.validation = (document.getElementById('validation') as HTMLInputElement).checked;
    this.productsService.validarCompra(this.validation, this.comment).subscribe(data => {
      if (this.locale === 'en') {
        alert('Thank you for your validation');
      } else {
        alert('Gracias por tu validación');
      }
      this.router.navigateByUrl('/home');
    },
    error => {
      console.log(error);
    });
  }


}
