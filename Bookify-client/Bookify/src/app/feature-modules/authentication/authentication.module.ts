import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegistrationComponent } from './registration/registration.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component'
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from "../../infrastructure/material/material.module";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "confirmation", component: ConfirmationComponent }
];

@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    ConfirmationComponent,
    ForgotPasswordComponent
  ],
    imports: [
        CommonModule,
        MaterialModule,
        BrowserModule,
        BrowserAnimationsModule,
        RouterModule.forChild(routes),
        ReactiveFormsModule
    ],
  exports: [
    LoginComponent,
    RegistrationComponent,
    ConfirmationComponent,
    ForgotPasswordComponent
  ]
})
export class AuthenticationModule { }
