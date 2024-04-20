import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {CertificateService} from "../../sysadmin/certificate.service";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatDialog} from "@angular/material/dialog";
import {AccountService} from "../account.service";
import {AuthenticationService} from "../../authentication/authentication.service";
import {ChangeDetection} from "@angular/cli/lib/config/workspace-schema";

@Component({
  selector: 'app-certificate-request',
  templateUrl: './certificate-request.component.html',
  styleUrl: './certificate-request.component.css'
})
export class CertificateRequestComponent{
  status: string = "";

  constructor(private authService: AuthenticationService, private accountService: AccountService){}
  requestCertificate() {
    this.accountService.sendCertificateRequest(this.authService.getUserId()).subscribe({
      next: (data) => {
        //ako vec postoji onda treba status da bude GENERATED

        //ovo je slucaj ako nema cert pa ceka na admina
        this.status = data.status;
      }
    })
  }
}
