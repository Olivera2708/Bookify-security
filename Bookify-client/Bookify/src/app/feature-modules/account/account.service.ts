import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Account } from "./model/account";
import { environment } from "../../../env/env";
import { CertificateRequestDTO } from "./model/CertificateRequestDTO";
import { TransactionResponse } from './model/transaction.respons.dto';
import { X509Certificate } from 'crypto';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private httpClient: HttpClient) {

  }

  getUser(userId: number): Observable<Account> {
    return this.httpClient.get<Account>(environment.apiHost + "users/" + userId);
  }

  getUserByEmail(email: string): Observable<Account> {
    return this.httpClient.get<Account>(environment.apiHost + "users/email/" + email);
  }

  getAccountImage(imageId: number | undefined): Observable<Blob> {
    return this.httpClient.get(environment.apiHost + "users/image/" + imageId, { responseType: "blob" });
  }

  updatePassword(userId: number | undefined, newPassword: string): Observable<string> {
    return this.httpClient.post(environment.apiHost + "users/" + userId + "/change-password", newPassword, { responseType: "text" });
  }

  updateUser(account: Account): Observable<Account> {
    return this.httpClient.put<Account>(environment.apiHost + "users", account);
  }

  updateAccountImage(userId: number | undefined, file: File): Observable<number> {
    const data: FormData = new FormData();
    data.append('image', file);
    return this.httpClient.post<number>(environment.apiHost + "users/change-image/" + userId, data);
  }
  deleteAccount(userId: number | undefined): Observable<string> {
    return this.httpClient.delete(environment.apiHost + "users/" + userId, { responseType: "text" });
  }
  getAccountImageId(userId: number | undefined): Observable<number> {
    return this.httpClient.get<number>(environment.apiHost + "users/account-pic/" + userId);
  }

  sendCertificateRequest(userId: number): Observable<CertificateRequestDTO> {
    return this.httpClient.post<CertificateRequestDTO>(environment.apiHost + "certificates/request/Bookify/" + userId, {});
  }

  doesUserAlreadyHaveValidCertificate(userId: string): Observable<boolean> {
    return this.httpClient.get<boolean>(environment.apiHost + "certificates/exists/" + userId);
  }

  getCertificate(email: string) : Observable<TransactionResponse> {
    return this.httpClient.get<TransactionResponse>(environment.apiHost + "certificates/request?email=" + email);
  }
}
