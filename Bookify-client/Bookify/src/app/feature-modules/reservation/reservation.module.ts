import { NgModule, SchemaMetadata } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationCardComponent } from './reservation-card/reservation-card.component';
import { RequestCardComponent } from './request-card/request-card.component';
import { OwnerReservationsComponent } from './owner-reservations/owner-reservations.component';
import { FilterReservationsComponent } from './filter-reservations/filter-reservations.component';
import { LayoutModule } from '../../layout/layout.module';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { GuestReservationCardComponent } from './guest-reservation-card/guest-reservation-card.component';
import { GuestReservationsComponent } from './guest-reservations/guest-reservations.component';
import { GuestRequestCardComponent } from './guest-request-card/guest-request-card.component';
import { GuestRequestsComponent } from './guest-requests/guest-requests.component';
import { RouterModule } from "@angular/router";

@NgModule({
  declarations: [
    ReservationCardComponent,
    RequestCardComponent,
    OwnerReservationsComponent,
    FilterReservationsComponent,
    GuestReservationCardComponent,
    GuestReservationsComponent,
    GuestRequestCardComponent,
    GuestRequestsComponent
  ],
  imports: [
    CommonModule,
    LayoutModule,
    MatInputModule,
    FormsModule,
    MatSelectModule,
    RouterModule.forRoot([])
  ],
  exports: [
    RequestCardComponent,
    ReservationCardComponent,
    OwnerReservationsComponent
  ]
})
export class ReservationModule { }
