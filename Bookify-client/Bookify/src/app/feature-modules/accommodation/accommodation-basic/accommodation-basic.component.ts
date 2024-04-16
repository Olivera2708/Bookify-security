import {Component, Input, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-accommodation-basic',
  templateUrl: './accommodation-basic.component.html',
  styleUrl: './accommodation-basic.component.css'
})
export class AccommodationBasicComponent implements OnInit{
  @Input() basicAccommodation: AccommodationBasicModel
  image: string | ArrayBuffer | null

  constructor(private accommodationService: AccommodationService) {}

  ngOnInit(): void {
    this.accommodationService.getImage(this.basicAccommodation.imageId).subscribe({
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
