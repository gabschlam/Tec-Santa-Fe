import { Component, OnInit } from '@angular/core';
import {ProductsService} from '../../../services/products.service';
import {Product } from 'src/app/interfaces/product';
import {FormsModule, NgForm} from '@angular/forms';
import { Router } from '@angular/router';
import { LOCALE_ID, Inject } from '@angular/core';
import { FacilitiesService } from 'src/app/services/facilities.service';
import { isEmpty } from 'rxjs/operators';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.scss']
})
export class AgregarComponent implements OnInit {
  product: Product = {_id: 0, name: '', presentation: '', description: '',  size:'', quantity: [], price: 0, url: ''};
  facilities: any;
  quantities: any;
  editField: string;
  checkboxField: boolean;
  totalQuantity: number;

  constructor(private productsService: ProductsService, private facilitiesService: FacilitiesService, private router: Router, @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit(): void {
    this.totalQuantity=0;
    this.quantities = [];
    console.log(this.product);
    this.facilitiesService.getFacilities().subscribe(facilities => {
      this.facilities = facilities;
      this.facilities.forEach(facility => {
        facility.hideRow = true;
      });
    });
  }

  addProduct(){
    console.log(this.quantities)
    console.log(this.quantities.length)
    var index = 0;
    for (let i = 0; i < this.quantities.length; i++) {
      if (this.quantities[i] != undefined && this.quantities[i] != 0) {
        this.product.quantity[index] = {"idFacility": i, "quantity": this.quantities[i]}
        index++;
      }
    }

    console.log(this.product.quantity);
    this.productsService.addProduct(this.product).subscribe(data => {
      if (this.locale === 'en') {
        alert('Product added');
      } else {
        alert('Producto agregado');
      }
      this.router.navigateByUrl('/admin');
    },
    error => {
      console.log(error);
    });

  }

  updateList(id: number, event: any) {
    this.totalQuantity=0;
    const editField = event.target.textContent;
    if (editField != '') {
      if (isNaN(editField.trim()) || !editField.replace(/\s/g, '').length) {
        alert("Escriba un numero");
        event.target.textContent = "";
      }
      else {
        this.quantities[id] = parseInt(editField);
        console.log(this.quantities);
        this.quantities.forEach(val => {
          this.totalQuantity += parseInt(val);
        });
      }
    }
  }

  changeValue(id: number, event: any) {
    this.editField = event.target.textContent;
  }

  checkboxChecked(id: number, event: any, facility) {
    this.totalQuantity=0;

    this.checkboxField = event.target.checked;
    console.log("id: ", id, " - ", this.checkboxField);
    
    facility.hideRow = !this.checkboxField;

    this.quantities[id] = 0;

    this.quantities.forEach(val => {
      this.totalQuantity += parseInt(val);
    });
    
  }
}
