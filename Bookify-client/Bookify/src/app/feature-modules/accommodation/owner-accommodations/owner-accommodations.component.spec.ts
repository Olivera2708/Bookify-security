import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerAccommodationsComponent } from './owner-accommodations.component';

describe('OwnerAccommodationsComponent', () => {
  let component: OwnerAccommodationsComponent;
  let fixture: ComponentFixture<OwnerAccommodationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerAccommodationsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OwnerAccommodationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
