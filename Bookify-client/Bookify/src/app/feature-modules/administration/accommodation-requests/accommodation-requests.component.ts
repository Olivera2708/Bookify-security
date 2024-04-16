import {Component, OnInit} from '@angular/core';
import {AccommodationRequests} from "../model/accommodation.requests";
import {AdministrationService} from "../administration.service";

@Component({
  selector: 'app-accommodation-requests',
  templateUrl: './accommodation-requests.component.html',
  styleUrl: './accommodation-requests.component.css'
})
export class AccommodationRequestsComponent implements OnInit{
  requests: AccommodationRequests[] = []

  constructor(private adminService: AdministrationService) {
  }

  ngOnInit(): void {
    this.adminService.getAccommodationRequests().subscribe({
      next: (data: AccommodationRequests[]) => {
        this.requests = data;
      },
      error: err => {}
    })
  }


}
