import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { CertificateDTO } from './model/certificate.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../env/env';
import { CertUserDTO } from './model/cert.user.dto';
import {BasicCertificateDTO} from "./model/basicCertificate.dto";
import {CreateCertificateDTO} from "./model/createcertificate.dto";

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getUserDTO(userId: number): Observable<CertUserDTO>{
    return this.httpClient.get<CertUserDTO>(environment.apiUser + "/" + userId);
  }

  getCertificateRequests(): Observable<CertificateDTO[]>{
    return this.httpClient.get<CertificateDTO[]>(environment.apiPKI + "/requests");
  }

  rejectCertificateRequest(requestId: number): Observable<CertificateDTO>{
    return this.httpClient.put<CertificateDTO>(environment.apiPKI + "/request/reject/" + requestId, {});
  }

  getAllCertificates(): Observable<BasicCertificateDTO[]>{
    return this.httpClient.get<BasicCertificateDTO[]>(environment.apiPKI);
  }

  createCertificate(certificateDTO: CreateCertificateDTO): Observable<CreateCertificateDTO> {
    return this.httpClient.post<CreateCertificateDTO>(environment.apiPKI + "/create", certificateDTO);
  }
}
