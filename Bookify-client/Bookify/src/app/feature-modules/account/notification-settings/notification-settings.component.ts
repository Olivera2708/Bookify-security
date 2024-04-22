import {Component, Input, OnInit} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {Settings} from "../model/settings";
import {NotificationService} from "../notification.service";

@Component({
  selector: 'app-notification-settings',
  templateUrl: './notification-settings.component.html',
  styleUrl: './notification-settings.component.css'
})
export class NotificationSettingsComponent implements OnInit {

  role: string = '';
  settings: Settings = {notificationType:{}}

  constructor(private authService: AuthenticationService,
              private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.role = this.authService.getRole();

    if (this.role === 'OWNER') {
      this.settings.notificationType.NEW_ACCOMMODATION_RATING = true;
      this.settings.notificationType.NEW_USER_RATING = true;
      this.settings.notificationType.RESERVATION_CREATED = true;
      this.settings.notificationType.RESERVATION_CANCELED = true;
    } else if (this.role === 'GUEST') {
      this.settings.notificationType.RESERVATION_RESPONSE = true;
    }
    this.notificationService.getNotificationSettings(this.authService.getUserId()).subscribe({
      next: (loadedSettings: Settings): void => {
        this.settings = loadedSettings;
      }, error: err => {

      }
    });
  }

  applyChanges(): void {
    this.notificationService.setNotificationSettings(this.authService.getUserId(), this.settings).subscribe({
      next: (value: Settings) => {
        console.log(value);
      },
      error: err => {}
    });
  }
}
