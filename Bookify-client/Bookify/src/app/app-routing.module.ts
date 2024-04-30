import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from "./feature-modules/account/account/account.component";
import { ResultsPageComponent } from "./feature-modules/accommodation/results-page/results-page.component";
import { AccommodationPageComponent } from "./feature-modules/accommodation/accommodation-page/accommodation-page.component";
import { LandingPageComponent } from "./feature-modules/accommodation/landing-page/landing-page.component";
import { LoginComponent } from './feature-modules/authentication/login/login.component';
import { RegistrationComponent } from './feature-modules/authentication/registration/registration.component';
import { ForgotPasswordComponent } from './feature-modules/authentication/forgot-password/forgot-password.component';
import { ConfirmationComponent } from './feature-modules/authentication/confirmation/confirmation.component';
import { AccommodationCreateComponent } from './feature-modules/accommodation/update/accommodation-create/accommodation-create.component';
import { CalendarComponent } from './feature-modules/accommodation/update/calendar/calendar.component';
import { OwnerAccommodationsComponent } from './feature-modules/accommodation/owner-accommodations/owner-accommodations.component';
import { AccommodationRequestsComponent } from "./feature-modules/administration/accommodation-requests/accommodation-requests.component";
import { OwnerPageComponent } from './feature-modules/review/owner-page/owner-page.component';
import {authGuard} from "./feature-modules/authentication/guard/auth.guard";
import {
  GuestReservationsComponent
} from "./feature-modules/reservation/guest-reservations/guest-reservations.component";
import {GuestRequestsComponent} from "./feature-modules/reservation/guest-requests/guest-requests.component";
import {
  OwnerReservationsComponent
} from "./feature-modules/reservation/owner-reservations/owner-reservations.component";
import {
  AccommodationFavoriteComponent
} from "./feature-modules/accommodation/accommodation-favorite/accommodation-favorite.component";
import {FavoritesPageComponent} from "./feature-modules/accommodation/favorites-page/favorites-page.component";
import {UsersComponent} from "./feature-modules/administration/users/users.component";
import { GuestPageComponent } from './feature-modules/review/guest-page/guest-page.component';
import { ReportsPageComponent } from "./feature-modules/accommodation/reports/reports-page/reports-page.component";
import {FeedbackComponent} from "./feature-modules/administration/feedback/feedback.component";
import { CertificateManagerComponent } from './feature-modules/sysadmin/certificate-manager/certificate-manager.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "forgot-password", component: ForgotPasswordComponent },
  { path: "confirmation", component: ConfirmationComponent },
  { path: "results", component: ResultsPageComponent },
  { path: "accommodation/details/:accommodationId", component: AccommodationPageComponent },
  { path: '', component: LandingPageComponent },
  { path: 'owner/:userId', component: OwnerPageComponent },
  { path: 'guest/:userId', component: GuestPageComponent },
  { path: 'accommodation/calendar/:accommodationId', component: CalendarComponent, canActivate: [authGuard] },
  { path: "account", component: AccountComponent, canActivate: [authGuard] },
  { path: "accommodation/create", component: AccommodationCreateComponent, canActivate: [authGuard] },
  { path: "accommodations", component: OwnerAccommodationsComponent, canActivate: [authGuard] },
  { path: "accommodation/modify/:accommodationId", component: AccommodationCreateComponent, canActivate: [authGuard] },
  { path: "accommodation-requests", component: AccommodationRequestsComponent, canActivate: [authGuard] },
  { path: "guest-requests", component: GuestRequestsComponent, canActivate: [authGuard] },
  { path: "owner-requests", component: OwnerReservationsComponent, canActivate: [authGuard] },
  { path: "users", component: UsersComponent, canActivate: [authGuard] },
  { path: "favorites", component: FavoritesPageComponent, canActivate: [authGuard] },
  { path: "accommodation/reports", component: ReportsPageComponent, canActivate: [authGuard] },
  { path: "feedback", component: FeedbackComponent, canActivate: [authGuard] },
  { path: "guest-reservations", component: GuestReservationsComponent, canActivate: [authGuard] },
  { path: "certificates", component: CertificateManagerComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
