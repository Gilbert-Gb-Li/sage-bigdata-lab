import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BizComponentEditComponent } from './biz-component-edit.component';

describe('BizComponentEditComponent', () => {
  let component: BizComponentEditComponent;
  let fixture: ComponentFixture<BizComponentEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BizComponentEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BizComponentEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
