import { Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';

@Component({
  selector: 'app-accommodation-amenities',
  templateUrl: './accommodation-amenities.component.html',
  styleUrl: './accommodation-amenities.component.css'
})
export class AccommodationAmenitiesComponent implements OnChanges {
  @Output() amenitiesChange = new EventEmitter<string[]>();
  @Input() amenities: string[];

  checkboxLabels: string[] = [
    'Free WiFi', 'Air conditioning', 'Terrace', 'Swimming pool', 'Bar', 'Sauna', 'Luggage storage',
    'Lunch', 'Airport shuttle', 'Wheelchair', 'Non-smoking', 'Free parking',
    'Family rooms', 'Garden', '24-hour front desk', 'Jacuzzi', 'Heating',
    'Breakfast', 'Diner', 'Private bathroom', 'Deposit box', 'City center'
  ];
  checkedCheckboxes: string[] = [];

  constructor(private el: ElementRef) { }

  onCheckboxChange(label: string) {
    if (this.checkedCheckboxes.includes(label)) {
      this.checkedCheckboxes = this.checkedCheckboxes.filter(item => item !== label);
    } else {
      this.checkedCheckboxes.push(label);
    }
    this.amenitiesChange.emit(this.checkedCheckboxes);
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.amenities = this.amenities.map(elem => this.transformLabel(elem));
    Array.from(this.el.nativeElement.querySelectorAll('input')).forEach((element: any) => {
      this.amenities.forEach((amenity) => {
        if(amenity === element.name){
          element.checked = true;
          this.checkedCheckboxes.push(amenity);
        }
      });
    });
  }

  transformLabel(label: string): string {
    if (label === 'FRONT_DESK') {
      return '24-hour front desk';
    }else if(label === 'FREE_WIFI'){
      return 'Free WiFi';
    }else if(label === 'NON_SMOKING'){
      return 'Non-smoking';
    }
    return [label.at(0), ...label.slice(1).toLowerCase()].join('').replace('_', ' ');
  }

  trackByFn(index: number, item: string): number {
    return index;
  }
}
