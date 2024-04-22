import {Component, OnInit} from '@angular/core';
import {Review} from "../model/review";
import {AdministrationService} from "../administration.service";

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrl: './feedback.component.css'
})
export class FeedbackComponent implements OnInit {

  reportedReviews: Review[] = []
  createdReviews: Review[] = []

  constructor(private adminService: AdministrationService) {
  }

  ngOnInit(): void {
    this.adminService.getAllCreatedReviews().subscribe({
      next: (createdReviews: Review[]): void =>{
        this.createdReviews = createdReviews;
      }
    });
    this.adminService.getAllReportedReviews().subscribe({
      next: (reportedReviews: Review[]): void => {
        this.reportedReviews = reportedReviews;
      }
    });
  }

  onRemovedReview(review: Review): void {
      const index: number = this.createdReviews.indexOf(review);
      this.createdReviews.splice(index,1);
  }

  onRemovedReportedReview(review: Review): void {
    const index: number = this.reportedReviews.indexOf(review);
    this.reportedReviews.splice(index,1);

  }

}
