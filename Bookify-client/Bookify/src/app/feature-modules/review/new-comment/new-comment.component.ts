import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ReviewService } from '../review.service';
import { NewCommentDTO } from '../model/new-comment.model.dto';
import { AuthenticationService } from '../../authentication/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrl: './new-comment.component.css'
})
export class NewCommentComponent implements OnInit {
  @Output() emit = new EventEmitter<boolean>();
  
  percent: number = 0;
  form: FormGroup;
  submitted: boolean;

  ownerId: number;
  
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private reviewService: ReviewService,
     private authenticationService: AuthenticationService, private _snackBar: MatSnackBar) {
    this.form = this.fb.group({
      comment: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['userId'];
    });
  }

  onRatingClick(event: MouseEvent) {
    const meter = event.target as HTMLMeterElement;
    this.percent = event.offsetX / meter.offsetWidth;
    this.percent *= 5;
    this.percent = Math.ceil(this.percent);
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.valid && this.percent > 0) {
      const comment: NewCommentDTO = {
        comment: this.form.get('comment')?.value,
        rate: this.percent,
        guestId: this.authenticationService.getUserId()
      } 
      this.reviewService.add(this.ownerId, comment).subscribe({
        next: () => {
          this.emit.emit(true);
          this.openSnackBar("Your comment has been sent to admin for approval", "close");
          this.form.get('comment')?.reset();
          this.percent = 0;
          this.submitted = false;
        },
        error: () => {
          this.openSnackBar("You need to have a reservation to be able to comment on the owner", "cancel");
        }
      })
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

}
