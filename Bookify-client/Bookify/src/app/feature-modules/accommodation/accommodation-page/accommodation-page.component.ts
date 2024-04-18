import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { AccommodationService } from "../accommodation.service";
import { AccommodationDetailsDTO } from "../model/accommodation-details.dto.model";
import { AccountService } from "../../account/account.service";
import { MapComponent } from "../../../shared/map/map.component";
import { CarouselComponent } from "../carousel/carousel.component";
import { AuthenticationService } from "../../authentication/authentication.service";
import { ReservationDialogComponent } from "../../../layout/reservation-dialog/reservation-dialog.component";
import { MatDialog } from "@angular/material/dialog";
import { ReserveComponent } from "../reserve/reserve.component";
import { MessageDialogComponent } from "../../../layout/message-dialog/message-dialog.component";
import { ReservationRequestDTO } from "../model/reservation-request.dto.model";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-accommodation-page',
  templateUrl: './accommodation-page.component.html',
  styleUrl: './accommodation-page.component.css'
})
export class AccommodationPageComponent implements OnInit {
  amenitiesMap: Map<string, string> = new Map([
    ['Free wifi', 'wifi'],
    ['Free parking', 'local_parking'],
    ['Terrace', 'balcony'],
    ['Breakfast', 'breakfast_dining'],
    ['Deposit box', 'local_atm'],
    ['Jacuzzi', 'hot_tub'],
    ['Wheelchair', 'accessible'],
    ['Non smoking', 'smoke_free'],
    ['Air conditioning', 'ac_unit'],
    ['Swimming pool', 'pool'],
    ['Bar', 'local_bar'],
    ['Sauna', 'sauna'],
    ['Luggage storage', 'luggage'],
    ['Lunch', 'lunch_dining'],
    ['Airport shuttle', 'airport_shuttle'],
    ['Family rooms', 'family_restroom'],
    ['Garden', 'yard'],
    ['Front desk', 'concierge'],
    ['Heating', 'heat'],
    ['Diner', 'dining'],
    ['Private bathroom', 'shower'],
    ['City center', 'location_city']
  ]);

  accommodation: AccommodationDetailsDTO;
  ownerImage: string | ArrayBuffer | null = null;
  accommodationImages: string[] = [];
  amenitiesList: [string, string][] = [];
  isFavorite: boolean = false;
  @ViewChild(MapComponent) mapComponent: MapComponent;
  @ViewChild(CarouselComponent) carouselComponent: CarouselComponent;
  @ViewChild(ReserveComponent) reservationComponent: ReserveComponent;

  role: string;

  constructor(public dialog: MatDialog, protected authenticationService: AuthenticationService, private route: ActivatedRoute,
    private accommodationService: AccommodationService, private accountService: AccountService, private _snackBar: MatSnackBar) {
    this.role = authenticationService.getRole();
  }

  ngOnInit(): void {
    window.scrollTo({
      top: 0,
      behavior: "instant"
    });
    this.getAccommodationData();
    this.setIfUser();
  }

  private setIfUser() {
    const reserve = document.getElementById("reserve-comp");
    const favorite = document.getElementById("favorite-button");
    if (reserve != null && favorite != null) {
      console.log(this.authenticationService.getRole());
      if (this.authenticationService.getRole() == "GUEST") {
        reserve.style.display = 'block';
        favorite.style.visibility = 'visible';
      }
    }
  }

  private getAccommodationData() {
    this.route.params.subscribe((params) => {
      const id = +params['accommodationId'];
      this.accommodationService.getAccommodationDetails(id).subscribe({
        next: (data) => {
          this.accommodation = data;
          this.changeDisplay();
          this.setAmenities();
          if (this.accommodation.owner.imageId != 0)
            this.getOwnerPhoto(this.accommodation.owner.imageId);
          else
            this.ownerImage = "../../assets/images/user.jpg";
          this.getAccommodationPhotos(id);
          const address = this.accommodation.address.address + ", " + this.accommodation.address.city + ", " + this.accommodation.address.zipCode + ", " + this.accommodation.address.country;
          this.mapComponent.search(address);
          this.accommodationService.checkIfInFavorites(this.authenticationService.getUserId(), this.accommodation.id).subscribe({
            next: (data) => {
              this.isFavorite = data;
            }
          })
        }
      })
    });
  }

  getAccommodationPhotos(id: number) {
    this.accommodationService.getAccommodationImages(id).subscribe({
      next: (data): void => {
        for (const b of data) {
          this.accommodationImages.push("data:image/*;base64," + b);
        }
        this.carouselComponent.images = this.accommodationImages;
      }
    });
  }

  getOwnerPhoto(id: number) {
    this.accountService.getAccountImage(this.accommodation.owner.imageId).subscribe({
      next: (data: Blob): void => {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.ownerImage = reader.result;
        }
        reader.readAsDataURL(data);
      },
      error: err => {
        console.error(err);
      }
    });
  }

  setAmenities() {
    if (this.accommodation.filters != null) {
      for (const filter of this.accommodation.filters) {
        let label = this.transformLabel(filter);
        this.amenitiesList.push([label, this.amenitiesMap.get(label) ?? '']);
      }
    }
  }

  transformLabel(label: string): string {
    label = label.replace("_", " ");
    return label.charAt(0) + label.slice(1).toLowerCase();
  }

  changeDisplay() {
    if (this.accommodation.avgRating == 0) {
      const el = document.getElementById("accRating");
      if (el != null)
        el.style.display = 'none';
    }
    if (this.accommodation.owner?.avgRating == 0) {
      const el1 = document.getElementById("ownerRating");
      if (el1 != null)
        el1.style.display = 'none';
    }
  }

  reservePressed(values: { persons: number, dateBegin: string, dateEnd: string }): void {
    this.openDialog(this.accommodation.id, new Date(Date.parse(values.dateBegin)), new Date(Date.parse(values.dateEnd)), values.persons, this.accommodation.pricePer);
  }

  openDialog(id: number, begin: Date, end: Date, persons: number, pricePer: string): void {
    this.accommodationService.getTotalPrice(id, begin, end, pricePer, persons).subscribe({
      next: (data): void => {
        if (data == -1 || begin < new Date())
          this.dialog.open(MessageDialogComponent, { data: { message: "Accommodation it not available for this parameters." } });
        else {
          this.dialog.open(ReservationDialogComponent, { data: { message: "Total cost for this reservation is " + Math.round(data * 100) / 100 + " EUR." } }).afterClosed().subscribe((result) => {
            if (result) {
              let reservation: ReservationRequestDTO = {
                created: new Date(),
                start: begin,
                end: end,
                guestNumber: persons,
                price: Math.round(data * 100) / 100
              };
              this.accommodationService.createReservationRequest(reservation, id, this.authenticationService.getUserId()).subscribe({
                next: (data): void => {
                }
              })
            }
          })
        }
      }
    });
  }

  addToFavorites() {
    if (!this.isFavorite) {
      this.accommodationService.addToFavorites(this.authenticationService.getUserId(), this.accommodation.id).subscribe({
        next: (data) => {
          this.isFavorite = !this.isFavorite;
          this.openSnackBar("Added to favorites", "Close");
        }
      })
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }
}
