import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationChartReservationsComponent } from './accommodation-chart-reservations.component';

describe('AccommodationChartReservationsComponent', () => {
  let component: AccommodationChartReservationsComponent;
  let fixture: ComponentFixture<AccommodationChartReservationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationChartReservationsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationChartReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
