import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentsRatingsComponent } from './comments-ratings.component';

describe('CommentsRatingsComponent', () => {
  let component: CommentsRatingsComponent;
  let fixture: ComponentFixture<CommentsRatingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CommentsRatingsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CommentsRatingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
