import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverallChartRevenueComponent } from './overall-chart-revenue.component';

describe('OverallChartRevenueComponent', () => {
  let component: OverallChartRevenueComponent;
  let fixture: ComponentFixture<OverallChartRevenueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OverallChartRevenueComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OverallChartRevenueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
