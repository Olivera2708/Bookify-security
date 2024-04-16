import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {UserInformationComponent} from './user-information/user-information.component';
import {NotificationsComponent} from './notifications/notifications.component';
import {NotificationSettingsComponent} from './notification-settings/notification-settings.component';
import {MaterialModule} from "../../infrastructure/material/material.module";
import {AccountComponent} from "./account/account.component";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { PasswordChangeDialogComponent } from './password-change-dialog/password-change-dialog.component';
import { AccountDeleteDialogComponent } from './account-delete-dialog/account-delete-dialog.component';
import { ReviewModule } from '../review/review.module';
import { NotificationCardComponent } from './notification-card/notification-card.component';

@NgModule({
  declarations: [
    AccountComponent,
    UserInformationComponent,
    NotificationsComponent,
    NotificationSettingsComponent,
    PasswordChangeDialogComponent,
    AccountDeleteDialogComponent,
    NotificationCardComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    NgOptimizedImage,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    ReviewModule
  ],
  exports: [
    AccountComponent
  ]
})
export class AccountModule {
}
