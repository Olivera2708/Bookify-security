import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { ActivatedRoute } from '@angular/router';
import { Message } from "../model/message.dto.model";

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {
  uuid: string;
  message: string;
  here: string = "here.";

  constructor(private authenticationService: AuthenticationService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.uuid = params['uuid'] || "";
    });
    this.message = "To acctivate account click "
  }

  activateAccount(): void {
    this.here = "";
    const uuid: Message = {
      token: this.uuid
    }
    this.authenticationService.activateAccount(uuid).subscribe({
      next: (message) => {
        this.message = message.token;
      },
      error: (_) => {
        this.message = "Registration failed, please try again.";
      }
    });
  }
}
