import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { UserDTO } from '../model/user.model.dto';
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-owner-page',
  templateUrl: './owner-page.component.html',
  styleUrl: './owner-page.component.css'
})
export class OwnerPageComponent {
  load: boolean = false;
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
        this.type = owner.type;
      }
    })
  }

  handleEmit(data: boolean) {
    this.load = data;
  }

}
