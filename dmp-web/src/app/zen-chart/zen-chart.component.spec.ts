import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ZenChartComponent } from './zen-chart.component';

describe('ZenChartComponent', () => {
  let component: ZenChartComponent;
  let fixture: ComponentFixture<ZenChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ZenChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ZenChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
