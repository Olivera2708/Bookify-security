import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from "../authentication.service";
import {Credentials} from "../model/credentials";
import {UserJWT} from "../model/UserJWT";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MessageDialogComponent} from "../../../layout/message-dialog/message-dialog.component";
import { KeycloakService } from '../../../keycloak/keycloak.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  credentialsForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  hide = true;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private dialog: MatDialog,
              private keycloakService: KeycloakService) {
  }
  // async ngOnInit(): Promise<void> {
  //   await this.keycloakService.init();
  //   await this.keycloakService.login();
  // }

  getErrorMessage() {
    if (this.credentialsForm.controls['email'].hasError('required')) {
      return 'You must enter a value';
    }

    return this.credentialsForm.controls['email'].hasError('email') ? 'Not a valid email' : '';
  }

  // login(): void {
  //   if(this.credentialsForm.valid){
  //     const credentials: Credentials = {
  //       email: this.credentialsForm.value.email || "",
  //       password: this.credentialsForm.value.password || ""
  //     }
  //     this.authenticationService.login(credentials).subscribe({
  //       next: (response: UserJWT) => {
  //        localStorage.setItem('user', response.accessToken);
  //        this.authenticationService.setUser();
  //        if(this.authenticationService.getRole() === "SYSADMIN"){
  //          this.router.navigate(['certificates'])
  //        }else{
  //          this.router.navigate(['']);
  //        }
  //       },
  //       error: err => {
  //         console.log(err);
  //         if(err.status === 400 ){
  //           this.dialog.open(MessageDialogComponent, { data: {message:err.error}} )
  //         } else if(err.status === 401){
  //           this.dialog.open(MessageDialogComponent, { data: {message:err.error.message}} )
  //         }
  //       }
  //     });
  //   }
  // }
}
