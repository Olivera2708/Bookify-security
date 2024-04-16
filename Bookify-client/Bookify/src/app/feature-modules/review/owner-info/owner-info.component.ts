import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { UserDTO } from '../model/user.model.dto';
import { AccountService } from '../../account/account.service';

@Component({
  selector: 'app-owner-info',
  templateUrl: './owner-info.component.html',
  styleUrl: './owner-info.component.css'
})
export class OwnerInfoComponent implements OnInit {
  ownerId: number;
  ownerFirstName: string;
  ownerLastName: string;
  ownerPhone: string;

  ownerImage: string | ArrayBuffer | null = null;

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute, private accountService: AccountService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['userId'];
    });
    if (!Number.isNaN(this.ownerId)) {
      this.reviewServise.getUserDTO(this.ownerId).subscribe({
        next: (owner: UserDTO) => {
          this.ownerFirstName = owner.firstName;
          this.ownerLastName = owner.lastName;
          this.ownerPhone = owner.phone;
          if (owner.imageId != null)
            this.getOwnerPhoto(owner.imageId);
          else
            this.ownerImage = "../../assets/images/user.jpg";
        }
      });
    }
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
}
