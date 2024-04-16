import {Component, OnInit, ViewChild} from '@angular/core';
import {AccommodationService} from "../../accommodation.service";
import {AuthenticationService} from "../../../authentication/authentication.service";
import {OverallChartReservationsComponent} from "../overall-chart-reservations/overall-chart-reservations.component";
import {OverallChartRevenueComponent} from "../overall-chart-revenue/overall-chart-revenue.component";
import {DatapickerRangeComponent} from "../../../../layout/datapicker-range/datapicker-range.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import { saveAs } from 'file-saver';
import {AccommodationChartRevenueComponent} from "../accommodation-chart-revenue/accommodation-chart-revenue.component";
import {AccommodationChartReservationsComponent} from "../accommodation-chart-reservations/accommodation-chart-reservations.component";

@Component({
  selector: 'app-reports-page',
  templateUrl: './reports-page.component.html',
  styleUrl: './reports-page.component.css'
})

export class ReportsPageComponent implements OnInit {
  @ViewChild(OverallChartReservationsComponent) overallReservations: OverallChartReservationsComponent;
  @ViewChild(OverallChartRevenueComponent) overallRevenue: OverallChartRevenueComponent;
  @ViewChild(DatapickerRangeComponent) dateComponent: DatapickerRangeComponent;
  @ViewChild(AccommodationChartRevenueComponent) chartRevenue: AccommodationChartRevenueComponent;
  @ViewChild(AccommodationChartReservationsComponent) chartReservations: AccommodationChartReservationsComponent;

  selectedAccommodation: number;
  accommodations: { [key: number]: string };
  selectedYear: number;
  years: number[] = [];

  constructor(private accommodationService: AccommodationService, private authenticationService: AuthenticationService, private _snackBar: MatSnackBar) {}
  ngOnInit(): void {
    this.setYears();
    this.setAccommodations();
  }

  showOverall(){
    this.accommodationService.getOverallCharts(this.authenticationService.getUserId(), this.dateComponent.dateBegin, this.dateComponent.dateEnd).subscribe({
      next: (data) => {
        if (data.length == 0)
          this.openSnackBar("No data for selected period", "Close");
        else {
          this.overallReservations.updateChart(data);
          this.overallRevenue.updateChart(data);
        }
      }
    });
  }

  setAccommodations(){
    this.accommodationService.getNamesForAccommodations(this.authenticationService.getUserId()).subscribe({
      next: (data) => {
        console.log(data);
        this.accommodations = data;
      }
    })
  }

  setYears(){
    let start_year = 2021;
    while (start_year <= new Date().getFullYear()) {
      this.years.push(start_year)
      start_year++;
    }
  }

  showForAccommodations(){
    if (this.selectedYear == undefined || this.selectedAccommodation == undefined)
      this.openSnackBar("No data for selected accommodation and year", "Close");
    else {
      this.accommodationService.getAccommodationCharts(this.authenticationService.getUserId(), this.selectedAccommodation, this.selectedYear).subscribe({
        next: (data) => {
          this.chartRevenue.updateChart(data);
          this.chartReservations.updateChart(data);
        }
      })
    }
  }

  downloadPdfForOverall(){
    this.accommodationService.generatePdfReportForOverall(this.authenticationService.getUserId(), this.dateComponent.dateBegin, this.dateComponent.dateEnd).subscribe((data: Blob) => {
      const blob = new Blob([data], { type: 'application/pdf' });
      saveAs(blob, 'report.pdf');
    });
  }

  downloadPdfForAccommodation(){
    this.accommodationService.generatePdfReportForAccommodation(this.authenticationService.getUserId(), this.selectedAccommodation, this.selectedYear).subscribe((data: Blob) => {
      const blob = new Blob([data], { type: 'application/pdf' });
      saveAs(blob, 'report.pdf');
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }
}
