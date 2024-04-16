import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccommodationAvailability } from '../../model/accommodation-availability.form.model';

@Component({
  selector: 'app-accommodation-availability',
  templateUrl: './accommodation-availability.component.html',
  styleUrl: './accommodation-availability.component.css'
})
export class AccommodationAvailabilityComponent implements OnChanges {
  @Output() availibilityChanged = new EventEmitter<AccommodationAvailability>();
  @Input() submitted: boolean = false;
  @Input() availabilityInfo: AccommodationAvailability | null = null;

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      cancellationDeadline: ['', Validators.required],
      pricePer: ['', Validators.required]
    })
    this.form.valueChanges.subscribe(value => {
      this.onAvailabilityChanged();
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.submitted) {
      this.form.markAllAsTouched();
    }
    if(this.availabilityInfo) {
      this.form.get('cancellationDeadline')?.setValue(this.availabilityInfo.cancellationDeadline);
      this.form.get('pricePer')?.setValue(this.availabilityInfo.pricePer);
    }
  }

  onAvailabilityChanged() {
    this.availibilityChanged.emit({
      cancellationDeadline: this.form.get('cancellationDeadline')?.value,
      pricePer: this.form.get('pricePer')?.value
    });
  }
}
