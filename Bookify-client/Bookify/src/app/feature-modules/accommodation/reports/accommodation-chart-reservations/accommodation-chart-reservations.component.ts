import {Component, OnInit} from '@angular/core';
import Chart from "chart.js/auto";
import {ChartsDTO} from "../../model/accommodation-charts.dto.model";

@Component({
  selector: 'app-accommodation-chart-reservations',
  templateUrl: './accommodation-chart-reservations.component.html',
  styleUrl: './accommodation-chart-reservations.component.css'
})
export class AccommodationChartReservationsComponent implements OnInit {
  public chart: any;
  data: ChartsDTO[];

  constructor() {}

  ngOnInit(): void {
    this.createChart();
  }

  createChart(){
    this.chart = new Chart("AccReservations", {
      type: 'line', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: ['Jan', 'Feb', 'Mar','Apr',
          'May', 'Jun', 'Jul', 'Avg', 'Sep', 'Oct', 'Nov', 'Dec' ],
        datasets: [
          {
            label: "Days reserved",
            data: [],
            backgroundColor: '#326268',
            borderColor: '#D8EBDE',
            pointBackgroundColor: '#326268'
          }
        ]
      },
      options: {
        aspectRatio:2.5
      }

    });
  }

  updateChart(data: ChartsDTO[]){
    this.data = data;
    this.chart.destroy();

    this.chart = new Chart("AccReservations", {
      type: 'line', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: ['Jan', 'Feb', 'Mar','Apr',
          'May', 'Jun', 'Jul', 'Avg', 'Sep', 'Oct', 'Nov', 'Dec' ],
        datasets: [
          {
            label: "Days reserved",
            data: this.getReservations(),
            backgroundColor: '#326268',
            borderColor: '#D8EBDE',
            pointBackgroundColor: '#326268'
          }
        ]
      },
      options: {
        aspectRatio:2.5
      }
    });
  }

  getReservations() : number[]{
    let data: number[] = [];
    for (let c of this.data)
      data.push(c.numberOfReservations);
    return data;
  }
}
