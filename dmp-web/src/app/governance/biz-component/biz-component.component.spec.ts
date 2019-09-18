import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BizComponentComponent } from './biz-component.component';

describe('BizComponentComponent', () => {
  let component: BizComponentComponent;
  let fixture: ComponentFixture<BizComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BizComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BizComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
