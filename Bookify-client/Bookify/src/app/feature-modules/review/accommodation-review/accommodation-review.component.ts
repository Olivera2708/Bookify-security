import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommentDTO } from '../model/comment.model.dto';
import { DatePipe } from '@angular/common';
import { AuthenticationService } from '../../authentication/authentication.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AccountService } from '../../account/account.service';
import { ReviewService } from '../review.service';

@Component({
  selector: 'app-accommodation-review',
  templateUrl: './accommodation-review.component.html',
  styleUrl: './accommodation-review.component.css',
  providers: [DatePipe]
})
export class AccommodationReviewComponent implements OnInit {
  @Input() comment: CommentDTO;
  @Input() ownerId: number | undefined;
  @Output() emit = new EventEmitter<boolean>();
  guestId: number;
  accommodationId: number;
  ownerImage: string | ArrayBuffer | null = null;

  constructor(public datepipe: DatePipe, private authenticationService: AuthenticationService, private route: ActivatedRoute,
    private reviewService: ReviewService, private accountService: AccountService, private _snackBar: MatSnackBar) {
    this.guestId = authenticationService.getUserId();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.accommodationId = +params['accommodationId'];
      if (this.comment.imageId != 0)
        this.getOwnerPhoto(this.comment.imageId);
      else
        this.ownerImage = "../../assets/images/user.jpg";
    });
  }

  getOwnerPhoto(id: number) {
    this.accountService.getAccountImage(id).subscribe({
      next: (data: Blob): void => {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.ownerImage = reader.result;
        }
        reader.readAsDataURL(data);
      },
      error: err => {
        console.error(err);
      }
    });
  }

  formatDate(date: Date): string | null {
    return this.datepipe.transform(date, 'dd.MM.yyyy.');
  }

  deleteComment(id: number) {
    this.reviewService.deleteAccommodationReview(this.accommodationId, id).subscribe({
      next: (_) => {
        this.emit.emit(true);
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  onClick(): void {
    this.reviewService.reportReview(this.comment.id).subscribe({
      next: (id: number) => {
        this.openSnackBar("Comment reported", "cancel");
      },
      error: (_) => {
        
      }
    })
  }
}
