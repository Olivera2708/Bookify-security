import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {
  email = new FormControl('', [Validators.required, Validators.email]);
  hide = true;
  form: FormGroup;

  constructor(private fb: FormBuilder, private authenticationService: AuthenticationService, private router: Router, private _snackBar: MatSnackBar) {
    this.form = fb.group({
      email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}')]]
    })
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  onSubmit() {
    if(this.form.valid){
      this.authenticationService.resetPassword(this.form.get('email')?.value).subscribe({
        next: (data: string) => {
          this.openSnackBar("Check email for new password", "close");
          this.router.navigate(['/login']);
        },
        error: (e) => {
          console.log(e);
          this.openSnackBar("Wrong email address", "close");
        }
      })
    }
  }
}
