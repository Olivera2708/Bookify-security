import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormDialogComponent} from "../form-dialog/form-dialog.component";
import {CertificateService} from "../certificate.service";
import {CertificateTreeNode} from "../certificate-manager/certificate-manager.component";
import {TableElement} from "../model/table.data";
import {environment} from "../../../../env/env";

@Component({
  selector: 'app-revoke-dialog',
  templateUrl: './revoke-dialog.component.html',
  styleUrl: './revoke-dialog.component.css'
})
export class RevokeDialogComponent {
  reasons = ['Certificate hold', 'AA Compromise', 'CA Compromise', "Affiliation changed",
                      'Cessation of operation', 'Key compromise', 'Privilege withdrawn', 'Remove from CRL',
                      'Superseded', 'Unspecified', 'Unused'];

  selectedReason: string;

  constructor(public dialogRef: MatDialogRef<FormDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { node: CertificateTreeNode, request: TableElement },
              private certificateService : CertificateService) {}

  private toReason(reason: string) : string{
    let new_reason = reason.replace(" ", "_");
    return new_reason.toUpperCase();
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  revokeClick(){
    if (this.selectedReason != undefined) {
      this.certificateService.revokeCertificate(environment.rootAlias, this.data.node.certificate.subjectCertificateAlias, this.toReason(this.selectedReason)).subscribe({
        next: (data) => {
          this.dialogRef.close(true);
        }
      });
    }
  }
}
