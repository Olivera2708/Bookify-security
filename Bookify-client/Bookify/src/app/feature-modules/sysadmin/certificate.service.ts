import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { CertificateRequestDto } from './model/certificateRequest.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../env/env';
import { CertUserDTO } from './model/cert.user.dto';
import {BasicCertificateDTO} from "./model/basicCertificate.dto";
import {CreateCertificateDTO} from "./model/createcertificate.dto";
import moment from "moment/moment";

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getUserDTO(userId: number): Observable<CertUserDTO>{
    return this.httpClient.get<CertUserDTO>(environment.apiUser + "/" + userId);
  }

  getCertificateRequests(): Observable<CertificateRequestDto[]>{
    return this.httpClient.get<CertificateRequestDto[]>(environment.apiPKI + "/requests");
  }

  rejectCertificateRequest(requestId: number): Observable<CertificateRequestDto>{
    return this.httpClient.put<CertificateRequestDto>(environment.apiPKI + "/request/reject/" + requestId, {});
  }

  getAllCertificates(): Observable<BasicCertificateDTO[]>{
    return this.httpClient.get<BasicCertificateDTO[]>(environment.apiPKI);
  }

  createCertificate(certificateDTO: CreateCertificateDTO): Observable<CreateCertificateDTO> {
    return this.httpClient.post<CreateCertificateDTO>(environment.apiPKI + "/create", certificateDTO);
  }

  revokeCertificate(ca: string, serialNumber: string, reason: string) : Observable<CertificateRequestDto> {
    return this.httpClient.post<CertificateRequestDto>(environment.apiPKI + "/reason" +
        "?CA=" + ca +
        "&serialNumber=" + serialNumber +
        "&reason=" + reason, {});
  }

  isCertificateRevoked(serialNumber: string) : Observable<boolean> {
    return this.httpClient.get<boolean>(environment.apiPKI + "/checkRevocation?serialNumber=" + serialNumber);
  }

  restoreCertificate(ca: string, serialNumber: string) : Observable<boolean> {
    return this.httpClient.put<boolean>(environment.apiPKI + "/restore" +
          "?CA=" + ca +
          "&serialNumber=" + serialNumber, {});
  }
}
