import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { AccommodationService } from '../../accommodation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Address } from '../../model/address.dto.model';
import { AccommodationDTO } from '../../model/accommodation.dto.model';
import { Accommodation } from '../../model/accommodation.model';
import { AccommodationBasicInformationComponent } from '../accommodation-basic-information/accommodation-basic-information.component';
import { MatSnackBar } from '@angular/material/snack-bar'
import { AccommodationBasicFormModel } from '../../model/accommodation-basic.form.model';
import { AccommodationGuestsFormModel } from '../../model/accommodation-guests.form.model';
import { ImagesDTO } from '../../model/images';
import { AccommodationAvailability } from '../../model/accommodation-availability.form.model';
import { AuthenticationService } from '../../../authentication/authentication.service';
import { ImageFileDTO } from '../../model/images.dto.model';

@Component({
  selector: 'app-accommodation-create',
  templateUrl: './accommodation-create.component.html',
  styleUrl: './accommodation-create.component.css'
})
export class AccommodationCreateComponent implements OnInit {
  @ViewChild(AccommodationBasicInformationComponent) basicInfo!: AccommodationBasicInformationComponent;

  amenitiesFilter: string[] = [];
  images: ImagesDTO[] = [];
  f: File[] = [];

  submitted: boolean = false;
  accommodationId: number;

  basicInfoForm: AccommodationBasicFormModel;
  basicInfoFormUpdate: AccommodationBasicFormModel;
  address: Address;
  addressUpdate: Address;
  gusetsInfo: AccommodationGuestsFormModel;
  gusetsInfoUpdate: AccommodationGuestsFormModel;
  availabilityInfo: AccommodationAvailability;
  availabilityInfoUpdate: AccommodationAvailability;

  constructor(private accommodationService: AccommodationService, private router: Router, private _snackBar: MatSnackBar, private route: ActivatedRoute, private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const accommodationId = +params['accommodationId'];
      this.accommodationId = accommodationId;
    });
    if (!Number.isNaN(this.accommodationId)) {
      this.accommodationService.getAccommodation(this.accommodationId).subscribe({
        next: (accommodation: Accommodation) => {
          this.basicInfoFormUpdate = {
            propertyName: accommodation.name,
            description: accommodation.description
          }
          this.addressUpdate = {
            country: accommodation.address.country,
            city: accommodation.address.city,
            address: accommodation.address.address,
            zipCode: accommodation.address.zipCode
          }
          this.amenitiesFilter = accommodation.filters;
          this.gusetsInfoUpdate = {
            type: accommodation.accommodationType,
            minGuests: accommodation.minGuest,
            maxGuests: accommodation.maxGuest,
            reservationAcceptance: accommodation.manual ? 'manual' : 'automatic'
          }
          this.availabilityInfoUpdate = {
            cancellationDeadline: accommodation.cancellationDeadline,
            pricePer: accommodation.pricePer
          }
          this.accommodationService.getImagesDTO(this.accommodationId).subscribe({
            next: (images: ImageFileDTO[]) => {
              images.forEach((image) => {
                const blob = new Blob([image.data], { type: 'application/octet-stream' })
                const imageDTO = {
                  url: "data:image/*;base64," + image.data,
                  file: null,
                  id: image.id
                };
                this.images.push(imageDTO);
              });
            }
          })

        }
      })
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  handleBasicInfoChange(data: AccommodationBasicFormModel) {
    this.basicInfoForm = data;
  }

  handleLocationChange(data: Address) {
    this.address = data;
  }

  handleAmenitiesChange(data: string[]) {
    this.amenitiesFilter = data.map(label => this.transformLabel(label));
  }

  transformLabel(label: string): string {
    if (label === '24-hour front desk') {
      return 'FRONT_DESK';
    }
    return label.toUpperCase().replace(/[^A-Z]/g, '_');
  }

  handlePhotosChange(data: ImagesDTO[]) {
    this.images = data;
  }

  handleGuestsChange(data: AccommodationGuestsFormModel) {
    this.gusetsInfo = data;
  }

  handleAvailabilityChange(data: AccommodationAvailability) {
    this.availabilityInfo = data;
  }

  onSubmit() {
    this.submitted = true;
    if (this.isValid()) {
      const addressDTO: Address = {
        country: this.address.country,
        city: this.address.city,
        address: this.address.address,
        zipCode: this.address.zipCode
      }
      const dto: AccommodationDTO = {
        name: this.basicInfoForm.propertyName,
        description: this.basicInfoForm.description,
        filters: [...new Set(this.amenitiesFilter)],
        accommodationType: this.gusetsInfo.type,
        minGuest: this.gusetsInfo.minGuests,
        maxGuest: this.gusetsInfo.maxGuests,
        manual: this.gusetsInfo.reservationAcceptance === 'manual',
        cancellationDeadline: this.availabilityInfo.cancellationDeadline,
        pricePer: this.availabilityInfo.pricePer,
        address: addressDTO
      };
      if (isNaN(this.accommodationId)) {
        const id: number = this.authenticationService.getUserId();
        this.accommodationService.add(id, dto).subscribe(
          {
            next: (data: Accommodation) => {
              this.images.forEach((elem) => {
                if (elem.file) {
                  this.f.push(elem.file);
                }
              })
              if (this.f.length > 0) {
                this.accommodationService.addImages(data.id, this.f).subscribe();
              }
              this.router.navigate(['/accommodation/calendar/', data.id]);
            },
            error: (_) => { }
          });
      } else {
        const accommodation: Accommodation = {
          id: this.accommodationId,
          name: this.basicInfoForm.propertyName,
          description: this.basicInfoForm.description,
          filters: [...new Set(this.amenitiesFilter)],
          accommodationType: this.gusetsInfo.type,
          minGuest: this.gusetsInfo.minGuests,
          maxGuest: this.gusetsInfo.maxGuests,
          manual: this.gusetsInfo.reservationAcceptance === 'manual',
          cancellationDeadline: this.availabilityInfo.cancellationDeadline,
          pricePer: this.availabilityInfo.pricePer,
          address: addressDTO
        };
        this.accommodationService.modify(accommodation).subscribe({
          next: (id: number) => {
            this.images.forEach((elem) => {
              if (elem.file) {
                this.f.push(elem.file);
              }
            })
            if (this.f.length > 0) {
              this.accommodationService.addImages(id, this.f).subscribe();
            }
            this.router.navigate(['/accommodation/calendar/', id]);
          },
          error: (e) => {
            this.openSnackBar(e.error, 'Close');
          }
        })
      }
    } else {
      this.openSnackBar('All field must be filled', 'Close');
    }
  }

  isValid(): boolean {
    return this.basicInfoForm && this.gusetsInfo && this.address && this.availabilityInfo && this.address.country !== '' && this.address.city !== '' && this.address.address !== ''
      && this.address.zipCode !== '' && this.basicInfoForm.propertyName !== '' && this.basicInfoForm.description !== '' &&
      this.gusetsInfo.type !== '' && this.gusetsInfo.reservationAcceptance !== '' && this.availabilityInfo.pricePer !== '' && this.images.length > 0 &&
      this.gusetsInfo.minGuests >= 0 && this.gusetsInfo.minGuests !== null && this.gusetsInfo.maxGuests >= 0 && this.gusetsInfo.maxGuests !== null &&
      this.availabilityInfo.cancellationDeadline >= 0 && this.availabilityInfo.cancellationDeadline !== null && this.gusetsInfo.minGuests <= this.gusetsInfo.maxGuests;
  }
}

async function fetchImageAsBlob(imageUrl: string): Promise<Blob> {
  const response = await fetch(imageUrl);
  const blob = await response.blob();
  return blob;
}

function createFileFromBlob(blob: Blob, fileName: string): File {
  return new File([blob], fileName, { type: blob.type });
}
