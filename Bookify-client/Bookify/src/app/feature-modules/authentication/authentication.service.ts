import { Injectable, Inject, LOCALE_ID } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Credentials } from "./model/credentials";
import { BehaviorSubject, Observable } from "rxjs";
import { UserJWT } from "./model/UserJWT";
import { environment } from "../../../env/env";
import { JwtHelperService } from "@auth0/angular-jwt";
import { UserRegistrationDTO } from "./model/user.registration.dto.model";
import { Message } from "./model/message.dto.model";
import {NotificationService} from "../account/notification.service";

@Injectable({
  providedIn: 'root',
})


export class AuthenticationService {
  constructor(@Inject(LOCALE_ID) private locale: string,
    private httpClient: HttpClient,
              private notificationService: NotificationService) {
  }

  user$: BehaviorSubject<string> = new BehaviorSubject("");
  userState: Observable<string> = this.user$.asObservable();

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    skip: 'true',
  });

  async getCountries(): Promise<string[]> {
    try {
      const response = await fetch('https://restcountries.com/v3.1/all');
      const countriesData = await response.json();
      const countries = countriesData.map((country: any) => country.name.common);
      countries.sort();
      return countries;
    } catch (error) {
      console.error('Error fetching countries:', error);
      return [];
    }
  }

  login(userCredentials: Credentials): Observable<UserJWT> {
    return this.httpClient.post<UserJWT>(environment.apiHost + "users/login", userCredentials,
      { headers: this.headers });
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('user') != null;
  }

  getRole(): string {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper: JwtHelperService = new JwtHelperService();
      return helper.decodeToken(accessToken).role;
    }
    return '';
  }

  getUserId(): number {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper: JwtHelperService = new JwtHelperService();
      return helper.decodeToken(accessToken).id
    }
    return -1;
  }

  setUser(): void {
    this.user$.next(this.getRole());
  }

  logout(): void {
    this.user$.next('');
    localStorage.removeItem('user');
    this.httpClient.get(environment.apiHost + "users/logout");
    this.notificationService.closeSocket();
  }

  connectWithWebSocket():void {
    this.notificationService.initializeWebSocketConnection(this.getUserId());
  }

  register(user: UserRegistrationDTO): Observable<Message> {
    return this.httpClient.post<Message>(environment.apiUser, user);
  }

  activateAccount(token: Message): Observable<Message> {
    if(window.innerWidth<=768){
      return this.httpClient.put<Message>(environment.apiMobile + "/activate-account", token);
    }
    return this.httpClient.put<Message>(environment.apiUser + "/activate-account", token);
  }

  resetPassword(email: string): Observable<string> {
    return this.httpClient.get<string>(environment.apiUser + "/forgot-password/" + email);
  }

  getNotificationNumber(): Observable<number> {
    return this.notificationService.notificationNum;
  }
}
