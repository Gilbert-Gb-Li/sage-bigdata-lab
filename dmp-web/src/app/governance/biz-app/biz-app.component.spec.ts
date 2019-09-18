import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BizAppComponent } from './biz-app.component';

describe('BizAppComponent', () => {
  let component: BizAppComponent;
  let fixture: ComponentFixture<BizAppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BizAppComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BizAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
