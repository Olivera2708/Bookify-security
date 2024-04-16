import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerAccommodationsCardComponent } from './owner-accommodations-card.component';

describe('OwnerAccommodationsCardComponent', () => {
  let component: OwnerAccommodationsCardComponent;
  let fixture: ComponentFixture<OwnerAccommodationsCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerAccommodationsCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OwnerAccommodationsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
