import {Component, inject, OnInit} from '@angular/core';
import {MatSnackBarAction, MatSnackBarActions, MatSnackBarLabel, MatSnackBarRef} from "@angular/material/snack-bar";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-default-snackbar',
  templateUrl: './default-snackbar.component.html',
  styleUrl: './default-snackbar.component.css',
  imports: [
    MatSnackBarLabel,
    MatSnackBarActions,
    MatSnackBarAction,
    MatButtonModule
  ],
  standalone: true
})
export class DefaultSnackbarComponent implements OnInit {

  snackBarRef = inject(MatSnackBarRef);
  message: string | undefined = ' ';
  constructor() {
  }

  ngOnInit(): void {
    this.message = this.snackBarRef.containerInstance.snackBarConfig.announcementMessage;
  }

  buttonAction():void {
    const action = this.snackBarRef.containerInstance.snackBarConfig.data['action'];
    if(action){
      action();
    }
    this.snackBarRef.dismissWithAction();
  }
}
