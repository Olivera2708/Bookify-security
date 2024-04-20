import { Component, Inject } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { environment } from '../../../../env/env';
import { CreateCertificateDTO } from '../model/createcertificate.dto';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormDialogComponent } from '../form-dialog/form-dialog.component';
import { CertificateTreeNode } from '../certificate-manager/certificate-manager.component';
import { TableElement } from '../model/table.data';
import { CertificateService } from '../certificate.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-details-dialog',
  templateUrl: './details-dialog.component.html',
  styleUrl: './details-dialog.component.css'
})
export class DetailsDialogComponent {
  extensionsCheckbox: string[] = [];

  constructor(public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { node: CertificateTreeNode, request: TableElement },
    private certificateService: CertificateService,
    private spinner: NgxSpinnerService) {
    this.extensionsCheckbox = this.data.node.certificate.extensions;
  }

  formatDate(date: Date): string {
    date = new Date(date)
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${day}.${month}.${year}.`;
  }

  trackByFn(index: number, item: string): number {
    return index;
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

  onCancelClick(): void {
    this.dialogRef.close();
  }
}
