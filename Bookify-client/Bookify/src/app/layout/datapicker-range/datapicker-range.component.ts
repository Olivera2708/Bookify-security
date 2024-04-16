import {Component, inject} from '@angular/core';
import {
  NgbCalendar,
  NgbDate,
  NgbDateParserFormatter,
  NgbDatepickerModule
} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-datapicker-range',
  templateUrl: './datapicker-range.component.html',
  styleUrl: './datapicker-range.component.css',
  standalone: true,
  imports: [NgbDatepickerModule, FormsModule, JsonPipe],
  providers: [DatePipe]
})
export class DatapickerRangeComponent {
  calendar = inject(NgbCalendar);
  formatter = inject(NgbDateParserFormatter);
  private ngbDateParserFormatter: any;

  hoveredDate: NgbDate | null = null;
  fromDate: NgbDate | null = this.calendar.getNext(this.calendar.getToday(), 'd', 1);
  toDate: NgbDate | null = this.calendar.getNext(this.calendar.getToday(), 'd', 6);

  dateBegin: string = this.calendar.getNext(this.calendar.getToday(), 'd', 1).day + "." + this.calendar.getNext(this.calendar.getToday(), 'd', 1).month + "." + this.calendar.getNext(this.calendar.getToday(), 'd', 1).year;
  dateEnd: string = this.calendar.getNext(this.calendar.getToday(), 'd', 6).day + "." + this.calendar.getNext(this.calendar.getToday(), 'd', 6).month + "." + this.calendar.getNext(this.calendar.getToday(), 'd', 6).year;

  public setDate(begin: string, end: string){
    this.dateBegin = begin;
    this.dateEnd = end;
    this.fromDate = this.parseDateString(begin);
    this.toDate = this.parseDateString(end);
  }

  private parseDateString(dateString: string): NgbDate {
    const dateStruct: string[] = dateString.split(".")
    return new NgbDate(Number(dateStruct[2]), Number(dateStruct[1]), Number(dateStruct[0]));
  }


  constructor(private datePipe: DatePipe) {}

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
      this.dateBegin = this.fromDate.day + "." + this.fromDate.month + "." + this.fromDate.year;
    } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
      this.toDate = date;
      this.dateEnd = this.toDate.day + "." + this.toDate.month + "." + this.toDate.year;
    } else {
      this.toDate = null;
      this.fromDate = date;
      this.dateEnd = "";
      this.dateBegin = this.fromDate.day + "." + this.fromDate.month + "." + this.fromDate.year;
    }
  }

  isHovered(date: NgbDate) {
    return (
      this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate)
    );
  }

  isInside(date: NgbDate) {
    return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return (
      date.equals(this.fromDate) ||
      (this.toDate && date.equals(this.toDate)) ||
      this.isInside(date) ||
      this.isHovered(date)
    );
  }

  validateInput(currentValue: NgbDate | null, input: string): NgbDate | null {
    const parsed = this.formatter.parse(input);
    return parsed && this.calendar.isValid(NgbDate.from(parsed)) ? NgbDate.from(parsed) : currentValue;
  }

  protected readonly Date = Date;
}
