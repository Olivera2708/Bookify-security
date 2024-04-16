import {Component, Input, OnInit} from '@angular/core';
import {ReservationDTO} from "../model/ReservationDTO";
import {ReservationService} from "../reservation.service";
import {AccommodationService} from "../../accommodation/accommodation.service";

@Component({
  selector: 'app-guest-request-card',
  templateUrl: './guest-request-card.component.html',
  styleUrl: './guest-request-card.component.css'
})
export class GuestRequestCardComponent implements OnInit{

  @Input() request: ReservationDTO;
  image: string | ArrayBuffer | null;
  visible: boolean = true;

  constructor(private reservationService: ReservationService, private accommodationService: AccommodationService) {
  }

  onDeletePressed(){
    this.reservationService.deleteRequest(this.request.id).subscribe({
      next: (data) => {
        this.visible = false;
    }
    });
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
}
