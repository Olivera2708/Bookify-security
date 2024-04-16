import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationBasicInformationComponent } from './update/accommodation-basic-information/accommodation-basic-information.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AccommodationLocationComponent } from './update/accommodation-location/accommodation-location.component';
import { AccommodationAmenitiesComponent } from './update/accommodation-amenities/accommodation-amenities.component';
import { AccommodationPhotosComponent } from './update/accommodation-photos/accommodation-photos.component';
import { AccommodationGuestsComponent } from './update/accommodation-guests/accommodation-guests.component';
import { AccommodationAvailabilityComponent } from './update/accommodation-availability/accommodation-availability.component';
import { AccommodationCreateComponent } from './update/accommodation-create/accommodation-create.component';
import { SharedModule } from "../../shared/shared.module";
import { CalendarComponent } from './update/calendar/calendar.component';
import { OwnerAccommodationsComponent } from './owner-accommodations/owner-accommodations.component';
import { OwnerAccommodationsCardComponent } from './owner-accommodations-card/owner-accommodations-card.component';
import { RouterLink } from "@angular/router";
import { MaterialModule } from "../../infrastructure/material/material.module";
import { AccommodationBasicComponent } from "./accommodation-basic/accommodation-basic.component";
import { ResultsPageComponent } from "./results-page/results-page.component";
import { AccommodationPageComponent } from "./accommodation-page/accommodation-page.component";
import { ReserveComponent } from "./reserve/reserve.component";
import { LandingPageComponent } from "./landing-page/landing-page.component";
import { TopAccommodationComponent } from "../../layout/top-accommodation/top-accommodation.component";
import { CarouselComponent } from "./carousel/carousel.component";
import { LayoutModule } from "../../layout/layout.module";
import { DatapickerRangeComponent } from "../../layout/datapicker-range/datapicker-range.component";
import {FavoritesPageComponent} from "./favorites-page/favorites-page.component";
import {AccommodationFavoriteComponent} from "./accommodation-favorite/accommodation-favorite.component";
import {ReservationModule} from "../reservation/reservation.module";
import {
  OverallChartReservationsComponent
} from "./reports/overall-chart-reservations/overall-chart-reservations.component";
import {OverallChartRevenueComponent} from "./reports/overall-chart-revenue/overall-chart-revenue.component";
import {
  AccommodationChartReservationsComponent
} from "./reports/accommodation-chart-reservations/accommodation-chart-reservations.component";
import {
  AccommodationChartRevenueComponent
} from "./reports/accommodation-chart-revenue/accommodation-chart-revenue.component";
import {ReportsPageComponent} from "./reports/reports-page/reports-page.component";
import { ReviewModule } from '../review/review.module';

@NgModule({
  declarations: [
    AccommodationBasicInformationComponent,
    AccommodationLocationComponent,
    AccommodationAmenitiesComponent,
    AccommodationPhotosComponent,
    AccommodationGuestsComponent,
    AccommodationAvailabilityComponent,
    AccommodationCreateComponent,
    CalendarComponent,
    OwnerAccommodationsComponent,
    OwnerAccommodationsCardComponent,
    AccommodationBasicComponent,
    ResultsPageComponent,
    AccommodationPageComponent,
    ReserveComponent,
    LandingPageComponent,
    TopAccommodationComponent,
    FavoritesPageComponent,
    AccommodationFavoriteComponent,
    OverallChartReservationsComponent,
    OverallChartRevenueComponent,
    AccommodationChartReservationsComponent,
    AccommodationChartRevenueComponent,
    ReportsPageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    LayoutModule,
    RouterLink,
    CarouselComponent,
    DatapickerRangeComponent,
    ReservationModule,
    ReviewModule
  ],
  exports: [
    AccommodationCreateComponent,
    OwnerAccommodationsComponent,
    ResultsPageComponent,
    AccommodationPageComponent,
    LandingPageComponent
  ]
})
export class AccommodationModule { }
