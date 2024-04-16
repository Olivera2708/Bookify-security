import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationReviewComponent } from './accommodation-review.component';

describe('AccommodationReviewComponent', () => {
  let component: AccommodationReviewComponent;
  let fixture: ComponentFixture<AccommodationReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationReviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
