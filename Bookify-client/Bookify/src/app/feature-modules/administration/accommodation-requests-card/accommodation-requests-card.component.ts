import {Component, Input, OnInit} from '@angular/core';
import {AccommodationRequests} from "../model/accommodation.requests";
import {AccommodationService} from "../../accommodation/accommodation.service";
import {AdministrationService} from "../administration.service";

@Component({
    selector: 'app-accommodation-requests-card',
    templateUrl: './accommodation-requests-card.component.html',
    styleUrl: './accommodation-requests-card.component.css'
})
export class AccommodationRequestsCardComponent implements OnInit {
    @Input()
    request: AccommodationRequests;
    image: ArrayBuffer | string | null = null;
    visible: boolean = true;

    constructor(private accommodationService: AccommodationService,
                private adminService: AdministrationService) {
    }

    ngOnInit(): void {
        if (this.request.imageId) {
            this.accommodationService.getImage(this.request.imageId).subscribe({
                next: (imageData: Blob): void => {
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
    }

    OnApproveClick(): void {
        this.adminService.approveAccommodationRequest(this.request.id).subscribe({
            next: value => {
                console.log(value);
                this.visible = !this.visible;
            }
        })
    }

    OnRejectClick(): void {
        this.adminService.rejectAccommodationRequest(this.request.id).subscribe({
            next: value => {
                console.log(value);
                this.visible = !this.visible;
            }
        })
    }
}
