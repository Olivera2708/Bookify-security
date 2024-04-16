import {Component, OnInit} from '@angular/core';
import {AccommodationBasicModel} from "../model/accommodation-basic.model";
import {AccommodationService} from "../accommodation.service";
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
  selector: 'app-favorites-page',
  templateUrl: './favorites-page.component.html',
  styleUrl: './favorites-page.component.css'
})
export class FavoritesPageComponent implements OnInit {
  accommodations: AccommodationBasicModel[]

  constructor(private accommodationService: AccommodationService, private authenticationService: AuthenticationService) {}
  ngOnInit(): void {
    this.getResults()
  }

  getResults(){
    this.accommodationService.getFavorites(this.authenticationService.getUserId()).subscribe({
      next: (data) => {
        this.accommodations = data;
      }
    })
  }

}
