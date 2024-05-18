import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { UserProfile } from './userprofile';
import {authGuard} from "../feature-modules/authentication/guard/auth.guard";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;

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
      const roles = decodedToken['realm_access'].roles;
      return this.findRole(roles);
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
