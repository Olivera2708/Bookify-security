import {Injectable, inject} from '@angular/core';
import {HttpEvent, HttpEventType, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest,} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication.service";
import { KeycloakService } from '../../../keycloak/keycloak.service';

@Injectable()
export class Interceptor implements HttpInterceptor {
  constructor(private router: Router, private authService: AuthenticationService, private keyclaokService: KeycloakService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const accessToken: any = localStorage.getItem('user');
    const token = this.keyclaokService.keycloak.token;
    // if (req.headers.get('skip'))
    //   return next.handle(req);
    if(token){
      const authReq = req.clone({
        headers: new HttpHeaders({
          Authorization: "Bearer " + token
        })
      })
      return next.handle(authReq);
    }
    return next.handle(req);
    // if (accessToken) {
    //   const cloned = req.clone({
    //     headers: req.headers.set('Authorization', "Bearer " + accessToken),
    //   });
    //   return next.handle(cloned).pipe(tap({
    //     next: (event: HttpEvent<any>): void => {},
    //     error: (error): void => {
    //       if(error.status === 401) {
    //         this.authService.logout();
    //         this.authService.setUser();
    //         this.router.navigate(['']);
    //       }
    //     }
    //   }));
    // } else {
    //   return next.handle(req);
    // }
  }
}
