import { APP_INITIALIZER, Injectable, NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withNoHttpTransferCache } from '@angular/platform-browser';
import { AccommodationModule } from './feature-modules/accommodation/accommodation.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from "./layout/layout.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthenticationModule } from './feature-modules/authentication/authentication.module';
import { CarouselComponent } from "./feature-modules/accommodation/carousel/carousel.component";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { DatapickerRangeComponent } from "./layout/datapicker-range/datapicker-range.component";
import { SharedModule } from "./shared/shared.module";
import { AccountModule } from "./feature-modules/account/account.module";
import { Interceptor } from "./feature-modules/authentication/interceptor/interceptor";
import { AdministrationModule } from "./feature-modules/administration/administration.module";
import { ReviewModule } from './feature-modules/review/review.module';
import { ReservationModule } from './feature-modules/reservation/reservation.module';
import { CertificateManagerComponent } from './feature-modules/sysadmin/certificate-manager/certificate-manager.component';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from "@angular/material/sort";
import { MatTreeModule } from "@angular/material/tree";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { FormDialogComponent } from './feature-modules/sysadmin/form-dialog/form-dialog.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MaterialModule } from './infrastructure/material/material.module';
import {NgxSpinnerModule} from "ngx-spinner";
import { DetailsDialogComponent } from './feature-modules/sysadmin/details-dialog/details-dialog.component';
import { RevokeDialogComponent } from './feature-modules/sysadmin/revoke-dialog/revoke-dialog.component';
import { KeycloakService } from './keycloak/keycloak.service';
import Keycloak from 'keycloak-angular';
import { Observable } from 'rxjs';


export function kcFactory(kcService: KeycloakService){
  return () => kcService.init(); 
}

// @Injectable()
// export class AddHeaderInterceptor implements HttpInterceptor {
//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     const modifiedRequest = req.clone({
//       setHeaders: {
//         'X-XSS-Protection': '1; mode=block'
//       }
//     });
//     return next.handle(modifiedRequest);
//   }
// }

@NgModule({
  declarations: [
    AppComponent,
    CertificateManagerComponent,
    FormDialogComponent,
    FormDialogComponent,
    DetailsDialogComponent,
    RevokeDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    AccountModule,
    BrowserAnimationsModule,
    AuthenticationModule,
    CarouselComponent,
    NgbModule,
    HttpClientModule,
    DatapickerRangeComponent,
    AccommodationModule,
    ReservationModule,
    SharedModule,
    AdministrationModule,
    ReviewModule,
    MatTableModule,
    MatSortModule,
    MatTreeModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    BrowserAnimationsModule,
    NgxSpinnerModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
    provideClientHydration(withNoHttpTransferCache()),
    { provide: APP_INITIALIZER, deps: [KeycloakService], useFactory: kcFactory, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}


