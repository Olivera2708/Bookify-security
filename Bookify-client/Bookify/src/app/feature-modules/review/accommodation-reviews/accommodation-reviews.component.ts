import { ChangeDetectorRef, Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommentDTO } from '../model/comment.model.dto';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-accommodation-reviews',
  templateUrl: './accommodation-reviews.component.html',
  styleUrl: './accommodation-reviews.component.css'
})
export class AccommodationReviewsComponent implements OnChanges {
  @Input() load : boolean;
  @Input() ownerId: number | undefined;
  accommodationId: number;
  comments: CommentDTO[];
  loading: boolean = false;

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute, private cdr: ChangeDetectorRef,
     private authenticationService: AuthenticationService) { }
 
  ngOnChanges(changes: SimpleChanges): void {
    if(this.load){
      this.reviewServise.getAccommodationComments(this.accommodationId).subscribe({
        next: (comments: CommentDTO[]) => {
          this.comments = comments;
        }
      });
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.accommodationId = +params['accommodationId'];
    });
    if (!Number.isNaN(this.accommodationId)) {
      this.reviewServise.getAccommodationComments(this.accommodationId).subscribe({
        next: (comments: CommentDTO[]) => {
          this.comments = comments;
        }
      });
    }
  }

  onEmitChange(data: boolean): void {
    this.reviewServise.getAccommodationComments(this.accommodationId).subscribe({
      next: (comments: CommentDTO[]) => {
        this.comments = comments;
        this.loading = true;
        this.load = false;
        this.cdr.detectChanges();
      }
    });
  }

  onLoadingChange(data: boolean): void {
    this.loading = false; 
  }
}
