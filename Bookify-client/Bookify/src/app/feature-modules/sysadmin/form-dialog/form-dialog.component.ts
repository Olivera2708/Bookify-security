import { Component, Inject } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CertificateTreeNode } from '../certificate-manager/certificate-manager.component';
import { TableElement } from '../model/table.data';
import { CreateCertificateDTO } from "../model/createcertificate.dto";
import { CertificateService } from "../certificate.service";
import { NgxSpinnerService } from "ngx-spinner";
import { environment } from "../../../../env/env";
import DOMPurify from 'dompurify';

@Component({
  selector: 'app-form-dialog',
  templateUrl: './form-dialog.component.html',
  styleUrl: './form-dialog.component.css'
})
export class FormDialogComponent {
  form: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required]),
    country: new FormControl('', [Validators.required, Validators.maxLength(2), Validators.minLength(2)]),
    organization: new FormControl('', [Validators.required]),
    organizationUnit: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    extensions: new FormControl(''),
    duration: new FormControl('', [this.validateDuration()]),
  });

  extensionsCheckbox: string[] = [];
  maxYear: number;

  constructor(public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { node: CertificateTreeNode, request: TableElement },
    private certificateService: CertificateService,
    private spinner: NgxSpinnerService) {
    if (data.request) {
      this.extensionsCheckbox = ["DIGITAL_SIGNATURE"]
    } else {
      this.extensionsCheckbox = this.data.node.certificate.extensions;
    }
    this.maxYear = Math.floor((new Date(data.node.certificate.expires).getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24 * 365));
    if(data.request){
      this.form.get("name")?.setValue(data.request.name);
      this.form.get("email")?.setValue(data.request.email);
    }
  }

  convertExtension(extension: string): string {
    if (extension === "CA") return "Certificate Authority";
    if (extension === "DIGITAL_SIGNATURE") return "Digital Signature";
    if (extension === "KEY_ENCIPHERMENT") return "Key Enchipherment";
    if (extension === "CERTIFICATE_SIGN") return "Key Cert Sign";
    if (extension === "CRL_SIGN") return "CRL Sign";
    return extension
  }

  getExtensionDescription(extension: string): string {
    if (extension === "CA") return "Extension specifies whether a certificate is authorized to sign other certificates within a PKI.";
    if (extension === "DIGITAL_SIGNATURE") return "Indicates that the public key can be used for digital signatures. This is commonly used in certificates for signing data or messages.";
    if (extension === "KEY_ENCIPHERMENT") return "Indicates that the public key can be used for encrypting session keys used in secure communication protocols like TLS/SSL.";
    if (extension === "CERTIFICATE_SIGN") return "Indicates that the public key can be used to verify signatures on certificates. This extension can be used only in CA certificates.";
    if (extension === "CRL_SIGN") return "Indicates that the public key can be used to verify signatures on certificate revocation lists (CRLs).";
    return "";
  }

  trackByFn(index: number, item: string): number {
    return index;
  }

  onSubmitClick(): void {
    this.certificateService.userHasValidCertificate(DOMPurify.sanitize(this.form.get("email")?.value)).subscribe({
      next: (data) => {
        if (data){
          alert("User already has a valid certificate. ");
        }
        else {
          if (this.form.valid && this.getCheckedExtensions().length>0) {
            this.spinner.show("create-spinner");
            const createCertificateDTO = this.initializeCreateCertificateDTO();

            this.certificateService.createCertificate(createCertificateDTO).subscribe({
              next: (data) => {
                this.spinner.hide("create-spinner");
                this.spinnerVisibleFor(2, "success-spinner");
                setTimeout(() => {this.dialogRef.close(data);}, (2000));
                this.certificateService.approveCertificateRequest(this.data.request.id).subscribe({
                  next: (data) => {
                  }
                });
              },error: (data) =>{
                this.spinner.hide("create-spinner");
                this.spinnerVisibleFor(2, "fail-spinner");
              }
            });
          }
        }
      }
    });
  }
  spinnerVisibleFor(seconds: number, spinnerName: string) {
    this.spinner.show(spinnerName);
    setTimeout(() => {
      this.spinner.hide(spinnerName);
    }, (seconds * 1000));
  }

  initializeCreateCertificateDTO(): CreateCertificateDTO {
    let durationYears: number = this.form.get('duration')!.value;
    if (durationYears === 0) durationYears = this.maxYear;

    const expiresDate: Date = new Date();
    expiresDate.setFullYear(expiresDate.getFullYear() + durationYears);

    return {
      issuerCertificateAlias: this.getIssuerAlias(),
      subject: {
        name: DOMPurify.sanitize(this.form.get('name')!.value),
        country: DOMPurify.sanitize(this.form.get('country')!.value),
        organization: DOMPurify.sanitize(this.form.get('organization')!.value),
        organizationUnit: DOMPurify.sanitize(this.form.get('organizationUnit')!.value),
        email: DOMPurify.sanitize(this.form.get('email')!.value)
      },
      extensions: this.getCheckedExtensions(),
      issued: new Date(),
      expires: expiresDate
    };
  }

  getIssuerAlias() {
    return this.data.node.certificate.subjectCertificateAlias
  }

  validateDuration(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const duration: number = control.value;
      const maxYear: number = this.maxYear;

      if (!Number.isInteger(duration) || duration < 0 || duration > maxYear) {
        return { invalidDuration: true };
      }

      return null;
    };
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  matchValidator(controlName: string, matchingControlName: string) {
    return (abstractControl: AbstractControl) => {
      const control = abstractControl.get(controlName);
      const matchingControl = abstractControl.get(matchingControlName);

      if (control?.value !== matchingControl?.value) {
        const error = { confirmedValidator: 'Passwords do not match.' };
        matchingControl?.setErrors(error);
        return error;
      } else {
        matchingControl!.setErrors(null);
        return null;
      }
    }
  }

  extensionsContains(extension: string) {
    return this.data.node.certificate.extensions.includes(extension);
  }

  getCheckedExtensions(): string[] {
    let result: string[] = []
    for (const el of this.extensionsCheckbox) {
      const c = document.getElementById("cbx-" + el) as HTMLInputElement;
      if (c.checked)
        result.push(el);
    }
    return result;
  }
}
