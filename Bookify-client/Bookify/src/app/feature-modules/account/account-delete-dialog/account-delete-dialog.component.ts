import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../model/DialogData";

@Component({
  selector: 'app-account-delete-dialog',
  templateUrl: './account-delete-dialog.component.html',
  styleUrl: './account-delete-dialog.component.css'
})
export class AccountDeleteDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<AccountDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
  }

  OnConfirmClick(): void{
    this.dialogRef.close(true);
  }

  OnCancelClick(): void{
    this.dialogRef.close(false);
  }
}
