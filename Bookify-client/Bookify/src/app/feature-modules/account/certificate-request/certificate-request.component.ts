import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {CertificateService} from "../../sysadmin/certificate.service";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatDialog} from "@angular/material/dialog";
import {AccountService} from "../account.service";
import {AuthenticationService} from "../../authentication/authentication.service";
import {ChangeDetection} from "@angular/cli/lib/config/workspace-schema";
import {Account} from "../model/account";

@Component({
  selector: 'app-certificate-request',
  templateUrl: './certificate-request.component.html',
  styleUrl: './certificate-request.component.css'
})
export class CertificateRequestComponent{
  status: string = "";

  constructor(private authService: AuthenticationService, private accountService: AccountService){}
  requestCertificate() {
    this.accountService.getUser(this.authService.getUserId()).subscribe({
      next: (data) => {
        this.doesUserHaveValidCertificate(data);
      }
    });
  }

  private doesUserHaveValidCertificate(data: Account) {
    if (data.email != null) {
      this.accountService.doesUserAlreadyHaveValidCertificate(data.email).subscribe({
        next: (data) => {
          if (data)
            // bezbedno prenosenje sertifikata korisniku
            this.status = "CERTIFIED";
          else{
            this.sendCertificateRequest();
          }
        }
      });
    }
  }

  private sendCertificateRequest() {
    this.accountService.sendCertificateRequest(this.authService.getUserId()).subscribe({
      next: (data) => {
        this.status = data.status;
      }
    });
  }
}
