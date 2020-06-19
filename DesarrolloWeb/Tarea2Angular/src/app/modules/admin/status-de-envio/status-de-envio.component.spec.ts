import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusDeEnvioComponent } from './status-de-envio.component';

describe('StatusDeEnvioComponent', () => {
  let component: StatusDeEnvioComponent;
  let fixture: ComponentFixture<StatusDeEnvioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatusDeEnvioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusDeEnvioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
