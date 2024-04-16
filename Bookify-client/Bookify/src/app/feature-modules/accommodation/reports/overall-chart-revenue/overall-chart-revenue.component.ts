import {Component, Input, OnInit} from '@angular/core';
import Chart from "chart.js/auto";
import {ChartsDTO} from "../../model/accommodation-charts.dto.model";

@Component({
  selector: 'app-overall-chart-revenue',
  templateUrl: './overall-chart-revenue.component.html',
  styleUrl: './overall-chart-revenue.component.css'
})

export class OverallChartRevenueComponent implements OnInit{
  public chart : any;
  overall: ChartsDTO[];
  constructor() {}
  ngOnInit(): void {
    this.createPieChart();
  }

  createPieChart(){
    this.chart = new Chart("BarChart", {
      type: 'bar', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: [],
        datasets: [
          {
            label: "Revenue in EUR",
            data: [],
            backgroundColor: '#326268'
          }
        ]
      },
      options: {
        aspectRatio:2.5
      }
    });
  }

  updateChart(data: ChartsDTO[]){
    this.overall = data;
    this.chart.destroy();

    this.chart = new Chart("BarChart", {
      type: 'bar', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: this.getLabels(),
        datasets: [
          {
            label: "Revenue in EUR",
            data: this.getRevenue(),
            backgroundColor: '#326268'
          }
        ]
      },
      options: {
        aspectRatio:2.5
      }
    });
  }

  getLabels(): string[] {
    let labels: string[] = [];
    for (let c of this.overall)
      labels.push(c.name);
    return labels;
  }

  getRevenue(): number[] {
    let data: number[] = [];
    for (let c of this.overall)
      data.push(c.profitOfAccommodation);
    return data;
  }
}
