import {Component, Input, OnInit} from '@angular/core';
import Chart from 'chart.js/auto';
import {ChartsDTO} from "../../model/accommodation-charts.dto.model";
import _default from "chart.js/dist/plugins/plugin.legend";
import labels = _default.defaults.labels;

@Component({
  selector: 'app-overall-chart-reservations',
  templateUrl: './overall-chart-reservations.component.html',
  styleUrl: './overall-chart-reservations.component.css'
})
export class OverallChartReservationsComponent implements  OnInit{
  public chart: any;
  overall: ChartsDTO[];
  constructor() {}

  ngOnInit(): void {
    this.createPieChart();
  }

  createPieChart(){
    this.chart = new Chart("PieChart", {
      type: 'pie', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: [],
        datasets: [{
          label: 'Days reserved',
          data: [],
          hoverOffset: 4
        }],
      },
      options: {
        aspectRatio:2.5
      }

    });
  }

  updateChart(data: ChartsDTO[]){
    this.chart.destroy();
    this.overall = data;

    this.chart = new Chart("PieChart", {
      type: 'pie', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: this.getLabels(),
        datasets: [{
          label: 'Days reserved',
          data: this.getReservations(),
          hoverOffset: 4
        }],
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

  getReservations(): number[] {
    let data: number[] = [];
    for (let c of this.overall)
      data.push(c.numberOfReservations);
    return data;
  }
}
