import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Review} from "../model/review";
import {AccountService} from "../../account/account.service";
import {AdministrationService} from "../administration.service";

@Component({
  selector: 'app-review-reported-card',
  templateUrl: './review-reported-card.component.html',
  styleUrl: './review-reported-card.component.css'
})
export class ReviewReportedCardComponent implements OnInit{

  @Input()
  review: Review;
  image: string | ArrayBuffer | null = 'assets/images/user.jpg';

  @Output()
  clicked: EventEmitter<Review> = new EventEmitter<Review>();

  constructor(private accountService: AccountService,
              private adminService: AdministrationService) {
  }

  ngOnInit(): void {
    if (this.review.guest.imageId != undefined && this.review.guest.imageId > 0) {
      this.accountService.getAccountImage(this.review.guest.imageId).subscribe({
        next: (image: Blob) => {
          const reader: FileReader = new FileReader();
          reader.onloadend = () => {
            if (reader.result) {
              this.image = reader.result;
            }
          };
          reader.readAsDataURL(image);
        },
        error: err => {

        }
      });
    }
  }

  ignore(): void {
    this.adminService.ignoreReview(this.review.id).subscribe({
      next: (review: Review): void => {
        this.review = review;
      }, error: err => {
        console.log(err);
      }
    });
  }

  remove(): void {
    this.adminService.removeReview(this.review.id).subscribe({
      next: (review: Review): void => {
        this.clicked.emit(this.review);
        this.review = review;
      }, error: err => {
        console.log(err);
      }
    });
  }
}
