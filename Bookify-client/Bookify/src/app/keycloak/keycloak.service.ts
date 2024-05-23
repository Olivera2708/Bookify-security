import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { UserProfile } from './userprofile';
import {authGuard} from "../feature-modules/authentication/guard/auth.guard";
import { AccountService } from '../feature-modules/account/account.service';
import { Account } from '../feature-modules/account/model/account';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;

  constructor(    private accountService: AccountService) {}

  get keycloak() {
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:8080',
        realm: 'SpringBootKeycloak',
        clientId: 'login-app'
      });
    }
    return this._keycloak;
  }

  private _profile: UserProfile | undefined;

  get profile(): UserProfile | undefined {
    return this._profile;
  }

  public getRole(): string {
    const token = this.profile?.token;
    if(token !== undefined){
      const decodedToken = JSON.parse(atob(token.split('.')[1]));
      return decodedToken['role'];
    }
    return "";
  }

  private findRole(roles : string[]) : string {
    for (let role of roles){
      if(role === "GUEST" || role === "OWNER" || role === "ADMIN" || role === "SYSADMIN"){
        return role;
      }
    }
    return "";
  }

  async init() {
    const authenticated = await this.keycloak.init({});

    if (authenticated) {
      this._profile = (await this.keycloak.loadUserProfile()) as UserProfile;
      this._profile.token = this.keycloak.token || '';
      if (this._profile?.username){
        var email = this._profile.username;
        localStorage.setItem('user', this._profile.username);

        this.accountService.getUserByEmail(email).subscribe({
          next: (data: Account) => {
            localStorage.setItem('userId', "" + data.id);
            localStorage.setItem('userRole', this.getRole())
          },
          error: (err) => {

          }
        });

      }
      console.log(this._profile);
    }
  }

  login() {
    return this.keycloak.login();
  }

  logout() {
    // this.keycloak.accountManagement();
    return this.keycloak.logout({redirectUri: 'https://localhost:4200'});
  }
}
