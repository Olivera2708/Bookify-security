import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CertificateService } from "../../sysadmin/certificate.service";
import { LiveAnnouncer } from "@angular/cdk/a11y";
import { MatDialog } from "@angular/material/dialog";
import { AccountService } from "../account.service";
import { AuthenticationService } from "../../authentication/authentication.service";
import { ChangeDetection } from "@angular/cli/lib/config/workspace-schema";
import { Account } from "../model/account";
import { response } from 'express';
import * as crypto from 'crypto';
import * as forge from 'node-forge';

@Component({
  selector: 'app-certificate-request',
  templateUrl: './certificate-request.component.html',
  styleUrl: './certificate-request.component.css'
})
export class CertificateRequestComponent {
  status: string = "";

  constructor(private authService: AuthenticationService, private accountService: AccountService) { }
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
        next: (data1) => {
          if (data1) {
            if (data.email) {
              this.accountService.getCertificate(data.email).subscribe({
                next: (response) => {
                  console.log(response);
                  console.log("-------------------");
                  const derBytes = forge.util.decode64(response.certificate);

                  const certificate = forge.pki.certificateFromAsn1(
                    forge.asn1.fromDer(derBytes));
                  console.log(certificate);

                  // const binaryData: string = atob(response.certificate);

                  // const uint8Array = new Uint8Array(binaryData.length);
                  // for (let i = 0; i < binaryData.length; i++) {
                  //   uint8Array[i] = binaryData.charCodeAt(i);
                  // }

                  // const publicKeyObject = crypto.createPublicKey("" + response.publicKey)

                  // const publicKeyData = window.atob("" + response.publicKey);

                  // const publicKeyBuffer = new Uint8Array(publicKeyData.length);
                  // for (let i = 0; i < publicKeyData.length; ++i) {
                  //   publicKeyBuffer[i] = publicKeyData.charCodeAt(i);
                  // }

                  // const publicKeyObject = crypto.createPublicKey({
                  //   key: "" + response.publicKey,
                  //   format: 'der',
                  //   type: 'spki'
                  // });

                  // const a = new crypto.X509Certificate(response.certificate);
                  // console.log(a.verify(publicKeyObject));
                  // crypto.subtle.importKey(
                  //   "spki",
                  //   publicKeyBuffer,
                  //   { name: "RSASSA-PKCS1-v1_5", hash: "SHA-256" },
                  //   true,
                  //   ["verify"]
                  // ).then((publicKey) => {
                  //   return crypto.subtle.verify(
                  //     { name: "RSASSA-PKCS1-v1_5", hash: "SHA-256" },
                  //     publicKey,
                  //     response.digitlSignature,
                  //     uint8Array
                  //   );
                  // }).then((isValid) => {
                  //   if (isValid) {
                  //     console.log("Digital signature is valid");
                  //   } else {
                  //     console.error("Digital signature verification failed");
                  //   }
                  // })
                  //   .catch((error) => {
                  //     console.error("Error verifying digital signature:", error);
                  //   });
                }
              })
              this.status = "CERTIFIED";
            }
          }
          else {
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
