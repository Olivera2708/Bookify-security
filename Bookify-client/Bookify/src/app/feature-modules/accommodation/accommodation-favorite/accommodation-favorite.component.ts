import {Component, Input, OnInit} from '@angular/core';
import {ReservationDTO} from "../../reservation/model/ReservationDTO";
import {ReservationService} from "../../reservation/reservation.service";
import {AccommodationService} from "../accommodation.service";
import {AccommodationBasicModel} from "../model/accommodation-basic.model";

@Component({
  selector: 'app-accommodation-favorite',
  templateUrl: './accommodation-favorite.component.html',
  styleUrl: './accommodation-favorite.component.css'
})
export class AccommodationFavoriteComponent implements OnInit {
  @Input() accommodation: AccommodationBasicModel;
  image: string | ArrayBuffer | null;

  constructor(private accommodationService: AccommodationService) {}

  ngOnInit(): void {
    this.accommodationService.getImage(this.accommodation.imageId).subscribe({
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
