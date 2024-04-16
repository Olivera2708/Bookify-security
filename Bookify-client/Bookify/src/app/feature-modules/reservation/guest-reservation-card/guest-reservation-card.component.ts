import {Component, Input, OnInit} from '@angular/core';
import {ReservationService} from "../reservation.service";
import {AccommodationService} from "../../accommodation/accommodation.service";
import {ReservationGuestViewDTO} from "../model/ReservationGuestViewDTO";
import {MessageDialogComponent} from "../../../layout/message-dialog/message-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-guest-reservation-card',
  templateUrl: './guest-reservation-card.component.html',
  styleUrl: './guest-reservation-card.component.css'
})
export class GuestReservationCardComponent implements OnInit {
  @Input() reservation: ReservationGuestViewDTO;
  image: string | ArrayBuffer | null;

  constructor(private reservationService: ReservationService, private accommodationService: AccommodationService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.accommodationService.getImage(this.reservation.imageId).subscribe({
      next: (data) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.image = reader.result;
        }
        reader.readAsDataURL(data);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  cancelReservation(): void {
    this.reservationService.cancelGuestReservation(this.reservation.id).subscribe({
      next: (data: ReservationGuestViewDTO) =>{
        this.reservation = data;
      },
      error: err => {
        this.openDialog(err.error);
      }
    });
  }
  openDialog(message: string): void {
    this.dialog.open(MessageDialogComponent, {data: {message: message}});
  }
}
