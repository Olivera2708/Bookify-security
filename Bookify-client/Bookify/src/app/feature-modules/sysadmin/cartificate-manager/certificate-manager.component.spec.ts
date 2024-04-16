import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateManagerComponent } from './certificate-manager.component';

describe('CartificateManagerComponent', () => {
  let component: CertificateManagerComponent;
  let fixture: ComponentFixture<CertificateManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CertificateManagerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificateManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
