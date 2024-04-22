import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AccountService } from "../account.service";
import { AuthenticationService } from "../../authentication/authentication.service";
import { Account } from "../model/account";
import * as forge from 'node-forge';
import { Base64 } from 'js-base64';

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
                  const publicKey = this.importRsaPublicKeyFromBase64(response.publicKey);
                  if (publicKey != null) {
                    const byteArray = new Uint8Array(Base64.toUint8Array(response.digitalSignature));
                    const cert = new Uint8Array(Base64.toUint8Array(response.certificate));
                    const isSigned = this.verifyDigitalSignature(publicKey, cert, byteArray);
                    if (isSigned)
                      this.status = "CERTIFIED";
                    else
                      this.status = "NOT CERTIFIED"
                  }

                  //view cert in log
                  // const derBytes = forge.util.decode64(response.certificate);
                  // const certificate = forge.pki.certificateFromAsn1(
                  //   forge.asn1.fromDer(derBytes));
                  // console.log(certificate);
                }
              });
            }
          }
          else {
            this.sendCertificateRequest();
          }
        }
      });
    }
  }

  private base64ToUint8Array(base64: string): Uint8Array {
    const binaryString = atob(base64);
    const bytes = new Uint8Array(binaryString.length);
    for (let i = 0; i < binaryString.length; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }
    return bytes;
  }

  private importRsaPublicKeyFromBase64(base64: string): forge.pki.rsa.PublicKey | null {
    try {
      const uint8Array = this.base64ToUint8Array(base64);
      const derBuffer = forge.util.createBuffer(uint8Array); // Convert to forge buffer
      const asn1 = forge.asn1.fromDer(derBuffer); // Parse DER
      return forge.pki.publicKeyFromAsn1(asn1) as forge.pki.rsa.PublicKey;
    } catch (error) {
      console.error("Error parsing RSA public key from Base64:", error);
      return null; // Return null if parsing fails
    }
  }

  private uint8ArrayToForgeBuffer(array: Uint8Array): forge.util.ByteBuffer {
    return forge.util.createBuffer(array); // Convert Uint8Array to forge ByteBuffer
  }

  private verifyDigitalSignature(
    publicKey: forge.pki.rsa.PublicKey,
    data: Uint8Array,
    digitalSignature: Uint8Array
  ): boolean {
    try {
      const hash = forge.md.sha256.create();

      // Convert Uint8Array to string without specifying encoding
      const dataString = String.fromCharCode(...data);
      hash.update(dataString);

      const signatureBuffer = this.uint8ArrayToForgeBuffer(digitalSignature);

      return publicKey.verify(hash.digest().bytes(), signatureBuffer.bytes());
    } catch (error) {
      console.error("Error verifying digital signature:", error);
      return false; // Return false if verification fails
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
