import {Component, EventEmitter, Output} from '@angular/core';
import {FilterDTO} from "../../feature-modules/accommodation/model/filter.dto.model";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {
  minPrice: number = 0;
  maxPrice: number = 10000;
  minPossiblePrice: number = 0;
  maxPossiblePrice: number = 10000;
  accommodationCheckbox: string[] = ["Hotel", "Apartment", "Room"];
  checkboxLabels: string[] = [
    'Free WiFi', 'Air conditioning', 'Terrace', 'Swimming pool', 'Bar', 'Sauna', 'Luggage storage',
    'Lunch', 'Airport shuttle', 'Wheelchair', 'Non-smoking', 'Free parking',
    'Family rooms', 'Garden', '24-hour front desk', 'Jacuzzi', 'Heating',
    'Breakfast', 'Diner', 'Private bathroom', 'Deposit box', 'City center'
  ];

  @Output() buttonPressed= new EventEmitter<FilterDTO>();

  onButtonPress(): void {
    let filter: FilterDTO = {
      filters: this.getFilters(),
      types: this.getTypes(),
      minPrice: this.minPrice,
      maxPrice: this.maxPrice
    }
    this.buttonPressed.emit(filter);
  }

  getFilters() : string[] {
    let result: string[] = []
    for (const el of this.checkboxLabels){
      const c = document.getElementById("cbx-" + el) as HTMLInputElement;
      if (c.checked)
        result.push(this.transformLabel(el));
    }
    return result;
  }

  getTypes() : string[] {
    let result: string[] = []
    for (const el of this.accommodationCheckbox){
      const c = document.getElementById("cbx-" + el) as HTMLInputElement;
      if (c.checked)
        result.push(el.toUpperCase());
    }
    return result;
  }

  transformLabel(label: string): string {
    if (label === '24-hour front desk') {
      return 'FRONT_DESK';
    }
    return label.toUpperCase().replace(/[^A-Z]/g, '_');
  }

  transformBackLabel(label: string): string {
    if (label === 'FRONT_DESK') {
      return '24-hour front desk';
    }else if(label === 'FREE_WIFI'){
      return 'Free WiFi';
    }else if(label === 'NON_SMOKING'){
      return 'Non-smoking';
    }
    return [label.at(0), ...label.slice(1).toLowerCase()].join('').replace('_', ' ');
  }

  resetFilter(){
    for (const el of this.checkboxLabels){
      const c = document.getElementById("cbx-" + el) as HTMLInputElement;
      c.checked = false;
    }
    for (const el of this.accommodationCheckbox){
      const c = document.getElementById("cbx-" + el) as HTMLInputElement;
      c.checked = true;
    }
  }

  loadFilter(minPrice: number, maxPrice: number, minPossiblePrice: number, maxPossiblePrice: number, filter: FilterDTO){
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.minPossiblePrice = minPossiblePrice;
    this.maxPossiblePrice = maxPossiblePrice;

    for (const el of filter.filters){
      const c = document.getElementById("cbx-" + this.transformBackLabel(el)) as HTMLInputElement;
      c.checked = true;
    }
    for (const el of this.accommodationCheckbox){
      const c = document.getElementById("cbx-" + el) as HTMLInputElement;
      c.checked = false;
    }
    for (const el of filter.types){
      const c = document.getElementById("cbx-" + this.transformBackLabel(el)) as HTMLInputElement;
      c.checked = true;
    }
  }
}
