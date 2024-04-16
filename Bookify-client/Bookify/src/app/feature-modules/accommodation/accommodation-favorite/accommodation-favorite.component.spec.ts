import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationFavoriteComponent } from './accommodation-favorite.component';

describe('AccommodationFavoriteComponent', () => {
  let component: AccommodationFavoriteComponent;
  let fixture: ComponentFixture<AccommodationFavoriteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccommodationFavoriteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccommodationFavoriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
