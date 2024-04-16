import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationNewReviewComponent } from './accommodation-new-review.component';

describe('AccommodationNewReviewComponent', () => {
  let component: AccommodationNewReviewComponent;
  let fixture: ComponentFixture<AccommodationNewReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationNewReviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationNewReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
