import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestRequestCardComponent } from './guest-request-card.component';

describe('GuestRequestCardComponent', () => {
  let component: GuestRequestCardComponent;
  let fixture: ComponentFixture<GuestRequestCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GuestRequestCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GuestRequestCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
