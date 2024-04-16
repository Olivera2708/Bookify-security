import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationAvailabilityComponent } from './accommodation-availability.component';

describe('AccommodationAvailabilityComponent', () => {
  let component: AccommodationAvailabilityComponent;
  let fixture: ComponentFixture<AccommodationAvailabilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationAvailabilityComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationAvailabilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
