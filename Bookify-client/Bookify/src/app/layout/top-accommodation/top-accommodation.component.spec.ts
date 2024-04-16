import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopAccommodationComponent } from './top-accommodation.component';

describe('TopAccommodationComponent', () => {
  let component: TopAccommodationComponent;
  let fixture: ComponentFixture<TopAccommodationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TopAccommodationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TopAccommodationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
