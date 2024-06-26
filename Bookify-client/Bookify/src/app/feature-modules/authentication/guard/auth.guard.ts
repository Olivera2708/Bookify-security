import {CanActivateFn, Router, UrlSegment} from '@angular/router';
import {AuthenticationService} from "../authentication.service";
import {inject} from "@angular/core";
import {AdminPaths} from "./adminpaths";
import {GuestPaths} from "./guestpaths";
import {OwnerPaths} from "./ownerpaths";
import {SysAdminPaths} from "./sysadminpaths";
import { KeycloakService } from '../../../keycloak/keycloak.service';

export const authGuard: CanActivateFn = (route, state) => {
  const keycloakService: KeycloakService = inject(KeycloakService);
  const router: Router = inject(Router);
  const path: string = getFullPath(route.url);
  const userRole = keycloakService.getRole();
  console.log(userRole);
  if(keycloakService.keycloak.isTokenExpired()){
    router.navigate([''])
    return false;
  }
  if (userRole === 'ADMIN' && checkForPaths(path, AdminPaths)) {
    return true;
  } else if (userRole === 'GUEST' && checkForPaths(path, GuestPaths)) {
    return true;
  } else if (userRole === 'OWNER' && checkForPaths(path, OwnerPaths)) {
    return true;
  } else if (userRole === 'SYSADMIN' && checkForPaths(path, SysAdminPaths)) {
    console.log("SSS");
    return true;
  } else {
    router.navigate(['']);
    return false;
  }
};

function getFullPath(urlSegments: UrlSegment[]): string {
  let path = '';
  for (const urlSegment of urlSegments) {
    path += urlSegment.path + '/';
  }
  return path;
}

function checkForPaths(accessedPath: string, paths: string[]): boolean {
  for (const path of paths) {
    if (accessedPath.includes(path)) return true;
  }
  return false;
}
