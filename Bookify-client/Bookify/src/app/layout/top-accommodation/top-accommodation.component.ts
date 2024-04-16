import {Component, Input, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../../feature-modules/accommodation/model/accommodation-basic.model";
import {AccommodationService} from "../../feature-modules/accommodation/accommodation.service";

@Component({
  selector: 'app-top-accommodation',
  templateUrl: './top-accommodation.component.html',
  styleUrl: './top-accommodation.component.css'
})
export class TopAccommodationComponent implements OnInit {
  @Input() accommodation: AccommodationBasicModel
  image: string | ArrayBuffer | null

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
