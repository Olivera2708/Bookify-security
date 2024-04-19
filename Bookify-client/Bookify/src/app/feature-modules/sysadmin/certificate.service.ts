import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { CertificateDTO } from './model/certificate.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../env/env';
import { CertUserDTO } from './model/cert.user.dto';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getUserDTO(userId: number): Observable<CertUserDTO>{
    console.log(this.httpClient.get<CertUserDTO>(environment.apiUser + "/user/" + userId))
    return this.httpClient.get<CertUserDTO>(environment.apiUser + "/" + userId);
  }

  getCertificateRequests(): Observable<CertificateDTO[]>{
    return this.httpClient.get<CertificateDTO[]>(environment.apiPKI + "/requests");
  }
}
