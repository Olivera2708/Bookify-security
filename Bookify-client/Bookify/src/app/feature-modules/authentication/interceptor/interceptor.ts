import {Injectable, inject} from '@angular/core';
import {HttpEvent, HttpEventType, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest,} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication.service";
import { KeycloakService } from '../../../keycloak/keycloak.service';
import DOMPurify from 'dompurify';

@Injectable()
export class Interceptor implements HttpInterceptor {
  constructor(private router: Router, private authService: AuthenticationService, private keyclaokService: KeycloakService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const sanitizedUrl = this.sanitizeUrl(req.url);
    req = req.clone({url: sanitizedUrl});
    const accessToken: any = localStorage.getItem('user');
    const token = this.keyclaokService.keycloak.token;
    req = req.clone({
      headers: new HttpHeaders({
        'X-Content-Type-Options': 'nosniff',
        'X-XSS-Protection': '1; mode=block'
      })
    });
    if(token){
      const authReq = req.clone({
        headers: new HttpHeaders({
          Authorization: "Bearer " + token,
          'X-Content-Type-Options': 'nosniff',
          'X-XSS-Protection': '1; mode=block'
        })
      })
      return next.handle(authReq);
    }
    return next.handle(req);
  }

  sanitizeUrl(url: string): string {
    const urlObj = new URL(url, window.location.origin);
    urlObj.searchParams.forEach((value, key) => {
      const sanitizedValue = this.sanitize(value);
      urlObj.searchParams.set(key, sanitizedValue);
    });
    return urlObj.toString();
  }

  sanitize(value: string): string {
    return DOMPurify.sanitize(value);
  }
}
