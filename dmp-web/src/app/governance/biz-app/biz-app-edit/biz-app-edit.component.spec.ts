import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BizAppEditComponent } from './biz-app-edit.component';

describe('BizAppEditComponent', () => {
  let component: BizAppEditComponent;
  let fixture: ComponentFixture<BizAppEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BizAppEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BizAppEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
