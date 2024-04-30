import {Component, OnInit} from '@angular/core';
import {AccommodationService} from "../accommodation.service";
import {AuthenticationService} from "../../authentication/authentication.service";
import {AccommodationOwnerDtoModel} from "../model/accommodation.owner.dto.model";

@Component({
  selector: 'app-owner-accommodations',
  templateUrl: './owner-accommodations.component.html',
  styleUrl: './owner-accommodations.component.css'
})
export class OwnerAccommodationsComponent implements OnInit{
  accommodations: AccommodationOwnerDtoModel[];

  constructor(private accommodationService: AccommodationService,
              private authenticationService: AuthenticationService) {

  }

  ngOnInit(): void {
    this.accommodationService.getOwnerAccommodations(this.authenticationService.getUserId()).subscribe({
      next: (data: AccommodationOwnerDtoModel[]) => {
        this.accommodations = data;
      },
      error: err => {}
    });
  }

}
