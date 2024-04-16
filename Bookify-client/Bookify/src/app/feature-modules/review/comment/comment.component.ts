import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommentDTO } from '../model/comment.model.dto';
import { DatePipe } from '@angular/common';
import { AuthenticationService } from '../../authentication/authentication.service';
import { ActivatedRoute } from '@angular/router';
import { ReviewService } from '../review.service';
import { AccountService } from '../../account/account.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.css',
  providers: [DatePipe]
})
export class CommentComponent implements OnInit {
  @Input() comment: CommentDTO;
  @Input() owner: number = 0;
  @Output() emit = new EventEmitter<boolean>();
  guestId: number;
  ownerId: number;
  ownerImage: string | ArrayBuffer | null = null;

  constructor(public datepipe: DatePipe, private authenticationService: AuthenticationService, private route: ActivatedRoute,
    private reviewService: ReviewService, private accountService: AccountService, private _snackBar: MatSnackBar) {
    this.guestId = authenticationService.getUserId();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['userId'];
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
    this.reviewService.delete(this.ownerId, id).subscribe({
      next: (_) => {
        this.emit.emit(true);
      }
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

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }
}
