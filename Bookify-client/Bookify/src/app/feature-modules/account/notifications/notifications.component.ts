import {Component, OnInit} from '@angular/core';
import {Message} from "../model/message";
import {NotificationService} from "../notification.service";
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements OnInit{
  notifications: Message[] = []
  role: string = '';
  constructor(private notificationService: NotificationService,
              private authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.notificationService.getAllNotifications(this.authService.getUserId()).subscribe({
      next: (notifications: Message[]) => {
        this.notifications = notifications;
        this.notificationService.messages = [];
        this.notificationService.notificationNumber$.next(0);
      },
      error: err => {}
    });
    this.role = this.authService.getRole();
  }
}
