import {Component, Input, OnInit} from "@angular/core";
import {ReservationDTO} from "../model/ReservationDTO";
import {ReservationService} from "../reservation.service";
import {AccommodationService} from "../../accommodation/accommodation.service";
import {MessageDialogComponent} from "../../../layout/message-dialog/message-dialog.component";
import {MatDialog} from "@angular/material/dialog";


@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.css']
})
export class ReservationCardComponent implements OnInit{

  @Input() request: ReservationDTO
  image: string | ArrayBuffer | null

  constructor(private reservationService: ReservationService,
              private accommodationService: AccommodationService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.accommodationService.getImage(this.request.imageId).subscribe({
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
  acceptReservation(): void {
    this.reservationService.acceptReservation(this.request.id).subscribe({
      next: (request: ReservationDTO) => {
        if(request) {
          this.request = request;
        }
      },
      error: err => {
        this.openDialog(err.error);
      }
    });
  }

  rejectReservation(): void {
    this.reservationService.rejectReservation(this.request.id).subscribe({
      next: (request: ReservationDTO) => {
        if(request) {
          this.request = request;
        }
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
