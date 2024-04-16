import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationChartRevenueComponent } from './accommodation-chart-revenue.component';

describe('AccommodationChartRevenueComponent', () => {
  let component: AccommodationChartRevenueComponent;
  let fixture: ComponentFixture<AccommodationChartRevenueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationChartRevenueComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationChartRevenueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
