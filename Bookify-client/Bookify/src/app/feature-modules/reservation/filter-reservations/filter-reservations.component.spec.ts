import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterReservationsComponent } from './filter-reservations.component';

describe('FilterReservationsComponent', () => {
  let component: FilterReservationsComponent;
  let fixture: ComponentFixture<FilterReservationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FilterReservationsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FilterReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
