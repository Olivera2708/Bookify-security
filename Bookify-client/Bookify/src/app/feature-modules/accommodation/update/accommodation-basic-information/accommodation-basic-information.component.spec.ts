import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationBasicInformationComponent } from './accommodation-basic-information.component';

describe('AccommodationBasicInformationComponent', () => {
  let component: AccommodationBasicInformationComponent;
  let fixture: ComponentFixture<AccommodationBasicInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationBasicInformationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationBasicInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
