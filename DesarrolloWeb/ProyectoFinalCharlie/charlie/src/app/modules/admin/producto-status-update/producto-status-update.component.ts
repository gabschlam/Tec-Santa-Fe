import { Component, OnInit, Input } from '@angular/core';
import {ProductsService} from '../../../services/products.service';
import { Router, ParamMap } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-producto-status-update',
  templateUrl: './producto-status-update.component.html',
  styleUrls: ['./producto-status-update.component.scss']
})
export class ProductoStatusUpdateComponent implements OnInit {
  product$: any;
  result: any;
  actualStatus: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private productsService: ProductsService) { }


  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      this.product$ = +params.get('idProd');
      console.log(this.product$);
    });
    this.productsService.getStatus(this.product$).subscribe(status => {
      this.result = status;
      this.actualStatus = this.result.status;
      console.log('this.actualStatus ' + this.actualStatus);
    });
  }

  update(s){
    const id = this.product$;
    const status = s;
    this.productsService.updateStatus(id, status).subscribe(el => {console.log('hola'); });
    window.location.reload();

  }





}
