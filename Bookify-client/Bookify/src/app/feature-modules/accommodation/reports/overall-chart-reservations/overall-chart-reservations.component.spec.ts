import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverallChartReservationsComponent } from './overall-chart-reservations.component';

describe('OverallChartReservationsComponent', () => {
  let component: OverallChartReservationsComponent;
  let fixture: ComponentFixture<OverallChartReservationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OverallChartReservationsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OverallChartReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
