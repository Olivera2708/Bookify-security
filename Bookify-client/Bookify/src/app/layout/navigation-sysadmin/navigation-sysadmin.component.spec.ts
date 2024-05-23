import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationSysadminComponent } from './navigation-sysadmin.component';

describe('NavigationSysadminComponent', () => {
  let component: NavigationSysadminComponent;
  let fixture: ComponentFixture<NavigationSysadminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavigationSysadminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NavigationSysadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
