import {Component, Output, EventEmitter, AfterViewInit, ViewChild} from '@angular/core';
import { DatapickerRangeComponent } from '../datapicker-range/datapicker-range.component';
import {NgbDate} from "@ng-bootstrap/ng-bootstrap";
import DOMPurify from 'dompurify';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements AfterViewInit {
  @ViewChild(DatapickerRangeComponent) dateComponent: DatapickerRangeComponent;
  public search: string = "";
  public persons: number = 2;

  @Output() buttonPressed= new EventEmitter<{ search: string; persons: number, dateBegin: string, dateEnd: string}>();

  onButtonPress(): void {
    const values = {
      search: "" + DOMPurify.sanitize(this.search),
      persons: this.persons,
      dateBegin: this.convertDate(this.dateComponent.dateBegin),
      dateEnd: this.convertDate(this.dateComponent.dateEnd)
    };
    this.buttonPressed.emit(values);
  }

  private convertDate (date: string): string{
    let el = date.split(".");
    return el[1] + "-" + el[0] + "-" + el[2];
  }

  ngAfterViewInit(): void {
  }
}
