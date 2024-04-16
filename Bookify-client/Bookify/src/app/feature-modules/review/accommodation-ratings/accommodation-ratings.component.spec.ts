import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationRatingsComponent } from './accommodation-ratings.component';

describe('AccommodationRatingsComponent', () => {
  let component: AccommodationRatingsComponent;
  let fixture: ComponentFixture<AccommodationRatingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationRatingsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationRatingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
