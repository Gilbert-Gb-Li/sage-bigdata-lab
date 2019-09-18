import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnumTypeComponent } from './enum-type.component';

describe('EnumTypeComponent', () => {
  let component: EnumTypeComponent;
  let fixture: ComponentFixture<EnumTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnumTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnumTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
