import {Component, OnInit} from '@angular/core';
import {AdministrationService} from "../administration.service";
import {User} from "../model/user";
import {ReportedUser} from "../model/reported.user";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {

    allUsers: User[] = []
    allReports: ReportedUser[] = []

    constructor(private adminService: AdministrationService) {
    }

    ngOnInit(): void {
        this.adminService.getAllUsers().subscribe({
            next: (users: User[]) => {
                this.allUsers = users;
            },
            error: err => {
            }
        });

        this.adminService.getAllReports().subscribe({
            next: (reports: ReportedUser[]) => {
                this.allReports = reports;
            },
            error: err => {
            }
        })

    }

}
