import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestReservationCardComponent } from './guest-reservation-card.component';

describe('GuestReservationCardComponent', () => {
  let component: GuestReservationCardComponent;
  let fixture: ComponentFixture<GuestReservationCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GuestReservationCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GuestReservationCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
