import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';
import { UserRegistrationDTO } from '../model/user.registration.dto.model';
import { Address } from '../../accommodation/model/address.dto.model';
import { Message } from '../model/message.dto.model';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {
  hide1 = true;
  hide2 = true;
  countries: Promise<string[]> = Promise.resolve([]);

  form: FormGroup;
  submitted: boolean = false;

  constructor(private authenticationService: AuthenticationService, private fb: FormBuilder, private router: Router, private _snackBar: MatSnackBar) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}')]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$')]],
      confirmPassword: ['', [Validators.required, this.passwordValidator]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      country: ['', Validators.required],
      city: ['', Validators.required],
      streetAddress: ['', Validators.required],
      zipCode: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern('[\+]?[0-9]{3,12}')]],
      role: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.countries = this.authenticationService.getCountries();
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  get passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!this.form) {
        return null;
      }
      const password = this.form.get('password')?.value;
      const confirmPassword = control.value;
      if (password !== confirmPassword) {
        return { different: true, message: 'Confirm password should be same as password' }
      }
      return null;
    }
  }

  onSubmit() {
    if (this.form.valid) {
      const address: Address = {
        country: this.form.get('country')?.value,
        city: this.form.get('city')?.value,
        address: this.form.get('streetAddress')?.value,
        zipCode: this.form.get('zipCode')?.value
      }
      const user: UserRegistrationDTO = {
        email: this.form.get('email')?.value,
        password: this.form.get('password')?.value,
        confirmPassword: this.form.get('confirmPassword')?.value,
        firstName: this.form.get('firstName')?.value,
        lastName: this.form.get('lastName')?.value,
        address: address,
        phone: this.form.get('phone')?.value,
        role: this.form.get('role')?.value
      }
      this.authenticationService.register(user).subscribe({
        next: (hashToken: Message) => {
          this.openSnackBar("Check mail for activating accoun", "close");
          this.router.navigate(['/login']);
        },
        error: (_) => {
          this.openSnackBar("Already have account with this email", "close");
        }
      })
    }
    this.submitted = true;
  }
}
