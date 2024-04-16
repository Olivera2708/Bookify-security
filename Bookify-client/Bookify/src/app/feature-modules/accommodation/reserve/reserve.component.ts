import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {DatapickerRangeComponent} from "../../../layout/datapicker-range/datapicker-range.component";
import {MessageDialogComponent} from "../../../layout/message-dialog/message-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ReservationDialogComponent} from "../../../layout/reservation-dialog/reservation-dialog.component";
import {AccommodationService} from "../accommodation.service";

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrl: './reserve.component.css'
})
export class ReserveComponent {
  @ViewChild(DatapickerRangeComponent) dateComponent: DatapickerRangeComponent;
  @Output() buttonPressed= new EventEmitter<{persons: number, dateBegin: string, dateEnd: string}>();
  persons: number = 2;

  constructor(public dialog: MatDialog){}

  onReservePress(): void {
    const values = {
      persons: this.persons,
      dateBegin: this.convertDate(this.dateComponent.dateBegin),
      dateEnd: this.convertDate(this.dateComponent.dateEnd)
    };
    this.buttonPressed.emit(values);
  }

  private convertDate (date: string): string {
    let el = date.split(".");
    return el[1] + "-" + el[0] + "-" + el[2];
  }
}
