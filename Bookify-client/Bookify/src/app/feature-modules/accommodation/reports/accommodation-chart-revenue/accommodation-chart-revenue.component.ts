import {Component, OnInit} from '@angular/core';
import Chart from "chart.js/auto";
import {ChartsDTO} from "../../model/accommodation-charts.dto.model";

@Component({
  selector: 'app-accommodation-chart-revenue',
  templateUrl: './accommodation-chart-revenue.component.html',
  styleUrl: './accommodation-chart-revenue.component.css'
})
export class AccommodationChartRevenueComponent implements OnInit {
  public chart: any;
  data: ChartsDTO[];

  constructor() {}

  ngOnInit(): void {
    this.createChart();
  }

  createChart(){
    this.chart = new Chart("AccRevenue", {
      type: 'line', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: ['Jan', 'Feb', 'Mar','Apr',
          'May', 'Jun', 'Jul', 'Avg', 'Sep', 'Oct', 'Nov', 'Dec' ],
        datasets: [
          {
            label: "Revenue in EUR",
            data: [],
            backgroundColor: '#2e4057',
            borderColor: 'lightgrey',
            pointBackgroundColor: '#2e4057'
          }
        ]
      },
      options: {
        aspectRatio:2.5
      }

    });
  }

  updateChart(data: ChartsDTO[]) {
    this.data = data;
    this.chart.destroy();

    this.chart = new Chart("AccRevenue", {
      type: 'line', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: ['Jan', 'Feb', 'Mar','Apr',
          'May', 'Jun', 'Jul', 'Avg', 'Sep', 'Oct', 'Nov', 'Dec' ],
        datasets: [
          {
            label: "Revenue in EUR",
            data: this.getRevenue(),
            backgroundColor: '#2e4057',
            borderColor: 'lightgrey',
            pointBackgroundColor: '#2e4057'
          }
        ]
      },
      options: {
        aspectRatio:2.5
      }
    });
  }

  getRevenue() : number[]{
    let data: number[] = [];
    for (let c of this.data)
      data.push(c.profitOfAccommodation);
    return data;
  }
}
