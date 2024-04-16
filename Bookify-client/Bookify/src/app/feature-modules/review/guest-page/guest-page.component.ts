import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { UserDTO } from '../model/user.model.dto';

@Component({
  selector: 'app-guest-page',
  templateUrl: './guest-page.component.html',
  styleUrl: './guest-page.component.css'
})
export class GuestPageComponent implements OnInit {
  role: string;
  ownerId: number;
  type: string;

  constructor(private authenticationService: AuthenticationService, private reviewService: ReviewService, private route: ActivatedRoute) {
    this.role = authenticationService.getRole();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['userId'];
    });
    this.reviewService.getUserDTO(this.ownerId).subscribe({
      next: (owner: UserDTO) => {
        console.log(owner);
        this.type = owner.type;
      }
    })
  }
}
