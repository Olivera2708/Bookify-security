import {Component, OnInit, ViewChild} from '@angular/core';
import {ReservationService} from "../reservation.service";
import {AuthenticationService} from "../../authentication/authentication.service";
import {ReservationRequestDTO} from "../../accommodation/model/reservation-request.dto.model";
import {ReservationDTO} from "../model/ReservationDTO";
import {MatPaginator} from "@angular/material/paginator";
import {FilterReservationsComponent} from "../filter-reservations/filter-reservations.component";
import {FilterDTO} from "../../accommodation/model/filter.dto.model";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-guest-requests',
  templateUrl: './guest-requests.component.html',
  styleUrl: './guest-requests.component.css'
})
export class GuestRequestsComponent implements OnInit {
  @ViewChild(FilterReservationsComponent) filterReservationComponent: FilterReservationsComponent;
  requests: ReservationDTO[];
  constructor(private reservationService: ReservationService, private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.getAccommodations();
    this.getResults();
  }

  filterPress(values: { accommodationId: number; dateBegin: Date, dateEnd: Date, statuses: string[]}){
    this.reservationService.getFilteredRequestsForGuest(this.authenticationService.getUserId(), values.accommodationId, values.dateBegin, values.dateEnd, values.statuses).subscribe({
      next: (data) => {
        this.requests = data;
      }
    })
  }

  getAccommodations(){
    this.reservationService.getAccommodationMapForGuest(this.authenticationService.getUserId()).subscribe({
      next: (data) => {
        this.filterReservationComponent.accommodations = data;
      }
    })
  }

  getResults() {
    this.reservationService.getAllRequestsForGuest(this.authenticationService.getUserId()).subscribe({
      next: (data) => {
        this.requests = data;
      }
    })
  }
}
