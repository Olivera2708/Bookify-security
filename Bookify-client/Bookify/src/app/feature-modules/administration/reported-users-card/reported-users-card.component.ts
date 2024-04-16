import {Component, Input, OnInit} from '@angular/core';
import {ReportedUser} from "../model/reported.user";
import {AdministrationService} from "../administration.service";
import {MatDialog} from "@angular/material/dialog";
import {MessageDialogComponent} from "../../../layout/message-dialog/message-dialog.component";

@Component({
  selector: 'app-reported-users-card',
  templateUrl: './reported-users-card.component.html',
  styleUrl: './reported-users-card.component.css'
})
export class ReportedUsersCardComponent implements OnInit {
  @Input()
  report: ReportedUser

  constructor(private adminService: AdministrationService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    console.log(this.report);
  }

  block(): void {
    this.adminService.blockUser(this.report.reportedUser.id).subscribe({
      next: (response) => {
        if (response) {
          this.openDialog("User " + response.firstName + " " + response.lastName + " has been blocked");
        }
      },
      error: err => {
        console.log(err);
        this.openDialog(err.error);
      }
    })
  }

  openDialog(message: string): void {
    this.dialog.open(MessageDialogComponent, {data: {message: message}});
  }
}
