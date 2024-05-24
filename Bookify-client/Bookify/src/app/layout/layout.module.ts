import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import {MaterialModule} from "../infrastructure/material/material.module";
import {AccountModule} from "../feature-modules/account/account.module";
import {RouterModule} from "@angular/router";
import { NavigationGuestComponent } from './navigation-guest/navigation-guest.component';
import { NavigationAdminComponent } from './navigation-admin/navigation-admin.component';
import { NavigationOwnerComponent } from './navigation-owner/navigation-owner.component';
import { MessageDialogComponent } from './message-dialog/message-dialog.component';
import { ReservationDialogComponent } from './reservation-dialog/reservation-dialog.component';
import {FilterComponent} from "./filter/filter.component";
import {FooterComponent} from "./footer/footer.component";
import {SearchComponent} from "./search/search.component";
import {FormsModule} from "@angular/forms";
import {DatapickerRangeComponent} from "./datapicker-range/datapicker-range.component";
import { NavigationSysadminComponent } from './navigation-sysadmin/navigation-sysadmin.component';


@NgModule({
  declarations: [
    NavigationBarComponent,
    NavigationGuestComponent,
    NavigationAdminComponent,
    NavigationOwnerComponent,
    MessageDialogComponent,
    ReservationDialogComponent,
    FilterComponent,
    FooterComponent,
    SearchComponent,
    NavigationSysadminComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    NgOptimizedImage,
    AccountModule,
    RouterModule,
    FormsModule,
    DatapickerRangeComponent
  ],
  exports: [
    NavigationBarComponent,
    DatapickerRangeComponent,
    FilterComponent,
    FooterComponent,
    SearchComponent
  ]
})
export class LayoutModule { }
