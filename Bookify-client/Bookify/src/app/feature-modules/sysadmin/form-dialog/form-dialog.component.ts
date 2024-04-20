import { Component, Inject } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CertificateTreeNode } from '../certificate-manager/certificate-manager.component';
import { TableElement } from '../model/table.data';

@Component({
  selector: 'app-form-dialog',
  templateUrl: './form-dialog.component.html',
  styleUrl: './form-dialog.component.css'
})
export class FormDialogComponent {
  form: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required]),
    country: new FormControl('', [Validators.required]),
    organization: new FormControl('', [Validators.required]),
    organizationUnit: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required]),
    extensions: new FormControl(''),
    duration: new FormControl(''),
  });

  extensionsCheckbox: string[] = [];
  maxYear: number;

  constructor(public dialogRef: MatDialogRef<FormDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: { node: CertificateTreeNode, request: TableElement}) {
    this.extensionsCheckbox = this.data.node.certificate.extensions;
    this.maxYear = Math.floor((new Date(data.node.certificate.expires).getTime() - new Date().getTime())/(1000*60*60*24*365));
  }

  convertExtension(extension : string) : string {
    if (extension === "CA") return "Certificate Authority";
    if (extension === "DIGITAL_SIGNATURE") return "Digital Signature";
    if (extension === "KEY_ENCIPHERMENT") return "Key Enchipherment";
    if (extension === "CERTIFICATE_SIGN") return "Key Cert Sign";
    if (extension === "CRL_SIGN") return "CRL Sign";
    return extension
  }

  getExtensionDescription(extension : string) : string {
    if (extension === "CA") return "Extension specifies whether a certificate is authorized to sign other certificates within a PKI.";
    if (extension === "DIGITAL_SIGNATURE") return "Indicates that the public key can be used for digital signatures. This is commonly used in certificates for signing data or messages.";
    if (extension === "KEY_ENCIPHERMENT") return "Indicates that the public key can be used for encrypting session keys used in secure communication protocols like TLS/SSL.";
    if (extension === "CERTIFICATE_SIGN") return "Indicates that the public key can be used to verify signatures on certificates. This extension can be used only in CA certificates.";
    if (extension === "CRL_SIGN") return "Indicates that the public key can be used to verify signatures on certificate revocation lists (CRLs).";
    return "";
  }

  onSubmitClick(): void {
    // if (this.passwordForm.valid) {
    //   this.data.password = this.passwordForm.get('password')?.value;
    //   this.dialogRef.close(this.data);
    // }
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  matchValidator(controlName: string, matchingControlName: string) {
    return (abstractControl: AbstractControl) => {
      const control = abstractControl.get(controlName);
      const matchingControl = abstractControl.get(matchingControlName);

      if (control?.value !== matchingControl?.value) {
        const error = {confirmedValidator: 'Passwords do not match.'};
        matchingControl?.setErrors(error);
        return error;
      } else {
        matchingControl!.setErrors(null);
        return null;
      }
    }
  }
}
