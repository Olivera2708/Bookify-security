import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../feature-modules/authentication/authentication.service";

@Component({
  selector: 'app-navigation-owner',
  templateUrl: './navigation-owner.component.html',
  styleUrl: './navigation-owner.component.css'
})
export class NavigationOwnerComponent implements OnInit{
  notificationNumber: number = 0
  constructor(private authService: AuthenticationService) {
  }
  ngOnInit(): void {
    this.authService.connectWithWebSocket();
    this.authService.getNotificationNumber().subscribe({
      next: value => this.notificationNumber = value
    });
  }

}
