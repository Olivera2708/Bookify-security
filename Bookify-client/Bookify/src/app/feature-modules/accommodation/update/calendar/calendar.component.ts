import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AccommodationService } from '../../accommodation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PriceList } from '../../model/priceList.model';
import { PriceListDTO } from '../../model/priceList.dto.model';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent implements OnInit {
  currentMonth: number;
  currentMonthName: string;
  currentYear: number;
  daysInMonth: number[];
  currentDate: Date;
  currentTime: Date;
  dayNames: string[];
  emptyDays: null[];
  cena: number;
  priceForm!: FormGroup;
  dateTextMap: { [date: string]: string } = {};
  accommodationId: number;

  constructor(private fb: FormBuilder, private _snackBar: MatSnackBar, private service: AccommodationService, private router: Router, private route: ActivatedRoute) {
    this.priceForm = this.fb.group({
      price: ['', [Validators.required, Validators.min(0)]],
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  ngOnInit(): void {
    this.initializeCalendar();
    this.route.params.subscribe(params => {
      const accommodationId = +params['accommodationId'];
      this.accommodationId = accommodationId;
    });
    this.getPriceList();
  }

  getPriceList(): void {
    this.service.getAllPriceListItems(this.accommodationId).subscribe({
      next: (data: PriceList[]) => {
        data.forEach((element) => {
          this.addPrice(new Date(element.startDate), new Date(element.endDate), element.price);
        })
      },
      error: (e) => {
        this.openSnackBar(e.error, 'Close');
      }
    });
  }

  private initializeCalendar(): void {
    const currentDate = new Date();
    this.currentMonth = currentDate.getMonth();
    this.currentMonthName = this.getMonthName(this.currentMonth);
    this.currentYear = currentDate.getFullYear();
    this.daysInMonth = this.generateDaysInMonth(this.currentMonth, this.currentYear);
    this.currentDate = currentDate;
    this.currentTime = currentDate;
    this.dayNames = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    this.emptyDays = Array.from({ length: this.currentDate.getDay() }, (_, index) => null);
    this.updateCalendar();
  }

  selectedStartDate: Date | null = null;
  selectedEndDate: Date | null = null;

  changeMonth(delta: number): void {
    this.currentMonth += delta;
    if (this.currentMonth > 11) {
      this.currentMonth = 0;
      this.currentYear++;
    } else if (this.currentMonth < 0) {
      this.currentMonth = 11;
      this.currentYear--;
    }
    this.processSelectedRange();
    this.updateCalendar();
  }

  private getMonthName(monthIndex: number): string {
    const monthNames = [
      'January', 'February', 'March', 'April', 'May', 'June',
      'July', 'August', 'September', 'October', 'November', 'December'
    ];
    return monthNames[monthIndex];
  }

  private generateDaysInMonth(month: number, year: number): number[] {
    const daysInMonth = [];
    const lastDay = new Date(year, month + 1, 0).getDate();
    for (let i = 1; i <= lastDay; i++) {
      daysInMonth.push(i);
    }
    return daysInMonth;
  }

  onDateClick(day: number): void {
    const selectedDate = new Date(this.currentYear, this.currentMonth, day);
    const today = new Date();
    if (selectedDate < new Date(today.getFullYear(), today.getMonth(), today.getDate())) {
      this.openSnackBar("Cannot select a date in the past", "close");
      return;
    }
    if (!this.selectedStartDate) {
      this.resetColors();
      this.selectedStartDate = selectedDate;
      this.selectStartDate();
    } else if (!this.selectedEndDate) {
      this.selectedEndDate = selectedDate;
      if (this.selectedEndDate < this.selectedStartDate) {
        [this.selectedStartDate, this.selectedEndDate] = [this.selectedEndDate, this.selectedStartDate];
      }
      this.processSelectedRange();
    } else {
      this.clearSelectedDates();
      this.resetColors();
      this.selectedStartDate = selectedDate;
      this.selectStartDate();
    }
  }

  selectStartDate() {
    if (document) {
      const days = document.querySelectorAll('.calendar-days div');
      days.forEach((dayElement) => {
        if (dayElement.firstElementChild?.textContent && this.selectedStartDate) {
          const day = +dayElement.firstElementChild?.textContent;
          const currentDate = new Date(this.currentYear, this.currentMonth, day);
          if (currentDate >= this.selectedStartDate && currentDate <= this.selectedStartDate) {
            dayElement.classList.add('selected-range');
          }
        }
      });
    }
  }

  processSelectedRange(): void {
    if (document) {
      this.resetColors();
      const days = document.querySelectorAll('.calendar-days div');
      days.forEach((dayElement) => {
        if (dayElement.firstElementChild?.textContent && this.selectedEndDate && this.selectedStartDate) {
          const day = +dayElement.firstElementChild?.textContent;
          const currentDate = new Date(this.currentYear, this.currentMonth, day);
          if (currentDate >= this.selectedStartDate && currentDate <= this.selectedEndDate) {
            dayElement.classList.add('selected-range');
          }
          if (currentDate <= new Date()) {
            dayElement.classList.add('past-days');
          }
        }
      });
    }
  }

  resetColors(): void {
    if (document) {
      const selectedDays = document.querySelectorAll('.calendar-days div.selected-range');
      selectedDays.forEach((dayElement) => {
        dayElement.classList.remove('selected-range');
      });
    }
  }

  clearSelectedDates(): void {
    this.selectedStartDate = null;
    this.selectedEndDate = null;
  }

  changeYear(offset: number): void {
    this.currentYear += offset;
    this.daysInMonth = this.generateDaysInMonth(this.currentMonth, this.currentYear);
    this.processSelectedRange();
    this.updateCalendar();
  }

  private updateCalendar(): void {
    this.currentMonthName = this.getMonthName(this.currentMonth);
    this.daysInMonth = this.generateDaysInMonth(this.currentMonth, this.currentYear);
    const day = new Date(this.currentYear, this.currentMonth, 1);
    this.emptyDays = Array.from({ length: day.getDay() }, (_, index) => null);
  }

  add() {
    if (this.selectedStartDate) {
      const startDate = this.selectedStartDate;
      const priceControl = this.priceForm.get('price');
      if (!priceControl?.value || priceControl?.value <= 0) {
        this.openSnackBar("You must input correct price", "close");
        return;
      }
      if (this.selectedEndDate) {
        const endDate = this.selectedEndDate;
        const priceList: PriceListDTO = {
          startDate: new Date(startDate),
          endDate: new Date(endDate),
          price: priceControl?.value
        };
        this.service.addPriceList(this.accommodationId, priceList).subscribe({
          next: (_) => {
            this.getPriceList();
          },
          error: (e) => {
            this.openSnackBar(e.error, 'Close');
          }
        });
      } else {
        const endDate = this.selectedStartDate;
        const priceList: PriceListDTO = {
          startDate: new Date(startDate),
          endDate: new Date(endDate),
          price: priceControl?.value
        };
        this.service.addPriceList(this.accommodationId, priceList).subscribe({
          next: (_) => {
            this.getPriceList();
          },
          error: (e) => {
            this.openSnackBar(e.error, 'Close');
          }
        });
      }
    } else {
      this.openSnackBar("You must select a date(s)", "close");
    }
  }

  private addPrice(start: Date, end: Date, price: number) {
    let currentDate = new Date(start);
    while (currentDate <= end) {
      this.dateTextMap[currentDate.getFullYear() + "-" + currentDate.getMonth() + "-" + currentDate.getDate()] = price + "€";
      currentDate.setDate(currentDate.getDate() + 1);
    }
    this.resetColors();
    this.clearSelectedDates();
  }

  delete() {
    if (this.selectedStartDate) {
      if (this.selectedEndDate) {
        const priceList: PriceListDTO = {
          startDate: new Date(this.selectedStartDate),
          endDate: new Date(this.selectedEndDate),
          price: 0
        };
        const ss = this.selectedStartDate;
        const se = this.selectedEndDate;
        this.service.deletePriceListItem(this.accommodationId, priceList).subscribe({
          next: (_) => {
            this.deletePrice(ss, se);
            this.getPriceList();
          },
          error: (e) => {
            this.openSnackBar(e.error, 'Close');
          }
        });
      } else {
        const priceList: PriceListDTO = {
          startDate: new Date(this.selectedStartDate),
          endDate: new Date(this.selectedStartDate),
          price: 0
        };
        const ss = this.selectedStartDate;
        this.service.deletePriceListItem(this.accommodationId, priceList).subscribe({
          next: (_) => {
            this.deletePrice(ss, ss);
            this.getPriceList();
          },
          error: (e) => {
          this.openSnackBar(e.error, 'Close');
        }
        });
      }
    } else {
      this.openSnackBar("You must select a date(s)", "close");
    }
  }

  private deletePrice(start: Date, end: Date) {
    if (document) {
      let currentDate = new Date(start);
      while (currentDate <= end) {
        this.dateTextMap[currentDate.getFullYear() + "-" + currentDate.getMonth() + "-" + currentDate.getDate()] = "";
        currentDate.setDate(currentDate.getDate() + 1);
      }
      this.resetColors();
      this.clearSelectedDates();
    }
  }

  finish() {
    this.router.navigate(['/accommodations']);
  }
}
