import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewReportedCardComponent } from './review-reported-card.component';

describe('ReviewReportedCardComponent', () => {
  let component: ReviewReportedCardComponent;
  let fixture: ComponentFixture<ReviewReportedCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewReportedCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReviewReportedCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
