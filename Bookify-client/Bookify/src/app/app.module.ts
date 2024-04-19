import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withNoHttpTransferCache } from '@angular/platform-browser';
import { AccommodationModule } from './feature-modules/accommodation/accommodation.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from "./layout/layout.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthenticationModule } from './feature-modules/authentication/authentication.module';
import { CarouselComponent } from "./feature-modules/accommodation/carousel/carousel.component";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { DatapickerRangeComponent } from "./layout/datapicker-range/datapicker-range.component";
import { SharedModule } from "./shared/shared.module";
import { AccountModule } from "./feature-modules/account/account.module";
import { Interceptor } from "./feature-modules/authentication/interceptor/interceptor";
import { AdministrationModule } from "./feature-modules/administration/administration.module";
import { ReviewModule } from './feature-modules/review/review.module';
import { ReservationModule } from './feature-modules/reservation/reservation.module';
import { CertificateManagerComponent } from './feature-modules/sysadmin/certificate-manager/certificate-manager.component';
import { MatTableModule } from '@angular/material/table';

@NgModule({
  declarations: [
    AppComponent,
    CertificateManagerComponent
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
    MatTableModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
    provideClientHydration(withNoHttpTransferCache()),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
