import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationRequestsCardComponent } from './accommodation-requests-card.component';

describe('AccommodationRequestsCardComponent', () => {
  let component: AccommodationRequestsCardComponent;
  let fixture: ComponentFixture<AccommodationRequestsCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationRequestsCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationRequestsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
