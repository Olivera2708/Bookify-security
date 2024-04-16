import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationGuestComponent } from './navigation-guest.component';

describe('NavigationGuestComponent', () => {
  let component: NavigationGuestComponent;
  let fixture: ComponentFixture<NavigationGuestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavigationGuestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NavigationGuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
