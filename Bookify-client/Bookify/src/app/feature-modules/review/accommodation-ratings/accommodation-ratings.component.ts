import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../../authentication/authentication.service';
import { RatingDTO } from '../model/rating.model.dto';

@Component({
  selector: 'app-accommodation-ratings',
  templateUrl: './accommodation-ratings.component.html',
  styleUrl: './accommodation-ratings.component.css'
})
export class AccommodationRatingsComponent implements OnInit, OnChanges {
  @Input() load: boolean;
  @Output() loadingChange = new EventEmitter<boolean>();
  accommodationId: number;

  oneStar: number;
  twoStars: number;
  threeStars: number;
  fourStars: number;
  fiveStars: number;
  sum: number;

  sumProgress: number;

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute, private authenticationService: AuthenticationService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.load) {
      this.getRating();
      this.loadingChange.emit(true);
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.accommodationId = +params['accommodationId'];
    });
    if(Number.isNaN(this.accommodationId)){
      this.accommodationId = this.authenticationService.getUserId();
    }
    if (!Number.isNaN(this.accommodationId)) {
      this.getRating();
    }
  }

  getRating(){
    this.reviewServise.getAccommodationRating(this.accommodationId).subscribe({
      next: (rating: RatingDTO) => {
        this.oneStar = rating.oneStars;
        this.twoStars = rating.twoStars;
        this.threeStars = rating.threeStars;
        this.fourStars = rating.fourStars;
        this.fiveStars = rating.fiveStars;
        this.sum = this.oneStar + this.twoStars + this.threeStars + this.fourStars + this.fiveStars;

        const temp = this.oneStar + 2 * this.twoStars + 3 * this.threeStars + 4 * this.fourStars + 5 * this.fiveStars;
        this.sumProgress = temp / this.sum;
        this.sumProgress = +this.sumProgress.toFixed(2);
        if(Number.isNaN(this.sumProgress)){
          this.sumProgress = 0;
        }
      }
    });
  }
}
