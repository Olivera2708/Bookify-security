import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatapickerRangeComponent } from './datapicker-range.component';

describe('DatapickerRangeComponent', () => {
  let component: DatapickerRangeComponent;
  let fixture: ComponentFixture<DatapickerRangeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DatapickerRangeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DatapickerRangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
