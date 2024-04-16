import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ReviewService } from '../review.service';
import { AuthenticationService } from '../../authentication/authentication.service';
import { ReportedUserDTO } from '../model/reported-user.model.dto';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-report-guest',
  templateUrl: './report-guest.component.html',
  styleUrl: './report-guest.component.css'
})
export class ReportGuestComponent implements OnInit {
  form: FormGroup;
  submitted: boolean;
  reportedUserId: number;
  reportingUserId: number;

  constructor(private fb: FormBuilder, private route: ActivatedRoute, private authenticationService: AuthenticationService,
    private reviewService: ReviewService, private _snackBar: MatSnackBar) {
    this.form = this.fb.group({
      comment: ['', Validators.required],
    });
    this.reportingUserId = authenticationService.getUserId();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.reportedUserId = +params['userId'];
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.valid) {
      const reportedUser: ReportedUserDTO = {
        reason: this.form.get('comment')?.value,
        reportedUser: this.reportedUserId,
        createdBy: this.reportingUserId
      }

      this.reviewService.reportUser(reportedUser).subscribe({
        next: (id: number) => {
          this.openSnackBar("Successfully reported user", "cancel");
          this.form.get('comment')?.reset();
          this.submitted = false;
        },
        error: (_) => {
          this.openSnackBar("Cannot report user", "cancel");
        }
      });
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }
}
