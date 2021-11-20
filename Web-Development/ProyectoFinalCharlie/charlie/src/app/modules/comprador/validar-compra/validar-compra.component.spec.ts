import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidarCompraComponent } from './validar-compra.component';

describe('ValidarCompraComponent', () => {
  let component: ValidarCompraComponent;
  let fixture: ComponentFixture<ValidarCompraComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ValidarCompraComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidarCompraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
