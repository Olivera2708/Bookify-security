import {Component, Input, OnInit} from '@angular/core';
import {Message} from "../model/message";

@Component({
  selector: 'app-notification-card',
  templateUrl: './notification-card.component.html',
  styleUrl: './notification-card.component.css'
})
export class NotificationCardComponent implements OnInit{
  @Input()
  notification: Message
  iconType: String = ""
  constructor() {
  }

  ngOnInit(): void {
    if (this.notification.notificationType === 'RESERVATION_CREATED') this.iconType='add';
    else if (this.notification.notificationType === 'RESERVATION_CANCELED') this.iconType='close';
    else if (this.notification.notificationType === 'NEW_USER_RATING') this.iconType='star';
    else if (this.notification.notificationType === 'NEW_ACCOMMODATION_RATING') this.iconType='star';
    else this.iconType='sms';
  }
}
