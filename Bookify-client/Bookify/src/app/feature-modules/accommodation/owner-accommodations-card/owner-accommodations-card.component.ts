import {Component, Input, OnInit} from '@angular/core';
import {AccommodationService} from "../accommodation.service";
import {Router} from '@angular/router';
import {AccommodationOwnerDtoModel} from "../model/accommodation.owner.dto.model";

@Component({
  selector: 'app-owner-accommodations-card',
  templateUrl: './owner-accommodations-card.component.html',
  styleUrl: './owner-accommodations-card.component.css'
})
export class OwnerAccommodationsCardComponent implements OnInit {
  @Input()
  accommodation: AccommodationOwnerDtoModel;
  image: string | ArrayBuffer | null = null;

  constructor(private accommodationService: AccommodationService, private router: Router) {

  }

  ngOnInit(): void {
    this.accommodationService.getImage(this.accommodation.imageId).subscribe({
      next: (imageData: Blob) => {
        const reader: FileReader = new FileReader();
        reader.onloadend = () => {
          this.image = reader.result;
        }
        reader.readAsDataURL(imageData);
      },
      error: err => {
      }
    })
  }

  editAccommodation(): void {
    this.router.navigate(['/accommodation/modify/', this.accommodation.id]);
  }

  editPrice(): void {
    this.router.navigate(['/accommodation/calendar/', this.accommodation.id]);
  }
}
