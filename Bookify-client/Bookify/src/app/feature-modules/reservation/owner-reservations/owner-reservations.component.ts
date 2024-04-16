import {Component, OnInit, ViewChild} from '@angular/core';
import {FilterReservationsComponent} from "../filter-reservations/filter-reservations.component";
import {ReservationDTO} from "../model/ReservationDTO";
import {ReservationService} from "../reservation.service";
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
  selector: 'app-owner-reservations',
  templateUrl: './owner-reservations.component.html',
  styleUrl: './owner-reservations.component.css'
})
export class OwnerReservationsComponent implements OnInit{
  @ViewChild(FilterReservationsComponent) filterReservationComponent: FilterReservationsComponent;
  requests: ReservationDTO[];
  constructor(private reservationService: ReservationService, private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.getAccommodations();
    this.getResults();
  }

  filterPress(values: { accommodationId: number; dateBegin: Date, dateEnd: Date, statuses: string[]}){
    this.reservationService.getFilteredRequestsForOwner(this.authenticationService.getUserId(), values.accommodationId, values.dateBegin, values.dateEnd, values.statuses).subscribe({
      next: (data) => {
        this.requests = data;
      }
    })
  }

  getAccommodations(){
    this.reservationService.getAccommodationMapForOwner(this.authenticationService.getUserId()).subscribe({
      next: (data) => {
        this.filterReservationComponent.accommodations = data;
      }
    })
  }

  getResults() {
    this.reservationService.getAllRequestsForOwner(this.authenticationService.getUserId()).subscribe({
      next: (data) => {
        this.requests = data;
      }
    })
  }

}
