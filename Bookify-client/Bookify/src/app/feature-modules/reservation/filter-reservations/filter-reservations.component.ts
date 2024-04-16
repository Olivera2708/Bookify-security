import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {DatapickerRangeComponent} from "../../../layout/datapicker-range/datapicker-range.component";
import {NgbDate} from "@ng-bootstrap/ng-bootstrap";
import {ReservationDTO} from "../model/ReservationDTO";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-filter-reservations',
  templateUrl: './filter-reservations.component.html',
  styleUrl: './filter-reservations.component.css'
})
export class FilterReservationsComponent {
  @ViewChild(DatapickerRangeComponent) dateComponent: DatapickerRangeComponent;
  selectedAccommodation: number;
  accommodations: any[];
  statuses = ["PENDING", "ACCEPTED", "REJECTED", "CANCELED"]

  constructor(private _snackBar: MatSnackBar) {}

  @Output() buttonPressed= new EventEmitter<{ accommodationId: number; dateBegin: Date, dateEnd: Date, statuses: string[] }>();

  onFilterPress(): void {
    let from = this.dateComponent.fromDate;
    let to = this.dateComponent.toDate;

    if (this.selectedAccommodation == undefined){
      this.openSnackBar("Please select accommodation", "Close");
    }
    else {
      if (from != null && to != null) {
        const values = {
          accommodationId: this.selectedAccommodation,
          dateBegin: new Date(from.year, from.month - 1, from.day),
          dateEnd: new Date(to.year, to.month - 1, to.day),
          statuses: this.getStatuses()
        };
        this.buttonPressed.emit(values);
      }
    }
  }

  getStatuses() : string[] {
    let result: string[] = []
    for (const el of this.statuses){
      const c = document.getElementById("cbx-" + el.toLowerCase()) as HTMLInputElement;
      if (c.checked)
        result.push(el.toUpperCase());
    }
    return result;
  }

  private convertDate (date: string): string{
    let el = date.split(".");
    return el[1] + "-" + el[0] + "-" + el[2];
  }

  onAccommodationChange(){

  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }
}
