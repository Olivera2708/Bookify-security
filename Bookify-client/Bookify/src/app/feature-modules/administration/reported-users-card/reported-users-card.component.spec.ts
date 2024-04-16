import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportedUsersCardComponent } from './reported-users-card.component';

describe('ReportedUsersCardComponent', () => {
  let component: ReportedUsersCardComponent;
  let fixture: ComponentFixture<ReportedUsersCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportedUsersCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReportedUsersCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
