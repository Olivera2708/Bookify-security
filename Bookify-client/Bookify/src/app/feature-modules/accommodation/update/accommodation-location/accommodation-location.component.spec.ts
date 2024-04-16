import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationLocationComponent } from './accommodation-location.component';

describe('AccommodationLocationComponent', () => {
  let component: AccommodationLocationComponent;
  let fixture: ComponentFixture<AccommodationLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationLocationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
