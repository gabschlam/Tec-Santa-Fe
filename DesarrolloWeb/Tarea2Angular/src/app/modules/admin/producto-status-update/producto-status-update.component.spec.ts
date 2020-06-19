import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductoStatusUpdateComponent } from './producto-status-update.component';

describe('ProductoStatusUpdateComponent', () => {
  let component: ProductoStatusUpdateComponent;
  let fixture: ComponentFixture<ProductoStatusUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductoStatusUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductoStatusUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
