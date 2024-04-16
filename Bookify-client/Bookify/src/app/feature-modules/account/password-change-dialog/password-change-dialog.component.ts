import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../model/DialogData";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-password-change-dialog',
  templateUrl: './password-change-dialog.component.html',
  styleUrl: './password-change-dialog.component.css'
})
export class PasswordChangeDialogComponent {

  passwordForm: FormGroup = new FormGroup({
    password: new FormControl('', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)]),
    repeatedpassword: new FormControl('', [Validators.required])
  }, {
    validators: this.matchValidator('password', 'repeatedpassword')
  });

  constructor(
    public dialogRef: MatDialogRef<PasswordChangeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {
  }

  onSubmitClick(): void {
    if (this.passwordForm.valid) {
      this.data.password = this.passwordForm.get('password')?.value;
      this.dialogRef.close(this.data);
    }
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
