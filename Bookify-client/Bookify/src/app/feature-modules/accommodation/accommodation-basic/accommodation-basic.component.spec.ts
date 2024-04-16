import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationBasicComponent } from './accommodation-basic.component';

describe('AccommodationBasicComponent', () => {
  let component: AccommodationBasicComponent;
  let fixture: ComponentFixture<AccommodationBasicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationBasicComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationBasicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
