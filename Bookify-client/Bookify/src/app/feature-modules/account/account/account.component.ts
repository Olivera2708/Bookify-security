import {Component, OnInit} from '@angular/core';
import {Account} from "../model/account";
import {AccountService} from "../account.service";
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit {

  account: Account = {}
  role: string;

  constructor(private accountService: AccountService, private authenticationService: AuthenticationService) {
    this.role = authenticationService.getRole();
  }

  ngOnInit(): void {

  }

}
