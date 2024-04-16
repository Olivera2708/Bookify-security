import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationGuestsComponent } from './accommodation-guests.component';

describe('AccommodationGuestsComponent', () => {
  let component: AccommodationGuestsComponent;
  let fixture: ComponentFixture<AccommodationGuestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationGuestsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationGuestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
