import {Component, OnInit} from '@angular/core';
import {ReservationService} from "../reservation.service";
import {ReservationGuestViewDTO} from "../model/ReservationGuestViewDTO";
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
  selector: 'app-guest-reservations',
  templateUrl: './guest-reservations.component.html',
  styleUrl: './guest-reservations.component.css'
})
export class GuestReservationsComponent implements OnInit {
  reservations: ReservationGuestViewDTO[]

  constructor(private reservationService: ReservationService, private authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.reservationService.getGuestReservations(this.authService.getUserId()).subscribe({
      next: (reservations: ReservationGuestViewDTO[]) => {
        this.reservations = reservations;
      },
    });
  }
}
