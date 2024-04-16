import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationAmenitiesComponent } from './accommodation-amenities.component';

describe('AccommodationAmenitiesComponent', () => {
  let component: AccommodationAmenitiesComponent;
  let fixture: ComponentFixture<AccommodationAmenitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationAmenitiesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationAmenitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
